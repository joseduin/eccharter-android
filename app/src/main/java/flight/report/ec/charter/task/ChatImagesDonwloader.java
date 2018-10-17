package flight.report.ec.charter.task;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import flight.report.ec.charter.bd.BdContructor;
import flight.report.ec.charter.interfaz.IChat;
import flight.report.ec.charter.modelo.components.Chat;
import flight.report.ec.charter.utils.Hora;
import flight.report.ec.charter.utils.Image;
import flight.report.ec.charter.utils.StringUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChatImagesDonwloader {
    private Image image;
    private BdContructor bd;

    private IChat iChat;
    private ArrayList<Chat> chats = new ArrayList<>();
    private ContentResolver contentResolver;

    public ChatImagesDonwloader(Context context, ArrayList<Chat> chats , IChat iChat, ContentResolver contentResolver) {
        this.image = new Image(context);
        this.bd = new BdContructor(context);
        this.iChat = iChat;
        this.contentResolver = contentResolver;
        this.chats = chats;
    }

    public void init() {
        new ImageByteInit().execute();
    }

    private void updateList(final Chat chat, final int i) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                bd.chat.chatUpdate(chat);
                chats.get(i).clone(chat);

                iChat.updateList(chats);
            }
        });
    }

    private class ImageByteInit extends AsyncTask<Void, Void, String> {

        public ImageByteInit() { }

        @Override
        protected void onPreExecute() { }

        @Override
        protected String doInBackground(Void... voids) {
            for (int i=0; i < chats.size(); i++) {
                Chat chat = chats.get(i);
                if (chat.getType()==1) {
                    if (chat.getSrcSave()==null) {
                        searchSource(chat, i);
                    }
                }
            }

            return "succeess";
        }

        private void searchSource(final Chat chat, final int i) {
            OkHttpClient client = new OkHttpClient();
            final Request request = new Request.Builder()
                    .url(chat.getSrc())
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    InputStream input = response.body().byteStream(); // Read the data from the stream

                    if (chat.getType()==1) {
                        Bitmap bitmap = BitmapFactory.decodeStream(input);
                        String img = image.saveImage(bitmap, Hora.time(), contentResolver);

                        chat.setSrcSave(img);
                    }

                    updateList(chat, i);
                }

                @Override
                public void onFailure(Call call, IOException e) {
                    StringUtil.console("ERROR", e.toString());
                }
            });
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}

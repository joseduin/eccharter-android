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
import flight.report.ec.charter.interfaz.IDocument;
import flight.report.ec.charter.modelo.components.Document;
import flight.report.ec.charter.utils.Image;
import flight.report.ec.charter.utils.StringUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ImageByte {
    private Image image;
    private BdContructor bd;

    private IDocument iDocument;
    private ArrayList<Document> documents = new ArrayList<>();
    private ContentResolver contentResolver;

    public ImageByte(Context context, IDocument iDocument, ContentResolver contentResolver) {
        this.image = new Image(context);
        this.bd = new BdContructor(context);
        this.iDocument = iDocument;
        this.contentResolver = contentResolver;
    }

    public void init() {
        new ImageByteInit().execute();
    }

    private void updateList(final Document document, final int i) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                bd.document.documenttUpdate(document);
                documents.get(i).clone(document);

                iDocument.updateList(documents);
            }
        });
    }

    private class ImageByteInit extends AsyncTask<Void, Void, String> {

        public ImageByteInit() { }

        @Override
        protected void onPreExecute() { }

        @Override
        protected String doInBackground(Void... voids) {
            documents = bd.document.getDocuments();
            for (int i=0; i < documents.size(); i++) {
                Document document = documents.get(i);
                if (document.getIs_img()) {
                    if (document.getSrcImgSave()==null) {
                        searchSource(document, i);
                    }
                } else {
                    if (document.getSrcSave()==null) {
                        searchSource(document, i);
                    }
                }
            }

            return "succeess";
        }

        private void searchSource(final Document document, final int i) {
            StringUtil.console("DOC_SRC",document.getSrc());
            OkHttpClient client = new OkHttpClient();
            final Request request = new Request.Builder()
                    .url(document.getSrc())
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    InputStream input = response.body().byteStream(); // Read the data from the stream

                    if (document.getIs_img()) {
                        Bitmap bitmap = BitmapFactory.decodeStream(input);
                        String img = image.saveImage(bitmap, document.getTitle(), contentResolver);

                        document.setSrcImgSave(img);
                    } else {
                        File f = image.pdfFile(document.getTitle());
                        image.createPDF(input, f);

                        String path = image.filePath(f);
                        document.setSrcSave(path);
                    }

                    updateList(document, i);
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

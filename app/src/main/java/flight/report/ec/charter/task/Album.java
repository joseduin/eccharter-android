package flight.report.ec.charter.task;

import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import flight.report.ec.charter.modelo.Image;
import flight.report.ec.charter.utils.Function;
import flight.report.ec.charter.utils.MapComparator;

public class Album {
    private Context context;
    private String album_name;
    private CallbackInterface mCallback;

    public Album(Context context, String album_name) {
        this.context = context;
        this.album_name = album_name;
        this.mCallback = (CallbackInterface) context;
    }

    public void load() {
        new Load().execute();
    }

    public interface CallbackInterface {
        void onHandleTask(ArrayList<Image> albumList);
    }

    private class Load extends AsyncTask<String, Void, String> {
        private ArrayList<Image> albumList = new ArrayList<Image>();

        public Load() { }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            albumList.clear();
        }

        protected String doInBackground(String... args) {
            Uri uriExternal = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            Uri uriInternal = android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI;

            String[] projection = { MediaStore.MediaColumns.DATA,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.MediaColumns.DATE_MODIFIED };

            Cursor cursorExternal = context.getContentResolver().query(uriExternal, projection, "bucket_display_name = \""+album_name+"\"", null, null);
            Cursor cursorInternal = context.getContentResolver().query(uriInternal, projection, "bucket_display_name = \""+album_name+"\"", null, null);
            Cursor cursor = new MergeCursor(new Cursor[]{cursorExternal,cursorInternal});
            while (cursor.moveToNext()) {
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
                String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                String timestamp = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_MODIFIED));
                if (timestamp==null)
                    timestamp = String.valueOf(new Date().getTime()/1000L);
                albumList.add(new Image(Function.mappingInbox(album, path, timestamp, Function.converToTime(timestamp), null)));
            }
            cursor.close();
            Collections.sort(albumList, new MapComparator(Function.KEY_TIMESTAMP, "dsc")); // Arranging photo album by timestamp decending
            return "";
        }

        @Override
        protected void onPostExecute(String xml) {
            mCallback.onHandleTask(albumList);
        }
    }
}

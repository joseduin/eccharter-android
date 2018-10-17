package flight.report.ec.charter.task;

import android.app.ProgressDialog;
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

import flight.report.ec.charter.R;
import flight.report.ec.charter.modelo.Image;
import flight.report.ec.charter.utils.Function;
import flight.report.ec.charter.utils.MapComparator;
import flight.report.ec.charter.utils.Mensaje;

public class Gallery {
    private ProgressDialog progressDialog;
    private Context context;
    private CallbackInterface mCallback;

    public Gallery(Context context) {
        this.context = context;
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

        public Load() {
            progressDialog = Mensaje.progressConsultar(context, context.getResources().getString(R.string.loading));
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            albumList.clear();
            progressDialog.show();
        }

        protected String doInBackground(String... args) {
            Uri uriExternal = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            Uri uriInternal = android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI;

            String[] projection = { MediaStore.MediaColumns.DATA,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.MediaColumns.DATE_MODIFIED };
            String selection = context.getResources().getString(R.string.select_from_gallery);
            Cursor cursorExternal = context.getContentResolver().query(uriExternal, projection, selection,
                    null, null);
            Cursor cursorInternal = context.getContentResolver().query(uriInternal, projection, selection,
                    null, null);
            Cursor cursor = new MergeCursor(new Cursor[]{cursorExternal,cursorInternal});

            while (cursor.moveToNext()) {
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
                String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                String timestamp = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_MODIFIED));
                String countPhoto = Function.getCount(context, album);
                if (timestamp==null)
                    timestamp = String.valueOf(new Date().getTime()/1000L);
                albumList.add(new Image(Function.mappingInbox(album, path, timestamp, Function.converToTime(timestamp), countPhoto)) );
            }
            Collections.sort(albumList, new MapComparator(Function.KEY_TIMESTAMP, "dsc")); // Arranging photo album by timestamp decending
            return "";
        }

        @Override
        protected void onPostExecute(String xml) {
            mCallback.onHandleTask(albumList);
            progressDialog.dismiss();
        }
    }
}

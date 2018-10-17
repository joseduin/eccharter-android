package flight.report.ec.charter.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import flight.report.ec.charter.R;

/**
 * Created by Jose on 23/1/2018.
 */

public final class Convert {

    /**
     * pasamos de content/sdcard/imagens/6576 a 6578
     */
    public static String photoFormat(String s) {
        if (s == null) {
            return "";
        }
        return s.substring( s.lastIndexOf("/") + 1 );
    }

    public static Uri pdfPath(String path) {
        Uri uri = MediaStore.Files.getContentUri(path);
        if (uri.toString().contains("/file"))
            path = uri.toString().substring( 0, uri.toString().lastIndexOf("/file") );
        return Uri.parse(path);
    }

    public static long getBitmapByteCount(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1)
            return bitmap.getRowBytes() * bitmap.getHeight();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            return bitmap.getByteCount();
        return bitmap.getAllocationByteCount();
    }

    public static String pathImageToByte(String imagen_path, Context context) {
        try {
            Uri imageUri = Uri.parse(imagen_path);
            InputStream imageStream = context.getContentResolver().openInputStream(imageUri);
            Bitmap photoBm = BitmapFactory.decodeStream(imageStream);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            photoBm.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] imgByte = stream.toByteArray();
            return Base64.encodeToString(imgByte, Base64.DEFAULT);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SecurityException se) {
            Mensaje.mensajeLargo(context, context.getResources().getString(R.string.img_from_other_app));
            se.printStackTrace();
        }
        return null;
    }

    /**
     * Redimencionamos las imagenes
     */
    public static String saveScaledPhotoToFile(String imagen_path, Context context) {
        try {
            Uri imageUri = Uri.parse(imagen_path);
            InputStream imageStream = context.getContentResolver().openInputStream(imageUri);
            Bitmap photoBm = BitmapFactory.decodeStream(imageStream);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            // Aprox 0.7 mb
            if (getBitmapByteCount(photoBm) > 11985408) {
                photoBm.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                return MediaStore
                        .Images
                        .Media
                        .insertImage(
                                context
                                        .getContentResolver(), photoBm,"ec_" + Hora.time() + Hora.time(), null);
            } else {
                return imagen_path;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imagen_path;
    }

}

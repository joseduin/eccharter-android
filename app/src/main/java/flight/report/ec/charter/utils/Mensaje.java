package flight.report.ec.charter.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

import flight.report.ec.charter.GalleryActivity;
import flight.report.ec.charter.GalleryPreview;
import flight.report.ec.charter.R;

/**
 * Created by Jose on 13/11/2017.
 */

public final class Mensaje {

    public static final String ERROR_LOGIN = "PIN Incorrect";
    public static String ERROR_CONEXION = "Something happened in the connection .. Try again";
    public static String INTERNET_CONEXION = "Check your internet connexion";

    public static void mensajeCorto(Context context, String mensaje) {
        toast(context, mensaje, Snackbar.LENGTH_SHORT);
    }

    public static void mensajeLargo(Context context, String mensaje) {
        toast(context, mensaje, Snackbar.LENGTH_LONG);
    }

    /**
     * Creamos un mensaje Toas en pantalla
     **/
    private static void toast(Context context, String mensaje, int duracion) {
        Toast.makeText(context, mensaje, duracion).show();
    }

    public static ProgressDialog progressConsultar(Context context, String texto) {
        return progressView(context, texto);
    }

    public static ProgressDialog progressConsultar(Context context) {
        return progressConsultar(context, "Searching...");
    }

    public static ProgressDialog progressEnvio(Context context) {
        return progressConsultar(context, "Sending...");
    }

    /**
     *  Creamos un ProgresDialog en la pantalla
     **/
    public static ProgressDialog progressView(Context context, String message) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    /**
     * Ventana emergente para el preview de la imagen
     */
    public static void imagePreview(Context context, Uri path) {
        Intent intent = new Intent(context, GalleryPreview.class);
        intent.putExtra("path", path.toString());
        intent.putExtra("file", false);
        context.startActivity(intent);
    }

}

package flight.report.ec.charter.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import flight.report.ec.charter.BuildConfig;

public class Image {
    private Context context;
    private final static File outDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString());

    public Image(Context context) {
        this.context = context;
    }

    /**
     * Inicializamos el Proveedor de imagenes
     **/
    public File initProvider() {
        return createImageFile(context);
    }

    /**
     * Creamos el archivo donde se alojara la imagen
     * @param context Contexto del Activity
     * @return archivo
     **/
    private File createImageFile(Context context) {
        File image = null;
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",    /* suffix */
                    storageDir      /* directory */
            );
        } catch (IOException ignored) { }
        return image;
    }

    /**
     * Obtenemos un archivo mediante la URL interna
     * @param path URL del archivo
     * @return URL donde esta alojado el archivo
     **/
    public Uri getUriFromPath(String path) {
        File f = new File(path);
        return fileUri(f);
    }

    /**
     * Obtenemos la URL interna mediante un archivo
     * @param file: Archivo
     * @return: URL interna, hecha mediante el Proveedor de imagenes
     **/
    public Uri fileUri(File file) {
        return FileProvider.getUriForFile(context,BuildConfig.APPLICATION_ID + ".provider", file);
    }

    /**
     * Obtenemos la ruta de un archivo
     * @param file Archivo
     * @return ruta del archivo
     **/
    public String filePath(File file) {
        return file.getAbsolutePath();
    }

    /**
     * Guardamos la imagen que viene de la Camara mediante el Proveedor de imagenes
     * @param imageUri URL interna donde se guardara la imagen
     * @param contentResolver
     **/
    public void savePic(Uri imageUri, ContentResolver contentResolver) {
        ContentResolver cr = contentResolver;
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(cr, imageUri);
            MediaStore.Images.Media.insertImage(cr, bitmap, "ec_" + Hora.time(), null);
        } catch (IOException e) {
            Log.d("ERROR", e.getMessage());
        }
    }

    /**
     * Guardamos la imagen que viene desde una URL Externa
     * @param bmp Imagen
     * @param title descripcion de la imagen
     * @param contentResolver
     **/
    public String saveImage(Bitmap bmp, String title, ContentResolver contentResolver) {
        return MediaStore.Images.Media.insertImage(contentResolver, bmp, title, null);
    }

    /**
     * Inicializamos un archivo para guardar un archivo PDF
     * @param title descripcion del archivo
     * @return Archivo
     **/
    public File pdfFile(String title) {
        if (!outDir.exists()) {
            outDir.mkdir();
        }
        File file = new File(outDir, title + ".pdf");
        try {
            file.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return file;
    }

    /**
     * Guardamos el archivo PDF en la carpera de 'Descargas'
     * @param in Datos del PDF
     * @param directory Directorio donde se guardara el PDF
     **/
    public void createPDF(InputStream in, File directory) {
        try {
            FileOutputStream f = new FileOutputStream(directory);

            byte[] buffer = new byte[1024];
            int len1;
            while ((len1 = in.read(buffer)) > 0) {
                f.write(buffer, 0, len1);
            }
            f.flush();
            f.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

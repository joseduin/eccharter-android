package flight.report.ec.charter;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;

import flight.report.ec.charter.utils.BaseActivity;
import flight.report.ec.charter.utils.Image;
import flight.report.ec.charter.utils.StringUtil;

public class GalleryPreview extends BaseActivity {
    private ImageView GalleryPreviewImg;
    private Toolbar toolbar;
    private MenuItem done;

    private boolean originPath;
    private String path;
    private Boolean file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_preview);

        loadBundleExtras();
        enlazarVista();
        setToolbar(toolbar, "", true);
        loadImage();
    }

    private void loadImage() {
        loadImg().into(GalleryPreviewImg);
    }

    private RequestCreator loadImg() {
        if (file)
            return Picasso.get().load(new File(path));
        else
            return Picasso.get().load(path);
    }

    private void loadBundleExtras() {
        Intent intent = getIntent();
        originPath = intent.getBooleanExtra("originPath", false);
        path = intent.getStringExtra("path");
        file = intent.getBooleanExtra("file", false);
    }

    private void enlazarVista() {
        GalleryPreviewImg = findViewById(R.id.GalleryPreviewImg);
        toolbar = findViewById(R.id.toolbar);
    }

    public Uri getPath() {
        return originPath ? Uri.parse(path) : FileProvider.getUriForFile(GalleryPreview.this,
                BuildConfig.APPLICATION_ID + ".provider",
                new File(path));
    }

    private void goBack(String val) {
        Intent i = new Intent();
        i.putExtra("SELECT_IMAGE", val);
        setResult(Activity.RESULT_OK, i);

        finish();
    }

    /**
     * Creamos un menu en el toolbar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_done, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        done = menu.findItem(R.id.done);
        if (!file)
            done.setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                goBack(getPath().toString());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

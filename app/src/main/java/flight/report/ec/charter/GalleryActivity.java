package flight.report.ec.charter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import flight.report.ec.charter.adaptador.AlbumAdapter;
import flight.report.ec.charter.modelo.Image;
import flight.report.ec.charter.task.Gallery;
import flight.report.ec.charter.utils.BaseActivity;
import flight.report.ec.charter.utils.Function;

public class GalleryActivity extends BaseActivity implements Gallery.CallbackInterface, AdapterView.OnItemClickListener {
    private GridView galleryGridView;
    private Toolbar toolbar;
    private ArrayList<Image> albumList = new ArrayList<>();
    public final static int PICK_PHOTO_CODE = 1046;
    private boolean originPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        loadBundleExtras();
        enlazarVista();
        confView();
        setToolbar(toolbar, getResources().getString(R.string.gallery), true);

        if (savedInstanceState != null) {
            albumList = savedInstanceState.getParcelableArrayList("albumList");
            loadAlbum();
        } else {
            Gallery gallery = new Gallery(GalleryActivity.this);
            gallery.load();
        }
    }

    private void loadBundleExtras() {
        Intent intent = getIntent();
        originPath = intent.getBooleanExtra("originPath", false);
    }

    private void confView() {
        int column = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 4;
        galleryGridView.setNumColumns(column);
    }

    private void enlazarVista() {
        galleryGridView = findViewById(R.id.galleryGridView);
        toolbar         = findViewById(R.id.toolbar);
    }

    @Override
    public void onHandleTask(ArrayList<Image> albumList) {
        this.albumList = albumList;
        loadAlbum();
    }

    private void loadAlbum() {
        AlbumAdapter adapter = new AlbumAdapter(GalleryActivity.this, albumList);
        galleryGridView.setAdapter(adapter);
        galleryGridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(GalleryActivity.this, AlbumActivity.class);
        intent.putExtra("name", albumList.get(i).getAlbum().get(Function.KEY_ALBUM));
        intent.putExtra("originPath", originPath);
        startActivityForResult(intent, PICK_PHOTO_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == PICK_PHOTO_CODE) {
                if (resultCode == Activity.RESULT_OK) {
                    goBack(data.getStringExtra("SELECT_IMAGE"));
                }
            }
        }
    }

    private void goBack(String val) {
        Intent i = new Intent();
        i.putExtra("SELECT_IMAGE", val);
        setResult(Activity.RESULT_OK, i);

        finish();
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);

        if (!albumList.isEmpty())
            currentState.putParcelableArrayList("albumList", albumList);
    }

}

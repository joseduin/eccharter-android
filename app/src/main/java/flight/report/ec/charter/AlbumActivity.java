package flight.report.ec.charter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import flight.report.ec.charter.adaptador.SingleAlbumAdapter;
import flight.report.ec.charter.modelo.Image;
import flight.report.ec.charter.task.Album;
import flight.report.ec.charter.utils.BaseActivity;
import flight.report.ec.charter.utils.Function;
import flight.report.ec.charter.utils.StringUtil;

public class AlbumActivity extends BaseActivity implements Album.CallbackInterface {
    private GridView galleryGridView;
    private Toolbar toolbar;
    private String album_name;
    private ArrayList<Image> albumList = new ArrayList<Image>();
    public final static int PICK_PHOTO_CODE = 1046;
    private boolean originPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        loadBundleExtras();
        enlazarVista();
        confView();
        setToolbar(toolbar, album_name, true);

        if (savedInstanceState != null) {
            albumList = savedInstanceState.getParcelableArrayList("albumList");
            loadAlbum();
        } else {
            Album gallery = new Album(AlbumActivity.this, album_name);
            gallery.load();
        }
    }

    private void loadBundleExtras() {
        Intent intent = getIntent();
        album_name = intent.getStringExtra("name");
        originPath = intent.getBooleanExtra("originPath", false);
    }

    private void confView() {
        int column = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 3 : 6;
        galleryGridView.setNumColumns(column);
    }

    private void enlazarVista() {
        galleryGridView = findViewById(R.id.galleryGridView);
        toolbar         = findViewById(R.id.toolbar);
    }

    @Override
    public void onHandleTask(final ArrayList<Image> albumList) {
        this.albumList = albumList;
        loadAlbum();
    }

    private void loadAlbum() {
        SingleAlbumAdapter adapter = new SingleAlbumAdapter(AlbumActivity.this, this.albumList);
        galleryGridView.setAdapter(adapter);
        galleryGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Intent intent = new Intent(AlbumActivity.this, GalleryPreview.class);
                intent.putExtra("path", albumList.get(position).getAlbum().get(Function.KEY_PATH));
                intent.putExtra("file", true);
                intent.putExtra("originPath", originPath);
                startActivityForResult(intent, PICK_PHOTO_CODE);
            }
        });
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

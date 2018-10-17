package flight.report.ec.charter.aircrafts.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.mindorks.paracamera.Camera;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;

import flight.report.ec.charter.R;
import flight.report.ec.charter.adaptador.AdapterMensajes;
import flight.report.ec.charter.bd.BdContructor;
import flight.report.ec.charter.interfaz.IChat;
import flight.report.ec.charter.modelo.components.Aircraft;
import flight.report.ec.charter.modelo.event.EventChat;
import flight.report.ec.charter.preferences.Preferences;
import flight.report.ec.charter.presenter.ChatPresenter;
import flight.report.ec.charter.presenter.IChatPresenter;
import flight.report.ec.charter.provider.AmazonS3;
import flight.report.ec.charter.utils.BaseFragment;
import flight.report.ec.charter.utils.DialogPhoto;
import flight.report.ec.charter.utils.Hora;
import flight.report.ec.charter.utils.Image;
import flight.report.ec.charter.utils.StringUtil;

public class Chat extends BaseFragment implements IChat, DialogPhoto.CallbackInterfaceImagen,
        DialogPhoto.CallbackInterfaceDialog, AmazonS3.CallbackInterface {
    private RecyclerView rvMensajes;
    private EditText txtMensajes;
    private Button btnEnviar;
    private AdapterMensajes adapter;
    private ImageButton btnEnviarFoto;

    private IChatPresenter iChatPresenter;
    private Aircraft aircraftGlobal;
    private BdContructor bd;
    private Preferences preferences;
    public static final String AIRCRAFT_ID = "raircraft_id";
    private String SAVED_INSTANCE_RECICLER_LIST = "saved_instance_recicler_list";

    private Image image;
    private Uri photoUri = null;
    private String mCurrentPhotoPath;
    private Bundle INSTANCESTATE = null;
    private ArrayList<flight.report.ec.charter.modelo.components.Chat> mensajesInstance = new ArrayList<>();

    private AmazonS3 amazonS3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat, container, false);
        eventListener();
        bd = new BdContructor(Chat.this.getContext());
        image = new Image(Chat.this.getContext());
        preferences = new Preferences(Chat.this.getContext());
        amazonS3 = new AmazonS3(Chat.this.getContext());

        INSTANCESTATE = savedInstanceState;
        enlazarVista(v);
        savedInstance(savedInstanceState);

        CanSavedInstance("recicler");
        iChatPresenter = new ChatPresenter(this, Chat.this.getContext(), aircraftGlobal, Chat.this.getContext().getContentResolver(), mensajesInstance);

        return v;
    }

    private void eventListener() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    /**
     * Cuando llegue una notificacion en el broadcast de un nuevo mensaje
     * y el usuario este con el chat abierto, se debe recargar el recicler para mostrar
     * dicho mensaje que acaba de llegar del servidor
     * o
     * la notificacion en el broadcast dice que hay nuevos mensajes en el chat
     * se busca los nuevos mensajes en el servidor para mostrarlos.
     **/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventChat eventChat) {
        StringUtil.console(eventChat.getMessage(), "onMessageEvent " + eventChat.getMaster_type());
    }

    public void onEvent(EventChat eventChat) {
        StringUtil.console(eventChat.getMessage(), eventChat.getMaster_type());
    }
    /**
     * fin evento
     **/

    private void savedInstance(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            Long id = savedInstanceState.getLong(AIRCRAFT_ID);
            aircraftGlobal = bd.aircraft.getAircraftById(id);
        }
    }

    public void CanSavedInstance(String value) {
        Bundle instance = INSTANCESTATE;
        if (instance!=null) {
            if (StringUtil.equals(value, "recicler")) {
                mensajesInstance = instance.getParcelableArrayList(SAVED_INSTANCE_RECICLER_LIST);
            }
        } else {
            mensajesInstance = new ArrayList<>();
        }
    }

    private void enlazarVista(View v) {
        rvMensajes      = v.findViewById(R.id.rvMensajes);
        txtMensajes     = v.findViewById(R.id.txtMensajes);
        btnEnviar       = v.findViewById(R.id.btnEnviar);
        btnEnviarFoto   = v.findViewById(R.id.btnEnviarFoto);

        btnEnviar.setOnClickListener(this);
        btnEnviarFoto.setOnClickListener(this);
    }

    public void setAircraftGlobal(Aircraft aircraft) {
        this.aircraftGlobal = aircraft;
    }

    @Override
    public void generateLayout() {
        LinearLayoutManager llm = new LinearLayoutManager(Chat.this.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvMensajes.setLayoutManager(llm);
    }

    @Override
    public AdapterMensajes initAdapter(ArrayList<flight.report.ec.charter.modelo.components.Chat> list) {
        adapter = new AdapterMensajes(Chat.this.getContext(), list);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollBar();
            }
        });

        return adapter;
    }

    @Override
    public void setAdapter(AdapterMensajes adapter) {
        rvMensajes.setAdapter(adapter);
    }

    @Override
    public void updateList(ArrayList<flight.report.ec.charter.modelo.components.Chat> list) {
        adapter.updateList(list);
    }

    private void setScrollBar() {
        rvMensajes.scrollToPosition(adapter.getItemCount()-1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Camera.REQUEST_TAKE_PHOTO) {
            if(resultCode == Activity.RESULT_OK) {
                if (data == null) {
                    // Android Version > Nuggats
                    handleBigCameraPhoto();
                } else {
                    // Android Version < Nuggats
                    photoUri = data.getData();
                }
                uploadWithTransferUtility(photoUri.toString());
            }
        } else if (requestCode == DialogPhoto.PICK_PHOTO_CODE) {
            if (data != null) {
                if(resultCode == Activity.RESULT_OK) {
                    if (data.getStringExtra("SELECT_IMAGE")!=null) {
                        photoUri = Uri.parse(data.getStringExtra("SELECT_IMAGE"));
                        uploadWithTransferUtility(photoUri.toString());
                    }
                }
            }
        }
        photoUri = null;
    }

    private void handleBigCameraPhoto() {
        if (mCurrentPhotoPath != null) {
            Uri imageUri = image.getUriFromPath(mCurrentPhotoPath);

            image.savePic(imageUri, Chat.this.getContext().getContentResolver());
            photoUri = imageUri;
            mCurrentPhotoPath = null;
        }
    }

    private void uploadWithTransferUtility(String path) {
        String nameImg = preferences.getUsername() + "_" + aircraftGlobal.getTail() + "_" + Hora.time();
        amazonS3.uploadImg(path, nameImg);
    }

    private void sendMessage(String path, String nameImg) {
        flight.report.ec.charter.modelo.components.Chat chat = new flight.report.ec.charter.modelo.components.Chat();
        chat.setMessage("");
        chat.setType(1);
        chat.setAircraftId(aircraftGlobal.getId_web());
        chat.setUserName(preferences.getUsername());
        chat.setTime(new Date().getTime() / 1000L);
        chat.setSrc(path);
        chat.setSrcSave(nameImg);
        chat.setSend(false);
        chat.setId( bd.chat.chatInsert(chat) );
        iChatPresenter = new ChatPresenter(this, Chat.this.getContext(), aircraftGlobal, chat);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEnviar:
                if (!StringUtil.isEmpty(txtMensajes.getText().toString())) {
                    flight.report.ec.charter.modelo.components.Chat chat = new flight.report.ec.charter.modelo.components.Chat();
                    chat.setMessage(txtMensajes.getText().toString());
                    chat.setType(0);
                    chat.setAircraftId(aircraftGlobal.getId_web());
                    chat.setUserName(preferences.getUsername());
                    chat.setTime(new Date().getTime() / 1000L);
                    chat.setSend(false);
                    chat.setId( bd.chat.chatInsert(chat) );
                    iChatPresenter = new ChatPresenter(this, Chat.this.getContext(), aircraftGlobal, chat);

                    txtMensajes.setText("");
                }
                break;
            case R.id.btnEnviarFoto:
                DialogPhoto.init(Chat.this, photoUri, true);
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putParcelableArrayList(SAVED_INSTANCE_RECICLER_LIST, adapter.getMensajes());

        if (aircraftGlobal != null)
            currentState.putLong(AIRCRAFT_ID, aircraftGlobal.getId_web());
    }

    @Override
    public void onHandleSelectionButton(boolean b) {
        if (b) {
            photoUri = null;
        }
    }

    @Override
    public void onHandleSelectionButton(String photopath) {
        mCurrentPhotoPath = photopath;
    }

    @Override
    public void onS3UploadCompleted(String filePath, String amazonURL) {
        sendMessage(filePath, amazonURL);
    }
}

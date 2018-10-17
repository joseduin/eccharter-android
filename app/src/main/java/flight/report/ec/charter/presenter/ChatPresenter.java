package flight.report.ec.charter.presenter;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.util.Log;

import com.amazonaws.services.s3.AmazonS3Client;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import flight.report.ec.charter.R;
import flight.report.ec.charter.bd.BdContructor;
import flight.report.ec.charter.interfaz.IChat;
import flight.report.ec.charter.modelo.components.Aircraft;
import flight.report.ec.charter.modelo.components.Chat;
import flight.report.ec.charter.modelo.request.ChatRequest;
import flight.report.ec.charter.preferences.Preferences;
import flight.report.ec.charter.restApi.EndPointsApi;
import flight.report.ec.charter.restApi.adapter.RestApiAdapter;
import flight.report.ec.charter.restApi.modelo.AircraftChatResponse;
import flight.report.ec.charter.restApi.modelo.MessageResponse;
import flight.report.ec.charter.task.ChatImagesDonwloader;
import flight.report.ec.charter.utils.Connection;
import flight.report.ec.charter.utils.Hora;
import flight.report.ec.charter.utils.Mensaje;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.s3.transferutility.*;
import com.amazonaws.mobile.client.AWSMobileClient;

public class ChatPresenter implements IChatPresenter {
    private IChat iChat;
    private Context context;
    private BdContructor bd;
    private Connection connection;
    private ArrayList<Chat> chats = new ArrayList<>();
    private Aircraft aircraftGlobal;
    private ContentResolver contentResolver;
    private Preferences preferences;

    private ProgressDialog progressDialog;

    public ChatPresenter(IChat iChat, Context context, Aircraft aircraftGlobal, ContentResolver contentResolver, ArrayList<Chat> local) {
        this.iChat = iChat;
        this.context = context;
        this.bd = new BdContructor(context);
        this.connection = new Connection(context);
        this.aircraftGlobal = aircraftGlobal;
        this.contentResolver = contentResolver;
        this.preferences = new Preferences(context);

        if (local.isEmpty()) {
            this.getChats();
        } else {
            this.chats = local;
            displayChats();
        }
    }

    private void searchBd() {
        this.chats = bd.chat.getChatByAircraft(aircraftGlobal.getId_web());
        this.displayChats();
    }

    private void searchServer(final boolean loading) {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gson = restApiAdapter.aircraftsChats();
        EndPointsApi endPointsApi = restApiAdapter.establecerConexionApi(gson);

        Call<AircraftChatResponse> call = endPointsApi.getChat(3, aircraftGlobal.getId_web());
        Log.d("RESPONSE", call.request().url().toString() + " ");
        call.enqueue(new Callback<AircraftChatResponse>() {
            @Override
            public void onResponse(Call<AircraftChatResponse> call, Response<AircraftChatResponse> response) {
                if (loading)
                    progressDialog.dismiss();
                if (response.code() == 200) {
                    updateList(response.body().getChats());
                }
            }

            @Override
            public void onFailure(Call<AircraftChatResponse> call, Throwable t) {
                if (loading)
                    progressDialog.dismiss();
                Mensaje.mensajeCorto(context, Mensaje.ERROR_CONEXION);
                Log.e("ERROR", t.toString() + " " + call.toString());
            }
        });
    }

    private void updateList(ArrayList<Chat> chats) {
        bd.chat.dataFromServer(chats, aircraftGlobal);
        this.chats = bd.chat.getChatByAircraft(aircraftGlobal.getId_web());

        if (!this.chats.isEmpty())
            (new ChatImagesDonwloader(context, chats, iChat, contentResolver)).init();

        listIsEmpty();
        this.iChat.updateList(this.chats);
    }

    private void listIsEmpty() {
        if (this.chats.isEmpty())
            Mensaje.mensajeCorto(context, context.getResources().getString(R.string.not_chat));
    }

    @Override
    public void getChats() {
        searchBd();
        if (connection.isOnline()) {
            boolean loading = false;//this.chats.isEmpty();
            if (loading) {
                progressDialog = Mensaje.progressConsultar(context);
                progressDialog.show();
            }
            searchServer(loading);
        } else {
            this.listIsEmpty();
        }
    }

    @Override
    public void displayChats() {
        this.iChat.setAdapter(this.iChat.initAdapter(this.chats));
        this.iChat.generateLayout();
    }

    public ChatPresenter(IChat iChat, Context context, Aircraft aircraftGlobal, Chat chat) {
        this.iChat = iChat;
        this.context = context;
        this.bd = new BdContructor(context);
        this.connection = new Connection(context);
        this.aircraftGlobal = aircraftGlobal;
        this.preferences = new Preferences(context);

        sendMessage(chat);
    }

    private void sendMessage(final Chat chat) {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gson = restApiAdapter.message();
        EndPointsApi endPointsApi = restApiAdapter.establecerConexionApi(gson);

        ChatRequest request = ChatRequest.cloneToChatAircraft(chat);

        Call<MessageResponse> call = endPointsApi.setChat(request);
        Log.d("RESPONSE", call.request().url().toString() + " ");
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.code() == 200) {
                    chat.setSend(true);
                    bd.chat.chatUpdate(chat);
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Mensaje.mensajeCorto(context, Mensaje.ERROR_CONEXION);
                Log.e("ERROR", t.toString() + " " + call.toString());
            }
        });
    }

}

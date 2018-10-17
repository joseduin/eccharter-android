package flight.report.ec.charter.presenter;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

import java.util.ArrayList;

import flight.report.ec.charter.R;
import flight.report.ec.charter.bd.BdContructor;
import flight.report.ec.charter.interfaz.IDocument;
import flight.report.ec.charter.modelo.components.Aircraft;
import flight.report.ec.charter.modelo.components.Document;
import flight.report.ec.charter.restApi.EndPointsApi;
import flight.report.ec.charter.restApi.adapter.RestApiAdapter;
import flight.report.ec.charter.restApi.modelo.AircraftDocumentResponse;
import flight.report.ec.charter.task.ImageByte;
import flight.report.ec.charter.utils.ArrayUtil;
import flight.report.ec.charter.utils.Connection;
import flight.report.ec.charter.utils.Image;
import flight.report.ec.charter.utils.Mensaje;
import flight.report.ec.charter.utils.StringUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocumentPresenter implements IDocumentPresenter {
    private IDocument iDocument;
    private Context context;
    private BdContructor bd;
    private Connection connection;
    private ArrayList<Document> documents = new ArrayList<>();
    private Aircraft aircraftGlobal;
    private ContentResolver contentResolver;

    private ProgressDialog progressDialog;

    public DocumentPresenter(IDocument iDocument, Context context, Aircraft aircraftGlobal, ContentResolver contentResolver, ArrayList<Document> local) {
        this.iDocument = iDocument;
        this.context = context;
        this.bd = new BdContructor(context);
        this.connection = new Connection(context);
        this.aircraftGlobal = aircraftGlobal;
        this.contentResolver = contentResolver;

        if (local.isEmpty()) {
            this.getDocuments();
        } else {
            this.documents = local;
            displayDocuments();
        }
    }

    private void searchBd() {
        this.documents = bd.document.getDocumentsByAircraft(aircraftGlobal.getId_web());
        this.displayDocuments();
    }

    private void searchServer(final boolean loading) {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gson = restApiAdapter.aircraftsDocuments();
        EndPointsApi endPointsApi = restApiAdapter.establecerConexionApi(gson);

        Call<AircraftDocumentResponse> call = endPointsApi.getAircraftDocuments(aircraftGlobal.getId_web());
        Log.d("RESPONSE", call.request().url().toString() + " ");
        call.enqueue(new Callback<AircraftDocumentResponse>() {
            @Override
            public void onResponse(Call<AircraftDocumentResponse> call, Response<AircraftDocumentResponse> response) {
                if (loading)
                    progressDialog.dismiss();
                if (response.code() == 200) {
                    updateList(response.body().getDocument());
                }
            }

            @Override
            public void onFailure(Call<AircraftDocumentResponse> call, Throwable t) {
                if (loading)
                    progressDialog.dismiss();
                Mensaje.mensajeCorto(context, Mensaje.ERROR_CONEXION);
                Log.e("ERROR", t.toString() + " " + call.toString());
            }
        });
    }

    private void updateList(ArrayList<Document> documents) {
        bd.document.dataFromServer(documents, aircraftGlobal);
        this.documents = bd.document.getDocumentsByAircraft(aircraftGlobal.getId_web());

        if (!this.documents.isEmpty())
            (new ImageByte(context, iDocument, contentResolver)).init();

        listIsEmpty();
        this.iDocument.updateList(this.documents);
    }

    private void listIsEmpty() {
        if (this.documents.isEmpty())
            Mensaje.mensajeCorto(context, context.getResources().getString(R.string.not_document));
    }

    @Override
    public void getDocuments() {
        searchBd();
        if (connection.isOnline()) {
            boolean loading = false;//this.documents.isEmpty();
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
    public void displayDocuments() {
        this.iDocument.setAdapter(this.iDocument.initAdapter(this.documents));
        this.iDocument.generateLayout();
    }
}

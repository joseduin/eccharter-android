package flight.report.ec.charter.adaptador;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import flight.report.ec.charter.R;
import flight.report.ec.charter.bd.BdContructor;
import flight.report.ec.charter.modelo.AircraftReport;
import flight.report.ec.charter.utils.Convert;
import flight.report.ec.charter.utils.Mensaje;

/**
 * Created by Jose on 23/1/2018.
 */

public class RecyclerAircraftAdapter extends RecyclerView.Adapter<RecyclerAircraftAdapter.AircraftViewHolder> {

    private List<AircraftReport> aircraftReports;
    private Context context;
    private BdContructor bd;
    private CallbackInterface mCallback;

    public interface CallbackInterface {
        void onHandleSelection(int position, String metohd);
    }

    public RecyclerAircraftAdapter(List<AircraftReport> aircraftReports, Fragment fragment) {
        this.aircraftReports = aircraftReports;
        this.context = fragment.getContext();
        this.bd = new BdContructor(context);
        this.mCallback = (CallbackInterface) fragment;
    }

    @Override
    public AircraftViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_report, parent, false);
        AircraftViewHolder pvh = new AircraftViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(AircraftViewHolder holder, int position) {
        final AircraftReport aircraftReport = aircraftReports.get(position);

        holder.report_descripcion.setText(aircraftReport.getDescription());
        holder.report_photo_path.setText(Convert.photoFormat(aircraftReport.getPhoto()));

        if (holder.report_photo_path.getText().toString().isEmpty()) {
            holder.report_btn_photo.setVisibility(View.GONE);

        }  else {
            holder.report_btn_photo.setVisibility(View.VISIBLE);
            holder.report_photo_path.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onPhotoPreview(aircraftReport);
                }
            });
            holder.report_btn_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onPhotoPreview(aircraftReport);
                }
            });
        }
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAircraft(aircraftReport, "edit");
            }
        });
        holder.report_btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAircraft(aircraftReport, "delete");
            }
        });
    }

    private void goToAircraft(AircraftReport aircraftReport, String metodo) {
        if(mCallback != null){
            mCallback.onHandleSelection(aircraftReport.getId(), metodo);
        }
    }

    private void onPhotoPreview(AircraftReport aircraftReport) {
        Uri photo = Uri.parse(aircraftReport.getPhoto());
        Mensaje.imagePreview(context, photo);
    }

    public void updateList(List<AircraftReport> aircraftReports) {
        this.aircraftReports = aircraftReports;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return aircraftReports.size();
    }

    public static class AircraftViewHolder extends RecyclerView.ViewHolder {

        private TextView report_descripcion, report_photo_path;
        private ImageButton report_btn_photo, report_btn_delete;
        private View v;

        public AircraftViewHolder(View itemView) {
            super(itemView);
            report_descripcion = itemView.findViewById(R.id.report_descripcion);
            report_photo_path = itemView.findViewById(R.id.report_photo_path);
            report_btn_photo = itemView.findViewById(R.id.report_btn_photo);
            report_btn_delete = itemView.findViewById(R.id.report_btn_delete);

            v = itemView;
        }
    }

}

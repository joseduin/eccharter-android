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
import flight.report.ec.charter.modelo.Plan;
import flight.report.ec.charter.utils.Convert;
import flight.report.ec.charter.utils.Mensaje;

/**
 * Created by Jose on 23/1/2018.
 */

public class RecyclerPlanAdapter extends RecyclerView.Adapter<RecyclerPlanAdapter.PlanViewHolder> {

    private List<Plan> plans;
    private Context context;
    private BdContructor bd;
    private CallbackInterface mCallback;

    public interface CallbackInterface {
        void onHandleSelection(int position, String metohd);
    }

    public RecyclerPlanAdapter(List<Plan> plans, Fragment fragment) {
        this.plans = plans;
        this.context = fragment.getContext();
        this.bd = new BdContructor(context);
        this.mCallback = (CallbackInterface) fragment;
    }

    @Override
    public PlanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_plan, parent, false);
        PlanViewHolder pvh = new PlanViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PlanViewHolder holder, int position) {
        final Plan plan = plans.get(position);

        holder.report_descripcion.setText(plan.getDescription());
        holder.report_photo_path.setText(Convert.photoFormat(plan.getPhoto()));

        if (holder.report_photo_path.getText().toString().isEmpty()) {
            holder.report_btn_photo.setVisibility(View.GONE);

        }  else {
            holder.report_btn_photo.setVisibility(View.VISIBLE);
            holder.report_photo_path.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onPhotoPreview(plan);
                }
            });
            holder.report_btn_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onPhotoPreview(plan);
                }
            });
        }
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPlan(plan, "edit");
            }
        });
        holder.report_btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPlan(plan, "delete");
            }
        });
    }

    private void goToPlan(Plan plan, String metodo) {
        if(mCallback != null){
            mCallback.onHandleSelection(plan.getId(), metodo);
        }
    }

    private void onPhotoPreview(Plan plan) {
        Uri photo = Uri.parse(plan.getPhoto());
        Mensaje.imagePreview(context, photo);
    }

    public void updateList(List<Plan> plans) {
        this.plans = plans;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return plans.size();
    }

    public static class PlanViewHolder extends RecyclerView.ViewHolder {

        private TextView report_descripcion, report_photo_path;
        private ImageButton report_btn_photo, report_btn_delete;
        private View v;

        public PlanViewHolder(View itemView) {
            super(itemView);
            report_descripcion = itemView.findViewById(R.id.report_descripcion);
            report_photo_path = itemView.findViewById(R.id.report_photo_path);
            report_btn_photo = itemView.findViewById(R.id.report_btn_photo);
            report_btn_delete = itemView.findViewById(R.id.report_btn_delete);

            v = itemView;
        }
    }

}

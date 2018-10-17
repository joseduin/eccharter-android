package flight.report.ec.charter.adaptador;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import flight.report.ec.charter.R;
import flight.report.ec.charter.aircrafts.AircraftTabs;
import flight.report.ec.charter.modelo.components.Aircraft;
import flight.report.ec.charter.utils.IrA;
import flight.report.ec.charter.utils.StringUtil;

/**
 * Created by Jose on 23/1/2018.
 */

public class RecyclerAircraftTabAdapter extends RecyclerView.Adapter<RecyclerAircraftTabAdapter.AircraftViewHolder> {
    private List<Aircraft> aircrafts;
    private List<Aircraft> aircraftsFilter;
    private Context context;

    public RecyclerAircraftTabAdapter(List<Aircraft> aircrafts, Context context) {
        this.aircraftsFilter = aircrafts;
        this.aircrafts = aircrafts;
        this.context = context;
    }

    @Override
    public AircraftViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_aircraft, parent, false);
        AircraftViewHolder pvh = new AircraftViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(AircraftViewHolder holder, int position) {
        final Aircraft aircraft = aircrafts.get(position);

        holder.tail.setText(aircraft.getTail());
        holder.brand.setText(aircraft.getBrand());
        holder.model.setText(aircraft.getModel());
        holder.component_medium.setText(component_alart_format(aircraft.getComponentMedium()));
        holder.component_alert.setText(component_alart_format(aircraft.getComponentAlert()));
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IrA.vista(context, AircraftTabs.class, aircraft.getId_web());
            }
        });
    }

    private String component_alart_format(int n) {
        return n > 99 ? "+99" : String.valueOf(n);
    }

    public void updateList(List<Aircraft> aircrafts) {
        this.aircrafts = aircrafts;
        notifyDataSetChanged();
    }

    public int filter(String charText) {
        this.aircrafts = new ArrayList<>();
        if (charText.isEmpty()) {
            this.aircrafts.addAll(this.aircraftsFilter);
        } else {
            for (Aircraft aircraft : this.aircraftsFilter) {
                if (StringUtil.contains(aircraft.getTail(), charText)
                        || StringUtil.contains(aircraft.getBrand(), charText)
                        || StringUtil.contains(aircraft.getModel(), charText)) {
                    aircrafts.add(aircraft);
                }
            }
        }
        notifyDataSetChanged();
        return aircrafts.size();
    }

    public List<Aircraft> getAircrafts() {
        return aircraftsFilter;
    }

    @Override
    public int getItemCount() {
        return aircrafts.size();
    }

    public static class AircraftViewHolder extends RecyclerView.ViewHolder {

        private TextView tail, component_medium, component_alert, brand, model;
        private View v;

        public AircraftViewHolder(View itemView) {
            super(itemView);
            tail = itemView.findViewById(R.id.tail);
            component_medium = itemView.findViewById(R.id.component_medium);
            component_alert = itemView.findViewById(R.id.component_alert);
            brand = itemView.findViewById(R.id.brand);
            model = itemView.findViewById(R.id.model);

            v = itemView;
        }
    }

}

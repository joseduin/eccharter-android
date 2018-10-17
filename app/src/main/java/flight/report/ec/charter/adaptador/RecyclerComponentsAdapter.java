package flight.report.ec.charter.adaptador;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import flight.report.ec.charter.R;
import flight.report.ec.charter.modelo.components.Component;
import flight.report.ec.charter.utils.StringUtil;

/**
 * Created by Jose on 23/1/2018.
 */

public class RecyclerComponentsAdapter extends RecyclerView.Adapter<RecyclerComponentsAdapter.ComponentsViewHolder> {
    private List<Component> components;
    private List<Component> componentsFilter;
    private Context context;

    public RecyclerComponentsAdapter(Context context, List<Component> components) {
        this.components = components;
        this.componentsFilter = components;
        this.context = context;
    }

    @Override
    public ComponentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_component, parent, false);
        ComponentsViewHolder pvh = new ComponentsViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ComponentsViewHolder holder, final int position) {
        final Component component = components.get(position);

        if (StringUtil.isEmpty(component.getItem())) {
            holder.item_title.setVisibility(View.GONE);
        } else {
            holder.item.setText(component.getItem());
        }
        if (StringUtil.isEmpty(component.getPosition())) {
            holder.title_position.setVisibility(View.GONE);
        } else {
            holder.position.setText(component.getPosition());
        }
        holder.description.setText( component.getDrawing() );
        holder.warning_icon.setImageDrawable( context.getResources().getDrawable(
               component.getStatus()==2 ?
                       R.drawable.ic_warning_medium :
                       R.drawable.ic_warning_alert
        ));
    }

    public void updateList(List<Component> components) {
        this.components = components;
        this.componentsFilter = components;
        notifyDataSetChanged();
    }

    public int filter(String charText) {
        this.components = new ArrayList<>();
        if (charText.isEmpty()) {
            this.components.addAll(this.componentsFilter);
        } else {
            for (Component component : this.componentsFilter) {
                if (StringUtil.contains(component.getDrawing(), charText)) {
                    components.add(component);
                }
            }
        }
        notifyDataSetChanged();
        return components.size();
    }

    public ArrayList<Component> getList() {
        return new ArrayList<>(componentsFilter);
    }

    @Override
    public int getItemCount() {
        return components.size();
    }

    public static class ComponentsViewHolder extends RecyclerView.ViewHolder {
        private TextView description, position, title_position, item, item_title;
        private ImageButton warning_icon;
        private View v;

        public ComponentsViewHolder(View itemView) {
            super(itemView);
            description     = itemView.findViewById(R.id.description);
            warning_icon    = itemView.findViewById(R.id.warning_icon);
            position        = itemView.findViewById(R.id.position);
            title_position  = itemView.findViewById(R.id.title_position);
            item            = itemView.findViewById(R.id.item);
            item_title      = itemView.findViewById(R.id.item_title);

            v = itemView;
        }
    }

}

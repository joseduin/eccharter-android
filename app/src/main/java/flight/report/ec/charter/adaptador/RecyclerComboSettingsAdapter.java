package flight.report.ec.charter.adaptador;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import flight.report.ec.charter.R;
import flight.report.ec.charter.bd.BdContructor;
import flight.report.ec.charter.utils.StringUtil;

/**
 * Created by Jose on 23/1/2018.
 */

public class RecyclerComboSettingsAdapter extends RecyclerView.Adapter<RecyclerComboSettingsAdapter.ComboSettingViewHolder> {
    private List<String> lista;
    private List<String> listaFilter;
    private Context context;
    private BdContructor bd;
    private CallbackInterface mCallback;

    public interface CallbackInterface {
        void onHandleSelection(int pos, String metohd);
    }

    public RecyclerComboSettingsAdapter(List<String> lista, Activity activity) {
        this.lista = lista;
        this.listaFilter = lista;
        this.context = activity;
        this.bd = new BdContructor(context);
        this.mCallback = (CallbackInterface) activity;
    }

    @Override
    public ComboSettingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_combo_settings, parent, false);
        ComboSettingViewHolder pvh = new ComboSettingViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ComboSettingViewHolder holder, final int position) {
        final String s = lista.get(position);

        holder.item.setText(s);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack(position, "edit");
            }
        });
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack(position, "delete");
            }
        });
    }

    private void callBack(int pos, String delete) {
        if(mCallback != null){
            mCallback.onHandleSelection(pos, delete);
        }
    }

    public void updateList(List<String> lista) {
        this.lista = lista;
        this.listaFilter = lista;
        notifyDataSetChanged();
    }

    public int filter(String charText) {
        this.lista = new ArrayList<>();
        if (charText.isEmpty()) {
            this.lista.addAll(this.listaFilter);
        } else {
            for (String s : this.listaFilter) {
                if (StringUtil.contains(s, charText)) {
                    lista.add(s);
                }
            }
        }
        notifyDataSetChanged();
        return lista.size();
    }

    public ArrayList<String> getList() {
        return new ArrayList<>(listaFilter);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ComboSettingViewHolder extends RecyclerView.ViewHolder {

        private Button item;
        private ImageButton btn_delete;
        private View v;

        public ComboSettingViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            btn_delete = itemView.findViewById(R.id.btn_delete);

            v = itemView;
        }
    }

}

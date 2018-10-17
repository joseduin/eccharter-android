package flight.report.ec.charter.adaptador;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

/**
 * Created by Jose on 19/1/2018.
 */

public class DialogComboAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private String tipe;

    public DialogComboAdapter(Context context, String tipe) {
        this.layoutInflater = LayoutInflater.from(context);
        this.tipe = tipe;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;
        viewHolder = (ViewHolder) view.getTag();

        viewHolder.dialog_edit.setText(tipe);
        return view;
    }

    static class ViewHolder {
       EditText dialog_edit;
    }

}

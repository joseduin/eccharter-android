package flight.report.ec.charter.adaptador;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Jose on 19/1/2018.
 */

public class DialogAlertAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private String content;

    public DialogAlertAdapter(Context context, String content) {
        this.layoutInflater = LayoutInflater.from(context);
        this.content = content;
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

        viewHolder.alert_text.setText(content);

        return view;
    }

    static class ViewHolder {
       TextView alert_text;
    }

}

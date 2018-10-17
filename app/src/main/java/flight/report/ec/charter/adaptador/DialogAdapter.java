package flight.report.ec.charter.adaptador;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import flight.report.ec.charter.R;

/**
 * Created by Jose on 19/1/2018.
 */

public class DialogAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private boolean preview;

    public DialogAdapter(Context context, boolean b) {
        this.layoutInflater = LayoutInflater.from(context);
        this.preview        = b;
    }

    @Override
    public int getCount() {
        return preview ?  4 : 2;
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

        if (view == null) {
            view = layoutInflater.inflate(R.layout.dialog_simple_list_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.textView  =  view.findViewById(R.id.text_view);
            viewHolder.imageView = view.findViewById(R.id.image_view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Context context = parent.getContext();

        if (position == 0 ) {
           viewHolder.textView.setText(context.getString(R.string.take_photo));
           viewHolder.imageView.setImageResource(R.drawable.if_camera);
        } else if (position == 1 ) {
            viewHolder.textView.setText(context.getString(R.string.gallery));
            viewHolder.imageView.setImageResource(R.drawable.if_folder);
        } else if ( position == 2 && preview) {
            viewHolder.textView.setText(context.getString(R.string.preview));
            viewHolder.imageView.setImageResource(R.drawable.if_gallery);
        } else if ( position == 3 && preview) {
            viewHolder.textView.setText(context.getString(R.string.delete_picture));
            viewHolder.imageView.setImageResource(R.drawable.if_stop_4);
        }

        return view;
    }

    static class ViewHolder {
        TextView textView;
        ImageView imageView;
    }

}

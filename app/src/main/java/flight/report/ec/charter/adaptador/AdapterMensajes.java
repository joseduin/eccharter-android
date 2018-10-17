package flight.report.ec.charter.adaptador;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import flight.report.ec.charter.GalleryPreview;
import flight.report.ec.charter.R;
import flight.report.ec.charter.modelo.components.Chat;
import flight.report.ec.charter.utils.StringUtil;

public class AdapterMensajes extends RecyclerView.Adapter<HolderMensajes> {
    private List<Chat> listMensaje;
    private Context c;

    public AdapterMensajes(Context c, List<Chat> listMensaje) {
        this.c = c;
        this.listMensaje = listMensaje;
    }
    public void addMensaje(Chat m){
        listMensaje.add(m);
        notifyItemInserted(listMensaje.size());
    }

    @Override
    public HolderMensajes onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.card_view_mensajes,parent,false);
        return new HolderMensajes(v);
    }

    @Override
    public void onBindViewHolder(final HolderMensajes holder, int position) {
        final Chat chat = listMensaje.get(position);

        holder.getNombre().setText(chat.getUserName());
        holder.getMensaje().setText(chat.getMessage());

        if (chat.getType()==1) {
            holder.getFotoMensajeEnviado().setVisibility(View.VISIBLE);
            holder.getMensaje().setVisibility(View.GONE);

            picasso(chat)
                    .placeholder(R.drawable.placeholder_image)
                    .into(holder.getFotoMensajeEnviado(), new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            holder.getRefresh_img().setVisibility(View.VISIBLE);
                        }
                    });
            holder.getV().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (chat.getSrcSave()==null)
                        return;

                    Intent intent = new Intent(c, GalleryPreview.class);
                    intent.putExtra("path", chat.getSrcSave());
                    intent.putExtra("file", false);
                    c.startActivity(intent);
                }
            });

        } else if(chat.getType()==0) {
            holder.getFotoMensajeEnviado().setVisibility(View.GONE);
            holder.getMensaje().setVisibility(View.VISIBLE);
        }

        Long codigoHora = chat.getTime();
        Date d = new Date(codigoHora);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss  a" );// a am o pm
        holder.getHora().setText(sdf.format(d));
    }

    private RequestCreator picasso(Chat chat) {
        if (chat.getSrcSave()==null) {
            return Picasso.get().load(R.drawable.placeholder_image);
        } else {
            return Picasso.get().load(Uri.parse( chat.getSrcSave() ));
        }
    }

    public void updateList(List<Chat> listMensaje) {
        this.listMensaje = listMensaje;
        notifyDataSetChanged();
    }

    public ArrayList<Chat> getMensajes() {
        return new ArrayList<>(listMensaje);
    }

    @Override
    public int getItemCount() {
        return listMensaje.size();
    }
}

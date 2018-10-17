package flight.report.ec.charter.adaptador;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import flight.report.ec.charter.R;

public class HolderMensajes extends RecyclerView.ViewHolder {
    private TextView mensaje;
    private TextView nombre;
    private TextView hora;
    private ImageView fotoMensajeEnviado, refresh_img;
    private View v;

    public HolderMensajes(View itemView) {
        super(itemView);
        nombre              = itemView.findViewById(R.id.nombre);
        mensaje             = itemView.findViewById(R.id.mensaje);
        hora                = itemView.findViewById(R.id.hora);
        fotoMensajeEnviado  = itemView.findViewById(R.id.mensajeFoto);
        refresh_img         = itemView.findViewById(R.id.refresh_img);
        v                   = itemView;
    }

    public TextView getMensaje() {
        return mensaje;
    }

    public ImageView getFotoMensajeEnviado() {
        return fotoMensajeEnviado;
    }

    public void setFotoMensajeEnviado(ImageView fotoMensajeEnviado) {
        this.fotoMensajeEnviado = fotoMensajeEnviado;
    }

    public void setMensaje(TextView mensaje) {
        this.mensaje = mensaje;
    }

    public TextView getNombre() {
        return nombre;
    }

    public void setNombre(TextView nombre) {
        this.nombre = nombre;
    }

    public TextView getHora() {
        return hora;
    }

    public void setHora(TextView hora) {
        this.hora = hora;
    }

    public ImageView getRefresh_img() {
        return refresh_img;
    }

    public void setRefresh_img(ImageView refresh_img) {
        this.refresh_img = refresh_img;
    }

    public View getV() {
        return v;
    }

    public void setV(View v) {
        this.v = v;
    }
}

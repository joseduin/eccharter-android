package flight.report.ec.charter.modelo;

import android.database.Cursor;

import flight.report.ec.charter.bd.ConstantesBaseDatos;

public class Send {

    private int id;
    private boolean server;
    private boolean mail;

    public Send() {}

    public Send(boolean server, boolean mail) {
        this.server = server;
        this.mail = mail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isServer() {
        return server;
    }

    public void setServer(boolean server) {
        this.server = server;
    }

    public boolean isMail() {
        return mail;
    }

    public void setMail(boolean mail) {
        this.mail = mail;
    }

    public static Send getSend(Cursor registros) {
        Send send = new Send();
        send.setId(registros.getInt(0));
        send.setServer( ConstantesBaseDatos.booleanConvert( registros.getInt(1) ) );
        send.setMail( ConstantesBaseDatos.booleanConvert( registros.getInt(2) ) );

        return send;
    }
}

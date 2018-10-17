package flight.report.ec.charter.bd.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import flight.report.ec.charter.bd.ConstantesBaseDatos;
import flight.report.ec.charter.modelo.components.Chat;

public class ChatDAO {
    private SQLiteOpenHelper db;

    public ChatDAO(SQLiteOpenHelper db) {
        this.db = db;
    }

    public ArrayList<Chat> getChats() {
        ArrayList<Chat> chats = new ArrayList<>();
        String query = "SELECT * FROM " + ConstantesBaseDatos.TABLE_CHAT;
        SQLiteDatabase db = this.db.getWritableDatabase();
        Cursor cursors = db.rawQuery(query, null);

        while (cursors.moveToNext()) {
            chats.add( getChat(cursors) );
        }

        db.close();
        return chats;
    }

    public ArrayList<Chat> getChatByAircraft(Long aircraftId) {
        ArrayList<Chat> chats = new ArrayList<>();
        String query = "SELECT * FROM " + ConstantesBaseDatos.TABLE_CHAT +
                " WHERE " + ConstantesBaseDatos.TABLE_CHAT_AIRCRAFT_ID + "=" + aircraftId;
        SQLiteDatabase db = this.db.getWritableDatabase();
        Cursor cursors = db.rawQuery(query, null);

        while (cursors.moveToNext()) {
            chats.add( getChat(cursors) );
        }

        db.close();
        return chats;
    }

    public Chat getDocumentsBySrc(String src) {
        Chat chat = null;
        String query = "SELECT * FROM " + ConstantesBaseDatos.TABLE_CHAT +
                " WHERE " + ConstantesBaseDatos.TABLE_CHAT_SRC + "='" + src + "'";
        SQLiteDatabase db = this.db.getWritableDatabase();
        Cursor cursors = db.rawQuery(query, null);

        while (cursors.moveToNext()) {
            chat = getChat(cursors);
        }

        db.close();
        return chat;
    }

    private Chat getChat(Cursor cursors) {
        return Chat.getChat(cursors);
    }
}

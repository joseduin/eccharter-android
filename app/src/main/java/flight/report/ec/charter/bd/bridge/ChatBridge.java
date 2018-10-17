package flight.report.ec.charter.bd.bridge;

import android.content.ContentValues;
import android.content.Context;

import java.io.File;
import java.util.ArrayList;

import flight.report.ec.charter.bd.BaseDatos;
import flight.report.ec.charter.bd.ConstantesBaseDatos;
import flight.report.ec.charter.modelo.components.Aircraft;
import flight.report.ec.charter.modelo.components.Chat;
import flight.report.ec.charter.utils.ArrayUtil;

public class ChatBridge {
    private BaseDatos db;

    public ChatBridge(Context context) {
        this.db = new BaseDatos(context);
    }

    public ArrayList<Chat> getChats() {
        return this.db.chat.getChats();
    }

    public ArrayList<Chat> getChatByAircraft(long aircraftId) {
        return this.db.chat.getChatByAircraft(aircraftId);
    }

    public void chatUpdate(Chat chat) {
        db.actualizar(convertChat(chat, true), ConstantesBaseDatos.TABLE_CHAT, ConstantesBaseDatos.TABLE_CHAT_ID, chat.getId());
    }

    public Long chatInsert(Chat chat) {
        return db.insertar(convertChat(chat, false), ConstantesBaseDatos.TABLE_CHAT);
    }

    public void chatDelete(Chat chat) {
        db.delete(ConstantesBaseDatos.TABLE_CHAT, ConstantesBaseDatos.TABLE_CHAT_ID, chat.getId());
    }

    public void chatDeleteAll() {
        db.deleteAll(ConstantesBaseDatos.TABLE_CHAT);
    }

    public Chat getChatBySrc(String src) {
        return db.chat.getDocumentsBySrc(src);
    }


    public void dataFromServer(ArrayList<Chat> server, Aircraft aircraft) {
        ArrayList<Chat> bdChats = getChatByAircraft(aircraft.getId_web());

        // arrServer >= arrLocal
        if (server.size() >= bdChats.size()) {
            for (int i = 0; i < server.size(); i++) {
                Chat bdChat = (Chat) ArrayUtil.canGet(bdChats, i);
                Chat chat = server.get(i);
                if (bdChat != null) {
                    Chat aux = getChatBySrc(chat.getSrc());
                    if (aux != null) {
                        bdChat.clone(chat);
                        bdChat.setSrcSave( aux.getSrcSave() );
                        bdChat.setSrcSave( aux.getSrcSave() );
                        chatUpdate(bdChat);
                    } else {
                        bdChat.clone(chat);
                        chatUpdate(bdChat);
                    }
                } else {
                    chatInsert(chat);
                }
            }
        } else {
            // arrServer < arrLocal
            for (int i = 0; i < bdChats.size(); i++) {
                Chat chat = (Chat) ArrayUtil.canGet(server, i);
                Chat bdChat = bdChats.get(i);
                if (chat != null) {
                    Chat aux = getChatBySrc(chat.getSrc());
                    if (aux != null) {
                        bdChat.clone(chat);
                        bdChat.setSrcSave( aux.getSrcSave() );
                        bdChat.setSrcSave( aux.getSrcSave() );
                        chatUpdate(bdChat);
                    } else {
                        bdChat.clone(chat);
                        chatUpdate(bdChat);
                    }
                } else {
                    if (bdChat.getSrcSave()==null) {
                        File file = new File(bdChat.getSrcSave());
                        file.delete();
                    }
                    chatDelete(bdChat);
                }
            }
        }
    }

    private ContentValues convertChat(Chat chat, boolean id) {
        ContentValues contentValues = new ContentValues();
        if (id)
            contentValues.put(ConstantesBaseDatos.TABLE_CHAT_ID, chat.getId());
        contentValues.put(ConstantesBaseDatos.TABLE_CHAT_AIRCRAFT_ID, chat.getAircraftId());
        contentValues.put(ConstantesBaseDatos.TABLE_CHAT_TIME, chat.getTime());
        contentValues.put(ConstantesBaseDatos.TABLE_CHAT_MGS, chat.getMessage());
        contentValues.put(ConstantesBaseDatos.TABLE_CHAT_USER, chat.getUserName());
        contentValues.put(ConstantesBaseDatos.TABLE_CHAT_TYPE, chat.getType());
        contentValues.put(ConstantesBaseDatos.TABLE_CHAT_SRC, chat.getSrc());
        contentValues.put(ConstantesBaseDatos.TABLE_CHAT_SRC_SAVE, chat.getSrcSave());
        contentValues.put(ConstantesBaseDatos.TABLE_CHAT_SEND, ConstantesBaseDatos.intergetConvert(chat.isSend()));
        return contentValues;
    }
}

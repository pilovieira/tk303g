package br.com.pilovieira.tk303g.persist;

import android.database.sqlite.SQLiteDatabase;

public class ScriptsV1 {

    private SQLiteDatabase sqld;

    public ScriptsV1(SQLiteDatabase sqld) {
        this.sqld = sqld;
    }

    public void upgrade() {
        sqld.execSQL(createTableServerLog());
    }

    private String createTableServerLog() {
        return
                "create table server_log (" +
                "id integer primary key autoincrement, " +
                "date integer, " +
                "title text, " +
                "message text);";
    }

}

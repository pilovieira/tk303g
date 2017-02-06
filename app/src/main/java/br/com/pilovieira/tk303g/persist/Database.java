package br.com.pilovieira.tk303g.persist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.pilovieira.tk303g.R;

public class Database extends SQLiteOpenHelper {

    private Context context;

    public static Database get(Context context) {
        return new Database(context,
                context.getString(R.string.DATABASE_NAME),
                getDatabseVersion(context));
    }

    private static int getDatabseVersion(Context context) {
        return context.getResources().getInteger(R.integer.DATABASE_VERSION);
    }

    public Database(Context context, String name, int version) {
        super(context, name, null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqld) {
        applyUpgrading(sqld, 0, getDatabseVersion(context));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqld, int oldVersion, int newVersion) {
        applyUpgrading(sqld, oldVersion, newVersion);
    }

    private void applyUpgrading(SQLiteDatabase sqld, int oldVersion, int newVersion) {
        for (int v = oldVersion + 1; v <= newVersion; v++) {
            if (v == 1)
                new ScriptsV1(sqld).upgrade();
        }
    }
}

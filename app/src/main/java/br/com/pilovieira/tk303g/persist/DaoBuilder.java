package br.com.pilovieira.tk303g.persist;

import android.content.Context;

import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class DaoBuilder extends BaseDaoImpl {

    public static DaoBuilder getInstance(Context context, Class clazz) {
        Database database = Database.get(context);
        ConnectionSource connectionSource = new AndroidConnectionSource(database);
        try {
            return new DaoBuilder(connectionSource, clazz);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    protected DaoBuilder(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public void close() {
        getConnectionSource().closeQuietly();
    }

}

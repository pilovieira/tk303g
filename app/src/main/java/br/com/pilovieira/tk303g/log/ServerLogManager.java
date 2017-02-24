package br.com.pilovieira.tk303g.log;

import android.content.Context;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import br.com.pilovieira.tk303g.persist.DaoManager;

public class ServerLogManager {

    private Context context;

    public ServerLogManager(Context context){
        this.context = context;
    }

	public void log(String title, String message) {
        ServerLog log = new ServerLog(title, message);
        DaoManager.persist(context, log);
	}

	public List<ServerLog> getLogs() {
        List<ServerLog> all = DaoManager.getAll(context, ServerLog.class);
        Collections.sort(all);
        return all;
    }

    public List<ServerLog> getLogs(String title) {
        List<ServerLog> search = DaoManager.search(context, ServerLog.class, "title", title);
        Collections.sort(search);
        return search;
    }
	
	public void clearLogs() {
        try {
            DaoManager.truncate(context, ServerLog.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

}

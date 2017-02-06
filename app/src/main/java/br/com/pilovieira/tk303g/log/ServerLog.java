package br.com.pilovieira.tk303g.log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "server_log")
public class ServerLog implements Comparable<ServerLog> {

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private Date date;
	@DatabaseField
	private String title;
	@DatabaseField
	private String message;

	public ServerLog(){}

	public ServerLog(String title, String message) {
		this.date = new Date();
        this.title = title;
        this.message = message;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
	public int compareTo(ServerLog o) {
		return date.compareTo(o.date);
	}
	
}

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

	public ServerLog(LogType logType, String message) {
		this.date = new Date();
		setLogType(logType);
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

	public LogType getLogType() {
		return LogType.get(title);
	}

	public void setLogType(LogType logType) {
		this.title = logType.name();
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

package br.com.pilovieira.tk303g.log;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import br.com.pilovieira.tk303g.R;
import br.com.pilovieira.tk303g.log.tag.AlarmTag;
import br.com.pilovieira.tk303g.log.tag.InfoTag;
import br.com.pilovieira.tk303g.log.tag.LocationTag;

import static br.com.pilovieira.tk303g.log.tag.AlarmTag.ACC_OFF_TYPE;
import static br.com.pilovieira.tk303g.log.tag.AlarmTag.ACC_ON_TYPE;
import static br.com.pilovieira.tk303g.log.tag.AlarmTag.MOVE_TYPE;
import static br.com.pilovieira.tk303g.log.tag.AlarmTag.SPEED_TYPE;

public enum LogType {

    ALARM(R.string.alarm, ACC_ON_TYPE, ACC_OFF_TYPE, MOVE_TYPE, SPEED_TYPE) {
        @Override
        public View createView(Context context, LayoutInflater inflater, ServerLog log) {
            View view = inflater.inflate(R.layout.list_log_alarm_item, null);
            view.setTag(new AlarmTag(view));
            return view;
        }
    },

    LOCATION(R.string.location, "maps.google.com") {
        @Override
        public View createView(Context context, LayoutInflater inflater, ServerLog log) {
            View view = inflater.inflate(R.layout.list_log_location_item, null);
            view.setTag(new LocationTag(view));
            return view;
        }
    },

    COMMAND(R.string.command) {
        @Override
        public View createView(Context context, LayoutInflater inflater, ServerLog log) {
            View view = inflater.inflate(R.layout.list_log_command_item, null);
            view.setTag(new InfoTag(view));
            return view;
        }
    },

    INFO(R.string.info) {
        @Override
        public View createView(Context context, LayoutInflater inflater, ServerLog log) {
            View view = inflater.inflate(R.layout.list_log_info_item, null);
            view.setTag(new InfoTag(view));
            return view;
        }
    };

    private int stringRes;
    private String[] messageIds;

    LogType(int stringRes, String... messageIds) {
        this.stringRes = stringRes;
        this.messageIds = messageIds;
    }

    public String stringValue(Context context) {
        return context.getString(stringRes);
    }

    public abstract View createView(Context context, LayoutInflater inflater, ServerLog log);

    public static LogType valueOfMessage(String message) {
        for (LogType type : values())
            for(String messageId : type.messageIds)
                if (message.contains(messageId))
                    return type;

        return INFO;
    }

    public static LogType get(String name) {
        for (LogType type : values())
            if (type.name().equals(name))
                return type;

        return INFO;
    }

}

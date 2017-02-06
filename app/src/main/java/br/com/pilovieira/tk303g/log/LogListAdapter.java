package br.com.pilovieira.tk303g.log;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import br.com.pilovieira.tk303g.R;
import br.com.pilovieira.tk303g.business.ListenerProvider;

public class LogListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<ServerLog> logs;
    private Context context;
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

    public LogListAdapter(Context context, List<ServerLog> logs) {
        this.context = context;
        Collections.sort(logs);
        Collections.reverse(logs);
        this.logs = logs;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return logs.size();
    }

    @Override
    public Object getItem(int position) {
        return logs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ServerLog log = logs.get(position);
        view = prepare(view, log);

        if (view.getTag() instanceof LocationTag) {
            mountLocation(view, log);
            return view;
        }

        mountInfo(view, log);

        return view;
    }

    private void mountInfo(View view, ServerLog log) {
        InfoTag tag = (InfoTag) view.getTag();
        tag.creationDate.setText(dateFormat.format(log.getDate()));
        tag.title.setText(log.getTitle());
        tag.message.setText(log.getMessage());
    }

    private void mountLocation(View view, ServerLog log) {
        LocationTag tag = (LocationTag) view.getTag();
        tag.creationDate.setText(dateFormat.format(log.getDate()));
        tag.title.setText(log.getTitle());
        String message = log.getMessage();
        String[] attrs = message.split("\\?")[1].split("&");
        final String lat = attrs[2].split("=")[1].split(",")[0];
        final String lng = attrs[2].split("=")[1].split(",")[1];

        tag.lat.setText(lat);
        tag.lng.setText(lng);
        tag.speed.setText(attrs[3].split("=")[1]);
        tag.saved.setText(attrs[4].split("=")[1]);

        ListenerProvider.openGeo(view, lat, lng);
    }

    private View prepare(View view, ServerLog log) {
        if (isLocation(log)){
            view = inflater.inflate(R.layout.list_log_location_item, null);

            LocationTag tag = new LocationTag();
            tag.creationDate = (TextView) view.findViewById(R.id.creation_date);
            tag.title = (TextView) view.findViewById(R.id.title);
            tag.lat = (TextView) view.findViewById(R.id.text_lat);
            tag.lng = (TextView) view.findViewById(R.id.text_lng);
            tag.speed = (TextView) view.findViewById(R.id.text_speed);
            tag.saved = (TextView) view.findViewById(R.id.text_saved);
            view.setTag(tag);
            return view;
        }

        if (isCommand(log))
            view = inflater.inflate(R.layout.list_log_command_item, null);
        else
            view = inflater.inflate(R.layout.list_log_info_item, null);

        InfoTag tag = new InfoTag();
        tag.creationDate = (TextView) view.findViewById(R.id.creation_date);
        tag.title = (TextView) view.findViewById(R.id.title);
        tag.message = (TextView) view.findViewById(R.id.message);
        view.setTag(tag);

        return view;
    }

    private boolean isInfo(ServerLog log) {
        return log.getTitle().equals(context.getString(R.string.info));
    }

    private boolean isCommand(ServerLog log) {
        return log.getTitle().equals(context.getString(R.string.command));
    }

    private boolean isLocation(ServerLog log) {
        return log.getTitle().equals(context.getString(R.string.location));
    }

    private class LocationTag {
        TextView creationDate;
        TextView title;
        TextView lat;
        TextView lng;
        TextView speed;
        TextView saved;
    }

    private class InfoTag {
        TextView creationDate;
        TextView title;
        TextView message;
    }
}

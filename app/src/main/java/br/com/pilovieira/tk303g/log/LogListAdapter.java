package br.com.pilovieira.tk303g.log;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Collections;
import java.util.List;

import br.com.pilovieira.tk303g.log.tag.LogTag;

public class LogListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<ServerLog> logs;
    private Context context;

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
        view = log.getLogType().createView(context, inflater, log);
        LogTag logTag = (LogTag) view.getTag();
        logTag.mount(view, log);

        return view;
    }

}

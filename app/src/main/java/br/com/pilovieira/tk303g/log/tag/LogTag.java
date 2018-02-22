package br.com.pilovieira.tk303g.log.tag;

import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import br.com.pilovieira.tk303g.R;
import br.com.pilovieira.tk303g.log.ServerLog;

public abstract class LogTag {

    private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.getDefault());

    private TextView creationDate;
    protected TextView title;

    public LogTag() {}

    public LogTag(View view) {
        this.creationDate = (TextView) view.findViewById(R.id.creation_date);
        this.title = (TextView) view.findViewById(R.id.title);
    }

    public void mount(View view, ServerLog log) {
        creationDate.setText(dateFormat.format(log.getDate()));
        title.setText(log.getLogType().stringValue(view.getContext()));
    }
}

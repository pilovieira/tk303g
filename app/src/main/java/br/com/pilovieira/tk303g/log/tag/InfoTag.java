package br.com.pilovieira.tk303g.log.tag;

import android.view.View;
import android.widget.TextView;

import br.com.pilovieira.tk303g.R;
import br.com.pilovieira.tk303g.log.ServerLog;

public class InfoTag extends LogTag {

    private TextView message;

    public InfoTag() {}

    public InfoTag(View view) {
        super(view);
        message = (TextView) view.findViewById(R.id.message);
    }

    @Override
    public void mount(View view, ServerLog log) {
        super.mount(view, log);
        message.setText(log.getMessage());
    }
}

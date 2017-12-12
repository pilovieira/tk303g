package br.com.pilovieira.tk303g.log.tag;

import android.view.View;
import android.widget.TextView;

import br.com.pilovieira.tk303g.R;
import br.com.pilovieira.tk303g.business.ListenerProvider;
import br.com.pilovieira.tk303g.location.LocationLogDigester;
import br.com.pilovieira.tk303g.log.ServerLog;

public class AlarmTag extends LogTag {

    public static final String ACC_ON_TYPE = "acc on!";
    public static final String ACC_OFF_TYPE = "acc off!";
    public static final String MOVE_TYPE = "move!";
    public static final String SPEED_TYPE = "speed!";
    private TextView lat;
    private TextView lng;
    private TextView speed;
    private TextView saved;

    public AlarmTag() {}

    public AlarmTag(View view) {
        super(view);
        lat = (TextView) view.findViewById(R.id.text_lat);
        lng = (TextView) view.findViewById(R.id.text_lng);
        speed = (TextView) view.findViewById(R.id.text_speed);
        saved = (TextView) view.findViewById(R.id.text_saved);
    }

    @Override
    public void mount(View view, ServerLog log) {
        super.mount(view, log);

        title.setText(view.getContext().getString(mountTitle(log)));

        LocationLogDigester digester = new LocationLogDigester(log);
        lat.setText(digester.getLat());
        lng.setText(digester.getLng());
        speed.setText(view.getContext().getString(R.string.speed_kmh, digester.getSpeed()));
        saved.setText(digester.getTime());

        ListenerProvider.openGeo(view, lat.getText().toString(), lng.getText().toString());
    }

    private int mountTitle(ServerLog log) {
        String message = log.getMessage();
        if (message.contains(ACC_ON_TYPE))
            return R.string.acc_on_alarm_title;
        if (message.contains(ACC_OFF_TYPE))
            return R.string.acc_off_alarm_title;
        if (message.contains(MOVE_TYPE))
            return R.string.move_alarm_title;
        if (message.contains(SPEED_TYPE))
            return R.string.speed_alarm_title;

        return R.string.alarm;
    }

}

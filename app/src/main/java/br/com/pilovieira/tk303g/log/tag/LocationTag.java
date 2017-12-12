package br.com.pilovieira.tk303g.log.tag;

import android.view.View;
import android.widget.TextView;

import br.com.pilovieira.tk303g.R;
import br.com.pilovieira.tk303g.business.ListenerProvider;
import br.com.pilovieira.tk303g.location.LocationLogDigester;
import br.com.pilovieira.tk303g.log.ServerLog;

public class LocationTag extends LogTag {

    public static final String LAST_TYPE = "Last:";
    private TextView lat;
    private TextView lng;
    private TextView speed;
    private TextView saved;
    private TextView pwr;
    private TextView door;
    private TextView acc;

    public LocationTag() {}

    public LocationTag(View view) {
        super(view);
        lat = (TextView) view.findViewById(R.id.text_lat);
        lng = (TextView) view.findViewById(R.id.text_lng);
        speed = (TextView) view.findViewById(R.id.text_speed);
        saved = (TextView) view.findViewById(R.id.text_saved);
        pwr = (TextView) view.findViewById(R.id.textPower);
        door = (TextView) view.findViewById(R.id.textDoor);
        acc = (TextView) view.findViewById(R.id.textAcc);
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
        pwr.setText(digester.getPower());
        door.setText(digester.getDoor());
        acc.setText(digester.getAcc());

        ListenerProvider.openGeo(view, lat.getText().toString(), lng.getText().toString());
    }

    private int mountTitle(ServerLog log) {
        String message = log.getMessage();
        if (message.contains(LAST_TYPE))
            return R.string.last_location_title;

        return R.string.location;
    }

}

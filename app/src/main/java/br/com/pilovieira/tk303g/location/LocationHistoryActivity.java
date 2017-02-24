package br.com.pilovieira.tk303g.location;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.pilovieira.tk303g.R;
import br.com.pilovieira.tk303g.log.ServerLog;

public class LocationHistoryActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<Marker> markers = new ArrayList<>();
    private Polyline polyLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_history);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        configureAdView();
    }

    private void configureAdView() {
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //List<ServerLog> locationLogs = new ServerLogManager(getBaseContext()).getLogs(getString(R.string.location));

        String message = "lat:-23.387790 lon:-51.953078\n" +
        "speed:0.00\n" +
        "T:17/02/23 09:20\n" +
        "http://maps.google.com/maps?f=q&q=-23.387790,-51.953078&z=16\n" +
        "Pwr: ON Door: OFF ACC: OFF";

        ServerLog log = new ServerLog("Location", message);
        message = "lat:-23.388790 lon:-51.953078\n" +
        "speed:0.00\n" +
        "T:17/02/23 09:20\n" +
        "http://maps.google.com/maps?f=q&q=-23.387790,-51.953078&z=16\n" +
        "Pwr: ON Door: OFF ACC: OFF";

        ServerLog log2 = new ServerLog("Location", message);
        message = "lat:-23.389790 lon:-51.956078\n" +
        "speed:0.00\n" +
        "T:17/02/23 09:20\n" +
        "http://maps.google.com/maps?f=q&q=-23.387790,-51.953078&z=16\n" +
        "Pwr: ON Door: OFF ACC: OFF";

        ServerLog log3 = new ServerLog("Location", message);
        message = "lat:-23.369790 lon:-51.959078\n" +
        "speed:0.00\n" +
        "T:17/02/23 09:20\n" +
        "http://maps.google.com/maps?f=q&q=-23.387790,-51.953078&z=16\n" +
        "Pwr: ON Door: OFF ACC: OFF";

        ServerLog log4 = new ServerLog("Location", message);


        createMarkers(Arrays.asList(log, log2, log3, log4));
        createPolyLine();
    }

    private void createMarkers(List<ServerLog> serverLogs) {
        for (ServerLog serverLog : serverLogs) {
            LocationLogDigester digester = new LocationLogDigester(serverLog);
            createMarker(digester);
        }
    }

    private void createPolyLine() {
        PolylineOptions polylineOptions = new PolylineOptions();
        for (Marker marker : markers)
            polylineOptions.add(marker.getPosition());

        polylineOptions.color(R.color.wallet_holo_blue_light);
        polyLine = mMap.addPolyline(polylineOptions);
    }

    private void createMarker(LocationLogDigester digester) {
        LatLng latLng = new LatLng(Double.parseDouble(digester.getLat()), Double.parseDouble(digester.getLng()));

        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title(getString(R.string.marker_detail, digester.getTime(), digester.getSpeed()));

        markers.add(mMap.addMarker(markerOptions));
    }

    public void refreshPolyLine(View view) {
        if (polyLine != null)
            polyLine.setVisible(!polyLine.isVisible());
    }
}

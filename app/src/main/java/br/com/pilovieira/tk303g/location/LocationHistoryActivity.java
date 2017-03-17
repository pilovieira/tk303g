package br.com.pilovieira.tk303g.location;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import br.com.pilovieira.tk303g.R;
import br.com.pilovieira.tk303g.log.ServerLog;
import br.com.pilovieira.tk303g.log.ServerLogManager;

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
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        List<ServerLog> locationLogs = new ServerLogManager(getBaseContext()).getLogs(getString(R.string.location));

        createMarkers(locationLogs);
        createPolyLine();
        animateCamera();
    }

    private void createMarkers(List<ServerLog> serverLogs) {
        for (ServerLog serverLog : serverLogs) {
            LocationLogDigester digester = new LocationLogDigester(serverLog);
            try {
                createMarker(digester);
            } catch (Exception ex) {}
        }
    }

    private void createPolyLine() {
        PolylineOptions polylineOptions = new PolylineOptions();
        for (Marker marker : markers)
            polylineOptions.add(marker.getPosition());

        polylineOptions.color(R.color.wallet_holo_blue_light);
        polyLine = mMap.addPolyline(polylineOptions);
    }

    private void animateCamera() {
        if (markers.isEmpty())
            return;

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers)
            builder.include(marker.getPosition());

        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 70));
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

    public void refreshMarkers(View view) {
        for (Marker marker : markers)
            marker.setVisible(!marker.isVisible());
    }
}

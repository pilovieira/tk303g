package br.com.pilovieira.tk303g.location;

import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
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
import br.com.pilovieira.tk303g.log.LogType;
import br.com.pilovieira.tk303g.log.ServerLog;
import br.com.pilovieira.tk303g.log.ServerLogManager;
import br.com.pilovieira.tk303g.utils.LanguageSetter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationHistoryActivity extends FragmentActivity implements OnMapReadyCallback {

    @BindView(R.id.adView) AdView mAdView;

    private GoogleMap mMap;
    private List<Marker> markers = new ArrayList<>();
    private Polyline polyLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LanguageSetter.refreshLanguage(this);
        setContentView(R.layout.activity_location_history);
        ButterKnife.bind(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mAdView.loadAd(new AdRequest.Builder().build());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        List<ServerLog> locationLogs = new ServerLogManager(getBaseContext()).getLogs(LogType.LOCATION);

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

        polylineOptions.color(R.color.colorPolyLine);
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

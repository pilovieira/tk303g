package br.com.pilovieira.tk303g.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import br.com.pilovieira.tk303g.R;
import br.com.pilovieira.tk303g.business.TK303GCommands;
import br.com.pilovieira.tk303g.business.ListenerProvider;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_FRAGMENT = "fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureToolbar();
        configureNavigationMenu();
        requestSMSPermission();
        requestCallPermission();
        initializeHotButtons();
        configureAdView();
    }

    private void configureToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureNavigationMenu() {
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new DrawerResolver(this));
        loadInitFragment(navigationView.getMenu());
    }

    private void configureAdView() {
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void loadInitFragment(Menu menu) {
        Bundle extras = getIntent().getExtras();
        int menuItemId = extras == null ? R.id.nav_info : extras.getInt(KEY_FRAGMENT, R.id.nav_info);
        new DrawerResolver(this).onNavigationItemSelected(menu.findItem(menuItemId));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void requestSMSPermission() {
        String permission = android.Manifest.permission.SEND_SMS;
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{permission}, 1000);
    }

    private void requestCallPermission() {
        String permission = Manifest.permission.CALL_PHONE;
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{permission}, 1001);
    }

    private void initializeHotButtons() {
        ListenerProvider.locationListener(findViewById(R.id.btn_hot_get_location));
        ListenerProvider.lock(findViewById(R.id.btn_hot_lock_vehicle));
        ListenerProvider.unlock(findViewById(R.id.btn_hot_unlock_vehicle));
    }
}

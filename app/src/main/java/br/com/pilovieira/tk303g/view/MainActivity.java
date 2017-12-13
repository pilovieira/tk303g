package br.com.pilovieira.tk303g.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import br.com.pilovieira.tk303g.R;
import br.com.pilovieira.tk303g.business.CommonOperations;
import br.com.pilovieira.tk303g.location.LocationHistoryActivity;
import br.com.pilovieira.tk303g.log.InfoFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.drawer_layout) DrawerLayout drawer;
    @Bind(R.id.nav_view) NavigationView navigationView;
    @Bind(R.id.adView) AdView mAdView;

    private CommonOperations common;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        common = new CommonOperations(getBaseContext());

        setSupportActionBar(toolbar);

        configureDrawer();
        configureNavigationMenu();
        requestPermissions();
        mAdView.loadAd(new AdRequest.Builder().build());
    }

    private void configureDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureNavigationMenu() {
        navigationView.setNavigationItemSelectedListener(this);

        MenuItem item = navigationView.getMenu().getItem(0);
        item.setChecked(true);
        onNavigationItemSelected(item);
    }

    private void requestPermissions() {
        String[] permissions = new String[] {
                android.Manifest.permission.SEND_SMS,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.ACCESS_FINE_LOCATION
        };
        if (ContextCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, permissions, 1000);
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

    @OnClick(R.id.btnHotGetLocation)
    public void locationAction(View view) {
        common.locationAction(view);
    }

    @OnClick(R.id.btnHotLock)
    public void lockAction() {
        common.lockAction();
    }

    @OnClick(R.id.btnHotUnlock)
    public void unlockAction() {
        common.unlockAction();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        selectItem(menuItem);
        drawer.closeDrawers();
        return true;
    }

    private void selectItem(MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.nav_info:
                replaceFragment(new InfoFragment());
                break;
            case R.id.nav_operations:
                replaceFragment(new OperationsFragment());
                break;
            case R.id.nav_alarms:
                replaceFragment(new AlarmsFragment());
                break;
            case R.id.nav_configs:
                replaceFragment(new ConfigsFragment());
                break;
            case R.id.nav_parameters:
                replaceFragment(new ParametersFragment());
                break;
            case R.id.nav_location_history:
                startActivity(new Intent(this, LocationHistoryActivity.class));
                return;
        }

        menuItem.setChecked(true);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main_frame, fragment).commit();
    }

}

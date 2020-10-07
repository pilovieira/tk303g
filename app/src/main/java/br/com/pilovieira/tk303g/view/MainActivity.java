package br.com.pilovieira.tk303g.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.google.android.material.navigation.NavigationView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import br.com.pilovieira.tk303g.R;
import br.com.pilovieira.tk303g.business.CommonOperations;
import br.com.pilovieira.tk303g.location.LocationHistoryActivity;
import br.com.pilovieira.tk303g.log.InfoFragment;
import br.com.pilovieira.tk303g.utils.LanguageSetter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.adView) AdView mAdView;

    private CommonOperations common;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LanguageSetter.refreshLanguage(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        common = new CommonOperations(getBaseContext());

        setSupportActionBar(toolbar);

        configureDrawer();
        configureNavigationMenu();
        //requestPermissions();
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
//                android.Manifest.permission.SEND_SMS,
//                Manifest.permission.CALL_PHONE,
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
        common.locationAction();
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
            case R.id.nav_tutorial:
                replaceFragment(new TutorialFragment());
//                break;
//            case R.id.nav_location_history:
//                startActivity(new Intent(this, LocationHistoryActivity.class));
                return;
        }

        menuItem.setChecked(true);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main_frame, fragment).commit();
    }

}

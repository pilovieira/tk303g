package br.com.pilovieira.tk303g.view;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.navigation.NavigationView;

import br.com.pilovieira.tk303g.R;
import br.com.pilovieira.tk303g.business.CommonOperations;
import br.com.pilovieira.tk303g.log.InfoFragment;
import br.com.pilovieira.tk303g.utils.LanguageSetter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private AdView mAdView;

    private CommonOperations common;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LanguageSetter.refreshLanguage(this);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        mAdView = findViewById(R.id.adView);

        findViewById(R.id.btnHotGetLocation).setOnClickListener(v -> locationAction(v));
        findViewById(R.id.btnHotLock).setOnClickListener(v -> lockAction());
        findViewById(R.id.btnHotUnlock).setOnClickListener(v -> unlockAction());

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

//    private void requestPermissions() {
//        String[] permissions = new String[] {
////                android.Manifest.permission.SEND_SMS,
////                Manifest.permission.CALL_PHONE,
//                Manifest.permission.ACCESS_FINE_LOCATION
//        };
//        if (ContextCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED)
//            ActivityCompat.requestPermissions(this, permissions, 1000);
//    }

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

    public void locationAction(View view) {
        common.locationAction();
    }

    public void lockAction() {
        common.lockAction();
    }

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
        int itemId = menuItem.getItemId();
        if (itemId == R.id.nav_info)
            replaceFragment(new InfoFragment());
        else if (itemId == R.id.nav_operations)
            replaceFragment(new OperationsFragment());
        else if (itemId == R.id.nav_alarms)
            replaceFragment(new AlarmsFragment());
        else if (itemId == R.id.nav_configs)
            replaceFragment(new ConfigsFragment());
        else if (itemId == R.id.nav_parameters)
            replaceFragment(new ParametersFragment());
        else if (itemId == R.id.nav_tutorial)
            replaceFragment(new TutorialFragment());

        menuItem.setChecked(true);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main_frame, fragment).commit();
    }

}

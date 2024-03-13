package br.com.pilovieira.tk303g.view;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import br.com.pilovieira.apputils.util.AdUtil;
import br.com.pilovieira.tk303g.R;
import br.com.pilovieira.tk303g.business.CommonOperations;
import br.com.pilovieira.tk303g.log.InfoFragment;
import br.com.pilovieira.tk303g.utils.LanguageSetter;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, NavigationBarView.OnItemReselectedListener {

    private CommonOperations common;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LanguageSetter.refreshLanguage(this);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigationView = findViewById(R.id.nav_bottom);

        navigationView.setOnItemSelectedListener(this);
        navigationView.setOnItemReselectedListener(this);

        findViewById(R.id.btnHotGetLocation).setOnClickListener(v -> locationAction(v));
        findViewById(R.id.btnHotLock).setOnClickListener(v -> lockAction());
        findViewById(R.id.btnHotUnlock).setOnClickListener(v -> unlockAction());

        common = new CommonOperations(getBaseContext());

        AdUtil.buildAds(findViewById(R.id.adViewMainTop));
        AdUtil.buildAds(findViewById(R.id.adViewMainBottom));

        replaceFragment(new InfoFragment());
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
        int itemId = menuItem.getItemId();
        if (itemId == R.id.nav_info)
            replaceFragment(new InfoFragment());
        else if (itemId == R.id.nav_operations)
            replaceFragment(new OperationsFragment());
        else if (itemId == R.id.nav_configs)
            replaceFragment(new ConfigsFragment());
        else if (itemId == R.id.nav_parameters)
            replaceFragment(new ParametersFragment());
        else if (itemId == R.id.nav_tutorial)
            replaceFragment(new TutorialFragment());
        return true;
    }

    @Override
    public void onNavigationItemReselected(@NonNull MenuItem item){
        onNavigationItemSelected(item);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main_frame, fragment).commit();
    }
}

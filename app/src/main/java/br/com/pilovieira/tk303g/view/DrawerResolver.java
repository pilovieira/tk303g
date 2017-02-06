package br.com.pilovieira.tk303g.view;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import br.com.pilovieira.tk303g.R;
import br.com.pilovieira.tk303g.log.InfoFragment;

public class DrawerResolver implements NavigationView.OnNavigationItemSelectedListener{

    private MainActivity activity;

    public DrawerResolver(MainActivity activity){
        this.activity = activity;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        Fragment fragment = null;

        switch(menuItem.getItemId()) {
            case R.id.nav_info:
                fragment = new InfoFragment();
                break;
            case R.id.nav_operations:
                fragment = new OperationsFragment();
                break;
            case R.id.nav_configurations:
                fragment = new ConfigurationsFragment();
                break;
            case R.id.nav_user_manual:
                fragment = new UserManualFragment();
                break;
        }

        replaceFragment(fragment);

        menuItem.setChecked(true);
        activity.setTitle(menuItem.getTitle());

        closeDrawers();
        return true;
    }

    private void closeDrawers() {
        DrawerLayout drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        drawer.closeDrawers();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main_frame, fragment).commit();
    }

}

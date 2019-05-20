package in.ac.bits_hyderabad.swd.swd.admin.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import in.ac.bits_hyderabad.swd.swd.R;
import in.ac.bits_hyderabad.swd.swd.admin.fragment.*;

public class Admin_Nav extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__nav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_admin);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_admin);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_admin);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_admin);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.admin_nav_search) {
            fragment = new Admin_SearchFragment();

        } else if (id == R.id.admin_nav_doc) {
            fragment = new Admin_DocGenFragment();

        } else if (id == R.id.admin_nav_mess) {
            fragment = new Admin_MessFragment();

        } else if (id == R.id.admin_nav_goodies) {
            fragment = new Admin_GoodiesFragment();

        } else if (id == R.id.admin_nav_gate) {
            fragment = new Admin_GateFragment();

        } else if (id == R.id.admin_nav_alumni) {
            fragment = new Admin_AlumniFragment();

        } else if (id == R.id.admin_nav_mcn) {
            fragment = new Admin_McnFragment();

        } else if (id == R.id.admin_nav_details) {
            fragment = new Admin_DetailsFragment();

        } else if (id == R.id.admin_nav_disco) {
            fragment = new Admin_DiscoFragment();

        } else if (id == R.id.admin_nav_counselor) {
            fragment = new Admin_CounselorFragment();
        }

        if (fragment == null)
            return false;

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.layout_frame_admin, fragment).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_admin);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}

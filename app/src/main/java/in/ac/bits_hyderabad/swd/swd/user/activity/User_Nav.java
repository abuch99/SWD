package in.ac.bits_hyderabad.swd.swd.user.activity;

import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;



import in.ac.bits_hyderabad.swd.swd.user.fragment.*;

import in.ac.bits_hyderabad.swd.swd.R;


public class User_Nav extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {
    public NavigationView navigationView;
    public Fragment fragment;
    FragmentManager manager;
    ActionBar actionBar;
    DrawerLayout drawer;
    ImageView header_img;
    TextView tvNav_header_Id_No,tvNav_header_name;
    SharedPreferences prefs;

    String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.toolbar_title);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.home);
        tvNav_header_Id_No=navigationView.getHeaderView(0).findViewById(R.id.tvIdNavHeader);
        tvNav_header_name=navigationView.getHeaderView(0).findViewById(R.id.tvNameNavHeader);
        header_img=navigationView.getHeaderView(0).findViewById(R.id.header_img);
        actionBar=getSupportActionBar();
        manager = getSupportFragmentManager();

        prefs=getApplicationContext().getSharedPreferences("USER_LOGIN_DETAILS",MODE_PRIVATE);

        tvNav_header_name.setText(prefs.getString("name",null));
        tvNav_header_Id_No.setText(prefs.getString("id",null));



        Log.e("name and id and pwd" ,prefs.getString("name",null)+prefs.getString("id",null));

        setHome();

        navigationView.getHeaderView(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(User_Nav.this,Profile.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {

        Log.e("onbackpressed", "method executed");

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            Log.e("onbackpressed","drawer closed");
        } else if(!fragment.getTag().equals("home")) {
            setHome();
            Log.e("onbackpressed","home set");
        }else{
            actionBar.setTitle(R.string.toolbar_title);
            super.onBackPressed();
            Log.e("onbackpressed","super");
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.home: {
                actionBar.setBackgroundDrawable(getDrawable(R.drawable.bgnd_dark));//action bar
                tag="home";
                if (drawer.isDrawerOpen(GravityCompat.START))
                    drawer.closeDrawer(GravityCompat.START);
                actionBar.setTitle(R.string.toolbar_title);
                navigationView.setCheckedItem(R.id.home);
                fragment=new User_HomeFragment();
                manager.beginTransaction().replace(R.id.layout_frame,fragment,tag).commit();
                break;
            }
            case R.id.mess: {
                tag="mess";
                if (drawer.isDrawerOpen(GravityCompat.START))
                    drawer.closeDrawer(GravityCompat.START);
                actionBar.setTitle(R.string.mess_title);
                actionBar.setBackgroundDrawable(getDrawable(R.drawable.toolbar_drawable));
                navigationView.setCheckedItem(R.id.mess);
                fragment=new User_MessFragment(prefs.getString("uid",null));
                manager.beginTransaction().replace(R.id.layout_frame,fragment,tag).commit();
                break;
            }
            case R.id.messReg: {
                tag="messReg";
                if (drawer.isDrawerOpen(GravityCompat.START))
                    drawer.closeDrawer(GravityCompat.START);
                actionBar.setTitle(R.string.messReg_title);
                actionBar.setBackgroundDrawable(getDrawable(R.drawable.toolbar_drawable));
                navigationView.setCheckedItem(R.id.messReg);
                fragment=new UserMessRegFragment();
                manager.beginTransaction().replace(R.id.layout_frame,fragment,tag).commit();
                break;
            }
            case  R.id.docs: {
                tag="docs";
                actionBar.setBackgroundDrawable(getDrawable(R.drawable.bgnd_dark));
                if (drawer.isDrawerOpen(GravityCompat.START))
                    drawer.closeDrawer(GravityCompat.START);
                actionBar.setTitle(R.string.docs_title);
                navigationView.setCheckedItem(R.id.docs);
                fragment=new User_DocFragment();
                manager.beginTransaction().replace(R.id.layout_frame,fragment,tag).commit();
                break;
            }
            case R.id.goodies: {
                tag="goodies";
                actionBar.setBackgroundDrawable(getDrawable(R.drawable.toolbar_drawable));
                if (drawer.isDrawerOpen(GravityCompat.START))
                    drawer.closeDrawer(GravityCompat.START);
                actionBar.setTitle(R.string.goodies_title);
                navigationView.setCheckedItem(R.id.goodies);
                fragment=new User_GoodiesFragment();
                manager.beginTransaction().replace(R.id.layout_frame,fragment,tag).commit();
                break;
            }
            case R.id.connect: {
                Log.e("frag during connect",fragment.getTag()+"   connect:"+R.id.connect+"   home:"+R.id.home );
                if (drawer.isDrawerOpen(GravityCompat.START))
                    drawer.closeDrawer(GravityCompat.START);
                Intent intent=new Intent(User_Nav.this, Connect.class);
                startActivity(intent);
                break;
            }
            case R.id.logout: {
                if (drawer.isDrawerOpen(GravityCompat.START))
                    drawer.closeDrawer(GravityCompat.START);
                new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle)
                        .setTitle("Log out")
                        .setMessage("Are you sure you want to log out?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                logout();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                navigationView.setCheckedItem(getSupportFragmentManager().findFragmentByTag(tag).getId());
                            }
                        }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                    }
                }).show();
                break;

            }
            case R.id.settings: {

            }
        }
        return true;
    }


    public void setHome(){
        tag="home";
        actionBar.setTitle(R.string.toolbar_title);
        actionBar.setBackgroundDrawable(getDrawable(R.drawable.bgnd_dark));
        navigationView.setCheckedItem(R.id.home);
        fragment=new User_HomeFragment();
        manager.beginTransaction().replace(R.id.layout_frame,fragment,tag).commit();
    }
    private void logout() {
        Log.e("prefs before logout",prefs.toString());
        SharedPreferences.Editor editor=prefs.edit();
        editor.clear();
        editor.commit();
        Log.e("prefs after logout",prefs.toString());
        startActivity(new Intent(this, User_Login.class));
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {

        switch (tag){

            case "home": {
                navigationView.setCheckedItem(R.id.home);
                break;
            }
            case "goodies": {
                navigationView.setCheckedItem(R.id.goodies);
                break;
            }
            case "mess": {
                navigationView.setCheckedItem(R.id.mess);
                break;
            }
            case "docs": {
                navigationView.setCheckedItem(R.id.docs);
                break;
            }
            default:
            {
                navigationView.setCheckedItem(R.id.home);
            }
        }

        super.onResume();
    }
}

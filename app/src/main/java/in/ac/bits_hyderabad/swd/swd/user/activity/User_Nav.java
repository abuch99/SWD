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
    int itemId=0;
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

        tvNav_header_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(User_Nav.this,Profile.class);
                if (drawer.isDrawerOpen(GravityCompat.START))
                    drawer.closeDrawer(GravityCompat.START);
                startActivity(intent);
            }
        });

        prefs=getApplicationContext().getSharedPreferences("USER_LOGIN_DETAILS",MODE_PRIVATE);

        tvNav_header_name.setText(prefs.getString("name",null));
        tvNav_header_Id_No.setText(prefs.getString("id",null));

        setHome();


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
        final int id = item.getItemId();
        if(id!=R.id.logout&&id!=R.id.MyProfile&&id!=R.id.connect)
            itemId=id;

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
                fragment=new User_MessFragment(prefs.getString("uid",null),prefs.getString("password",null));
                manager.beginTransaction().replace(R.id.layout_frame,fragment,tag).commit();
                break;
            }
            /* case R.id.messReg: {

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
            */
            case R.id.BusTimings: {

                tag = "busTimings";
                if (drawer.isDrawerOpen(GravityCompat.START))
                    drawer.closeDrawer(GravityCompat.START);
                actionBar.setTitle(R.string.busTimings);
                actionBar.setBackgroundDrawable(getDrawable(R.drawable.toolbar_drawable));
                navigationView.setCheckedItem(R.id.BusTimings);
                fragment = new BusTimingsFragment();
                manager.beginTransaction().replace(R.id.layout_frame, fragment, tag).commit();
                break;
            }
            case  R.id.docs: {

                tag="docs";
                if (drawer.isDrawerOpen(GravityCompat.START))
                    drawer.closeDrawer(GravityCompat.START);
                actionBar.setTitle(R.string.docs_title);
                actionBar.setBackgroundDrawable(getDrawable(R.drawable.toolbar_drawable));
                navigationView.setCheckedItem(R.id.docs);
                fragment=User_DocFragment.newInstance(prefs.getString("uid",null),prefs.getString("id",null),prefs.getString("password",null));
                manager.beginTransaction().replace(R.id.layout_frame,fragment,tag).commit();
                break;
            }
            case R.id.MyProfile: {
                Intent intent =new Intent(User_Nav.this,Profile.class);
                if (drawer.isDrawerOpen(GravityCompat.START))
                    drawer.closeDrawer(GravityCompat.START);
                startActivity(intent);
                break;
            }
            case R.id.goodies: {

                tag="goodies";
                actionBar.setBackgroundDrawable(getDrawable(R.drawable.toolbar_drawable));
                if (drawer.isDrawerOpen(GravityCompat.START))
                    drawer.closeDrawer(GravityCompat.START);
                actionBar.setTitle(R.string.goodies_title);
                navigationView.setCheckedItem(R.id.goodies);
                fragment=User_GoodiesFragment.newInstance(prefs.getString("uid",null),prefs.getString("id",null),prefs.getString("password",null));
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
                                navigationView.setCheckedItem(itemId);
                                Log.e("on logout clicked",itemId+"");
                            }
                        }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                    }
                }).setCancelable(false).show();
                break;

            }
        }
        return true;
    }


    public void setHome(){
        itemId=R.id.home;
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
                itemId=R.id.home;
                navigationView.setCheckedItem(R.id.home);
                break;
            }
            case "goodies": {
                itemId=R.id.goodies;
                navigationView.setCheckedItem(R.id.goodies);
                break;
            }
            case "mess": {
                itemId=R.id.mess;
                navigationView.setCheckedItem(R.id.mess);
                break;
            }
            case "docs": {
                itemId=R.id.docs;
                navigationView.setCheckedItem(R.id.docs);
                break;
            }
            default:
            {
                itemId=R.id.home;
                navigationView.setCheckedItem(R.id.home);
            }
        }
        super.onResume();
    }
}

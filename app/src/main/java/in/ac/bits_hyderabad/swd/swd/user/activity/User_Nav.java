package in.ac.bits_hyderabad.swd.swd.user.activity;

import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import in.ac.bits_hyderabad.swd.swd.databases.UserTable;
import in.ac.bits_hyderabad.swd.swd.user.fragment.*;

import in.ac.bits_hyderabad.swd.swd.R;


public class User_Nav extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {
    NavigationView navigationView;
    Fragment fragment;
    FragmentManager manager;

    UserTable mUserTable;
    ActionBar actionBar;
    DrawerLayout drawer;

    TextView tvNav_header_Id_No,tvNav_header_name;

    Button btnMess;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.toolbar_title);
        setSupportActionBar(toolbar);
        //space for floating button if needed

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

        mUserTable=new UserTable(getApplicationContext());

        tvNav_header_name.setText(mUserTable.getName());
        tvNav_header_Id_No.setText(mUserTable.getID());
        JSONObject o=new JSONObject();

        Log.e("name and id and pwd" ,mUserTable.getName()+mUserTable.getID());
        fragment=new User_HomeFragment();
        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.layout_frame, fragment,"home").commit();
        actionBar=getSupportActionBar();


        /*btnMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionBar.setTitle(R.string.mess_title);
                navigationView.setCheckedItem(R.id.mess);
                fragment=new User_MessFragment();
                manager.beginTransaction().replace(R.id.layout_frame,fragment,"mess").commit();

            }
        });
        btnGoodies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionBar.setTitle(R.string.goodies_title);
                navigationView.setCheckedItem(R.id.goodies);
                fragment=new User_GoodiesFragment();
                manager.beginTransaction().replace(R.id.layout_frame,fragment,"goodies").commit();
            }
        });
        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionBar.setTitle(R.string.connect_title);
                navigationView.setCheckedItem(R.id.connect);
                fragment=new User_ConnectFragment();
                manager.beginTransaction().replace(R.id.layout_frame,fragment,"connect").commit();
            }
        });*/

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
                if (drawer.isDrawerOpen(GravityCompat.START))
                    drawer.closeDrawer(GravityCompat.START);
                actionBar.setTitle(R.string.toolbar_title);
                navigationView.setCheckedItem(R.id.home);
                fragment=new User_HomeFragment();
                manager.beginTransaction().replace(R.id.layout_frame,fragment,"home").commit();
                break;
            }
            case R.id.connect: {
                if (drawer.isDrawerOpen(GravityCompat.START))
                    drawer.closeDrawer(GravityCompat.START);
                actionBar.setTitle(R.string.connect_title);
                navigationView.setCheckedItem(R.id.connect);
                fragment=new User_ConnectFragment();
                manager.beginTransaction().replace(R.id.layout_frame,fragment,"connect").commit();
                break;
            }
            case R.id.notices: {
                if (drawer.isDrawerOpen(GravityCompat.START))
                    drawer.closeDrawer(GravityCompat.START);
                actionBar.setTitle(R.string.notices_title);
                navigationView.setCheckedItem(R.id.notices);
                fragment=new User_NoticeFragment();
                manager.beginTransaction().replace(R.id.layout_frame,fragment,"notices").commit();
                break;
            }
            case R.id.complaint: {
                if (drawer.isDrawerOpen(GravityCompat.START))
                    drawer.closeDrawer(GravityCompat.START);
                actionBar.setTitle(R.string.complaints_title);
                navigationView.setCheckedItem(R.id.complaint);
                fragment=new User_ComplaintFragment();
                manager.beginTransaction().replace(R.id.layout_frame,fragment,"complaint").commit();
                break;
            }
            case R.id.mess: {
                if (drawer.isDrawerOpen(GravityCompat.START))
                    drawer.closeDrawer(GravityCompat.START);
                actionBar.setTitle(R.string.mess_title);
                navigationView.setCheckedItem(R.id.mess);
                fragment=new User_MessFragment();
                manager.beginTransaction().replace(R.id.layout_frame,fragment,"mess").commit();
                break;
            }
            case  R.id.docs: {
                if (drawer.isDrawerOpen(GravityCompat.START))
                    drawer.closeDrawer(GravityCompat.START);
                actionBar.setTitle(R.string.docs_title);
                navigationView.setCheckedItem(R.id.docs);
                fragment=new User_DocFragment();
                manager.beginTransaction().replace(R.id.layout_frame,fragment,"docs").commit();
                break;
            }
            case R.id.uploads: {
                if (drawer.isDrawerOpen(GravityCompat.START))
                    drawer.closeDrawer(GravityCompat.START);
                actionBar.setTitle(R.string.uploads_title);
                navigationView.setCheckedItem(R.id.uploads);
                fragment=new User_UploadFragment();
                manager.beginTransaction().replace(R.id.layout_frame,fragment,"uploads").commit();
                break;
            }
            case R.id.goodies: {
                if (drawer.isDrawerOpen(GravityCompat.START))
                    drawer.closeDrawer(GravityCompat.START);
                actionBar.setTitle(R.string.goodies_title);
                navigationView.setCheckedItem(R.id.goodies);
                fragment=new User_GoodiesFragment();
                manager.beginTransaction().replace(R.id.layout_frame,fragment,"goodies").commit();
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
                        .setNegativeButton("Cancel", null)
                        .show();
                break;

            }
            case R.id.settings: {

            }
        }
        return true;
    }


    public void setHome(){
        actionBar.setTitle(R.string.toolbar_title);
        navigationView.setCheckedItem(R.id.home);
        fragment=new User_HomeFragment();
        manager.beginTransaction().replace(R.id.layout_frame,fragment,"home").commit();
    }
    private void logout() {
        mUserTable.deleteAll();
        startActivity(new Intent(this, User_Login.class));
        finish();
    }
    public void replaceFragment (Fragment someFragment,String tag,String title){
        actionBar.setTitle(title);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.layout_frame, someFragment,tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

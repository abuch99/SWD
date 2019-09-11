package in.ac.bits_hyderabad.swd.swd.user.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonObject;

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
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.ac.bits_hyderabad.swd.swd.BuildConfig;
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


        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(!fragment.getTag().equals("home")) {
            setHome();
        }else{
            actionBar.setTitle(R.string.toolbar_title);
            super.onBackPressed();
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
            case R.id.messReg: {

                tag="messReg";
                if (drawer.isDrawerOpen(GravityCompat.START))
                    drawer.closeDrawer(GravityCompat.START);
                actionBar.setTitle(R.string.messReg_title);
                actionBar.setBackgroundDrawable(getDrawable(R.drawable.toolbar_drawable));
                navigationView.setCheckedItem(R.id.messReg);
                fragment=UserMessRegFragment.newInstance(prefs.getString("uid", null),prefs.getString("password",null));
                manager.beginTransaction().replace(R.id.layout_frame,fragment,tag).commit();
                break;
            }
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

            case R.id.Deduction: {
                tag="deductions";
                if (drawer.isDrawerOpen(GravityCompat.START))
                    drawer.closeDrawer(GravityCompat.START);
                actionBar.setTitle(getString(R.string.DeductionsTitle));
                actionBar.setBackgroundDrawable(getDrawable(R.drawable.toolbar_drawable));
                navigationView.setCheckedItem(R.id.docs);
                fragment=User_DeductionsFragment.newInstance(prefs.getString("uid",null),prefs.getString("id",null),prefs.getString("password",null));
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
                fragment=User_GoodiesFragment.newInstance(prefs.getString("uid",null),prefs.getString("id",null),prefs.getString("password",null));
                manager.beginTransaction().replace(R.id.layout_frame,fragment,tag).commit();
                break;
            }
            case R.id.connect: {

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

        SharedPreferences.Editor editor=prefs.edit();
        editor.clear();
        editor.commit();
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
        CheckUpdates task=new CheckUpdates();
        task.execute();
        super.onResume();
    }

    public class CheckUpdates extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            RequestQueue queue = Volley.newRequestQueue(User_Nav.this);
            StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.BASE_URL), new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject obj = new JSONObject(response);
                        int latest_version = obj.getInt("version");
                        int current_version = BuildConfig.VERSION_CODE;

                        if (latest_version > current_version) {
                            showDialog();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(User_Nav.this, "Please check your Internet connection!", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<>();
                    params.put("tag", "check_updates");
                    return params;

                }
            };
            queue.add(request);
            return null;
        }
    }
    public void closeApp(){
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
    public void showDialog(){

        AlertDialog.Builder dialogBuilder;

        dialogBuilder=new AlertDialog.Builder(User_Nav.this, R.style.AppCompatAlertDialogStyle);
        dialogBuilder.setTitle("New Update Available!!");
            dialogBuilder.setMessage("A new Version of the app is available.");
            dialogBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final String appPackageName = "in.ac.bits_hyderabad.swd.swd"; // getPackageName() from Context or Activity object
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    }
                }
            })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            closeApp();
                        }
                    }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                }
            });
            AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        }
}
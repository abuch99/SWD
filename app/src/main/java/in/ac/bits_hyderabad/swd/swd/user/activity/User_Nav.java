package in.ac.bits_hyderabad.swd.swd.user.activity;

import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.FragmentTransaction;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import in.ac.bits_hyderabad.swd.swd.user.fragment.*;

import in.ac.bits_hyderabad.swd.swd.R;


public class User_Nav extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {
    public NavigationView navigationView;
    public Fragment fragment;
    FragmentManager manager;

    //UserTable mUserTable;
    ActionBar actionBar;
    DrawerLayout drawer;

    ImageView header_img;
    TextView tvNav_header_Id_No,tvNav_header_name;
    SharedPreferences prefs;

    Button btnMess;
    String urlImageIcard;



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
        header_img=navigationView.getHeaderView(0).findViewById(R.id.header_img);
        //mUserTable=new UserTable(getApplicationContext());

        prefs=getApplicationContext().getSharedPreferences("USER_LOGIN_DETAILS",MODE_PRIVATE);

        tvNav_header_name.setText(prefs.getString("name",null));
        tvNav_header_Id_No.setText(prefs.getString("id",null));

        //Log.e("error",mUserTable.getUID().substring(5)+"    "+mUserTable.getUID().substring(1,5)+"    "+mUserTable.getUID()+"    "+header_img.toString());
        /*String four_digits=mUserTable.getUID().substring(5);//0459
        String year=mUserTable.getUID().substring(1,5);//2018
        String degree=null;//f
        switch (mUserTable.getUID().charAt(0)){
            case 'f':
               degree="fd";
               break;
            case 'h':
                degree="hd";
                break;
            case 'p':
                degree="phd";
                break;
        }
        urlImageIcard="http://swd.bits-hyderabad.ac.in/components/navbar/id/2018/fd/0459H.jpg";//"http://swd.bits-hyderabad.ac.in/components/navbar/id/"+year+"/"+degree+"/"+four_digits+"H.jpg";
        Picasso.get().load(urlImageIcard)
                .resize(125,125)
                .centerInside()
                .onlyScaleDown()
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_error)
                .into(header_img);

*/
        JSONObject o=new JSONObject();

        Log.e("name and id and pwd" ,prefs.getString("name",null)+prefs.getString("id",null));
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
                Intent intent=new Intent(User_Nav.this, Connect.class);
                startActivity(intent);
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
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                navigationView.setCheckedItem(R.id.home);
                            }
                        }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        navigationView.setCheckedItem(R.id.home);
                    }
                })
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
        Log.e("prefs before logout",prefs.toString());
        SharedPreferences.Editor editor=prefs.edit();
        editor.clear();
        editor.commit();
        Log.e("prefs after logout",prefs.toString());
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

    @Override
    protected void onResume() {
        navigationView.setCheckedItem(R.id.home);
        super.onResume();
    }
}

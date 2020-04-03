package in.ac.bits_hyderabad.swd.swd.user.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import in.ac.bits_hyderabad.swd.swd.R;
import in.ac.bits_hyderabad.swd.swd.user.fragment.MessMenu;
import in.ac.bits_hyderabad.swd.swd.user.fragment.MiscFragment;
import in.ac.bits_hyderabad.swd.swd.user.fragment.User_ConnectFragment;
import in.ac.bits_hyderabad.swd.swd.user.fragment.User_GoodiesFragment;
import in.ac.bits_hyderabad.swd.swd.user.fragment.User_HomeFragment;


public class MainActivity extends AppCompatActivity {
    public Fragment fragment;
    FragmentManager manager;
    ActionBar actionBar;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getApplicationContext().getSharedPreferences("USER_LOGIN_DETAILS", MODE_PRIVATE);
        String uid = prefs.getString("uid", null);
        String pwd = prefs.getString("password", null);
        String id_no = prefs.getString("id", null);

        BottomNavigationView navBar = findViewById(R.id.bottom_navigation);
        navBar.setSelectedItemId(R.id.navigation_home);
        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_goodies:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new User_GoodiesFragment(uid, id_no, pwd)).commit();
                        break;
                    case R.id.navigation_mess:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new MessMenu(uid, pwd)).commit();
                        break;
                    case R.id.navigation_connect:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new User_ConnectFragment()).commit();
                        break;
                    case R.id.navigation_miscellaneous:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new MiscFragment(uid, id_no, pwd)).commit();
                        break;
                    case R.id.navigation_home:
                    default:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new User_HomeFragment()).commit();
                        break;
                }
                return true;
            }
        });


        getSupportFragmentManager().beginTransaction().replace(R.id.container, new User_HomeFragment()).commit();

        actionBar = getSupportActionBar();
        manager = getSupportFragmentManager();
    }

    public void closeApp() {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

    public void showDialog() {

        AlertDialog.Builder dialogBuilder;

        dialogBuilder = new AlertDialog.Builder(MainActivity.this, R.style.AppCompatAlertDialogStyle);
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
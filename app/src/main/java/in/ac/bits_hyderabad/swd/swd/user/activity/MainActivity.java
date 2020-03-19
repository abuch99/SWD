package in.ac.bits_hyderabad.swd.swd.user.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.ac.bits_hyderabad.swd.swd.BuildConfig;
import in.ac.bits_hyderabad.swd.swd.R;
import in.ac.bits_hyderabad.swd.swd.user.fragment.User_ConnectFragment;
import in.ac.bits_hyderabad.swd.swd.user.fragment.User_DocFragment;
import in.ac.bits_hyderabad.swd.swd.user.fragment.User_GoodiesFragment;
import in.ac.bits_hyderabad.swd.swd.user.fragment.User_HomeFragment;
import in.ac.bits_hyderabad.swd.swd.user.fragment.User_MessFragment;


public class MainActivity extends AppCompatActivity {
    public Fragment fragment;
    FragmentManager manager;
    ActionBar actionBar;
    SharedPreferences prefs;
    int itemId = 0;
    String tag;

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
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new User_MessFragment(uid, pwd)).commit();
                        break;
                    case R.id.navigation_connect:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new User_ConnectFragment()).commit();
                        break;
                    case R.id.navigation_miscellaneous:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new User_DocFragment(uid, id_no, pwd)).commit();
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


    //TODO: Implemet logout
    private void logout() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
        startActivity(new Intent(this, User_Login.class));
        finish();
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

    public class CheckUpdates extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
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
                    Toast.makeText(MainActivity.this, "Please check your Internet connection!", Toast.LENGTH_SHORT).show();
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
}
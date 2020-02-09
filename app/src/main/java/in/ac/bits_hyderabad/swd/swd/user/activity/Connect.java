package in.ac.bits_hyderabad.swd.swd.user.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.TableLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.ac.bits_hyderabad.swd.swd.R;
import in.ac.bits_hyderabad.swd.swd.helper.ConnectAdapter;
import in.ac.bits_hyderabad.swd.swd.helper.Goodies;
import in.ac.bits_hyderabad.swd.swd.helper.Person;
import in.ac.bits_hyderabad.swd.swd.helper.PersonAdapter;
import in.ac.bits_hyderabad.swd.swd.user.fragment.CRC_ConnectFragment;
import in.ac.bits_hyderabad.swd.swd.user.fragment.EC_ConnectFragment;
import in.ac.bits_hyderabad.swd.swd.user.fragment.SMC_ConnectFragment;
import in.ac.bits_hyderabad.swd.swd.user.fragment.SU_ConnectFragment;
import in.ac.bits_hyderabad.swd.swd.user.fragment.SWD_ConnectFragment;

public class Connect extends AppCompatActivity implements PersonAdapter.itemClicked {

    Toolbar toolbarConnect;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        float dpW=dpWidth/3;

        int wid=Math.round(dpW);

        toolbarConnect=findViewById(R.id.toolbarConnect);
        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager);

        toolbarConnect.setTitle(R.string.connect_title);
        setSupportActionBar(toolbarConnect);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);
    }
    public void setupViewPager(ViewPager viewPager){
        ConnectAdapter adapter=new ConnectAdapter(getSupportFragmentManager());


        adapter.addFragment(new SWD_ConnectFragment(),"SWD");
        adapter.addFragment(new SU_ConnectFragment(),"SUC");
        adapter.addFragment(new CRC_ConnectFragment(),"CRC");
        adapter.addFragment(new SMC_ConnectFragment(),"SMC");
        adapter.addFragment(new EC_ConnectFragment(),"EC");
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return  super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onItemClicked(int intent_action, String data) {
        switch (intent_action){
            case 0:
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:"+data));//change the number
                    startActivity(callIntent);
                break;

            case 1:
                Intent intentMail = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", data+"@hyderabad.bits-pilani.ac.in", null));
                startActivity(intentMail);
                break;

        }
    }

}

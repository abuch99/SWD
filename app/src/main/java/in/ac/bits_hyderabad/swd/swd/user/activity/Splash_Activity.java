package in.ac.bits_hyderabad.swd.swd.user.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.ac.bits_hyderabad.swd.swd.R;

public class Splash_Activity extends AppCompatActivity {

    final int SPLASH_SCREEN_TIMEOUT=1500;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Boolean passwordChanged=false;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ProgressBar progressBar=(ProgressBar) findViewById(R.id.progressLoader);
        Sprite threeBounce =new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                prefs=getApplicationContext().getSharedPreferences("USER_LOGIN_DETAILS",MODE_PRIVATE);
                if(prefs.getInt("exists",0)==1) {
                    String uid = prefs.getString("uid", null);
                    String pwd = prefs.getString("password", null);

                    sendRequest(uid, pwd);
                }
                else{
                    Intent intent =new Intent(Splash_Activity.this,User_Login.class);
                    startActivity(intent);
                    finish();
                }

                /*Intent intent =new Intent(Splash_Activity.this,User_Login.class);
                startActivity(intent);
                finish();*/
            }
        },SPLASH_SCREEN_TIMEOUT);


    }

    public void sendRequest(String uid, String pwd){

        RequestQueue queue = Volley.newRequestQueue(Splash_Activity.this);
        StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.BASE_URL), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    if (!object.getBoolean("error")) {
                        Log.e("error", "false");
                        Log.e("error",response);

                        Intent intent = new Intent(Splash_Activity.this, User_Nav.class);
                        if (getIntent().getStringExtra("default") != null) {
                            intent.putExtra("default", getIntent().getStringArrayExtra("default"));
                            if (getIntent().getLongExtra("uploadedTime", -1) != -1) {
                                intent.putExtra("uploadedTime", getIntent().getLongExtra("uploadedTime", -1));
                            }
                        }
                        startActivity(intent);
                        finish();

                    } else {
                        passwordChanged=true;
                        Intent intent =new Intent(Splash_Activity.this,User_Login.class);
                        startActivity(intent);
                        finish();
                        Log.e("error ","true");
                        Toast.makeText(Splash_Activity.this, "Password changed, Please login again!", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    Log.e("jsonexception",e.toString());
                    e.printStackTrace();
                    Toast.makeText(Splash_Activity.this, "Wrong Id or Password!!", Toast.LENGTH_SHORT).show();

                    closeApp();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Splash_Activity.this, "Please check your Internet connection!", Toast.LENGTH_SHORT).show();
                closeApp();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("tag", "login");
                params.put("id", uid);
                params.put("pwd", pwd);

                return params;

            }
        };


        queue.add(request);

    }

    public void closeApp(){
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
}

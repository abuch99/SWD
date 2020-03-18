package in.ac.bits_hyderabad.swd.swd.user.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.ac.bits_hyderabad.swd.swd.R;
import in.ac.bits_hyderabad.swd.swd.helper.HashString;

public class User_Login extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Button mbutton;
    ProgressBar loggingInProgressBar;
    TextView tvloginFgtPass;
    TextView fabContactus;
    ImageView contactUsImage;
    TextInputLayout idLayout, passwordLayout;
    boolean passwordChangedFlag = false;
    TextInputEditText my_id_get, password_get;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //userTable = new UserTable(getApplicationContext());
        preferences = getApplicationContext().getSharedPreferences("USER_LOGIN_DETAILS", MODE_PRIVATE);
        editor = preferences.edit();
        setContentView(R.layout.activity_login);
        //checkLogin();

        fabContactus=findViewById(R.id.fabContactUsfromLoginPage);
        contactUsImage = findViewById(R.id.contact_us_image);
        my_id_get = findViewById(R.id.id_fill);
        password_get = findViewById(R.id.pwd_fill);
        idLayout = findViewById(R.id.id_fill_layout);
        passwordLayout = findViewById(R.id.pwd_fill_layout);
        mbutton = findViewById(R.id.submit);
        loggingInProgressBar = findViewById(R.id.login_progress_bar);
        tvloginFgtPass=findViewById(R.id.login_forgot_pwd);

        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLoggingIndicator();
                final String my_id = my_id_get.getText().toString().trim();
                final String password = password_get.getText().toString();
                final String hashedPass = HashString.getSHA(password);
                boolean success = true;
                if (my_id.isEmpty()) {
                    idLayout.setError("Cannot be empty");
                    success = false;
                }
                if (password.isEmpty()) {
                    passwordLayout.setError("Cannot be empty");
                    success = false;
                }
                if (!success) {
                    removeLoggingIndicator();
                    return;
                }
                RequestQueue queue = Volley.newRequestQueue(User_Login.this);
                StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.BASE_URL), new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);
                            if (!object.getBoolean("error")) {
                                if(object.getBoolean("profile_completed")) {
                                    Log.e("error", "false");
                                    Log.e("error", response);
                                    editor.putInt("exists", 1);
                                    editor.putString("name", object.getString("name"));
                                    editor.putString("uid", my_id);
                                    editor.putString("password", hashedPass);
                                    editor.putString("id", object.getString("id_no"));

                                    editor.commit();

                                    Log.i("prefs", preferences.getInt("exists", 1) + "   " + preferences.getString("name", null) + "     " + preferences.getString("uid", "uid nai hai") + "   " + preferences.getString("password", "password nai hai") + "    " + preferences.getString("id", "id nai hai"));
                                    checkLogin();
                                }
                                /***************************/
                                else {
                                    /*******************have to do something here*********************/
                                }


                            } else {
                                Log.e("error ","true");
                                Toast.makeText(User_Login.this, "Wrong ID or Password", Toast.LENGTH_SHORT).show();
                                removeLoggingIndicator();
                            }

                        } catch (JSONException e) {
                            Log.e("jsonexception",e.toString());
                            e.printStackTrace();
                            Toast.makeText(User_Login.this, "Wrong Id or Password!!", Toast.LENGTH_SHORT).show();
                            removeLoggingIndicator();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(User_Login.this, "Please check your Internet connection!", Toast.LENGTH_SHORT).show();
                        removeLoggingIndicator();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<>();
                        params.put("tag", "login");
                        params.put("id", my_id);
                        params.put("pwd", hashedPass);

                        return params;

                    }
                };


                queue.add(request);


            }
        });
        tvloginFgtPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_Login.this, ForgotPassword.class);
                startActivity(intent);
            }
        });

        fabContactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "swdnucleus@hyderabad.bits-pilani.ac.in", null));
                startActivity(Intent.createChooser(intent, "Choose an Email client :"));
            }
        });

        contactUsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "swdnucleus@hyderabad.bits-pilani.ac.in", null));
                startActivity(Intent.createChooser(intent, "Choose an Email client :"));
            }
        });

    }

    private void setLoggingIndicator() {
        my_id_get.setEnabled(false);
        password_get.setEnabled(false);
        mbutton.setText("");
        mbutton.setEnabled(false);
        loggingInProgressBar.setVisibility(View.VISIBLE);
    }

    private void removeLoggingIndicator() {
        my_id_get.setEnabled(true);
        password_get.setEnabled(true);
        mbutton.setText(getString(R.string.login));
        mbutton.setEnabled(true);
        loggingInProgressBar.setVisibility(View.GONE);
    }




    public void checkLogin() {

        if(preferences.getInt("exists",0)==1)
        {

            Intent intent = new Intent(User_Login.this, MainActivity.class);
            if (getIntent().getStringExtra("default") != null) {
                intent.putExtra("default", getIntent().getStringArrayExtra("default"));
                if (getIntent().getLongExtra("uploadedTime", -1) != -1) {
                    intent.putExtra("uploadedTime", getIntent().getLongExtra("uploadedTime", -1));
                }
            }
            startActivity(intent);
            finish();
        }
    }

}


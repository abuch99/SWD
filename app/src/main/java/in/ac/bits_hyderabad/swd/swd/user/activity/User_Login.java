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

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import in.ac.bits_hyderabad.swd.swd.APIConnection.GetDataService;
import in.ac.bits_hyderabad.swd.swd.APIConnection.Login;
import in.ac.bits_hyderabad.swd.swd.R;
import in.ac.bits_hyderabad.swd.swd.helper.HashString;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        preferences = getApplicationContext().getSharedPreferences("USER_LOGIN_DETAILS", MODE_PRIVATE);
        editor = preferences.edit();
        setContentView(R.layout.activity_login);

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

                Retrofit retrofitClient = new Retrofit.Builder()
                        .baseUrl(getString(R.string.URL))
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                GetDataService retrofitService = retrofitClient.create(GetDataService.class);

                Call<Login> call = retrofitService.getLoginSuccessful("login", my_id, hashedPass);
                call.enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                        Log.d("LoginDebug", response.toString());
                        Log.d("LoginDebug", response.message());
                        if (!response.body().getError()) {
                            if (response.body().getProfile_completed()) {
                                editor.putInt("exists", 1);
                                editor.putString("name", response.body().getName());
                                editor.putString("uid", my_id);
                                editor.putString("password", hashedPass);
                                editor.putString("id", response.body().getId_no());
                                editor.apply();
                                checkLogin();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Wrong ID or Password", Toast.LENGTH_SHORT).show();
                            removeLoggingIndicator();
                        }
                    }

                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error connecting to our servers", Toast.LENGTH_SHORT).show();
                        removeLoggingIndicator();
                    }
                });

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


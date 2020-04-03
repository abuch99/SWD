package in.ac.bits_hyderabad.swd.swd.user.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import in.ac.bits_hyderabad.swd.swd.APIConnection.GetDataService;
import in.ac.bits_hyderabad.swd.swd.APIConnection.Login;
import in.ac.bits_hyderabad.swd.swd.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Splash_Activity extends AppCompatActivity {

    private static final long SPLASH_SCREEN_TIMEOUT = 1500;
    SharedPreferences prefs;

    ProgressBar loggingInProgressBar;
    TextView loggingInStatus;
    ImageView noInternetImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        loggingInProgressBar = findViewById(R.id.logginInProgress);
        loggingInStatus = findViewById(R.id.loginStatusText);
        noInternetImage = findViewById(R.id.noInternetImage);

        //checking whether the user has logged in before or not
        prefs = getApplicationContext().getSharedPreferences("USER_LOGIN_DETAILS", MODE_PRIVATE);

        if (prefs.getInt("exists", 0) == 1) {
            String uid = prefs.getString("uid", null);
            String pwd = prefs.getString("password", null);
            Log.d("Encrypted password", pwd);
            checkPasswordChanged(uid, pwd);
        } else {
            Intent intent = new Intent(Splash_Activity.this, User_Login.class);
            startActivity(intent);
            finish();
        }

    }

    public void checkPasswordChanged(String uid, String pwd) {
        Retrofit retrofitClient = new Retrofit.Builder()
                .baseUrl(getString(R.string.URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetDataService retrofitService = retrofitClient.create(GetDataService.class);

        Call<Login> call = retrofitService.getLoginSuccessful("login", uid, pwd);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.body().getError()) {
                    Intent intent = new Intent(Splash_Activity.this, User_Login.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(getApplicationContext(), "Password changed, Please login again!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Splash_Activity.this, MainActivity.class);
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

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                t.printStackTrace();
                loggingInProgressBar.setVisibility(View.INVISIBLE);
                noInternetImage.setVisibility(View.VISIBLE);
                loggingInStatus.setText(getString(R.string.servers_no_connection));
            }
        });

    }
}

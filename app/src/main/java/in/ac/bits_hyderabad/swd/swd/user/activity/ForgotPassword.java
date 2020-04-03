package in.ac.bits_hyderabad.swd.swd.user.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import in.ac.bits_hyderabad.swd.swd.APIConnection.GetDataService;
import in.ac.bits_hyderabad.swd.swd.APIConnection.Login;
import in.ac.bits_hyderabad.swd.swd.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForgotPassword extends AppCompatActivity {

    TextInputEditText etUsernameForgotPassword;
    Button btnForgotSubmit;
    ProgressBar sendingRequestProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etUsernameForgotPassword = findViewById(R.id.etUsernameForgotPassword);
        btnForgotSubmit = findViewById(R.id.btnForgotSubmit);
        sendingRequestProgress = findViewById(R.id.forgot_pwd_progress_bar);

        btnForgotSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLoading();
                String uid = etUsernameForgotPassword.getText().toString().trim();
                if (uid.isEmpty())
                {
                    Toast.makeText(ForgotPassword.this,"Please enter your BITS Mail",Toast.LENGTH_LONG).show();
                    setNoLoading();
                } else if (uid.substring(0, 4).equalsIgnoreCase("f201"))
                {
                    sendRequest(uid);
                }
                else
                {
                    setLoading();
                    Toast.makeText(ForgotPassword.this,"Please enter your BITS Mail in the correct format!!",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void sendRequest(String uid)
    {
        Retrofit retrofitClient = new Retrofit.Builder()
                .baseUrl(getString(R.string.URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetDataService retrofitService = retrofitClient.create(GetDataService.class);

        Call<Login> call = retrofitService.getPasswordResetResponse(uid, "submit");
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Toast.makeText(getApplicationContext(), "The link to reset your password has been sent to your email address", Toast.LENGTH_LONG).show();
                etUsernameForgotPassword.setText(null);
                setNoLoading();
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                setNoLoading();
                Toast.makeText(ForgotPassword.this, "Please check your Internet connection!", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private void setLoading() {
        etUsernameForgotPassword.setEnabled(false);
        btnForgotSubmit.setEnabled(false);
        sendingRequestProgress.setVisibility(View.VISIBLE);
        btnForgotSubmit.setText("");
    }

    private void setNoLoading() {
        etUsernameForgotPassword.setEnabled(true);
        btnForgotSubmit.setEnabled(true);
        sendingRequestProgress.setVisibility(View.GONE);
        btnForgotSubmit.setText(getString(R.string.resetForgot_pass));
    }


    @Override
    public void onBackPressed() {
        setNoLoading();
        finish();
        super.onBackPressed();
    }
}

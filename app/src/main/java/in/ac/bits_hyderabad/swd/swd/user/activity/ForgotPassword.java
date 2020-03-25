package in.ac.bits_hyderabad.swd.swd.user.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
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

import java.util.HashMap;
import java.util.Map;

import in.ac.bits_hyderabad.swd.swd.R;

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
                String username = etUsernameForgotPassword.getText().toString().trim();
                if(username.isEmpty())
                {
                    Toast.makeText(ForgotPassword.this,"Please enter your BITS Mail",Toast.LENGTH_LONG).show();
                    setNoLoading();
                } else if (username.substring(0, 4).equalsIgnoreCase("f201"))
                {
                    sendRequest(username);
                }
                else
                {
                    setLoading();
                    Toast.makeText(ForgotPassword.this,"Please enter your BITS Mail in the correct format!!",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public  void sendRequest(final String username)
    {
        RequestQueue queue = Volley.newRequestQueue(ForgotPassword.this);

        StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.RESET_URL), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("onResponse called", "onResponse called");
                Toast.makeText(getApplicationContext(), "The link to reset your password has been sent to your email address", Toast.LENGTH_LONG).show();
                etUsernameForgotPassword.setText(null);
                setNoLoading();
                /*if(response.contains("Please check if you have entered you email ID in the appropriate format!"))
                {
                    Toast.makeText(ForgotPassword.this,"Please check if you have entered your email ID in the appropriate format!", Toast.LENGTH_LONG).show();
                }
                else if(response.contains("Password Reset Link Sent to your BITS Mail!"))
                {
                    Toast.makeText(ForgotPassword.this,"Password Reset Link Sent to your BITS Mail!", Toast.LENGTH_LONG).show();
                }*/
                //Toast.makeText(ForgotPassword.this,"Please check if you have entered your email ID in the appropriate format!", Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "The link to reset your password has been sent to your email address", Toast.LENGTH_LONG).show();
                setNoLoading();
                //Toast.makeText(ForgotPassword.this, "Please check your Internet connection!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("uid", username);
                params.put("reset", "submit");

                return params;

            }
        };


        queue.add(request);


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

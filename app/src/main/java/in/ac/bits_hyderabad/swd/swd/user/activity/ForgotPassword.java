package in.ac.bits_hyderabad.swd.swd.user.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.mbms.DownloadProgressListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import in.ac.bits_hyderabad.swd.swd.R;

public class ForgotPassword extends AppCompatActivity {

    EditText etUsernameForgotPassword;
    Button btnForgotSubmit;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etUsernameForgotPassword=findViewById(R.id.etUsernameForgotPassword);
        btnForgotSubmit=findViewById(R.id.btnForgotSubmit);

        dialog=new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.create();


        btnForgotSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();
                String username =etUsernameForgotPassword.getText().toString().trim();
                if(username.isEmpty())
                {
                    Toast.makeText(ForgotPassword.this,"Please enter your BITS Mail",Toast.LENGTH_LONG).show();
                }
                else if(username.substring(0,4).equalsIgnoreCase("F201") )
                {
                    sendRequest(username);
                }
                else
                {
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
                Log.e("LoginResponse: ", response);
                etUsernameForgotPassword.setText(null);
                dialog.hide();

                if(response.contains("Please check if you have entered you email ID in the appropriate format!"))
                {
                    Toast.makeText(ForgotPassword.this,"Please check if you have entered your email ID in the appropriate format!", Toast.LENGTH_LONG).show();
                }
                else if(response.contains("Password Reset Link Sent to your BITS Mail!"))
                {
                    Toast.makeText(ForgotPassword.this,"Password Reset Link Sent to your BITS Mail!", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                dialog.hide();
                Toast.makeText(ForgotPassword.this, "Please check your Internet connection!", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        dialog.dismiss();
        finish();
        super.onBackPressed();
    }
}

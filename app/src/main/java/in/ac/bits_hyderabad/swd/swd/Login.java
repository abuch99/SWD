package in.ac.bits_hyderabad.swd.swd;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import in.ac.bits_hyderabad.swd.swd.databaseconnection.APIService;
import in.ac.bits_hyderabad.swd.swd.databaseconnection.APIUtils;
import in.ac.bits_hyderabad.swd.swd.databaseconnection.responseclasses.AuthenticationData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    Button mbutton;
    private APIService loginUsingApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mbutton = (Button) findViewById(R.id.submit);
        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText my_id_get, password_get;
                my_id_get = findViewById(R.id.id_fill);
                password_get =findViewById(R.id.pwd_fill);
                final String my_id = my_id_get.getText().toString();
                final String password = password_get.getText().toString();

                loginUsingApi = APIUtils.getAPIService();
                if (true) {
                    loginUsingApi.loginUser(my_id, password).enqueue(new Callback<AuthenticationData>() {
                        @Override
                        public void onResponse(Call<AuthenticationData> call, Response<AuthenticationData> response) {
                            int response_code = response.body().getResponseCode();
                            if (response_code == 200) {
//                                sharedPreferences.edit().putString("loggedInID", response.body().getId()).apply();
//                                sharedPreferences.edit().putString("mobile_number", mobileValidator.returnText()).apply();
//                                sharedPreferences.edit().putString("password", passwordValidator.returnText()).apply();
                                Toast.makeText(Login.this, "logged in ID is", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Login.this, Nav.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Login.this, my_id, Toast.LENGTH_LONG).show();
//                                Toast.makeText(Login.this, password, Toast.LENGTH_LONG).show();
//                                Toast.makeText(Login.this, "Request gave erroneous response", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AuthenticationData> call, Throwable t) {
                            Toast.makeText(Login.this, "Failed to make request", Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    Toast.makeText(Login.this, "WRONG", Toast.LENGTH_LONG).show();
                }
//                startActivity(intent);


            }
        });
    }

}

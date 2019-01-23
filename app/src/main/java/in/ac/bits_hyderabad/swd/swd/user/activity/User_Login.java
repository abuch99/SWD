package in.ac.bits_hyderabad.swd.swd.user.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import in.ac.bits_hyderabad.swd.swd.admin.activity.Admin_Login;
import in.ac.bits_hyderabad.swd.swd.R;
import in.ac.bits_hyderabad.swd.swd.databaseconnection.APIService;
import in.ac.bits_hyderabad.swd.swd.databaseconnection.APIUtils;
import in.ac.bits_hyderabad.swd.swd.databaseconnection.responseclasses.AuthenticationData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User_Login extends AppCompatActivity {

    Button mbutton;
    private APIService loginUsingApi;

    TextView mTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mTextView = (TextView) findViewById(R.id.login_admin);

        mTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_Login.this, Admin_Login.class);
                startActivity(intent);
            }
        });

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
                                Toast.makeText(User_Login.this, "logged in ID is", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(User_Login.this, User_Nav.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(User_Login.this, my_id, Toast.LENGTH_LONG).show();
//                                Toast.makeText(User_Login.this, password, Toast.LENGTH_LONG).show();
//                                Toast.makeText(User_Login.this, "Request gave erroneous response", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AuthenticationData> call, Throwable t) {
                            Toast.makeText(User_Login.this, "Failed to make request", Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    Toast.makeText(User_Login.this, "WRONG", Toast.LENGTH_LONG).show();
                }
//                startActivity(intent);


            }
        });
    }

}

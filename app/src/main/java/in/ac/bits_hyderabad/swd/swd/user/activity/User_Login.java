package in.ac.bits_hyderabad.swd.swd.user.activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import in.ac.bits_hyderabad.swd.swd.R;
import in.ac.bits_hyderabad.swd.swd.databaseconnection.APIService;

public class User_Login extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Button mbutton;
    TextView tvloginFgtPass;

    private APIService loginUsingApi;

    TextView mTextView;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //userTable = new UserTable(getApplicationContext());
        preferences=getApplicationContext().getSharedPreferences("USER_LOGIN_DETAILS",MODE_PRIVATE);
        editor=preferences.edit();
        setContentView(R.layout.activity_login);
        checkLogin();

        dialog = new ProgressDialog(this);
        dialog.setMessage("Logging In");

        dialog.setCanceledOnTouchOutside(false);
        dialog.setInverseBackgroundForced(false);


        mbutton = (Button) findViewById(R.id.submit);
        tvloginFgtPass=findViewById(R.id.login_forgot_pwd);
        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                final EditText my_id_get, password_get;
                my_id_get = findViewById(R.id.id_fill);
                password_get = findViewById(R.id.pwd_fill);
                final String my_id = my_id_get.getText().toString().trim();
                final String password = password_get.getText().toString();
                boolean success = true;
                if (my_id.isEmpty()) {
                    my_id_get.setError("Cannot be empty");
                    success = false;
                }
                if (password.isEmpty()) {
                    password_get.setError("Cannot be empty");
                    success = false;
                }
                if (!success) {
                    dialog.hide();
                    return;
                }
                RequestQueue queue = Volley.newRequestQueue(User_Login.this);

                StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.BASE_URL), new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("LoginResponse: ", response);
                        //if correct credentials
                        //response {"tag":"login","error":false,"uid":"abcd","name":"Monil Shah","id":"abcd","branch":"A3PS","room":"G 270","gender":"M","phone":"9553305670","email":"abcd@gmail.com","dob":"1996-07-19","father":"Atul Shah","mother":"Sejal Shah","fmail":"atulshah1965@reddifmail.com","fphone":"9825543307","foccup":"Businessman","mmail":null,"moccup":"Homemaker","hphone":"02613015838","homeadd":"702 Manibhadra Enclave,B\/h Sargam Shopping Center,Parle Point .","city":"Surat","state":"Gujarat","localadd":null,"guardian":null,"gphone":null,"nation":"India","blood":"AB+","bank":"State Bank Of Hyderabad","acno":"62352253278","ifsc":"ifsc1234","pimage":null,"time":"2016-02-03 14:07:21"}

                        //if wrong credentials
                        //{"tag":"login","error":true,"error_msg":"Error occured in Logging In"}

                        try {
                            JSONObject object = new JSONObject(response);
                            if (!object.getBoolean("error")) {
                                editor.putInt("exists",1);
                                editor.putString("name",object.getString("name"));
                                editor.putString("uid",my_id);
                                editor.putString("password" ,password);
                                editor.putString("id",object.getString("id"));

                                editor.commit();
                                checkLogin();

                                /*
                                userTable.deleteAll();
                                object.put("password", password);
                                userTable.createEntry(object);
                                checkLogin();
                                */

                            } else {
                                Toast.makeText(User_Login.this, "Wrong ID or Password", Toast.LENGTH_SHORT).show();
                                dialog.hide();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(User_Login.this, "Wrong Id or Password!!", Toast.LENGTH_SHORT).show();
                            dialog.hide();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.toString());
                        Toast.makeText(User_Login.this, "Please check your Internet connection!", Toast.LENGTH_SHORT).show();
                        dialog.hide();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<>();
                        params.put("tag", "login");
                        params.put("id", my_id);
                        params.put("pwd", password);

                        return params;

                    }
                };


                queue.add(request);


            }
        });

                /*loginUsingApi = APIUtils.getAPIService();
                if (true) {
                    loginUsingApi.loginUser("login",my_id,password).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            Log.e("LoginResponse",response.body().toString());

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                            Log.e("LoginFailure",t.getMessage());
                        }
                    });

                }else{
                    Toast.makeText(User_Login.this, "WRONG", Toast.LENGTH_LONG).show();
                }*/
//                startActivity(intent);
            tvloginFgtPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(User_Login.this, ForgotPassword.class);
                    startActivity(intent);
                }
            });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.hide();
        dialog.dismiss();
    }


    public void checkLogin() {
        /*if (userTable.hasData()) {
            Intent intent = new Intent(User_Login.this, User_Nav.class);
            if (getIntent().getStringExtra("default") != null) {
                intent.putExtra("default", getIntent().getStringArrayExtra("default"));
                if (getIntent().getLongExtra("uploadedTime", -1) != -1) {
                    intent.putExtra("uploadedTime", getIntent().getLongExtra("uploadedTime", -1));
                }
            }
            startActivity(intent);
            finish();
        }
        */
        if(preferences.getInt("exists",0)==1)
        {
            Intent intent = new Intent(User_Login.this, User_Nav.class);
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


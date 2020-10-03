package in.ac.bits_hyderabad.swd.swd.user.activity;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import in.ac.bits_hyderabad.swd.swd.APIConnection.GetDataService;
import in.ac.bits_hyderabad.swd.swd.APIConnection.Login;
import in.ac.bits_hyderabad.swd.swd.APIConnection.UpdateLoginResponse;
import in.ac.bits_hyderabad.swd.swd.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Profile extends AppCompatActivity {


    //UserTable mUserTable;
    TextInputEditText etName, etRoom, etPhn, etId, etDOB, etGender, etNat, etEmail, etBlood, etAddress, etCity, etState, etAadhaar, etPan, etfcomp, etfdesg, etmcomp, etmdesg, etmed_history, etcurrent_med, etCategory, etFname, etFphn, etFemail, etFocc, etMname, etMphn, etMemail, etMocc, etBank, etAccNo, etIfsc;
    ActionBar actionBar;
    FloatingActionButton fabUpdate;
    String uid,password;

    SharedPreferences prefs;
    JSONObject object;
    Boolean updating=false;
    Boolean successfull=false;
    ProgressDialog dialog;


    private Retrofit mRetrofitClient;
    private GetDataService mRetrofitService;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar=findViewById(R.id.profile_toolbar);
        setSupportActionBar(toolbar);
        actionBar=getSupportActionBar();

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        mRetrofitClient = new Retrofit.Builder()
                .baseUrl(getString(R.string.URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mRetrofitService = mRetrofitClient.create(GetDataService.class);


        dialog.create();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        prefs = getApplicationContext().getSharedPreferences("USER_LOGIN_DETAILS", MODE_PRIVATE);
        uid = prefs.getString("uid", null);
        password = prefs.getString("password", null);
        initialize_views();

        //mUserTable=new UserTable(getApplicationContext());
        reload();

        fabUpdate=findViewById(R.id.fabUpdate);
        fabUpdate.setEnabled(false);
        fabUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!updating)
                {
                    onEditClick();
                    updating=true;
                    fabUpdate.setImageResource(R.drawable.ic_save);
                }
                else
                {
                    try {
                        JSONObject updatedObj = getupdatedJSONObject(object);
                        updatetoServer(updatedObj);
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }

                }
            }
        });

    }

    public void reload() {
        Call<Login> call = mRetrofitService.getLoginSuccessful("login", uid, password);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (!response.body().getError()) {
                    setData(response.body());
                        disable_EditText();
                        fabUpdate.setEnabled(true);
                    } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong!!", Toast.LENGTH_SHORT).show();
                        fabUpdate.setEnabled(false);
                    }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Please check your Internet connection!", Toast.LENGTH_SHORT).show();
                fabUpdate.setEnabled(false);
            }
        });
    }

    public void setData(Login login) {
        etName.setText(login.getName());
        etRoom.setText(login.getRoom());
        etPhn.setText(login.getPhone());
        etId.setText(login.getId_no());
        etGender.setText(login.getGender());
        etDOB.setText(login.getDob());
        etAadhaar.setText(login.getAadhaar());
        etPan.setText(login.getPan_card());
        etCategory.setText(login.getCategory());
        etEmail.setText(login.getEmail());
        etBlood.setText(login.getBlood());
        etAddress.setText(login.getHomeadd());
        etCity.setText(login.getCity());
        etState.setText(login.getState());
        etNat.setText(login.getNation());
        etFname.setText(login.getFather());
        etFphn.setText(login.getFphone());
        etFemail.setText(login.getFmail());
        etFocc.setText(login.getFoccup());
        etfcomp.setText(login.getFcomp());
        etfdesg.setText(login.getFdesg());
        etMname.setText(login.getMother());
        etMemail.setText(login.getMmail());
        etMphn.setText(login.getHphone());
        etMocc.setText(login.getMoccup());
        etmcomp.setText(login.getMcomp());
        etmdesg.setText(login.getMdesg());
        etmed_history.setText(login.getMed_history());
        etcurrent_med.setText(login.getCurrent_med());
        etBank.setText(login.getBank());
        etAccNo.setText(login.getAcno());
        etIfsc.setText(login.getIfsc());

        dialog.hide();
    }
    public void disable_EditText() {
        etName.setEnabled(false);
        etRoom.setEnabled(false);
        etPhn.setEnabled(false);
        etId.setEnabled(false);
        etAadhaar.setEnabled(false);
        etPan.setEnabled(false);
        etCategory.setEnabled(false);
        etGender.setEnabled(false);
        etDOB.setEnabled(false);
        etNat.setEnabled(false);
        etIfsc.setEnabled(false);
        etAccNo.setEnabled(false);
        etMocc.setEnabled(false);
        etMphn.setEnabled(false);
        etMemail.setEnabled(false);
        etMname.setEnabled(false);
        etState.setEnabled(false);
        etCity.setEnabled(false);
        etAddress.setEnabled(false);
        etBlood.setEnabled(false);
        etEmail.setEnabled(false);
        etFocc.setEnabled(false);
        etFemail.setEnabled(false);
        etFname.setEnabled(false);
        etFphn.setEnabled(false);
        etBank.setEnabled(false);
        etfcomp.setEnabled(false);
        etfdesg.setEnabled(false);
        etmcomp.setEnabled(false);
        etmdesg.setEnabled(false);
        etmed_history.setEnabled(false);
        etcurrent_med.setEnabled(false);
    }
    public void onEditClick() {

        etRoom.setEnabled(true);
        etPhn.setEnabled(true);
        etIfsc.setEnabled(true);
        etAccNo.setEnabled(true);
        etMocc.setEnabled(true);
        etMphn.setEnabled(true);
        etMemail.setEnabled(true);
        etState.setEnabled(true);
        etCity.setEnabled(true);
        etAddress.setEnabled(true);
        etEmail.setEnabled(true);
        etFocc.setEnabled(true);
        etFemail.setEnabled(true);
        etFphn.setEnabled(true);
        etBank.setEnabled(true);
        etAadhaar.setEnabled(true);
        etPan.setEnabled(true);
        etfcomp.setEnabled(true);
        etfdesg.setEnabled(true);
        etmcomp.setEnabled(true);
        etmdesg.setEnabled(true);
        etmed_history.setEnabled(true);
        etcurrent_med.setEnabled(true);

        etRoom.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(etRoom, InputMethodManager.SHOW_IMPLICIT);

    }
    public static Map<String,String> JSONtoMap(JSONObject object)throws JSONException {
        Map<String, String> map= new HashMap<String, String>();
        if(object !=JSONObject.NULL)
        {
            Iterator<String> keysItr= object.keys();
            while (keysItr.hasNext()) {
                String key = keysItr.next();
                String value = object.getString(key);

                map.put(key, value);
            }
        }
        return map;
    }
    public void initialize_views() {
        etName=findViewById(R.id.etName);
        etRoom=findViewById(R.id.etRoom);
        etPhn=findViewById(R.id.etPhn);
        etId=findViewById(R.id.etId);
        etAadhaar=findViewById(R.id.etAadhaar);
        etPan=findViewById(R.id.etPan);
        etCategory=findViewById(R.id.etCategory);
        etDOB=findViewById(R.id.etDOB);
        etGender=findViewById(R.id.etGender);
        etNat=findViewById(R.id.etNat);
        etEmail=findViewById(R.id.etEmail);
        etBlood=findViewById(R.id.etBlood);
        etAddress=findViewById(R.id.etAddress);
        etCity=findViewById(R.id.etCity);
        etState=findViewById(R.id.etState);
        etFname=findViewById(R.id.etFname);
        etFemail=findViewById(R.id.etFemail);
        etFphn=findViewById(R.id.etFphn);
        etFocc=findViewById(R.id.etFocc);
        etfcomp=findViewById(R.id.etfcomp);
        etfdesg=findViewById(R.id.etfdesg);
        etMname=findViewById(R.id.etMname);
        etMemail=findViewById(R.id.etMemail);
        etMphn=findViewById(R.id.etMphn);
        etMocc=findViewById(R.id.etMocc);
        etmcomp=findViewById(R.id.etmcomp);
        etmdesg=findViewById(R.id.etmdesg);
        etmed_history=findViewById(R.id.etmed_history);
        etcurrent_med=findViewById(R.id.etcurrent_med);
        etBank=findViewById(R.id.etBank);
        etAccNo=findViewById(R.id.etAccNo);
        etIfsc=findViewById(R.id.etIfsc);
    }

    public void updatetoServer(final JSONObject object) throws JSONException {
        dialog.show();
        Map<String, String> params = JSONtoMap(object);
        Call<UpdateLoginResponse> call = mRetrofitService.getUpdateLoginResponse("update_details", uid, password, params);
        call.enqueue(new Callback<UpdateLoginResponse>() {
            @Override
            public void onResponse(Call<UpdateLoginResponse> call, Response<UpdateLoginResponse> response) {
                dialog.hide();
                dialog.dismiss();
                if (!response.body().getError()) {
                    Toast.makeText(getApplicationContext(), "Updated successfully", Toast.LENGTH_SHORT).show();
                    successfull = true;
                    fabUpdate.setImageResource(R.drawable.ic_update_details);
                    updating = false;
                    disable_EditText();
                } else {
                    Toast.makeText(getApplicationContext(), "Unknown error occurred!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateLoginResponse> call, Throwable t) {
                dialog.hide();
                Toast.makeText(getApplicationContext(), "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        dialog.dismiss();
        finish();

    }

    public JSONObject getupdatedJSONObject(JSONObject object) {
        try {
            object.put("room",etRoom.getText().toString().trim());
            object.put("phone",etPhn.getText().toString().trim());
            object.put("ifsc",etIfsc.getText().toString().trim());
            object.put("acno",etAccNo.getText().toString().trim());
            object.put("bank",etBank.getText().toString().trim());
            object.put("state",etState.getText().toString().trim());
            object.put("city",etCity.getText().toString().trim());
            object.put("homeadd",etAddress.getText().toString().trim());
            object.put("email",etEmail.getText().toString().trim());
            object.put("fphone",etFphn.getText().toString().trim());
            object.put("fmail",etFemail.getText().toString().trim());
            object.put("foccup",etFocc.getText().toString().trim());
            object.put("mmail",etMemail.getText().toString().trim());
            object.put("moccup",etMocc.getText().toString().trim());
            object.put("hphone",etMphn.getText().toString().trim());
            object.put("hphone",etMphn.getText().toString().trim());
            object.put("aadhaar",etAadhaar.getText().toString().trim());
            object.put("pan_card",etPan.getText().toString().trim());
            object.put("fcomp",etfcomp.getText().toString().trim());
            object.put("fdesg",etfdesg.getText().toString().trim());
            object.put("mcomp",etmcomp.getText().toString().trim());
            object.put("mdesg",etmdesg.getText().toString().trim());
            object.put("med_history",etmed_history.getText().toString().trim());
            object.put("current_med",etcurrent_med.getText().toString().trim());

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return object;

    }
}

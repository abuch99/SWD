package in.ac.bits_hyderabad.swd.swd.user.activity;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import in.ac.bits_hyderabad.swd.swd.R;

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
    RequestQueue queue;


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

        dialog.create();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        prefs = getApplicationContext().getSharedPreferences("USER_LOGIN_DETAILS", MODE_PRIVATE);
        uid = prefs.getString("uid", null);
        password = prefs.getString("password", null);
        initialize_views();

        //mUserTable=new UserTable(getApplicationContext());
        reload();
        queue = Volley.newRequestQueue(this);

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

        RequestQueue queue = Volley.newRequestQueue(Profile.this);

        StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.BASE_URL), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    object = new JSONObject(response);
                    if (!object.getBoolean("error")) {
                        try {
                            setData(object);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            fabUpdate.setEnabled(false);
                        }
                        disable_EditText();
                        fabUpdate.setEnabled(true);
                    } else {
                        Toast.makeText(Profile.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                        fabUpdate.setEnabled(false);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Profile.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                    fabUpdate.setEnabled(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Profile.this, "Please check your Internet connection!", Toast.LENGTH_SHORT).show();
                fabUpdate.setEnabled(false);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("tag", "login");
                params.put("id", uid );
                params.put("pwd", password);

                return params;

            }
        };


        queue.add(request);


    }

    public void setData(JSONObject object) throws JSONException {
        etName.setText(object.getString("name"));
        etRoom.setText(object.getString("room"));
        etPhn.setText(object.getString("phone"));
        etId.setText(object.getString("id_no"));
        etGender.setText(object.getString("gender"));
        etDOB.setText(object.getString("dob"));
        etAadhaar.setText(object.getString("aadhaar"));
        etPan.setText(object.getString("pan_card"));
        etCategory.setText(object.getString("category"));
        etEmail.setText(object.getString("email"));
        etBlood.setText(object.getString("blood"));
        etAddress.setText(object.getString("homeadd"));
        etCity.setText(object.getString("city"));
        etState.setText(object.getString("state"));
        etNat.setText(object.getString("nation"));
        etFname.setText(object.getString("father"));
        etFphn.setText(object.getString("fphone"));
        etFemail.setText(object.getString("fmail"));
        etFocc.setText(object.getString("foccup"));
        etfcomp.setText(object.getString("fcomp"));
        etfdesg.setText(object.getString("fdesg"));
        etMname.setText(object.getString("mother"));
        etMemail.setText(object.getString("mmail"));
        etMphn.setText(object.getString("hphone"));
        etMocc.setText(object.getString("moccup"));
        etmcomp.setText(object.getString("mcomp"));
        etmdesg.setText(object.getString("mdesg"));
        etmed_history.setText(object.getString("med_history"));
        etcurrent_med.setText(object.getString("current_med"));
        etBank.setText(object.getString("bank"));
        etAccNo.setText(object.getString("acno"));
        etIfsc.setText(object.getString("ifsc"));

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

//      "branch":"","aadhar":null,"pan_card":"EPBPB8351D","category":"General","fcomp":"Indian Army","fdesg":"Ex Havaldar","mcomp":"NIL","mdesg":"NIL",
//      "localadd":"NIL","guardian":"NIL","gphone":"NIL","med_history":"NIL","current_med":"NIL",

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

    public void updatetoServer(final JSONObject object)throws JSONException {
        Log.i("object sent to server", object.toString());
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.BASE_URL), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.hide();
                dialog.dismiss();
                try{
                    JSONObject obj = new JSONObject(response);
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(Profile.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                        successfull=true;
                        fabUpdate.setImageResource(R.drawable.ic_update_details);
                        updating=false;
                        disable_EditText();

                    }
                    else{
                        Toast.makeText(Profile.this, "Unknown error occurred!", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.hide();
                Toast.makeText(Profile.this,"Please Check your Internet Connection",Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                try {
                    Map<String, String> params = JSONtoMap(object);
                    params.put("tag", "update_details");
                    params.put("id",uid);
                    params.put("pwd",password);
//                    Log.e("save sent",params.toString());
                    return params;
                } catch (JSONException e) {
//                    Log.e("Error",e.toString());
                }
                return null;
            }
        };
        queue.add(request);

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

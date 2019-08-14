package in.ac.bits_hyderabad.swd.swd.user.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import java.util.Iterator;
import java.util.Map;

import in.ac.bits_hyderabad.swd.swd.R;

public class Profile extends AppCompatActivity {


    //UserTable mUserTable;
    EditText etName,etRoom,etPhn,etId,etDOB,etGender,etNat,etEmail,etBlood,etAddress,etCity,etState
            ,etFname,etFphn,etFemail,etFocc,etMname,etMphn,etMemail,etMocc,etBank,etAccNo,etIfsc;
    ActionBar actionBar;
    FloatingActionButton fabUpdate;
    String uid,password;

    SharedPreferences prefs;
    JSONObject object;
    Boolean updating=false;
    Boolean successfull=false;
    ProgressDialog dialog;
    RequestQueue queue;
    LinearLayout llProfile;
    SwipeRefreshLayout srProfile;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar=findViewById(R.id.profile_toolbar);
        setSupportActionBar(toolbar);
        actionBar=getSupportActionBar();

        dialog =new ProgressDialog(this);
        dialog.setMessage("Loading");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        dialog.create();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        prefs=getApplicationContext().getSharedPreferences("USER_LOGIN_DETAILS",MODE_PRIVATE);
        uid=prefs.getString("uid",null);
        password=prefs.getString("password",null);
        initialize_views();

        llProfile.setVisibility(View.GONE);
        //mUserTable=new UserTable(getApplicationContext());
        srProfile.setRefreshing(true);
        reload();
        queue= Volley.newRequestQueue(this);

        fabUpdate=findViewById(R.id.fabUpdate);
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
                        JSONObject updatedObj=getupdatedJSONObject(object);
                        updatetoServer(updatedObj);
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }

                }
            }
        });

        srProfile.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                llProfile.setVisibility(View.GONE);
                fabUpdate.setImageResource(R.drawable.ic_update_details);
                updating=false;
                reload();
            }
        });

    }

    public void reload() {

        RequestQueue queue = Volley.newRequestQueue(Profile.this);

        StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.BASE_URL), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //if correct credentials
                //response {"tag":"login","error":false,"uid":"abcd","name":"Monil Shah","id":"abcd","branch":"A3PS","room":"G 270","gender":"M","phone":"9553305670","email":"abcd@gmail.com","dob":"1996-07-19","father":"Atul Shah","mother":"Sejal Shah","fmail":"atulshah1965@reddifmail.com","fphone":"9825543307","foccup":"Businessman","mmail":null,"moccup":"Homemaker","hphone":"02613015838","homeadd":"702 Manibhadra Enclave,B\/h Sargam Shopping Center,Parle Point .","city":"Surat","state":"Gujarat","localadd":null,"guardian":null,"gphone":null,"nation":"India","blood":"AB+","bank":"State Bank Of Hyderabad","acno":"62352253278","ifsc":"ifsc1234","pimage":null,"time":"2016-02-03 14:07:21"}
                //if wrong credentials
                //{"tag":"login","error":true,"error_msg":"Error occured in Logging In"}

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
                        llProfile.setVisibility(View.VISIBLE);
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

                srProfile.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Profile.this, "Please check your Internet connection!", Toast.LENGTH_SHORT).show();
                fabUpdate.setEnabled(false);
                srProfile.setRefreshing(false);
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
    public void setData(JSONObject object)throws JSONException {
        etName.setText(object.getString("name"));
        etRoom.setText(object.getString("room"));
        etPhn.setText(object.getString("phone"));
        etId.setText(object.getString("id_no"));
        etGender.setText(object.getString("gender"));
        etDOB.setText(object.getString("dob"));
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
        etMname.setText(object.getString("mother"));
        etMemail.setText(object.getString("mmail"));
        etMphn.setText(object.getString("hphone"));
        etMocc.setText(object.getString("moccup"));
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            etRoom.setTextColor(getColor(R.color.colorAccent));
            etPhn.setTextColor(getColor(R.color.colorAccent));
            etIfsc.setTextColor(getColor(R.color.colorAccent));
            etAccNo.setTextColor(getColor(R.color.colorAccent));
            etMocc.setTextColor(getColor(R.color.colorAccent));
            etMphn.setTextColor(getColor(R.color.colorAccent));
            etMemail.setTextColor(getColor(R.color.colorAccent));
            etState.setTextColor(getColor(R.color.colorAccent));
            etCity.setTextColor(getColor(R.color.colorAccent));
            etAddress.setTextColor(getColor(R.color.colorAccent));
            etEmail.setTextColor(getColor(R.color.colorAccent));
            etFocc.setTextColor(getColor(R.color.colorAccent));
            etFemail.setTextColor(getColor(R.color.colorAccent));
            etFphn.setTextColor(getColor(R.color.colorAccent));
            etBank.setTextColor(getColor(R.color.colorAccent));
        }
        etRoom.setBackground(getDrawable(R.drawable.edit_text));
        etPhn.setBackground(getDrawable(R.drawable.edit_text));
        etIfsc.setBackground(getDrawable(R.drawable.edit_text));
        etAccNo.setBackground(getDrawable(R.drawable.edit_text));
        etMocc.setBackground(getDrawable(R.drawable.edit_text));
        etMphn.setBackground(getDrawable(R.drawable.edit_text));
        etEmail.setBackground(getDrawable(R.drawable.edit_text));
        etMemail.setBackground(getDrawable(R.drawable.edit_text));
        etState.setBackground(getDrawable(R.drawable.edit_text));
        etCity.setBackground(getDrawable(R.drawable.edit_text));
        etAddress.setBackground((getDrawable(R.drawable.edit_text)));
        etFocc.setBackground((getDrawable(R.drawable.edit_text)));
        etFemail.setBackground((getDrawable(R.drawable.edit_text)));
        etFphn.setBackground(getDrawable(R.drawable.edit_text));
        etBank.setBackground(getDrawable(R.drawable.edit_text));


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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            etRoom.setTextColor(getColor(R.color.black));
            etPhn.setTextColor(getColor(R.color.black));
            etIfsc.setTextColor(getColor(R.color.black));
            etAccNo.setTextColor(getColor(R.color.black));
            etMocc.setTextColor(getColor(R.color.black));
            etMphn.setTextColor(getColor(R.color.black));
            etMemail.setTextColor(getColor(R.color.black));
            etState.setTextColor(getColor(R.color.black));
            etCity.setTextColor(getColor(R.color.black));
            etAddress.setTextColor(getColor(R.color.black));
            etEmail.setTextColor(getColor(R.color.black));
            etFocc.setTextColor(getColor(R.color.black));
            etFemail.setTextColor(getColor(R.color.black));
            etFphn.setTextColor(getColor(R.color.black));
            etBank.setTextColor(getColor(R.color.black));
        }


        etRoom.setBackground(getDrawable(R.drawable.edit_text_selected));
        etPhn.setBackground(getDrawable(R.drawable.edit_text_selected));
        etIfsc.setBackground(getDrawable(R.drawable.edit_text_selected));
        etAccNo.setBackground(getDrawable(R.drawable.edit_text_selected));
        etMocc.setBackground(getDrawable(R.drawable.edit_text_selected));
        etMphn.setBackground(getDrawable(R.drawable.edit_text_selected));
        etEmail.setBackground(getDrawable(R.drawable.edit_text_selected));
        etMemail.setBackground(getDrawable(R.drawable.edit_text_selected));
        etState.setBackground(getDrawable(R.drawable.edit_text_selected));
        etCity.setBackground(getDrawable(R.drawable.edit_text_selected));
        etAddress.setBackground((getDrawable(R.drawable.edit_text_selected)));
        etFocc.setBackground((getDrawable(R.drawable.edit_text_selected)));
        etFemail.setBackground((getDrawable(R.drawable.edit_text_selected)));
        etFphn.setBackground(getDrawable(R.drawable.edit_text_selected));
        etBank.setBackground(getDrawable(R.drawable.edit_text_selected));

        etRoom.setFocusable(true);
        etPhn.setFocusable(true);
        etIfsc.setFocusable(true);
        etAccNo.setFocusable(true);
        etMocc.setFocusable(true);
        etMphn.setFocusable(true);
        etMemail.setFocusable(true);
        etState.setFocusable(true);
        etCity.setFocusable(true);
        etAddress.setFocusable(true);
        etEmail.setFocusable(true);
        etFocc.setFocusable(true);
        etFemail.setFocusable(true);
        etFphn.setFocusable(true);
        etBank.setFocusable(true);


        etRoom.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
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

        llProfile=findViewById(R.id.llProfile);
        srProfile=findViewById(R.id.srProfile);
        etName=findViewById(R.id.etName);
        etRoom=findViewById(R.id.etRoom);
        etPhn=findViewById(R.id.etPhn);
        etId=findViewById(R.id.etId);
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
        etMname=findViewById(R.id.etMname);
        etMemail=findViewById(R.id.etMemail);
        etMphn=findViewById(R.id.etMphn);
        etMocc=findViewById(R.id.etMocc);
        etBank=findViewById(R.id.etBank);
        etAccNo=findViewById(R.id.etAccNo);
        etIfsc=findViewById(R.id.etIfsc);
    }

    public void updatetoServer(final JSONObject object)throws JSONException {
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
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return object;

    }
}

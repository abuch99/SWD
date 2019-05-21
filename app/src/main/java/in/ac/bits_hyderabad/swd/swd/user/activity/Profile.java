package in.ac.bits_hyderabad.swd.swd.user.activity;


import android.app.ProgressDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import in.ac.bits_hyderabad.swd.swd.databases.UserTable;

public class Profile extends AppCompatActivity  {


    UserTable mUserTable;
    EditText etName,etRoom,etPhn,etId,etDOB,etGender,etNat,etEmail,etBlood,etAddress,etCity,etState
            ,etFname,etFphn,etFemail,etFocc,etMname,etMphn,etMemail,etMocc,etBank,etAccNo,etIfsc;
    ActionBar actionBar;

    FloatingActionButton fabUpdate;

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

        dialog =new ProgressDialog(this);
        dialog.setMessage("Loading");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        dialog.create();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

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


        mUserTable=new UserTable(getApplicationContext());
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
                    try{
                            JSONObject object=mUserTable.getJsonObject();
                            Log.e("fabupdateonclick_beforesave",object.toString());
                            object=saveDatatoJson(object);
                            Log.e("fabupdateonclick_aftersave",object.toString());
                            disable_EditText();
                            updatetoServer(object);
                            mUserTable.createEntry(object);
                            Log.e("chk",mUserTable.getJsonObject().toString());
                            fabUpdate.setImageResource(R.drawable.ic_update_details);
                            updating=false;
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
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
        finish();
        super.onBackPressed();
    }
    public void setData()throws JSONException
    {
        JSONObject object=new JSONObject();
        object=mUserTable.getJsonObject();

        etName.setText(object.getString(mUserTable.KEY_NAME));
        etRoom.setText(object.getString(mUserTable.KEY_ROOM));
        etPhn.setText(object.getString(mUserTable.KEY_PHONE));
        etId.setText(object.getString(mUserTable.KEY_ID));
        etGender.setText(object.getString(mUserTable.KEY_GENDER));
        etDOB.setText(object.getString(mUserTable.KEY_DOB));
        etEmail.setText(object.getString(mUserTable.KEY_EMAIL));
        etBlood.setText(object.getString(mUserTable.KEY_BLOOD));
        etAddress.setText(object.getString(mUserTable.KEY_HOMEADD));
        etCity.setText(object.getString(mUserTable.KEY_CITY));
        etState.setText(object.getString(mUserTable.KEY_STATE));
        etNat.setText(object.getString(mUserTable.KEY_NATION));
        etFname.setText(object.getString(mUserTable.KEY_FATHER));
        etFphn.setText(object.getString(mUserTable.KEY_FPHONE));
        etFemail.setText(object.getString(mUserTable.KEY_FMAIL));
        etFocc.setText(object.getString(mUserTable.KEY_FOCCUP));
        etMname.setText(object.getString(mUserTable.KEY_MOTHER));
        etMemail.setText(object.getString(mUserTable.KEY_MMAIL));
        etMphn.setText(object.getString(mUserTable.KEY_HPHONE));
        etMocc.setText(object.getString(mUserTable.KEY_MOCCUP));
        etBank.setText(object.getString(mUserTable.KEY_BANK));
        etAccNo.setText(object.getString(mUserTable.KEY_ACNO));
        etIfsc.setText(object.getString(mUserTable.KEY_IFSC));


    }
    public void reload() {
        try {
            setData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        disable_EditText();
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
    }

    public void onEditClick()
    {
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

    }
    public static Map<String,String> JSONtoMap(JSONObject object)throws JSONException
    {
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

    public JSONObject saveDatatoJson(JSONObject object)throws JSONException
    {
        object.put(mUserTable.KEY_ROOM,etRoom.getText().toString().trim());
        object.put(mUserTable.KEY_PHONE,etPhn.getText().toString().trim());
        object.put(mUserTable.KEY_IFSC,etIfsc.getText().toString().trim());
        object.put(mUserTable.KEY_ACNO,etAccNo.getText().toString().trim());
        object.put(mUserTable.KEY_MOCCUP,etMocc.getText().toString().trim());
        object.put(mUserTable.KEY_HPHONE,etMphn.getText().toString().trim());
        object.put(mUserTable.KEY_MMAIL,etMemail.getText().toString().trim());
        object.put(mUserTable.KEY_STATE,etState.getText().toString().trim());
        object.put(mUserTable.KEY_CITY,etCity.getText().toString().trim());
        object.put(mUserTable.KEY_HOMEADD,etAddress.getText().toString().trim());
        object.put(mUserTable.KEY_EMAIL,etEmail.getText().toString().trim());
        object.put(mUserTable.KEY_FOCCUP,etFocc.getText().toString().trim());
        object.put(mUserTable.KEY_FMAIL,etFemail.getText().toString().trim());
        object.put(mUserTable.KEY_BANK,etBank.getText().toString().trim());

        return object;
    }
    public void updatetoServer(final JSONObject object)throws JSONException {
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.BASE_URL), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Updateresponse",response);
                dialog.hide();
                dialog.dismiss();
                try{
                    JSONObject obj = new JSONObject(response);
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(Profile.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                        mUserTable.deleteAll();
                        successfull=true;
                        Log.e("obj in table before change",mUserTable.getJsonObject().toString());
                        Log.e("object to be created",object.toString());
                        Log.e("obj in table after change",mUserTable.getJsonObject().toString());
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


}

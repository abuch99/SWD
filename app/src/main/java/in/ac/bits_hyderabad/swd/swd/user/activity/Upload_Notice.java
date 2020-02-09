package in.ac.bits_hyderabad.swd.swd.user.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import in.ac.bits_hyderabad.swd.swd.R;
import in.ac.bits_hyderabad.swd.swd.helper.VolleyMultipartRequest;

import static java.security.AccessController.getContext;

public class Upload_Notice extends AppCompatActivity {

    TextInputLayout tilCategory, tilName, tilId, tilTitle, tilText, tilLink, tilImage, tilExpiry;
    TextInputEditText tietName, tietId, tietTitle, tietText, tietLink, tietImage, tietExpiry;
    SharedPreferences prefs;
    Toolbar toolbar;
    ProgressDialog dialog;
    private Uri filePath;
    private Bitmap bitmap;
    MaterialButton  btnUpload;
    LinearLayout llUpNotice;
    ImageView ivNotice;
    SwipeRefreshLayout srUpNotice;
    final int PICK_IMAGE_REQUEST=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload__notice);

        toolbar = findViewById(R.id.upNoticeToolbar);
        toolbar.setTitle("Upload Notice");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        tilName = findViewById(R.id.tilName);
        tilId = findViewById(R.id.tilId);
        tilTitle = findViewById(R.id.tilTitle);
        tilCategory = findViewById(R.id.tilCategory);
        tilText = findViewById(R.id.tilText);
        tilLink = findViewById(R.id.tilLink);
        tilImage=findViewById(R.id.tilImage);
        tilExpiry=findViewById(R.id.tilExpiry);
        llUpNotice=findViewById(R.id.llUpNotice);
        ivNotice=findViewById(R.id.ivNotice);
        btnUpload=findViewById(R.id.btnUpload);

        dialog = new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setInverseBackgroundForced(false);

        tietName = tilName.findViewById(R.id.tietName);
        tietId = tilId.findViewById(R.id.tietId);
        tietTitle = tilTitle.findViewById(R.id.tietTitle);
        AutoCompleteTextView autoCompleteTextView = tilCategory.findViewById(R.id.categExpDropdown);
        tietText = tilText.findViewById(R.id.tietText);
        tietImage= tilImage.findViewById(R.id.tietImage);
        tietLink = tilLink.findViewById(R.id.tietLink);
        tietExpiry= tilExpiry.findViewById(R.id.tietExpiry);

        tilImage.bringChildToFront(tietImage);

        prefs = getApplicationContext().getSharedPreferences("USER_LOGIN_DETAILS", MODE_PRIVATE);

        tietName.setText(prefs.getString("name", null));
        tietId.setText(prefs.getString("id", null));

        ArrayList<String> CATEGORIES = new ArrayList<>();
        CATEGORIES = getCategories();


        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(
                        this,
                        R.layout.notice_category_popup,
                        CATEGORIES);

        autoCompleteTextView.setAdapter(adapter);

        tietImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage();
            }
        });

        tietExpiry.setText("DD/MM/YYYY");

        tietExpiry.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        cal.set(Calendar.MONTH, mon-1);
                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    tietExpiry.setText(current);
                    tietExpiry.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filePath != null && !filePath.equals(Uri.EMPTY)) {
                    uploadBitmap(bitmap);
                }
                else {
                    dialog.setMessage("Uploading Notice");
                    dialog.show();
                    uploadNotice("");
                }
            }
        });



    }




    public ArrayList<String> getCategories() {
        RequestQueue queue = Volley.newRequestQueue(Upload_Notice.this);


        ArrayList<String> categories = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.POST, getResources().getString(R.string.UPLOAD_NOTICE_URL), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray arr = obj.getJSONArray("categories");
                    for (int i = 0; i < arr.length(); i++) {
                        categories.add(arr.getString(i));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Upload_Notice.this, "Please check your Internet connection!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("tag", "getCategories");
                return map;

            }
        };


        queue.add(request);

        return categories;

    }

    public void getImage(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                Cursor returnCursor =
                        getContentResolver().query(filePath, null, null, null, null);

                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                returnCursor.moveToFirst();

                String size=Long.toString(returnCursor.getLong(sizeIndex));
                String name=returnCursor.getString(nameIndex);

                if(Long.valueOf(size)>300*1024) {
                    tilImage.setHelperTextEnabled(false);
                    ivNotice.setImageBitmap(null);
                    tietImage.setText(null);
                    tilImage.setError("Image exceeding Size limit- max 300KB");
                }
                else {
                    tilImage.setErrorEnabled(false);
                    tilImage.setHelperTextEnabled(true);
                    tilImage.setHelperText("Max Size (300KB)");
                    ivNotice.setImageBitmap(bitmap);
                    tietImage.setText(name);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void uploadBitmap(final Bitmap bitmap) {

        dialog.setMessage("Uploading Image");
        dialog.show();
        final String tags="newImage";
        final String imagename=prefs.getString("uid",null)+"_"+ System.currentTimeMillis() + ".png";

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST,"http://swd.bits-hyderabad.ac.in/swd_app/api.php?apicall=uploadpic" ,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {

                            JSONObject obj = new JSONObject(new String(response.data));
                            if(!obj.getBoolean("error")){
                                uploadNotice(imagename);

                            }
                            else{
                                Toast.makeText(getApplicationContext(),"kuch to gadbad hai daya!!",Toast.LENGTH_LONG).show();
                                dialog.cancel();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),"Ashish hai bakchod",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                            dialog.cancel();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getClass().getName(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tags", tags);
                return params;
            }
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                params.put("pic", new DataPart(imagename, getFileDataFromDrawable(bitmap)));
                return params;
            }
        };
        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public void uploadNotice(String imagename){
        dialog.setMessage("Uploading Notice");

        String hosterName=tietName.getText().toString().trim();
        String hosterID=prefs.getString("uid",null);
        String title=tietTitle.getText().toString().trim();
        String category=tilCategory.getEditText().getText().toString().trim();
        String text=tietText.getText().toString().trim();
        String link=tietLink.getText().toString().trim();
        String image=imagename;
        String expiry=tietExpiry.getText().toString().trim();


        RequestQueue queue = Volley.newRequestQueue(Upload_Notice.this);
        StringRequest request = new StringRequest(Request.Method.POST, getResources().getString(R.string.UPLOAD_NOTICE_URL), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object=new JSONObject(response);
                    if(!object.getBoolean("error")){
                        Toast.makeText(Upload_Notice.this,"Notice uploaded successfully",Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Upload_Notice.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Upload_Notice.this, "Please check your Internet connection!", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("tag", "uploadnotice");
                map.put("uname",hosterName );
                map.put("uid", hosterID);
                map.put("title",title);
                map.put("category",category);
                map.put("text",text);
                map.put("link",link);
                map.put("image",image);
                map.put("expiry",expiry);
                return map;

            }
        };


        queue.add(request);



    }

}

package in.ac.bits_hyderabad.swd.swd.user.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.andreilisun.swipedismissdialog.SwipeDismissDialog;
import com.google.gson.JsonObject;
import com.itextpdf.text.pdf.parser.Line;
import com.jsibbold.zoomage.ZoomageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import in.ac.bits_hyderabad.swd.swd.R;
import in.ac.bits_hyderabad.swd.swd.helper.Goodies;

public class OrderGoodie extends AppCompatActivity {

    private final int FUNDRAISER_TYPE=1;
    private final int TSHIRT_TYPE=2;
    private final int WORKSHOP_TYPE=3;
    private final int IDCARD_TYPE=4;
    private final int FINGERPRINT_REQUEST=101;

    int goodie_type;
    Dialog dialogSummary;

    SharedPreferences prefs;
    final String url="http://swd.bits-hyderabad.ac.in/swd_app/index.php";
    ArrayList <EditText> sizes=new ArrayList<>();
    ArrayList <String> sizes_available=new ArrayList<>();
    Boolean AT_LEAST_ONE_TSHIRT_SIZE_ORDERD=false;

    ProgressDialog dialog;
    Goodies goodie;
    Boolean firstTime=true;
    JSONObject previous;
    Button btnok;
    Boolean fundraiser;
    Toolbar toolbar;
    TextView tvHost,tvPrice,tvSizeChart,tvGoodieOrderName,tvminamt,tvmaxamt,tvQty, tvTotalPrice;
    RelativeLayout rlxs,rls,rlm,rll,rlxl,rlxxl,rlxxxl,rlqty,rlcustom,rlminamt_fraiser,rlmaxamt_fraiser;
    EditText etxsQty,etsQty,etmQty,etlQty,etxlQty,etxxlQty,etxxxlQty,etCustom,etQty;
    CheckBox cbAgree;
    Button btnOrder;
    ImageView ivImageOrder;
    LinearLayout llsize,llprice,llSummary;
    int leftItems=99999999;
    int totalqty;
    Boolean tshirt=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_goodie);

        toolbar = findViewById(R.id.orderGoodiesToolbar);
        toolbar.setTitle("Order Goodies");

        prefs = getApplicationContext().getSharedPreferences("USER_LOGIN_DETAILS", MODE_PRIVATE);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initViews();
        btnOrder.setVisibility(View.GONE);
        rlminamt_fraiser.setVisibility(View.GONE);
        rlmaxamt_fraiser.setVisibility(View.GONE);

        btnOrder.setEnabled(true);

        dialog=new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCanceledOnTouchOutside(false);



        dialogSummary=new Dialog(OrderGoodie.this);
        dialogSummary.setContentView(R.layout.dialog_summary);
        dialogSummary.setCanceledOnTouchOutside(false);
        dialogSummary.setCancelable(false);
        dialogSummary.create();

        btnok=dialogSummary.findViewById(R.id.btnok);

        final Intent intent = getIntent();
        goodie = intent.getParcelableExtra("goodieClicked");
        try {
            previous = new JSONObject(intent.getStringExtra("previous"));
            leftItems = intent.getIntExtra("items_left", 99999999);
            firstTime = previous.getBoolean("first_time");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        sizes_available.add(goodie.getXs());
        sizes_available.add(goodie.getS());
        sizes_available.add(goodie.getM());
        sizes_available.add(goodie.getL());
        sizes_available.add(goodie.getXl());
        sizes_available.add(goodie.getXxl());
        sizes_available.add(goodie.getXxxl());


        tvGoodieOrderName.setText(goodie.getName());
        tvHost.setText(goodie.getHost());
        tvPrice.setText(goodie.getPrice());


        fundraiser = false;


        Picasso.get().load("http://swd.bits-hyderabad.ac.in/goodies/img/" + goodie.getImage())
                .resize(125, 125)
                .placeholder(R.drawable.ic_loading)
                .centerInside().error(R.drawable.ic_error)
                .into(ivImageOrder);


        if (!goodie.getSize_chart().isEmpty()) {
            tvSizeChart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dialog = new Dialog(OrderGoodie.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                    dialog.setContentView(R.layout.dialog_my);
                    dialog.setCanceledOnTouchOutside(true);

                    ZoomageView image = dialog.findViewById(R.id.ivFullGoodieImage);

                    Uri imageUri = Uri.parse("http://swd.bits-hyderabad.ac.in/goodies/img/" + goodie.getSize_chart());        //add full link here
                    Picasso.get().load(imageUri)
                            .resize(700, 700)
                            .centerInside()
                            .placeholder(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                            .into(image);
                    dialog.show();

                }
            });
        } else {
            llsize.setVisibility(View.GONE);
        }


        ivImageOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView image;
                Dialog myDialog = new Dialog(OrderGoodie.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                myDialog.setContentView(R.layout.dialog_my);
                myDialog.getWindow().setBackgroundDrawableResource(R.color.semiTransparentColor99black);
                image = myDialog.findViewById(R.id.ivFullGoodieImage);


                String ImageUrl = "http://swd.bits-hyderabad.ac.in/goodies/img/" + goodie.getImage();

                Picasso.get().load(ImageUrl)
                        .resize(1500, 1500)
                        .centerInside()
                        .placeholder(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                        .into(image);
                myDialog.show();

            }
        });

        //KYA HI CHUTIYAAPP HAI YE
        if (goodie.getXs().equals("0")) {
            rlxs.setVisibility(View.GONE);
        }
        if (goodie.getS().equals("0")) {
            rls.setVisibility(View.GONE);
        }
        if (goodie.getM().equals("0")) {
            rlm.setVisibility(View.GONE);
        }
        if (goodie.getL().equals("0")) {
            rll.setVisibility(View.GONE);
        }
        if (goodie.getXl().equals("0")) {
            rlxl.setVisibility(View.GONE);
        }
        if (goodie.getXxl().equals("0")) {
            rlxxl.setVisibility(View.GONE);
        }
        if (goodie.getXxxl().equals("0")) {
            rlxxxl.setVisibility(View.GONE);
        }
        if (goodie.getCustom().equals("0")) {
            rlcustom.setVisibility(View.GONE);
        }
        if (goodie.getQut().equals("0")) {
            rlqty.setVisibility(View.GONE);
        }
        if (!(goodie.getMin_amount().equals("0") && goodie.getMax_amount().equals("0"))) {
            fundraiser = true;
            tvQty.setText("Amount");
            llprice.setVisibility(View.GONE);
            rlminamt_fraiser.setVisibility(View.VISIBLE);
            tvminamt.setText(goodie.getMin_amount());
            rlmaxamt_fraiser.setVisibility(View.VISIBLE);
            tvmaxamt.setText(goodie.getMax_amount());
        }

        cbAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    if (fundraiser) {
                        goodie_type=FUNDRAISER_TYPE;
                        if (!etQty.getText().toString().isEmpty()) {
                            int entered_amt = Integer.parseInt(etQty.getText().toString());
                            int min = Integer.parseInt(goodie.getMin_amount());
                            int max = Integer.parseInt(goodie.getMax_amount());
                            if (!(entered_amt >= min && entered_amt <= max)) {
                                Toast.makeText(OrderGoodie.this, "Please enter an amount in the given limits!", Toast.LENGTH_SHORT).show();
                                cbAgree.setChecked(false);
                            } else {
                                chkPassed(FUNDRAISER_TYPE,entered_amt);
                                //auth_identity(FUNDRAISER_TYPE);
                            }
                        } else {
                            etQty.setError("You cannot leave this field empty");
                            cbAgree.setChecked(false);
                        }

                    }
                    //goodie is not a fundraiser...REALLY TATTTTTTIIII DB (-_-)
                    else {

                        for (String str : sizes_available) {
                            if (str.equals("1")) {
                                tshirt = true;
                                break;
                            }
                        }

                        // goodie is a t shirt type (needs a size)
                        if (goodie.getQut().equals("0") && tshirt) {
                            goodie_type=TSHIRT_TYPE;
                            totalqty=0;
                            for (int i = 0; i < sizes.size(); i++) {
                                if (!sizes.get(i).getText().toString().trim().isEmpty()) {
                                    AT_LEAST_ONE_TSHIRT_SIZE_ORDERD = true;
                                    totalqty += Integer.parseInt(sizes.get(i).getText().toString().trim());
                                }
                            }

                            if (AT_LEAST_ONE_TSHIRT_SIZE_ORDERD) {
                                //AT_LEAST_ONE_TSHIRT_SIZE_ORDERD = false;
                                int amount=totalqty*Integer.parseInt(goodie.getPrice().substring(2));
                                chkPassed(TSHIRT_TYPE,amount);
                                //auth_identity(TSHIRT_TYPE);
                            } else {
                                Toast.makeText(OrderGoodie.this, "Enter quantity for at least one size!!", Toast.LENGTH_SHORT).show();
                                cbAgree.setChecked(false);

                            }

                        }
                        //goodie is id card , lunch, workshop type(does not need size) REALLY FUCK DB (-_-)
                        else if (goodie.getQut().equals("1")) {
                            goodie_type= WORKSHOP_TYPE;
                            if (!etQty.getText().toString().trim().isEmpty()) {
                                totalqty = Integer.parseInt(etQty.getText().toString().trim());
                                int amount=totalqty*Integer.parseInt(goodie.getPrice().substring(2));
                                chkPassed(WORKSHOP_TYPE,amount);
                                //auth_identity(WORKSHOP_TYPE);
                            } else {
                                Toast.makeText(OrderGoodie.this, "Please enter the quantity", Toast.LENGTH_SHORT).show();
                                cbAgree.setChecked(false);

                            }

                        } else {
                            goodie_type=IDCARD_TYPE;
                           chkPassed(IDCARD_TYPE,Integer.parseInt(goodie.getPrice().substring(2)));
                            //auth_identity(IDCARD_TYPE);
                        }
                    }
                } else {
                    btnOrder.setVisibility(View.GONE);
                    llSummary.setVisibility(View.GONE);
                }
            }
        });


        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnOrder.setEnabled(false);

                auth_identity(FINGERPRINT_REQUEST);

            }
        });
        if (!firstTime){
            try {
                setEditTexts_previous(previous);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK )
        {
            dialog.show();
            placeOrder();
        }
        else
        {
            Toast.makeText(this, "Failure: Unable to verify user's identity", Toast.LENGTH_SHORT).show();
        }
    }

    public void auth_identity(int REQUEST_CODE) {
        KeyguardManager km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

        if (km.isKeyguardSecure()) {
            Intent i = km.createConfirmDeviceCredentialIntent("Authentication required","");
            startActivityForResult(i, REQUEST_CODE);
        } else
            Toast.makeText(OrderGoodie.this, "No any security setup done by user(pattern or password or pin or fingerprint)", Toast.LENGTH_SHORT).show();
    }


    public void placeOrder(){

        String u_id=prefs.getString("uid","");
        String full_id=prefs.getString("id","");
        String  u_name=prefs.getString("name","");
        String pwd= prefs.getString("password","");
        String g_id=goodie.getId();
        String custom_name=etCustom.getText().toString();
        int acceptance=0;
        int xs=convertToInt(etxsQty.getText().toString());
        int s=convertToInt(etsQty.getText().toString());
        int m=convertToInt(etmQty.getText().toString());
        int l=convertToInt(etlQty.getText().toString());
        int xl=convertToInt(etxlQty.getText().toString());
        int xxl=convertToInt(etxxlQty.getText().toString());
        int xxxl=convertToInt(etxxxlQty.getText().toString());

        int qut=convertToInt(etQty.getText().toString());
        if(g_id.equals("1000")){
            qut=1;
        }
        int net_qut=xs+s+m+l+xl+xxl+xxxl+qut;
        int net_price=net_qut*Integer.parseInt(goodie.getPrice().substring(2));

        //g_id,u_id,full_id,u_name,custom_name,xs,s,m,l,xl,xxl,xxxl,qut,acceptance,net_qut,net_price
        JSONObject obj=new JSONObject();
        try {
            obj.put("u_id",u_id);
            obj.put("full_id",full_id);
            obj.put("u_name",u_name);
            obj.put("g_id",g_id);
            obj.put("acceptance",String.valueOf(acceptance));
            obj.put("xs",String.valueOf(xs));
            obj.put("s",String.valueOf(s));
            obj.put("m",String.valueOf(m));
            obj.put("l",String.valueOf(l));
            obj.put("xl",String.valueOf(xl));
            obj.put("xxl",String.valueOf(xxl));
            obj.put("xxxl",String.valueOf(xxxl));
            obj.put("custom_name",custom_name);
            obj.put("qut",String.valueOf(qut));
            obj.put("net_qut",String.valueOf(net_qut));
            obj.put("net_price",String.valueOf(net_price));
            if(firstTime) {
                obj.put("type", "0");
            }
            else
                obj.put("type","1");

        } catch (JSONException e) {
            e.printStackTrace();
            dialog.cancel();
            Toast.makeText(OrderGoodie.this,"Something went wrong!",Toast.LENGTH_SHORT).show();
        }

        sendRequest(obj);
    }

    public int convertToInt(String str){

        if(str.isEmpty()){
            return 0;
        }
        return Integer.parseInt(str);
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

    private void sendRequest(JSONObject obj){

        String u_id=prefs.getString("uid","");
        String pwd= prefs.getString("password","");


        RequestQueue queue = Volley.newRequestQueue(OrderGoodie.this);

        StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.BASE_URL), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("res",response);
                dialog.cancel();
                try {
                    JSONObject res=new JSONObject(response);
                    if(res.getString("error").equalsIgnoreCase("false")) {
                        String msg = res.getString("msg");
                        firstTime=false;
                        btnOrder.setText("UPDATE");
                        Toast.makeText(OrderGoodie.this, msg, Toast.LENGTH_SHORT).show();
                        showOrderSummary(obj,goodie);
                    }
                    else
                    {
                        Toast.makeText(OrderGoodie.this, "Something went wrong! Please contact SWD", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(OrderGoodie.this, "Something went wrong! Please contact SWD1", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.cancel();
                Toast.makeText(OrderGoodie.this, "Please check your Internet connection!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map=new HashMap<>();
                try {
                    map=JSONtoMap(obj);

                    map.put("tag","place_order");
                    map.put("id",u_id);
                    map.put("pwd",pwd);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return  map;

            }
        };


        queue.add(request);
    }

    public void setEditTexts_previous(JSONObject object) throws JSONException {

        btnOrder.setText("UPDATE");
        etxsQty.setText(object.getString("xs"));
        etsQty.setText(object.getString("s"));
        etmQty.setText(object.getString("m"));
        etlQty.setText(object.getString("l"));
        etxlQty.setText(object.getString("xl"));
        etxxlQty.setText(object.getString("xxl"));
        etxxxlQty.setText(object.getString("xxxl"));
        etCustom.setText(object.getString("custom_name"));
        etQty.setText(object.getString("qut"));


    }

    private void initViews() {
        tvHost=findViewById(R.id.tvHost);
        tvPrice=findViewById(R.id.tvPrice);
        llsize=findViewById(R.id.llSize);
        llprice=findViewById(R.id.llprice);
        llSummary=findViewById(R.id.llSummary);
        tvSizeChart=findViewById(R.id.tvSizeChart);
        tvGoodieOrderName=findViewById(R.id.tvGoodieOrderName);
        tvminamt=findViewById(R.id.tvminamt);
        tvmaxamt=findViewById(R.id.tvmaxamt);
        tvQty=findViewById(R.id.tvqty);
        tvTotalPrice=findViewById(R.id.tvTotalPrice);
        rlxs=findViewById(R.id.rlxs);
        rls=findViewById(R.id.rls);
        rlm=findViewById(R.id.rlm);
        rll=findViewById(R.id.rll);
        rlxl=findViewById(R.id.rlxl);
        rlxxl=findViewById(R.id.rlxxl);
        rlxxxl=findViewById(R.id.rlxxxl);
        rlminamt_fraiser=findViewById(R.id.rlminamt_fraiser);
        rlmaxamt_fraiser=findViewById(R.id.rlmaxamt_fraiser);
        rlqty=findViewById(R.id.rlqty);
        rlcustom=findViewById(R.id.rlcustom);
        etxsQty=findViewById(R.id.etxsQty);
        etsQty=findViewById(R.id.etsQty);
        etmQty=findViewById(R.id.etmQty);
        etlQty=findViewById(R.id.etlQty);
        etxlQty=findViewById(R.id.etxlQty);
        etxxlQty=findViewById(R.id.etxxlQty);
        etxxxlQty=findViewById(R.id.etxxxlQty);
        etQty=findViewById(R.id.etQty);
        etCustom=findViewById(R.id.etCustomName);
        cbAgree=findViewById(R.id.cbAgree);
        btnOrder=findViewById(R.id.btnOrder);
        ivImageOrder=findViewById(R.id.ivimageOrder);

        sizes.add(etxsQty);
        sizes.add(etsQty);
        sizes.add(etmQty);
        sizes.add(etlQty);
        sizes.add(etxlQty);
        sizes.add(etxxlQty);
        sizes.add(etxxxlQty);

        llSummary.setVisibility(View.GONE);

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {

        this.finishAfterTransition();
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        btnOrder.setEnabled(true);
        super.onResume();
    }

    public void chkPassed(int type, int amt){

        TextView tvAmount=findViewById(R.id.tvTotalPrice);
        String amount=" ₹ "+amt;
        tvAmount.setText(amount);

        btnOrder.setEnabled(true);
        InputMethodManager imm = (InputMethodManager) OrderGoodie.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = OrderGoodie.this.getCurrentFocus();
        if (view == null) {
            view = new View(OrderGoodie.this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        llSummary.setVisibility(View.VISIBLE);
        btnOrder.setVisibility(View.VISIBLE);

    }

    public void showOrderSummary(JSONObject object,Goodies goodie) throws JSONException {

        Log.e("obj",object.toString());

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialogSummary.show();
        dialogSummary.getWindow().setLayout((6 * width)/7, ViewGroup.LayoutParams.WRAP_CONTENT);

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("okpressed","");
                OrderGoodie.this.finishAfterTransition();
            }
        });

        ImageView ivSummaryGoodie=dialogSummary.findViewById(R.id.ivSummaryGoodieImage);
        String ImageUrl = "http://swd.bits-hyderabad.ac.in/goodies/img/" + goodie.getImage();

        Picasso.get().load(ImageUrl)
                .resize(400,300)
                .placeholder(R.drawable.ic_loading)
                .centerInside()
                .error(R.drawable.ic_error)
                .into(ivSummaryGoodie);

        TextView tvsummary_name=dialogSummary.findViewById(R.id.tvsummary_name);
        TextView tvsummary_time=dialogSummary.findViewById(R.id.tvsummary_ordertime);
        TextView tvsummary_xs=dialogSummary.findViewById(R.id.tvsummary_xs);
        TextView tvsummary_s=dialogSummary.findViewById(R.id.tvsummary_s);
        TextView tvsummary_m=dialogSummary.findViewById(R.id.tvsummary_m);
        TextView tvsummary_l=dialogSummary.findViewById(R.id.tvsummary_l);
        TextView tvsummary_xl=dialogSummary.findViewById(R.id.tvsummary_xl);
        TextView tvsummary_xxl=dialogSummary.findViewById(R.id.tvsummary_xxl);
        TextView tvsummary_xxxl=dialogSummary.findViewById(R.id.tvsummary_xxxl);
        TextView tvsummary_price=dialogSummary.findViewById(R.id.tvsummary_price);
        TextView tvsummary_qty=dialogSummary.findViewById(R.id.tvsummary_qty);
        TextView tvsummary_netQty=dialogSummary.findViewById(R.id.tvsummary_netQty);
        TextView tvsummary_netPrice=dialogSummary.findViewById(R.id.tvsummary_netPrice);


        LinearLayout llsummary_price=dialogSummary.findViewById(R.id.llsummary_price);
        LinearLayout llsummary_xs=dialogSummary.findViewById(R.id.llsummary_xs);
        LinearLayout llsummary_s=dialogSummary.findViewById(R.id.llsummary_s);
        LinearLayout llsummary_m=dialogSummary.findViewById(R.id.llsummary_m);
        LinearLayout llsummary_l=dialogSummary.findViewById(R.id.llsummary_l);
        LinearLayout llsummary_xl=dialogSummary.findViewById(R.id.llsummary_xl);
        LinearLayout llsummary_xxl=dialogSummary.findViewById(R.id.llsummary_xxl);
        LinearLayout llsummary_xxxl=dialogSummary.findViewById(R.id.llsummary_xxxl);
        LinearLayout llsummary_qty=dialogSummary.findViewById(R.id.llsummary_qty);
        LinearLayout llsummary_netQty=dialogSummary.findViewById(R.id.llsummary_netQty);
        LinearLayout llsummary_netPrice=dialogSummary.findViewById(R.id.llsummary_netPrice);


        ArrayList<LinearLayout>layouts=new ArrayList<>();
        layouts.add(llsummary_price);
        layouts.add(llsummary_xs);
        layouts.add(llsummary_s);
        layouts.add(llsummary_m);
        layouts.add(llsummary_l);
        layouts.add(llsummary_xl);
        layouts.add(llsummary_xxl);
        layouts.add(llsummary_xxxl);
        layouts.add(llsummary_qty);
        layouts.add(llsummary_netQty);

        for(LinearLayout ll:layouts){
            ll.setVisibility(View.GONE);
        }

        tvsummary_name.setText(goodie.getName());
        tvsummary_time.setText(new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.ENGLISH).format(Calendar.getInstance().getTime()));
        String netPrice="₹ "+object.getString("net_price");

        tvsummary_netPrice.setText(netPrice);

        if(!object.getString("xs").equals("0")) {
            llsummary_xs.setVisibility(View.VISIBLE);
            tvsummary_xs.setText(object.getString("xs"));
        }
        if(convertToInt(object.getString("s"))!=0) {
            llsummary_s.setVisibility(View.VISIBLE);
            tvsummary_s.setText(object.getString("s"));
        }
        if(convertToInt(object.getString("m"))!=0) {
            llsummary_m.setVisibility(View.VISIBLE);
            tvsummary_m.setText(object.getString("m"));
        }
        if(convertToInt(object.getString("l"))!=0) {
            llsummary_l.setVisibility(View.VISIBLE);
            tvsummary_l.setText(object.getString("l"));
        }
        if(convertToInt(object.getString("xl"))!=0) {
            llsummary_xl.setVisibility(View.VISIBLE);
            tvsummary_xl.setText(object.getString("xl"));
        }
        if(convertToInt(object.getString("xxl"))!=0) {
            llsummary_xxl.setVisibility(View.VISIBLE);
            tvsummary_xxl.setText(object.getString("xxl"));
        }
        if(convertToInt(object.getString("xxxl"))!=0) {
            llsummary_xxxl.setVisibility(View.VISIBLE);
            tvsummary_xxxl.setText(object.getString("xxxl"));
        }
        if(convertToInt(object.getString("qut"))!=0&&goodie_type!=FUNDRAISER_TYPE) {
            llsummary_qty.setVisibility(View.VISIBLE);
            tvsummary_qty.setText(object.getString("qut"));
        }
        if(convertToInt(object.getString("net_qut"))!=0&&goodie_type!=IDCARD_TYPE&&goodie_type!=WORKSHOP_TYPE) {
            llsummary_netQty.setVisibility(View.VISIBLE);
            tvsummary_netQty.setText(object.getString("net_qut"));}

        if(goodie_type!=FUNDRAISER_TYPE){
            llsummary_price.setVisibility(View.VISIBLE);
            String price=goodie.getPrice();
            tvsummary_price.setText(price);
        }



    }

}

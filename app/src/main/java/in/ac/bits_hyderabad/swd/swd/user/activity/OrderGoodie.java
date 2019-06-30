package in.ac.bits_hyderabad.swd.swd.user.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.content.Intent;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
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

import com.jsibbold.zoomage.ZoomageView;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

import in.ac.bits_hyderabad.swd.swd.R;
import in.ac.bits_hyderabad.swd.swd.helper.Goodies;

public class OrderGoodie extends AppCompatActivity {

    private final int FUNDRAISER_TYPE=1;
    private final int TSHIRT_TYPE=2;
    private final int WORKSHOP_TYPE=3;
    private final int IDCARD_TYPE=4;


    ArrayList <EditText> sizes=new ArrayList<>();
    ArrayList <String> sizes_available=new ArrayList<>();

    Boolean AT_LEAST_ONE_TSHIRT_SIZE_ORDERD=false;

    Boolean fundraiser;
    Toolbar toolbar;
    TextView tvHost,tvPrice,tvSizeChart,tvGoodieOrderName,tvminamt,tvmaxamt,tvQty;
    RelativeLayout rlxs,rls,rlm,rll,rlxl,rlxxl,rlxxxl,rlqty,rlcustom,rlminamt_fraiser,rlmaxamt_fraiser;
    EditText etxsQty,etsQty,etmQty,etlQty,etxlQty,etxxlQty,etxxxlQty,etCustom,etQty;
    CheckBox cbAgree;
    Button btnOrder;
    ImageView ivImageOrder;
    LinearLayout llsize,llprice;
    int totalqty;
    Boolean tshirt=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_goodie);

        toolbar=findViewById(R.id.orderGoodiesToolbar);
        toolbar.setTitle("Order Goodies");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initViews();
        btnOrder.setVisibility(View.GONE);
        rlminamt_fraiser.setVisibility(View.GONE);
        rlmaxamt_fraiser.setVisibility(View.GONE);

        btnOrder.setEnabled(true);

        final Intent intent=getIntent();
        final Goodies goodie=intent.getParcelableExtra("goodieClicked");

        sizes_available.add(goodie.getXs());
        sizes_available.add(goodie.getS());
        sizes_available.add(goodie.getM());
        sizes_available.add(goodie.getL());
        sizes_available.add(goodie.getXl());
        sizes_available.add(goodie.getXxl());
        sizes_available.add(goodie.getXxxl());

        Log.e("GoodieRecieved",goodie.getName());

        tvGoodieOrderName.setText(goodie.getName());
        tvHost.setText(goodie.getHost());
        tvPrice.setText(goodie.getPrice());


        fundraiser=false;

        Log.e("image url","swd.bits-hyderabad.ac.in/goodies/img/"+goodie.getImage());

        Picasso.get().load("http://swd.bits-hyderabad.ac.in/goodies/img/"+goodie.getImage())
                .resize(125,125)
                .placeholder(R.drawable.ic_loading)
                .centerInside().error(R.drawable.ic_error)
                .into(ivImageOrder);


        if(!goodie.getSize_chart().isEmpty()){
            tvSizeChart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dialog= new Dialog(OrderGoodie.this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                    dialog.setContentView(R.layout.my_dialog);
                    dialog.setCanceledOnTouchOutside(true);

                    ZoomageView image=dialog.findViewById(R.id.ivFullGoodieImage);

                    Uri imageUri=Uri.parse("http://swd.bits-hyderabad.ac.in/goodies/img/"+goodie.getSize_chart());        //add full link here
                    Picasso.get().load(imageUri)
                            .resize(700,700)
                            .centerInside()
                            .placeholder(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                            .into(image);
                    dialog.show();

                }
            });
        }

        else
        {
            llsize.setVisibility(View.GONE);
        }


            ivImageOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView image;
                    Dialog myDialog = new Dialog(OrderGoodie.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                    myDialog.setContentView(R.layout.my_dialog);
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
        if(goodie.getXs().equals("0")){
            rlxs.setVisibility(View.GONE);
        }
        if(goodie.getS().equals("0")){
            rls.setVisibility(View.GONE);
        }
        if(goodie.getM().equals("0")){
            rlm.setVisibility(View.GONE);
        }
        if(goodie.getL().equals("0")){
            rll.setVisibility(View.GONE);
        }
        if(goodie.getXl().equals("0")){
            rlxl.setVisibility(View.GONE);
        }
        if(goodie.getXxl().equals("0")){
            rlxxl.setVisibility(View.GONE);
        }
        if(goodie.getXxxl().equals("0")){
            rlxxxl.setVisibility(View.GONE);
        }
        if(goodie.getCustom().equals("0")){
            rlcustom.setVisibility(View.GONE);
        }
        if(goodie.getQut().equals("0")){
            rlqty.setVisibility(View.GONE);
        }
        if(!(goodie.getMin_amount().equals("0")&&goodie.getMax_amount().equals("0")))
        {
            fundraiser=true;
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

                if(isChecked)
                {
                    InputMethodManager imm = (InputMethodManager) OrderGoodie.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    View view = OrderGoodie.this.getCurrentFocus();
                    if (view == null) {
                        view = new View(OrderGoodie.this);
                    }
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    btnOrder.setVisibility(View.VISIBLE);
                }
                else
                {
                    btnOrder.setVisibility(View.GONE);
                }
            }
        });


        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnOrder.setEnabled(false);
                // DATABASE TATTI HONE KE KARAD ITNA GANDA HUA HAI CODE

                Log.e("OnClick","At least one size "+AT_LEAST_ONE_TSHIRT_SIZE_ORDERD);
                Log.e("OnClick", fundraiser+"");
                //goodie is a fundraiser
                if(fundraiser){
                    if(!etQty.getText().toString().isEmpty()){
                        int entered_amt=Integer.parseInt(etQty.getText().toString());
                        int min=Integer.parseInt(goodie.getMin_amount());
                        int max=Integer.parseInt(goodie.getMax_amount());
                        if(!(entered_amt>=min&&entered_amt<=max)){
                            Toast.makeText(OrderGoodie.this,"Please enter an amount in the given limits!",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            auth_identity(FUNDRAISER_TYPE);
                        }
                    }
                    else
                    {
                        etQty.setError("You cannot leave this field empty");
                    }

                }
                //goodie is not a fundraiser...REALLY TATTTTTTIIII DB (-_-)
                else
                {

                    for( String str: sizes_available)
                    {
                        if (str.equals("1"))
                        {
                            tshirt=true;
                            break;
                        }
                    }

                    Log.e("OnClick", "came in else");
                    // goodie is a t shirt type (needs a size)
                    if(goodie.getQut().equals("0")&&tshirt){
                        Log.e("OnClick", "tshirt");
                        for(int i=0;i<sizes.size();i++)
                        {
                            if(!sizes.get(i).getText().toString().trim().isEmpty())
                            {
                                AT_LEAST_ONE_TSHIRT_SIZE_ORDERD=true;
                                totalqty+=Integer.parseInt(sizes.get(i).getText().toString().trim());
                            }
                        }
                        Log.e("After for loop","At least one size "+AT_LEAST_ONE_TSHIRT_SIZE_ORDERD);

                        if(AT_LEAST_ONE_TSHIRT_SIZE_ORDERD){
                            AT_LEAST_ONE_TSHIRT_SIZE_ORDERD=false;
                            auth_identity(TSHIRT_TYPE);
                        }
                        else
                        {
                            Toast.makeText(OrderGoodie.this,"Enter quantity for at least one size!!",Toast.LENGTH_SHORT).show();
                        }

                    }
                    //goodie is id card , lunch, workshop type(does not need size) REALLY FUCK DB (-_-)
                    else if(goodie.getQut().equals("1"))
                    {
                        Log.e("OnClick", "Workshop");
                        if(!etQty.getText().toString().trim().isEmpty()){
                            totalqty=Integer.parseInt(etQty.getText().toString().trim());
                            auth_identity(WORKSHOP_TYPE);
                        }
                        else
                        {
                            Toast.makeText(OrderGoodie.this,"Please enter the quantity", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        auth_identity(IDCARD_TYPE);
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

        this.finishAfterTransition();
        super.onBackPressed();
    }

    private void initViews()
    {
        tvHost=findViewById(R.id.tvHost);
        tvPrice=findViewById(R.id.tvPrice);
        llsize=findViewById(R.id.llSize);
        llprice=findViewById(R.id.llprice);
        tvSizeChart=findViewById(R.id.tvSizeChart);
        tvGoodieOrderName=findViewById(R.id.tvGoodieOrderName);
        tvminamt=findViewById(R.id.tvminamt);
        tvmaxamt=findViewById(R.id.tvmaxamt);
        tvQty=findViewById(R.id.tvqty);
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

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK )
        {
            if(requestCode==FUNDRAISER_TYPE)
            {
                Toast.makeText(this, "Success: Verified user's identity", Toast.LENGTH_SHORT).show();
            }
            else if(requestCode==TSHIRT_TYPE)
            {
                Toast.makeText(this, "Success: Verified user's identity", Toast.LENGTH_SHORT).show();
            }
            else if(requestCode==WORKSHOP_TYPE)
            {
                Toast.makeText(this, "Success: Verified user's identity", Toast.LENGTH_SHORT).show();
            }
            else if(requestCode==IDCARD_TYPE)
            {
                Toast.makeText(this, "Success: Verified user's identity", Toast.LENGTH_SHORT).show();
            }

        }
        else
        {
            Toast.makeText(this, "Failure: Unable to verify user's identity", Toast.LENGTH_SHORT).show();
        }
    }

    public void auth_identity(int REQUEST_CODE) {
        KeyguardManager km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

        if (km.isKeyguardSecure()) {
            Intent i = km.createConfirmDeviceCredentialIntent("Authentication required", "password");
            startActivityForResult(i, REQUEST_CODE);
        } else
            Toast.makeText(OrderGoodie.this, "No any security setup done by user(pattern or password or pin or fingerprint", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        btnOrder.setEnabled(true);
        super.onResume();
    }
}

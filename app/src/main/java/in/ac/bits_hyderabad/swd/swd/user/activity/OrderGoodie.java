package in.ac.bits_hyderabad.swd.swd.user.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jsibbold.zoomage.ZoomageView;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;

import in.ac.bits_hyderabad.swd.swd.R;
import in.ac.bits_hyderabad.swd.swd.helper.Goodies;

public class OrderGoodie extends AppCompatActivity {

    Toolbar toolbar;
    TextView tvHost,tvPrice,tvSizeChart,tvGoodieOrderName;
    RelativeLayout rlxs,rls,rlm,rll,rlxl,rlxxl,rlxxxl,rlqty,rlcustom;
    EditText etxsQty,etsQty,etmQty,etlQty,etxlQty,etxxlQty,etxxxlQty,etCustom;
    CheckBox cbAgree;
    Button btnOrder;
    ImageView ivImageOrder;

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
        Intent intent=getIntent();
        final Goodies goodie=intent.getParcelableExtra("goodieClicked");

        Log.e("GoodieRecieved",goodie.getName());

        tvGoodieOrderName.setText(goodie.getName());
        tvHost.setText(goodie.getHost());
        tvPrice.setText(goodie.getPrice());
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
            tvSizeChart.setVisibility(View.GONE);
        }


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

        cbAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
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
        super.onBackPressed();
    }

    private void initViews()
    {
        tvHost=findViewById(R.id.tvHost);
        tvPrice=findViewById(R.id.tvPrice);
        tvSizeChart=findViewById(R.id.tvSizeChart);
        tvGoodieOrderName=findViewById(R.id.tvGoodieOrderName);
        rlxs=findViewById(R.id.rlxs);
        rls=findViewById(R.id.rls);
        rlm=findViewById(R.id.rlm);
        rll=findViewById(R.id.rll);
        rlxl=findViewById(R.id.rlxl);
        rlxxl=findViewById(R.id.rlxxl);
        rlxxxl=findViewById(R.id.rlxxxl);
        rlqty=findViewById(R.id.rlqty);
        rlcustom=findViewById(R.id.rlcustom);
        etxsQty=findViewById(R.id.etxsQty);
        etsQty=findViewById(R.id.etsQty);
        etmQty=findViewById(R.id.etmQty);
        etlQty=findViewById(R.id.etlQty);
        etxlQty=findViewById(R.id.etxlQty);
        etxxlQty=findViewById(R.id.etxxlQty);
        etxxxlQty=findViewById(R.id.etxxxlQty);
        etCustom=findViewById(R.id.etCustomName);
        cbAgree=findViewById(R.id.cbAgree);
        btnOrder=findViewById(R.id.btnOrder);
        ivImageOrder=findViewById(R.id.ivimageOrder);
    }
}

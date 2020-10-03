package in.ac.bits_hyderabad.swd.swd.user.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import in.ac.bits_hyderabad.swd.swd.R;
import in.ac.bits_hyderabad.swd.swd.user.fragment.OrderDonationTypeGoodieFragment;
import in.ac.bits_hyderabad.swd.swd.user.fragment.OrderIDCardTypeGoodieFragment;
import in.ac.bits_hyderabad.swd.swd.user.fragment.OrderTshirtTypeGoodieFragment;

public class OrderGoodie extends AppCompatActivity {

    public static final int FUNDRAISER_TYPE = 1;
    public static final int TSHIRT_TYPE = 2;
    public static final int ID_WORKSHOP_LUNCH_TYPE = 3;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_goodie);

        String goodieId = getIntent().getStringExtra("goodieId");
        int goodieType = getIntent().getIntExtra("goodieType", ID_WORKSHOP_LUNCH_TYPE);

        getBaseContext();

        if (goodieType == FUNDRAISER_TYPE) {
            getSupportFragmentManager().beginTransaction().replace(R.id.order_goodie_frame, new OrderDonationTypeGoodieFragment(goodieId)).commit();
        } else if (goodieType == TSHIRT_TYPE) {
            getSupportFragmentManager().beginTransaction().replace(R.id.order_goodie_frame, new OrderTshirtTypeGoodieFragment(goodieId)).commit();
        } else if (goodieType == ID_WORKSHOP_LUNCH_TYPE) {
            getSupportFragmentManager().beginTransaction().replace(R.id.order_goodie_frame, new OrderIDCardTypeGoodieFragment(goodieId)).commit();
        }

    }
}

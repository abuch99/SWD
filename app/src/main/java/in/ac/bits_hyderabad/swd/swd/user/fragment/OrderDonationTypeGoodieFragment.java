package in.ac.bits_hyderabad.swd.swd.user.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executor;

import in.ac.bits_hyderabad.swd.swd.APIConnection.GetDataService;
import in.ac.bits_hyderabad.swd.swd.APIConnection.Goodie;
import in.ac.bits_hyderabad.swd.swd.APIConnection.GoodieOrderPlacedResponse;
import in.ac.bits_hyderabad.swd.swd.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderDonationTypeGoodieFragment extends Fragment {

    String goodieId;

    ImageView goodieImageView;
    TextView goodieNameTextView;
    TextView goodieHostTextView;
    TextView goodiePriceTextView;
    TextInputEditText donateEditText;
    TextView orderTotalTextView;
    CheckBox otherAdvancesAgreeCheckbox;
    Button orderGoodieButton;
    ConstraintLayout layout;
    ProgressBar loadingProgress;

    int minAmount, maxAmount;
    int currentAmount;

    Retrofit mRetrofitClient;
    GetDataService mRetrofitService;

    String uid, pwd;

    Goodie goodie;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    Call<ArrayList<Goodie>> call;
    Call<in.ac.bits_hyderabad.swd.swd.APIConnection.GoodieOrderPlacedResponse> call2;

    public OrderDonationTypeGoodieFragment(String goodie) {
        goodieId = goodie;
    }

    private void setData() {
        Picasso.get().load("http://swd.bits-hyderabad.ac.in/goodies/img/" + goodie.getImg())
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_error)
                .into(goodieImageView);

        goodieNameTextView.setText(goodie.getName());
        goodieHostTextView.setText(goodie.getHosted_by());
        String priceRange = "₹" + goodie.getMin_amount() + " to ₹" + goodie.getMax_amount();
        currentAmount = minAmount;
        goodiePriceTextView.setText(priceRange);
        String orderTotal = "Order total: ₹" + minAmount;
        orderTotalTextView.setText(orderTotal);
    }

    private void setupQuantityClickListeners() {

        donateEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //DO NOTHING
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //DO NOTHING
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    currentAmount = Integer.parseInt(s.toString());
                    String orderTotal = "Order total: ₹" + currentAmount;
                    orderTotalTextView.setText(orderTotal);
                } catch (Exception e) {
                    currentAmount = minAmount;
                    String orderTotal = "Order total: ₹" + currentAmount;
                    orderTotalTextView.setText(orderTotal);
                }
            }
        });

        orderGoodieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderGoodie();
            }
        });
    }

    private void orderGoodie() {
        if (isAgreed()) {
            bitometricAuthenticate();
        }
    }

    private void bitometricAuthenticate() {
        BiometricManager biometricManager = BiometricManager.from(getContext());
        if (biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS) {
            executor = ContextCompat.getMainExecutor(getContext());
            biometricPrompt = new BiometricPrompt(getActivity(), executor, new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    Toast.makeText(getContext(), "Order placed", Toast.LENGTH_SHORT).show();
                    placeOrder();
                }
            });
            promptInfo = new BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Confirm transaction")
                    .setSubtitle("Confirm you want to place this order amounting to ₹" + currentAmount)
                    .setNegativeButtonText("Cancel")
                    .build();
            biometricPrompt.authenticate(promptInfo);
        } else {
            placeOrder();
        }
    }

    private void placeOrder() {
        SharedPreferences preferences = getContext().getSharedPreferences("USER_LOGIN_DETAILS", Context.MODE_PRIVATE);
        String name = preferences.getString("name", null);
        String fullId = preferences.getString("id", null);

        int xs = 0, s = 0, m = 0, l = 0, xl = 0, xxl = 0, xxxl = 0;

        JSONObject obj = new JSONObject();
        try {
            obj.put("u_id", uid);
            obj.put("full_id", fullId);
            obj.put("u_name", name);
            obj.put("g_id", goodieId);
            obj.put("acceptance", String.valueOf(0));
            obj.put("xs", String.valueOf(xs));
            obj.put("s", String.valueOf(s));
            obj.put("m", String.valueOf(m));
            obj.put("l", String.valueOf(l));
            obj.put("xl", String.valueOf(xl));
            obj.put("xxl", String.valueOf(xxl));
            obj.put("xxxl", String.valueOf(xxxl));
            obj.put("custom_name", "");
            obj.put("qut", String.valueOf(goodie.getQut()));
            obj.put("net_qut", String.valueOf(1));
            obj.put("net_price", String.valueOf(currentAmount));
            obj.put("type", "0");
            sendRequest(obj);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_order_donation_type_goodie, container, false);

        SharedPreferences prefs = getContext().getSharedPreferences("USER_LOGIN_DETAILS", Context.MODE_PRIVATE);
        uid = prefs.getString("uid", null);
        pwd = prefs.getString("password", null);

        layout = rootView.findViewById(R.id.order_goodie_outer_layout);
        loadingProgress = rootView.findViewById(R.id.order_goodie_loading_progress);
        layout.setVisibility(View.INVISIBLE);
        loadingProgress.setVisibility(View.VISIBLE);
        goodieImageView = rootView.findViewById(R.id.goodieImageView);
        goodieNameTextView = rootView.findViewById(R.id.goodieNameTextView);
        goodieHostTextView = rootView.findViewById(R.id.goodieHostTextView);
        goodiePriceTextView = rootView.findViewById(R.id.goodiePriceTextView);
        donateEditText = rootView.findViewById(R.id.donate_amount_edittext);
        orderTotalTextView = rootView.findViewById(R.id.order_total_textview);
        otherAdvancesAgreeCheckbox = rootView.findViewById(R.id.cb_agree_to_pay);
        orderGoodieButton = rootView.findViewById(R.id.orderGoodieButton);

        mRetrofitClient = new Retrofit.Builder()
                .baseUrl(getString(R.string.URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mRetrofitService = mRetrofitClient.create(GetDataService.class);

        call = mRetrofitService.getGoodies("goodies", uid, pwd);
        call.enqueue(new Callback<ArrayList<Goodie>>() {
            @Override
            public void onResponse(Call<ArrayList<Goodie>> call, retrofit2.Response<ArrayList<Goodie>> response) {
                for (int i = 0; i < response.body().size(); i++) {
                    Goodie nextGoodie = response.body().get(i);
                    if (nextGoodie.getG_id().equals(goodieId)) {
                        goodie = nextGoodie;
                        minAmount = Integer.parseInt(goodie.getMin_amount());
                        maxAmount = Integer.parseInt(goodie.getMax_amount());
                        setData();
                        setupQuantityClickListeners();
                        layout.setVisibility(View.VISIBLE);
                        loadingProgress.setVisibility(View.INVISIBLE);
                        break;
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Goodie>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), "Please check your Internet connection!", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    private void sendRequest(JSONObject obj) {
        Map<String, String> map = new HashMap<>();
        try {
            map = JSONtoMap(obj);
        } catch (JSONException e) {
            Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
        }

        call2 = mRetrofitService.getGoodieOrderPlacedResponse("place_order", uid, pwd, map);

        call2.enqueue(new Callback<GoodieOrderPlacedResponse>() {
            @Override
            public void onResponse(Call<GoodieOrderPlacedResponse> call, Response<GoodieOrderPlacedResponse> response) {
                if (!response.body().getError())
                    Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getContext(), "Something went wrong! Please contact SWD", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<GoodieOrderPlacedResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Please check your Internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public Map<String, String> JSONtoMap(JSONObject object) throws JSONException {
        Map<String, String> map = new HashMap<String, String>();
        if (object != JSONObject.NULL) {
            Iterator<String> keysItr = object.keys();
            while (keysItr.hasNext()) {
                String key = keysItr.next();
                String value = object.getString(key);
                map.put(key, value);
            }
        }
        return map;
    }

    private boolean isAgreed() {
        if (otherAdvancesAgreeCheckbox.isChecked())
            return true;
        else {
            Toast.makeText(getContext(), "You must agree to pay the amount from your Other Advances account", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}

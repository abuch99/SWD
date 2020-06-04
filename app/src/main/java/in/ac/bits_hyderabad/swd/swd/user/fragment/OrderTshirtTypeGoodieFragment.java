package in.ac.bits_hyderabad.swd.swd.user.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class OrderTshirtTypeGoodieFragment extends Fragment {

    private final int SIZE_XS = 100;
    private final int SIZE_S = 101;
    private final int SIZE_M = 102;
    private final int SIZE_L = 103;
    private final int SIZE_XL = 104;
    private final int SIZE_XXL = 105;
    private final int SIZE_XXXL = 106;

    String goodieId;
    ImageView goodieImageView;
    TextView goodieNameTextView;
    TextView goodieHostTextView;
    TextView goodiePriceTextView;
    Button xsButton;
    Button sButton;
    Button mButton;
    Button lButton;
    Button xlButton;
    Button xxlButton;
    Button xxxlButton;
    TextView qtyTextView;
    Button qtyDecButton;
    Button qtyIncButton;
    TextView orderTotalTextView;
    CheckBox otherAdvancesAgreeCheckbox;
    Button orderGoodieButton;
    ConstraintLayout layout;
    ProgressBar loadingProgress;

    int quantitySelected;
    int sizeSelected;
    int goodiePrice;
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

    public OrderTshirtTypeGoodieFragment(String goodie) {
        goodieId = goodie;
    }

    private void setData() {
        Picasso.get().load("http://swd.bits-hyderabad.ac.in/goodies/img/" + goodie.getImg())
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_error)
                .into(goodieImageView);

        goodieNameTextView.setText(goodie.getName());
        goodieHostTextView.setText(goodie.getHosted_by());
        goodiePriceTextView.setText("₹" + goodie.getPrice());
        qtyTextView.setText(Integer.toString(quantitySelected));
        currentAmount = goodiePrice;
        orderTotalTextView.setText("Order total: ₹" + currentAmount);
    }

    private void placeOrder() {
        SharedPreferences preferences = getContext().getSharedPreferences("USER_LOGIN_DETAILS", Context.MODE_PRIVATE);
        String name = preferences.getString("name", null);
        String fullId = preferences.getString("id", null);

        int xs = 0, s = 0, m = 0, l = 0, xl = 0, xxl = 0, xxxl = 0;
        if (sizeSelected == SIZE_S)
            s = quantitySelected;
        else if (sizeSelected == SIZE_M)
            m = quantitySelected;
        else if (sizeSelected == SIZE_L)
            l = quantitySelected;
        else if (sizeSelected == SIZE_XL)
            xl = quantitySelected;
        else if (sizeSelected == SIZE_XXL)
            xxl = quantitySelected;
        else if (sizeSelected == SIZE_XXXL)
            xxxl = quantitySelected;
        else
            xs = quantitySelected;

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
            obj.put("net_qut", String.valueOf(quantitySelected));
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
        View rootView = inflater.inflate(R.layout.fragment_order_tshirt_type_goodie, container, false);

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
        xsButton = rootView.findViewById(R.id.button_xs);
        sButton = rootView.findViewById(R.id.button_s);
        mButton = rootView.findViewById(R.id.button_m);
        lButton = rootView.findViewById(R.id.button_l);
        xlButton = rootView.findViewById(R.id.button_xl);
        xxlButton = rootView.findViewById(R.id.button_xxl);
        xxxlButton = rootView.findViewById(R.id.button_xxxl);
        qtyTextView = rootView.findViewById(R.id.qty_textview);
        qtyDecButton = rootView.findViewById(R.id.button_qty_dec);
        qtyIncButton = rootView.findViewById(R.id.button_qty_inc);
        orderTotalTextView = rootView.findViewById(R.id.order_total_textview);
        otherAdvancesAgreeCheckbox = rootView.findViewById(R.id.cb_agree_to_pay);
        orderGoodieButton = rootView.findViewById(R.id.orderGoodieButton);

        quantitySelected = 1;

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
                        goodiePrice = Integer.parseInt(goodie.getPrice());
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
                Toast.makeText(getActivity(), "Please check your Internet connection!", Toast.LENGTH_SHORT).show();
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

    private void setupQuantityClickListeners() {

        xsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSizeXs();
            }
        });

        sButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSizeS();
            }
        });

        xsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSizeXs();
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSizeM();
            }
        });

        lButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSizeL();
            }
        });

        xlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSizeXl();
            }
        });

        xxlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSizeXxl();
            }
        });

        xxxlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSizeXxxl();
            }
        });

        qtyDecButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decQty();
            }
        });

        qtyDecButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                quantitySelected = 1;
                currentAmount = goodiePrice;
                orderTotalTextView.setText("Order total: ₹" + currentAmount);
                qtyTextView.setText(Integer.toString(quantitySelected));
                return true;
            }
        });

        qtyIncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incQty();
            }
        });

        orderGoodieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderGoodie();
            }
        });
    }

    private void decQty() {
        if (quantitySelected > 1) {
            quantitySelected--;
            currentAmount = quantitySelected * goodiePrice;
            orderTotalTextView.setText("Order total: ₹" + currentAmount);
            qtyTextView.setText(Integer.toString(quantitySelected));
        }
    }

    private void incQty() {
        quantitySelected++;
        currentAmount = quantitySelected * goodiePrice;
        orderTotalTextView.setText("Order total: ₹" + currentAmount);
        qtyTextView.setText(Integer.toString(quantitySelected));
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

    private void selectSizeXs() {
        sizeSelected = SIZE_XS;
        xsButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.size_selected));
        xsButton.setTextColor(getResources().getColor(R.color.whiteText));
        sButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        sButton.setTextColor(getResources().getColor(R.color.whiteText));
        mButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        mButton.setTextColor(getResources().getColor(R.color.whiteText));
        lButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        lButton.setTextColor(getResources().getColor(R.color.whiteText));
        xlButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        xlButton.setTextColor(getResources().getColor(R.color.whiteText));
        xxlButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        xxlButton.setTextColor(getResources().getColor(R.color.whiteText));
        xxxlButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        xxxlButton.setTextColor(getResources().getColor(R.color.whiteText));
    }

    private void selectSizeS() {
        sizeSelected = SIZE_S;
        xsButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        xsButton.setTextColor(getResources().getColor(R.color.whiteText));
        sButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.size_selected));
        sButton.setTextColor(getResources().getColor(R.color.whiteText));
        mButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        mButton.setTextColor(getResources().getColor(R.color.whiteText));
        lButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        lButton.setTextColor(getResources().getColor(R.color.whiteText));
        xlButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        xlButton.setTextColor(getResources().getColor(R.color.whiteText));
        xxlButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        xxlButton.setTextColor(getResources().getColor(R.color.whiteText));
        xxxlButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        xxxlButton.setTextColor(getResources().getColor(R.color.whiteText));
    }

    private void selectSizeM() {
        sizeSelected = SIZE_M;
        xsButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        xsButton.setTextColor(getResources().getColor(R.color.whiteText));
        sButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        sButton.setTextColor(getResources().getColor(R.color.whiteText));
        mButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.size_selected));
        mButton.setTextColor(getResources().getColor(R.color.whiteText));
        lButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        lButton.setTextColor(getResources().getColor(R.color.whiteText));
        xlButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        xlButton.setTextColor(getResources().getColor(R.color.whiteText));
        xxlButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        xxlButton.setTextColor(getResources().getColor(R.color.whiteText));
        xxxlButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        xxxlButton.setTextColor(getResources().getColor(R.color.whiteText));
    }

    private void selectSizeL() {
        sizeSelected = SIZE_L;
        xsButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        xsButton.setTextColor(getResources().getColor(R.color.whiteText));
        sButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        sButton.setTextColor(getResources().getColor(R.color.whiteText));
        mButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        mButton.setTextColor(getResources().getColor(R.color.whiteText));
        lButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.size_selected));
        lButton.setTextColor(getResources().getColor(R.color.whiteText));
        xlButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        xlButton.setTextColor(getResources().getColor(R.color.whiteText));
        xxlButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        xxlButton.setTextColor(getResources().getColor(R.color.whiteText));
        xxxlButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        xxxlButton.setTextColor(getResources().getColor(R.color.whiteText));
    }

    private void selectSizeXl() {
        sizeSelected = SIZE_XL;
        xsButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        xsButton.setTextColor(getResources().getColor(R.color.whiteText));
        sButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        sButton.setTextColor(getResources().getColor(R.color.whiteText));
        mButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        mButton.setTextColor(getResources().getColor(R.color.whiteText));
        lButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        lButton.setTextColor(getResources().getColor(R.color.whiteText));
        xlButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.size_selected));
        xlButton.setTextColor(getResources().getColor(R.color.whiteText));
        xxlButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        xxlButton.setTextColor(getResources().getColor(R.color.whiteText));
        xxxlButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        xxxlButton.setTextColor(getResources().getColor(R.color.whiteText));
    }

    private void selectSizeXxl() {
        sizeSelected = SIZE_XXL;
        xsButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        xsButton.setTextColor(getResources().getColor(R.color.whiteText));
        sButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        sButton.setTextColor(getResources().getColor(R.color.whiteText));
        mButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        mButton.setTextColor(getResources().getColor(R.color.whiteText));
        lButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        lButton.setTextColor(getResources().getColor(R.color.whiteText));
        xlButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        xlButton.setTextColor(getResources().getColor(R.color.whiteText));
        xxlButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.size_selected));
        xxlButton.setTextColor(getResources().getColor(R.color.whiteText));
        xxxlButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        xxxlButton.setTextColor(getResources().getColor(R.color.whiteText));
    }

    private void selectSizeXxxl() {
        sizeSelected = SIZE_XXL;
        xsButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        xsButton.setTextColor(getResources().getColor(R.color.whiteText));
        sButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        sButton.setTextColor(getResources().getColor(R.color.whiteText));
        mButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        mButton.setTextColor(getResources().getColor(R.color.whiteText));
        lButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        lButton.setTextColor(getResources().getColor(R.color.whiteText));
        xlButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        xlButton.setTextColor(getResources().getColor(R.color.whiteText));
        xxlButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.increment_decrement_button_background));
        xxlButton.setTextColor(getResources().getColor(R.color.whiteText));
        xxxlButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.size_selected));
        xxxlButton.setTextColor(getResources().getColor(R.color.whiteText));
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

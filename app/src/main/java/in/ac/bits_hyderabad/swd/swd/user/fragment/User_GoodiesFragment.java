package in.ac.bits_hyderabad.swd.swd.user.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import in.ac.bits_hyderabad.swd.swd.APIConnection.Deduction;
import in.ac.bits_hyderabad.swd.swd.APIConnection.GetDataService;
import in.ac.bits_hyderabad.swd.swd.APIConnection.Goodie;
import in.ac.bits_hyderabad.swd.swd.R;
import in.ac.bits_hyderabad.swd.swd.helper.GoodiesAdapter;
import in.ac.bits_hyderabad.swd.swd.user.activity.OrderGoodie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class User_GoodiesFragment extends Fragment implements GoodiesAdapter.itemClicked {

    private GridView rvGoodiesList;
    private GoodiesAdapter mAdaptor;
    private ArrayList<Goodie> goodies = new ArrayList<>();
    private SwipeRefreshLayout swipeRefresh;
    private TextView totalDeductionsText;
    private MaterialButton viewDeductionButton;
    private ProgressBar totalDeductionsProgress;

    private Retrofit mRetrofitClient;
    private GetDataService mRetrofitService;

    private String uid, id_no, pwd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goodies, parent, false);

        SharedPreferences prefs = getContext().getSharedPreferences("USER_LOGIN_DETAILS", Context.MODE_PRIVATE);
        uid = prefs.getString("uid", null);
        pwd = prefs.getString("password", null);
        id_no = prefs.getString("id", null);

        mRetrofitClient = new Retrofit.Builder()
                .baseUrl(getString(R.string.URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mRetrofitService = mRetrofitClient.create(GetDataService.class);

        totalDeductionsText = view.findViewById(R.id.totalDeductionsText);
        viewDeductionButton = view.findViewById(R.id.viewDeductionButton);
        totalDeductionsProgress = view.findViewById(R.id.totalDeductionsProgress);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        rvGoodiesList = view.findViewById(R.id.rvGoodiesList);

        loadGoodies();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                goodies.clear();
                loadGoodies();
            }
        });

        viewDeductionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_user_GoodiesFragment_to_user_DeductionsFragment);
            }
        });

        return view;
    }

    Call<ArrayList<Goodie>> call;
    Call<ArrayList<Deduction>> call2;
    private void loadGoodies() {
        swipeRefresh.setRefreshing(true);

        call = mRetrofitService.getGoodies("goodies", uid, pwd);
        call2 = mRetrofitService.getDeductions("deductions", uid, pwd);

        call.enqueue(new Callback<ArrayList<Goodie>>() {
            @Override
            public void onResponse(Call<ArrayList<Goodie>> call, retrofit2.Response<ArrayList<Goodie>> response) {
                goodies = response.body();
                if (getContext() != null) {
                    mAdaptor = new GoodiesAdapter(getContext(), User_GoodiesFragment.this, goodies);
                    rvGoodiesList.setAdapter(mAdaptor);
                }
                swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ArrayList<Goodie>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), "Please check your Internet connection!", Toast.LENGTH_SHORT).show();
                swipeRefresh.setRefreshing(false);
            }
        });
        call2.enqueue(new Callback<ArrayList<Deduction>>() {
            @Override
            public void onResponse(Call<ArrayList<Deduction>> call, retrofit2.Response<ArrayList<Deduction>> response) {
                int totalDeductions = 0;
                for (int i = 0; i < response.body().size(); i++) {
                    String amount = response.body().get(i).getAmount();
                    totalDeductions = totalDeductions + Integer.parseInt(amount);
                }
                swipeRefresh.setRefreshing(false);
                String textToDisplay = "You've spent ₹" + totalDeductions + " this semester";
                totalDeductionsProgress.setVisibility(View.GONE);
                totalDeductionsText.setText(textToDisplay);
            }

            @Override
            public void onFailure(Call<ArrayList<Deduction>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), "Please check your Internet connection!", Toast.LENGTH_SHORT).show();
                swipeRefresh.setRefreshing(false);
            }
        });
    }


    @Override
    public void onItemClicked(int index) {
        Goodie goodie = goodies.get(index);

        int goodieType = OrderGoodie.ID_WORKSHOP_LUNCH_TYPE;

        if (!(goodie.getMin_amount().equals("0") && goodies.get(index).getMax_amount().equals("0"))) {
            goodieType = OrderGoodie.FUNDRAISER_TYPE;
        } else {
            if (goodie.getQut().equals("0"))
                goodieType = OrderGoodie.TSHIRT_TYPE;
            else if (goodie.getQut().equals("1"))
                goodieType = OrderGoodie.ID_WORKSHOP_LUNCH_TYPE;
        }

        Intent intent = new Intent(getActivity(), OrderGoodie.class);
        intent.putExtra("goodieId", goodie.getG_id());
        intent.putExtra("goodieType", goodieType);
        intent.putExtra("uid", uid);
        intent.putExtra("pwd", pwd);

        startActivity(intent);
    }
}
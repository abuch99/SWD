package in.ac.bits_hyderabad.swd.swd.user.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import in.ac.bits_hyderabad.swd.swd.APIConnection.Deduction;
import in.ac.bits_hyderabad.swd.swd.APIConnection.GetDataService;
import in.ac.bits_hyderabad.swd.swd.R;
import in.ac.bits_hyderabad.swd.swd.helper.DeductionsAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class User_DeductionsFragment extends Fragment {


    RecyclerView rvDeductions;
    DeductionsAdapter mAdaptor;
    LinearLayoutManager mLayoutManager;
    ArrayList<Deduction> deductions;
    SwipeRefreshLayout swipeRefreshDed;

    Call<ArrayList<in.ac.bits_hyderabad.swd.swd.APIConnection.Deduction>> call;
    private Retrofit mRetrofitClient;

    String uid, id_no, pwd;

    TextView totalDeductionsText, spentHeaderText;

    private GetDataService mRetrofitService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_user_deductions, container, false);

        SharedPreferences prefs = getContext().getSharedPreferences("USER_LOGIN_DETAILS", Context.MODE_PRIVATE);
        uid = prefs.getString("uid", null);
        pwd = prefs.getString("password", null);
        id_no = prefs.getString("id", null);

        rvDeductions = rootView.findViewById(R.id.rvDeductions);
        swipeRefreshDed = rootView.findViewById(R.id.swipeRefreshDed);
        swipeRefreshDed.setRefreshing(true);
        deductions = new ArrayList<>();

        mRetrofitClient = new Retrofit.Builder()
                .baseUrl(getString(R.string.URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mRetrofitService = mRetrofitClient.create(GetDataService.class);

        totalDeductionsText = rootView.findViewById(R.id.totalDeductionsText);
        spentHeaderText = rootView.findViewById(R.id.spent_header_text);

        loadDeductions();

        mLayoutManager = new LinearLayoutManager(this.getActivity());
        rvDeductions.setLayoutManager(mLayoutManager);

        swipeRefreshDed.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                deductions.clear();
                loadDeductions();
            }
        });

        return rootView;
    }

    private void loadDeductions() {

        spentHeaderText.setVisibility(View.INVISIBLE);

        call = mRetrofitService.getDeductions("deductions", uid, pwd);

        call.enqueue(new Callback<ArrayList<in.ac.bits_hyderabad.swd.swd.APIConnection.Deduction>>() {
            @Override
            public void onResponse(Call<ArrayList<in.ac.bits_hyderabad.swd.swd.APIConnection.Deduction>> call, retrofit2.Response<ArrayList<in.ac.bits_hyderabad.swd.swd.APIConnection.Deduction>> response) {
                spentHeaderText.setVisibility(View.VISIBLE);
                int totalDeductions = 0;
                for (int i = 0; i < response.body().size(); i++) {
                    String amount = response.body().get(i).getAmount();
                    totalDeductions = totalDeductions + Integer.parseInt(amount);
                    deductions.add(response.body().get(i));
                }
                mAdaptor = new DeductionsAdapter(getContext(), deductions);
                rvDeductions.setAdapter(mAdaptor);
                swipeRefreshDed.setRefreshing(false);
                String textToDisplay = "₹" + totalDeductions;
                totalDeductionsText.setText(textToDisplay);
            }

            @Override
            public void onFailure(Call<ArrayList<in.ac.bits_hyderabad.swd.swd.APIConnection.Deduction>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), "Please check your Internet connection!", Toast.LENGTH_SHORT).show();
                swipeRefreshDed.setRefreshing(false);
            }
        });

    }
}

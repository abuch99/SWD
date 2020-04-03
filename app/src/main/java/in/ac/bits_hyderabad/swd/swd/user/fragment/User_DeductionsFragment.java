package in.ac.bits_hyderabad.swd.swd.user.fragment;


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

    TextView totalDeductionsText;

    public User_DeductionsFragment(String uid, String id_no, String pwd) {
        this.uid = uid;
        this.id_no = id_no;
        this.pwd = pwd;
    }

    private GetDataService mRetrofitService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_user_deductions, container, false);

        rvDeductions = rootView.findViewById(R.id.rvDeductions);
        swipeRefreshDed = rootView.findViewById(R.id.swipeRefreshDed);
        swipeRefreshDed.setRefreshing(true);
        deductions=new ArrayList<>();

        mRetrofitClient = new Retrofit.Builder()
                .baseUrl(getString(R.string.URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mRetrofitService = mRetrofitClient.create(GetDataService.class);

        loadDeductions();

        totalDeductionsText = rootView.findViewById(R.id.totalDeductionsText);
        mLayoutManager =new LinearLayoutManager(this.getActivity());
        rvDeductions.setLayoutManager(mLayoutManager);
        mAdaptor=new DeductionsAdapter(this.getActivity(),deductions);
        rvDeductions.setAdapter(mAdaptor);
        mAdaptor.notifyDataSetChanged();

        swipeRefreshDed.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                deductions.clear();
                mAdaptor.notifyDataSetChanged();
                loadDeductions();
            }
        });

        return rootView;
    }

    private void loadDeductions() {

        call = mRetrofitService.getDeductions("deductions", uid, pwd);

        call.enqueue(new Callback<ArrayList<in.ac.bits_hyderabad.swd.swd.APIConnection.Deduction>>() {
            @Override
            public void onResponse(Call<ArrayList<in.ac.bits_hyderabad.swd.swd.APIConnection.Deduction>> call, retrofit2.Response<ArrayList<in.ac.bits_hyderabad.swd.swd.APIConnection.Deduction>> response) {
                int totalDeductions = 0;
                for (int i = 0; i < response.body().size(); i++) {
                    String amount = response.body().get(i).getAmount();
                    totalDeductions = totalDeductions + Integer.parseInt(amount);
                    deductions.add(response.body().get(i));
                }
                mAdaptor.notifyDataSetChanged();
                swipeRefreshDed.setRefreshing(false);
                String textToDisplay = "₹" + totalDeductions;
                totalDeductionsText.setText(textToDisplay);
            }

            @Override
            public void onFailure(Call<ArrayList<in.ac.bits_hyderabad.swd.swd.APIConnection.Deduction>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(), "Please check your Internet connection!", Toast.LENGTH_SHORT).show();
                swipeRefreshDed.setRefreshing(false);
            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        call.cancel();
    }
}

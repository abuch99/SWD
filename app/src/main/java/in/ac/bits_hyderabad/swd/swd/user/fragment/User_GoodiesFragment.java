package in.ac.bits_hyderabad.swd.swd.user.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

    GridView rvGoodiesList;
    GoodiesAdapter mAdaptor;
    ProgressDialog dialog;
    ArrayList<Goodie> goodies;
    SwipeRefreshLayout swipeRefresh;
    TextView totalDeductionsText;
    Button viewDeductionButton;
    ProgressBar totalDeductionsProgress;

    Retrofit mRetrofitClient;
    GetDataService mRetrofitService;

    String uid, id_no, pwd;

    public User_GoodiesFragment(String uid, String id_no, String pwd) {
        this.uid = uid;
        this.id_no = id_no;
        this.pwd = pwd;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View view = inflater.inflate(R.layout.fragment_goodies, parent, false);

        mRetrofitClient = new Retrofit.Builder()
                .baseUrl(getString(R.string.URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mRetrofitService = mRetrofitClient.create(GetDataService.class);

        totalDeductionsText = view.findViewById(R.id.totalDeductionsText);
        viewDeductionButton = view.findViewById(R.id.viewDeductionButton);
        viewDeductionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container, new User_DeductionsFragment(uid, id_no, pwd)).addToBackStack("goodies-view").commit();
            }
        });
        totalDeductionsProgress = view.findViewById(R.id.totalDeductionsProgress);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        swipeRefresh.setRefreshing(true);
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Loading...");
        dialog.setCanceledOnTouchOutside(false);
        goodies = new ArrayList<>();
        loadGoodies();//define your goodies array list here
        rvGoodiesList = view.findViewById(R.id.rvGoodiesList);


        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                goodies.clear();
                mAdaptor.notifyDataSetChanged();
                loadGoodies();
            }
        });
    }

    Call<ArrayList<Goodie>> call;
    Call<ArrayList<Deduction>> call2;
    private void loadGoodies() {
        call = mRetrofitService.getGoodies("goodies", uid, pwd);
        call2 = mRetrofitService.getDeductions("deductions", uid, pwd);

        call.enqueue(new Callback<ArrayList<Goodie>>() {
            @Override
            public void onResponse(Call<ArrayList<Goodie>> call, retrofit2.Response<ArrayList<Goodie>> response) {
                Log.d("Goodies", response.toString());
                goodies = response.body();
                mAdaptor = new GoodiesAdapter(getActivity(), User_GoodiesFragment.this, goodies);
                rvGoodiesList.setAdapter(mAdaptor);
                mAdaptor.notifyDataSetChanged();
                swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ArrayList<Goodie>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(), "Please check your Internet connection!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), "Please check your Internet connection!", Toast.LENGTH_SHORT).show();
                swipeRefresh.setRefreshing(false);
            }
        });
    }


    @Override
    public void onItemClicked(int index) {
        Goodie goodie = goodies.get(index);

        int goodieType = OrderGoodie.ID_WORKSHOP_LUNCH_TYPE;

        if (!(goodie.getMinAmount().equals("0") && goodies.get(index).getMaxAmount().equals("0"))) {
            goodieType = OrderGoodie.FUNDRAISER_TYPE;
        } else {
            if (goodie.getQut().equals("0"))
                goodieType = OrderGoodie.TSHIRT_TYPE;
            else if (goodie.getQut().equals("1"))
                goodieType = OrderGoodie.ID_WORKSHOP_LUNCH_TYPE;
        }

        Intent intent = new Intent(getActivity(), OrderGoodie.class);
        intent.putExtra("goodieId", goodie.getGoodieID());
        intent.putExtra("goodieType", goodieType);
        intent.putExtra("uid", uid);
        intent.putExtra("pwd", pwd);

        startActivity(intent);
    }

    @Override
    public void onStop() {
        super.onStop();
        call.cancel();
        call2.cancel();
    }
}
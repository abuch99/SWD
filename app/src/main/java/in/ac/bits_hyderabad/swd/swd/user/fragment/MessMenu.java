package in.ac.bits_hyderabad.swd.swd.user.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import in.ac.bits_hyderabad.swd.swd.APIConnection.GetDataService;
import in.ac.bits_hyderabad.swd.swd.APIConnection.MessReq;
import in.ac.bits_hyderabad.swd.swd.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessMenu extends Fragment {
    private String day;
    int mess=0;
    private LinearLayout llMenu;
    private String uid, password;
    private SwipeRefreshLayout swipeRefresh;

    private Retrofit mRetrofitClient;
    private GetDataService mRetrofitService;

    public MessMenu(String day, String uid, String password) {
        this.day=day;
        this.uid=uid;
        this.password=password;
    }

    private TextView tvDay, tvBreakfast, tvLunch, tvSnacks, tvDinner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_mess_menu, container, false);
        llMenu=view.findViewById(R.id.llMenu);
        tvDay=view.findViewById(R.id.tvDay);
        tvBreakfast=view.findViewById(R.id.tvBreakfast);
        tvLunch=view.findViewById(R.id.tvLunch);
        tvSnacks=view.findViewById(R.id.tvSnacks);
        tvDinner=view.findViewById(R.id.tvDinner);
        swipeRefresh=view.findViewById(R.id.swipeRefreshMenu);
        llMenu.setVisibility(View.GONE);

        mRetrofitClient = new Retrofit.Builder()
                .baseUrl(getString(R.string.URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mRetrofitService = mRetrofitClient.create(GetDataService.class);

        getMessNo();
        swipeRefresh.setRefreshing(true);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMessNo();
            }
        });

        return view;
    }

    private void getMessNo() {

        Call<in.ac.bits_hyderabad.swd.swd.APIConnection.MessReq> call = mRetrofitService.getMessReq("mess_req", uid, password);

        call.enqueue(new Callback<MessReq>() {
            @Override
            public void onResponse(Call<MessReq> call, Response<MessReq> response) {
                if (!response.body().getError()) {
                    String mess_no_string = response.body().getData().getMess();
                        if(!mess_no_string.equalsIgnoreCase("null")) {
                            mess = Integer.parseInt(mess_no_string);
                            if (!(mess != 1 && mess != 2)) {
                                getMessMenu(day, mess);
                            }
                            else
                            {
                                swipeRefresh.setRefreshing(false);
                                Toast.makeText(getContext(), "You may not be registered to either of the Mess", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            swipeRefresh.setRefreshing(false);
                            Toast.makeText(getContext(), "You may not be registered to either of the Mess", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Sorry! something went wrong. We will be back soon!!",Toast.LENGTH_SHORT).show();
                    }
                }

            @Override
            public void onFailure(Call<MessReq> call, Throwable t) {
                swipeRefresh.setRefreshing(false);
                Toast.makeText(getActivity(), "Sorry! something went wrong. We will be back soon", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });

    }

    private void getMessMenu(final String day, final int messNo) {

        Call<ArrayList<in.ac.bits_hyderabad.swd.swd.APIConnection.MessMenu>> call = mRetrofitService.getMessMenu("mess_menu", uid, password);

        call.enqueue(new Callback<ArrayList<in.ac.bits_hyderabad.swd.swd.APIConnection.MessMenu>>() {
            @Override
            public void onResponse(Call<ArrayList<in.ac.bits_hyderabad.swd.swd.APIConnection.MessMenu>> call, retrofit2.Response<ArrayList<in.ac.bits_hyderabad.swd.swd.APIConnection.MessMenu>> response) {
                for (int i = 0; i < response.body().size(); i++) {
                    if (!response.body().get(i).getMess().equals(Integer.toString(messNo)))
                        continue;
                    if (response.body().get(i).getDay().equals(day)) {
                        tvDay.setText(day);
                        tvBreakfast.setText(response.body().get(i).getBreakfast().trim());
                        tvLunch.setText(response.body().get(i).getLunch().trim());
                        tvSnacks.setText(response.body().get(i).getSnacks().trim());
                        tvDinner.setText(response.body().get(i).getDinner().trim());
                        break;
                    }
                }
                llMenu.setVisibility(View.VISIBLE);
                swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ArrayList<in.ac.bits_hyderabad.swd.swd.APIConnection.MessMenu>> call, Throwable t) {
                swipeRefresh.setRefreshing(false);
                t.printStackTrace();
            }
        });

    }

}

package in.ac.bits_hyderabad.swd.swd.user.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;

import in.ac.bits_hyderabad.swd.swd.APIConnection.GetDataService;
import in.ac.bits_hyderabad.swd.swd.APIConnection.MessReq;
import in.ac.bits_hyderabad.swd.swd.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessMenu extends Fragment {

    int mess = 0;
    private String dayToday;
    private String uid, password;

    private Retrofit mRetrofitClient;
    private GetDataService mRetrofitService;
    private TextView tvDay, tvBreakfast, tvLunch, tvSnacks, tvDinner, registeredMessText;
    private ProgressBar registeredMessProgress, menuProgress;
    private Button messRegistrationButton;
    private ConstraintLayout messMenu;

    public MessMenu(String uid, String password) {
        this.uid=uid;
        this.password=password;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_mess_menu, container, false);

        messMenu = view.findViewById(R.id.mess_menu_layout);
        menuProgress = view.findViewById(R.id.messMenuProgress);
        tvDay=view.findViewById(R.id.tvDay);
        tvBreakfast=view.findViewById(R.id.tvBreakfast);
        tvLunch=view.findViewById(R.id.tvLunch);
        tvSnacks=view.findViewById(R.id.tvSnacks);
        tvDinner=view.findViewById(R.id.tvDinner);
        registeredMessText = view.findViewById(R.id.registeredMessText);
        registeredMessProgress = view.findViewById(R.id.registeredMessProgress);
        messRegistrationButton = view.findViewById(R.id.messRegistrationButton);

        messRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container, new UserMessRegFragment(uid, password)).commit();
            }
        });

        assignDay();
        String displayDay = "(" + dayToday + ")";
        tvDay.setText(displayDay);

        mRetrofitClient = new Retrofit.Builder()
                .baseUrl(getString(R.string.URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mRetrofitService = mRetrofitClient.create(GetDataService.class);

        getMessNo();

        return view;
    }

    private void getMessMenu() {

        Call<ArrayList<in.ac.bits_hyderabad.swd.swd.APIConnection.MessMenu>> call = mRetrofitService.getMessMenu("mess_menu", uid, password);

        call.enqueue(new Callback<ArrayList<in.ac.bits_hyderabad.swd.swd.APIConnection.MessMenu>>() {
            @Override
            public void onResponse(Call<ArrayList<in.ac.bits_hyderabad.swd.swd.APIConnection.MessMenu>> call, retrofit2.Response<ArrayList<in.ac.bits_hyderabad.swd.swd.APIConnection.MessMenu>> response) {
                for (int i = 0; i < response.body().size(); i++) {
                    if (!response.body().get(i).getMess().equals(Integer.toString(mess)))
                        continue;
                    if (response.body().get(i).getDay().equals(dayToday)) {
                        tvBreakfast.setText(response.body().get(i).getBreakfast().trim());
                        tvLunch.setText(response.body().get(i).getLunch().trim());
                        tvSnacks.setText(response.body().get(i).getSnacks().trim());
                        tvDinner.setText(response.body().get(i).getDinner().trim());
                        menuProgress.setVisibility(View.GONE);
                        messMenu.setVisibility(View.VISIBLE);
                        break;
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<in.ac.bits_hyderabad.swd.swd.APIConnection.MessMenu>> call, Throwable t) {
                Toast.makeText(getContext(), "Error connecting to the internet", Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });

    }

    private void getMessNo() {

        Call<in.ac.bits_hyderabad.swd.swd.APIConnection.MessReq> call = mRetrofitService.getMessReq("mess_req", uid, password);

        call.enqueue(new Callback<MessReq>() {
            @Override
            public void onResponse(Call<MessReq> call, Response<MessReq> response) {
                if (!response.body().getError()) {
                    String mess_no_string = response.body().getData().getMess();
                    if (!mess_no_string.equalsIgnoreCase("null")) {
                        mess = Integer.parseInt(mess_no_string);
                        if (!(mess != 1 && mess != 2)) {
                            String registeredMessDisplay = "You're registered to mess " + mess;
                            registeredMessProgress.setVisibility(View.GONE);
                            registeredMessText.setText(registeredMessDisplay);
                            getMessMenu();
                        } else {
                            registeredMessText.setText("Unable to determine registered mess");
                        }
                    } else {
                        registeredMessText.setText("Unable to determine registered mess");
                    }

                } else {
                    Toast.makeText(getActivity(), "Sorry! something went wrong. We will be back soon!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessReq> call, Throwable t) {
                Toast.makeText(getActivity(), "Sorry! something went wrong. We will be back soon", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });

    }

    private void assignDay() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.SUNDAY:
                dayToday = "Sunday";
                break;
            case Calendar.MONDAY:
                dayToday = "Monday";
                break;
            case Calendar.TUESDAY:
                dayToday = "Tuesday";
                break;
            case Calendar.WEDNESDAY:
                dayToday = "Wednesday";
                break;
            case Calendar.THURSDAY:
                dayToday = "Thursday";
                break;
            case Calendar.FRIDAY:
                dayToday = "Friday";
                break;
            case Calendar.SATURDAY:
                dayToday = "Saturday";
                break;
        }
    }

}

package in.ac.bits_hyderabad.swd.swd.user.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import in.ac.bits_hyderabad.swd.swd.APIConnection.GetDataService;
import in.ac.bits_hyderabad.swd.swd.APIConnection.MessReg;
import in.ac.bits_hyderabad.swd.swd.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserMessRegFragment extends Fragment {

    CoordinatorLayout layout;
    SwipeRefreshLayout swipeRefresh;
    ImageView messRegErrorImage;
    Button btnRegister;
    String uid, password;
    RadioGroup radioGroup;
    TextView messRegInstructionsText, messRegErrorText, mess1SeatsLeft, mess2SeatsLeft;
    ProgressBar messRegProgress;

    private Retrofit mRetrofitClient;
    private GetDataService mRetrofitService;

    public UserMessRegFragment(String uid, String pwd) {
        this.uid = uid;
        password = pwd;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_mess_reg, container, false);

        mRetrofitClient = new Retrofit.Builder()
                .baseUrl(getString(R.string.URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mRetrofitService = mRetrofitClient.create(GetDataService.class);

        swipeRefresh = rootView.findViewById(R.id.swipeRefreshMessReg);
        swipeRefresh.setVisibility(View.GONE);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkRegStatus();
            }
        });

        messRegInstructionsText = rootView.findViewById(R.id.messRegInstructionsText);
        messRegErrorText = rootView.findViewById(R.id.messRegErrorText);
        messRegErrorImage = rootView.findViewById(R.id.messRegErrorImage);
        mess1SeatsLeft = rootView.findViewById(R.id.mess1SeatsLeft);
        mess2SeatsLeft = rootView.findViewById(R.id.mess2SeatsLeft);
        messRegProgress = rootView.findViewById(R.id.messRegProgress);
        btnRegister = rootView.findViewById(R.id.registerButton);
        radioGroup = rootView.findViewById(R.id.rgMessChoice);

        checkRegStatus();

        return rootView;
    }

    public void checkRegStatus(){
        Call<MessReg> call = mRetrofitService.getMessRegDetails("1");
        call.enqueue(new Callback<MessReg>() {
            @Override
            public void onResponse(Call<MessReg> call, Response<MessReg> response) {
                swipeRefresh.setVisibility(View.VISIBLE);
                messRegProgress.setVisibility(View.GONE);
                if (response.body() == null) {
                    if (response.code() == 502) {
                        setMessRegClosed();
                    } else {
                        setNetworkError();
                    }
                } else if (response.body().getPass().equals("0")) {
                    setMessRegOpened();
                    String mess1Left = response.body().getMess1SeatsLeft();
                    String mess2Left = response.body().getMess2SeatsLeft();
                    //TODO: Start registration
                } else {
                    setNetworkError();
                }
            }

            @Override
            public void onFailure(Call<MessReg> call, Throwable t) {
                setNetworkError();
            }
        });
    }

    private void setMessRegClosed() {
        messRegInstructionsText.setVisibility(View.GONE);
        radioGroup.setVisibility(View.GONE);
        mess1SeatsLeft.setVisibility(View.GONE);
        mess2SeatsLeft.setVisibility(View.GONE);
        btnRegister.setVisibility(View.GONE);
        messRegErrorImage.setVisibility(View.VISIBLE);
        messRegErrorText.setVisibility(View.VISIBLE);
        messRegErrorImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.outline_warning_24));
        messRegErrorText.setText(getString(R.string.mess_reg_closed));
    }

    private void setMessRegOpened() {
        messRegInstructionsText.setVisibility(View.VISIBLE);
        messRegInstructionsText.setText(getString(R.string.mess_reg_instructions));
        radioGroup.setVisibility(View.VISIBLE);
        mess1SeatsLeft.setVisibility(View.VISIBLE);
        mess2SeatsLeft.setVisibility(View.VISIBLE);
        btnRegister.setVisibility(View.VISIBLE);
        messRegErrorImage.setVisibility(View.GONE);
        messRegErrorText.setVisibility(View.GONE);
    }

    private void setNetworkError() {
        messRegInstructionsText.setVisibility(View.GONE);
        radioGroup.setVisibility(View.GONE);
        mess1SeatsLeft.setVisibility(View.GONE);
        mess2SeatsLeft.setVisibility(View.GONE);
        btnRegister.setVisibility(View.GONE);
        messRegErrorImage.setVisibility(View.VISIBLE);
        messRegErrorText.setVisibility(View.VISIBLE);
        messRegErrorImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.outline_cloud_off_24));
        messRegErrorText.setText("Error connecting to our servers");
    }


}

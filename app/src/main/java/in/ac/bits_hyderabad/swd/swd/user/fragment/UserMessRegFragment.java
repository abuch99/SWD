package in.ac.bits_hyderabad.swd.swd.user.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import in.ac.bits_hyderabad.swd.swd.APIConnection.GetDataService;
import in.ac.bits_hyderabad.swd.swd.APIConnection.MessReg;
import in.ac.bits_hyderabad.swd.swd.APIConnection.MessRegistrationResponse;
import in.ac.bits_hyderabad.swd.swd.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserMessRegFragment extends Fragment {

    SwipeRefreshLayout swipeRefresh;
    ImageView messRegErrorImage;
    Button btnRegister;
    String uid, password;
    RadioGroup radioGroup;
    RadioButton rbMess1, rbMess2;
    TextView messRegInstructionsText, messRegMsgText, mess1SeatsLeft, mess2SeatsLeft;
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

        messRegInstructionsText = rootView.findViewById(R.id.messRegInstructionsText);
        messRegMsgText = rootView.findViewById(R.id.messRegMsgText);
        messRegErrorImage = rootView.findViewById(R.id.messRegErrorImage);
        mess1SeatsLeft = rootView.findViewById(R.id.mess1SeatsLeft);
        mess2SeatsLeft = rootView.findViewById(R.id.mess2SeatsLeft);
        messRegProgress = rootView.findViewById(R.id.messRegProgress);
        btnRegister = rootView.findViewById(R.id.registerButton);
        radioGroup = rootView.findViewById(R.id.rgMessChoice);
        rbMess1 = rootView.findViewById(R.id.rbMess1);
        rbMess2 = rootView.findViewById(R.id.rbMess2);
        swipeRefresh = rootView.findViewById(R.id.swipeRefreshMessReg);
        swipeRefresh.setVisibility(View.GONE);
        checkRegStatus();
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkRegStatus();
            }
        });

        return rootView;
    }

    public void checkRegStatus(){
        Call<MessReg> call = mRetrofitService.getMessRegDetails("1");
        call.enqueue(new Callback<MessReg>() {
            @Override
            public void onResponse(Call<MessReg> call, Response<MessReg> response) {
                swipeRefresh.setVisibility(View.VISIBLE);
                swipeRefresh.setRefreshing(false);
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
                    showMmessRegLayout(mess1Left, mess2Left);
                    //TODO: Start registration
                } else {
                    setNetworkError();
                }
            }

            @Override
            public void onFailure(Call<MessReg> call, Throwable t) {
                swipeRefresh.setVisibility(View.VISIBLE);
                messRegProgress.setVisibility(View.GONE);
                setNetworkError();
            }
        });
    }

    private void showMmessRegLayout(String mess1left, String mess2left) {
        boolean anyOneMessFilled = Integer.parseInt(mess1left) == 0 || Integer.parseInt(mess2left) == 0;
        int filledMess = 0;
        if (anyOneMessFilled) {
            if (Integer.parseInt(mess1left) == 0)
                filledMess = 1;
            else
                filledMess = 2;

            String result = "Mess " + filledMess + " is already filled up.";
            messRegMsgText.setText(result);
            btnRegister.setEnabled(false);
        } else {
            btnRegister.setEnabled(true);
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rbMess1.isChecked()) {
                        sendRequest("1");
                    } else if (rbMess2.isChecked()) {
                        sendRequest("2");
                    } else {
                        Toast.makeText(getContext(), "Select which mess you want to register to", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        mess1SeatsLeft.setText(mess1left);
        mess2SeatsLeft.setText(mess2left);
    }

    private void sendRequest(String messNo) {
        Call<MessRegistrationResponse> call = mRetrofitService.getMessRegResponse("1", uid, password, messNo);
        call.enqueue(new Callback<MessRegistrationResponse>() {
            @Override
            public void onResponse(Call<MessRegistrationResponse> call, Response<MessRegistrationResponse> response) {
                MessRegistrationResponse regResponse = response.body();

                if (regResponse.getPass().equals("0")) {
                    String result = "Successfully registered to mess " + regResponse.getMess();
                    messRegMsgText.setText(result);
                } else {
                    Toast.makeText(getActivity(), "Something went wrong. Please register by website! ", Toast.LENGTH_LONG).show();
                }

                mess1SeatsLeft.setText(regResponse.getMess1SeatsLeft());
                mess2SeatsLeft.setText(regResponse.getMess2SeatsLeft());

                boolean anyOneMessFilled = Integer.parseInt(regResponse.getMess1SeatsLeft()) == 0 || Integer.parseInt(regResponse.getMess2SeatsLeft()) == 0;
                int filledMess = 0;
                if (anyOneMessFilled) {
                    if (Integer.parseInt(regResponse.getMess1SeatsLeft()) == 0)
                        filledMess = 1;
                    else
                        filledMess = 2;

                    String seats = "Mess " + filledMess + " is already filled up.";
                    messRegMsgText.setText(seats);
                    btnRegister.setEnabled(false);
                }
            }

            @Override
            public void onFailure(Call<MessRegistrationResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Something went wrong. Please register by website!", Toast.LENGTH_LONG).show();
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
        messRegMsgText.setVisibility(View.VISIBLE);
        messRegErrorImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.outline_warning_24));
        messRegMsgText.setText(getString(R.string.mess_reg_closed));
    }

    private void setMessRegOpened() {
        messRegInstructionsText.setVisibility(View.VISIBLE);
        messRegInstructionsText.setText(getString(R.string.mess_reg_instructions));
        radioGroup.setVisibility(View.VISIBLE);
        mess1SeatsLeft.setVisibility(View.VISIBLE);
        mess2SeatsLeft.setVisibility(View.VISIBLE);
        btnRegister.setVisibility(View.VISIBLE);
        messRegErrorImage.setVisibility(View.GONE);
    }

    private void setNetworkError() {
        messRegInstructionsText.setVisibility(View.GONE);
        radioGroup.setVisibility(View.GONE);
        mess1SeatsLeft.setVisibility(View.GONE);
        mess2SeatsLeft.setVisibility(View.GONE);
        btnRegister.setVisibility(View.GONE);
        messRegErrorImage.setVisibility(View.VISIBLE);
        messRegMsgText.setVisibility(View.VISIBLE);
        messRegErrorImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.outline_cloud_off_24));
        messRegMsgText.setText("Error connecting to our servers");
    }


}

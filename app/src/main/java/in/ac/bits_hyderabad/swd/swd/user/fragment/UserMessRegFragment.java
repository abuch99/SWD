package in.ac.bits_hyderabad.swd.swd.user.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import in.ac.bits_hyderabad.swd.swd.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserMessRegFragment extends Fragment {


    SwipeRefreshLayout swipeRefresh;
    LinearLayout llmessRegOpen,llmessRegClosed;
    boolean regOpenFlag=false;
    Button btnRegister;
    String uid, password;
    RadioGroup radioGroup;
    ProgressDialog dialog;
    TextView tvMess1Left,tvMess2Left;

    public UserMessRegFragment() {
        // Required empty public constructor
    }

    public static UserMessRegFragment newInstance(String uid, String password){
        UserMessRegFragment f = new UserMessRegFragment();
        Bundle args=new Bundle();
        args.putString("uid",uid);
        args.putString("password",password);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_mess_reg, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        llmessRegClosed=view.findViewById(R.id.llmessRegClosed);
        llmessRegOpen=view.findViewById(R.id.llmessRegOpen);
        llmessRegClosed.setVisibility(View.GONE);
        llmessRegOpen.setVisibility(View.GONE);
        radioGroup=view.findViewById(R.id.rgMessChoice);
        btnRegister=view.findViewById(R.id.btnRegister);

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setInverseBackgroundForced(false);

        tvMess1Left=view.findViewById(R.id.tvMess1Left);
        tvMess2Left=view.findViewById(R.id.tvMess2Left);

        uid=this.getArguments().getString("uid");
        password=this.getArguments().getString("password");

        swipeRefresh=view.findViewById(R.id.swipeRefreshMessReg);

        swipeRefresh.setRefreshing(true);

        checkRegStatus();
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkRegStatus();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mess_no="0";
                RadioButton button=view.findViewById(radioGroup.getCheckedRadioButtonId());
                if (button.getId()==R.id.rbMess1){
                    mess_no="1";
                    Log.e("mess_no",mess_no);
                    sendRequest(uid,password,mess_no);
                }
                else if(button.getId()==R.id.rbMess2){
                    mess_no="2";
                    Log.e("mess_no",mess_no);
                    sendRequest(uid,password,mess_no);
                }
                else {
                    Toast.makeText(getActivity(),"Please select one of the mess!!",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void checkRegStatus(){
        Log.e("checking reg status","");
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.MessURL), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object=new JSONObject(response);
                    if(object.getString("pass").equals("0")){
                        String mess1Left=object.getString("Mess1left");
                        String mess2Left=object.getString("Mess2left");
                        regOpenFlag=true;
                        showMessRegLayout(mess1Left,mess2Left);
                        swipeRefresh.setRefreshing(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefresh.setRefreshing(false);
                int errorCode=error.networkResponse.statusCode;
                if(errorCode==502){
                    regOpenFlag=false;
                    llmessRegClosed.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Mess reg is closed right now!", Toast.LENGTH_SHORT).show();
                    swipeRefresh.setRefreshing(false);
                }
                else {
                    Toast.makeText(getContext(), "Please check your Internet Connection!", Toast.LENGTH_SHORT).show();
                    swipeRefresh.setRefreshing(false);
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                return params;

            }
        };

        queue.add(request);
    }

    public void showMessRegLayout(String mess1left, String  mess2left){

        llmessRegOpen.setVisibility(View.VISIBLE);
        llmessRegClosed.setVisibility(View.GONE);

        tvMess1Left.setText(mess1left);
        tvMess2Left.setText(mess2left);

    }
    public void sendRequest(String uid, String password, String mess_no){

        dialog.show();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.MessRegURL), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);

                    String uid=jsonObject.getString("UID");
                    String mess=jsonObject.getString("Mess");
                    String mess1_left=jsonObject.getString("Mess1left");
                    String mess2_left=jsonObject.getString("Mess2left");
                    String pass=jsonObject.getString("Pass");

                    tvMess1Left.setText(mess1_left);
                    tvMess2Left.setText(mess2_left);

                    if(pass.equals("0")){
                        Toast.makeText(getActivity(),"Succesfully registered to Mess "+mess,Toast.LENGTH_LONG).show();
                        dialog.cancel();
                    }
                    else{
                        Toast.makeText(getActivity(),"Something went wrong. Please register by website! ",Toast.LENGTH_LONG).show();
                        dialog.cancel();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(),"Something went wrong. Please register by website!",Toast.LENGTH_LONG).show();
                    dialog.cancel();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefresh.setRefreshing(false);
                int errorCode=error.networkResponse.statusCode;
                if(errorCode==502){
                    regOpenFlag=false;
                    Toast.makeText(getContext(), "Mess reg is closed right now!", Toast.LENGTH_SHORT).show();
                    llmessRegOpen.setVisibility(View.GONE);
                    dialog.cancel();
                }
                else {
                    Toast.makeText(getContext(), "Please check your Internet Connection!", Toast.LENGTH_SHORT).show();
                    llmessRegClosed.setVisibility(View.GONE);
                    llmessRegOpen.setVisibility(View.GONE);
                    dialog.cancel();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("user_name", uid);
                params.put("user_password",password);
                params.put("isapp","1");
                params.put("user_mess",mess_no);
                return params;

            }
        };

        queue.add(request);
    }
}

package in.ac.bits_hyderabad.swd.swd.user.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.ac.bits_hyderabad.swd.swd.R;
import in.ac.bits_hyderabad.swd.swd.helper.Deduction;
import in.ac.bits_hyderabad.swd.swd.helper.DeductionsAdapter;
import in.ac.bits_hyderabad.swd.swd.helper.Goodies;
import in.ac.bits_hyderabad.swd.swd.helper.GoodiesAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class User_DeductionsFragment extends Fragment {


    RecyclerView rvDeductions;
    DeductionsAdapter mAdaptor;
    LinearLayoutManager mLayoutManager;
    ArrayList<Deduction> deductions;
    SwipeRefreshLayout swipeRefreshDed;

    public User_DeductionsFragment() {
        // Required empty public constructor
    }

    public static User_DeductionsFragment newInstance(String uid, String id_no, String password){
        User_DeductionsFragment f = new User_DeductionsFragment();
        Bundle args=new Bundle();
        args.putString("uid",uid);
        args.putString("id_no",id_no);
        args.putString("password",password);
        f.setArguments(args);
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_deductions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        rvDeductions=view.findViewById(R.id.rvDeductions);
        swipeRefreshDed=view.findViewById(R.id.swipeRefreshDed);
        swipeRefreshDed.setRefreshing(true);
        rvDeductions.bringToFront();
        rvDeductions.setHasFixedSize(false);
        deductions=new ArrayList<>();
        loadDeductions();
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

    }

    public void loadDeductions(){
        String u_id=this.getArguments().getString("uid");
        String password=this.getArguments().getString("password");

        RequestQueue queue = Volley.newRequestQueue(getContext());

        StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.BASE_URL), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray array =new JSONArray(response);
                    for(int i=0;i<array.length();i++){
                        String id=array.getJSONObject(i).getString("id");
                        String name=array.getJSONObject(i).getString("name");
                        String amount=array.getJSONObject(i).getString("amount");
                        String xs=array.getJSONObject(i).getString("xs");
                        String s=array.getJSONObject(i).getString("s");
                        String m=array.getJSONObject(i).getString("m");
                        String l=array.getJSONObject(i).getString("l");
                        String xl=array.getJSONObject(i).getString("xl");
                        String xxl=array.getJSONObject(i).getString("xxl");
                        String xxxl=array.getJSONObject(i).getString("xxxl");
                        String qut=array.getJSONObject(i).getString("qut");
                        String type=array.getJSONObject(i).getString("type");
                        String netqut=array.getJSONObject(i).getString("netqut");
                        if(!amount.equals("0")){
                        deductions.add(new Deduction(type,id,name,amount, xs,s,m,l,xl,xxl,xxxl,qut,netqut));}
                    }
                    mAdaptor.notifyDataSetChanged();
                    swipeRefreshDed.setRefreshing(false);
                } catch (JSONException e) {
                    Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Please check your Internet connection!", Toast.LENGTH_SHORT).show();
                swipeRefreshDed.setRefreshing(false);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("tag", "deductions");
                params.put("id",u_id);
                params.put("pwd",password);
                return params;

            }
        };


        queue.add(request);

    }
}

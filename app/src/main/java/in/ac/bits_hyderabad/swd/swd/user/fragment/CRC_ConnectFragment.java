package in.ac.bits_hyderabad.swd.swd.user.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import in.ac.bits_hyderabad.swd.swd.R;
import in.ac.bits_hyderabad.swd.swd.helper.Person;
import in.ac.bits_hyderabad.swd.swd.helper.PersonAdapter;

public class CRC_ConnectFragment extends Fragment {

    private RecyclerView rvCRC;
    private RecyclerView.Adapter mAdaptor;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Person> personCRC;
    SwipeRefreshLayout swipeRefresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_connect_org, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        swipeRefresh = view.findViewById(R.id.swipeRefreshConnect);
        swipeRefresh.setRefreshing(true);

        personCRC =new ArrayList<>();
        rvCRC = view.findViewById(R.id.rvConnect);
        mLayoutManager =new LinearLayoutManager(getActivity());
        rvCRC.setLayoutManager(mLayoutManager);
        mAdaptor=new PersonAdapter(personCRC,getActivity());
        rvCRC.setAdapter(mAdaptor);
        getContactsCRC();
        mAdaptor.notifyDataSetChanged();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                personCRC.clear();
                mAdaptor.notifyDataSetChanged();
                getContactsCRC();
            }
        });

    }
    public void getContactsCRC()
    {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.BASE_URL), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    JSONArray jsonArray=object.getJSONArray("data");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject obj=jsonArray.getJSONObject(i);
                        if(obj.getString("heading").equals("Corroboration and Review Committee"))
                        {
                            String name=obj.getString("name");
                            String designation=obj.getString("designation");
                            String phone=obj.getString("phone");
                            String uid=obj.getString("uid");
                            String heading=obj.getString("heading");
                            String subheading=obj.getString("subheading");
                            String order=obj.getString("order");

                            personCRC.add(new Person(name,designation,phone,uid,heading,subheading,order));
                            Collections.sort(personCRC);

                        }

                    }
                    mAdaptor.notifyDataSetChanged();
                    swipeRefresh.setRefreshing(false);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();

                    swipeRefresh.setRefreshing(false);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefresh.setRefreshing(false);
                Toast.makeText(getContext(), "Please check your Internet connection!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("tag", "get_contacts");
                return params;

            }
        };


        queue.add(request);
    }
}

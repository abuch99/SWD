package in.ac.bits_hyderabad.swd.swd.user.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import in.ac.bits_hyderabad.swd.swd.helper.Goodies;
import in.ac.bits_hyderabad.swd.swd.helper.GoodiesAdapter;
import in.ac.bits_hyderabad.swd.swd.user.activity.User_Login;
import in.ac.bits_hyderabad.swd.swd.user.activity.User_Nav;

public class User_GoodiesFragment extends Fragment implements GoodiesAdapter.itemClicked {

    RecyclerView rvGoodiesList;
    RecyclerView.Adapter mAdaptor;
    RecyclerView.LayoutManager mLayoutManager;
    ProgressDialog dialog;
    ArrayList<Goodies> goodies;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View view = inflater.inflate(R.layout.goodies_fragment, parent, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        dialog=new ProgressDialog(getContext());
        dialog.setMessage("Loading...");
        goodies=new ArrayList<Goodies>();
        loadGoodies();//define your goodies array list here
        Log.e("goodie array list",goodies.toString());
        rvGoodiesList=view.findViewById(R.id.rvGoodiesList);
        rvGoodiesList.setHasFixedSize(false);
        mLayoutManager =new LinearLayoutManager(this.getActivity());
        rvGoodiesList.setLayoutManager(mLayoutManager);
        mAdaptor=new GoodiesAdapter(this,goodies);
        rvGoodiesList.setAdapter(mAdaptor);
        mAdaptor.notifyDataSetChanged();
    }

    @Override
    public void onItemClicked(int index) {
        Log.e("hello","hello");

    }
    public  void loadGoodies()
    {
        dialog.show();
        RequestQueue queue = Volley.newRequestQueue(getContext());

        StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.BASE_URL), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("GoodieResponse: ", response);

                try {
                    JSONArray jsonArray=new JSONArray(response);
                    Log.e("Goodieobj: ",jsonArray.get(0).toString());
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject obj=jsonArray.getJSONObject(i);
                        String id=obj.getString("g_id");
                        String name=obj.getString("name");
                        String host=obj.getString("hosted_by");
                        String image=obj.getString("img");
                        String price=obj.getString("price");
                        String host_name=obj.getString("hoster_name");
                        String mobile=obj.getString("hoster_mob_num");
                        goodies.add(new Goodies(id,name,host,image,price,host_name,mobile));
                        Log.e("added", obj.toString());
                    }
                    mAdaptor.notifyDataSetChanged();
                    dialog.hide();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                    Log.e("exc: ",e.toString());
                    dialog.hide();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                Toast.makeText(getContext(), "Please check your Internet connection!", Toast.LENGTH_SHORT).show();
                dialog.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("tag", "goodie");
                return params;

            }
        };


        queue.add(request);


    }
}

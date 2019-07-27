package in.ac.bits_hyderabad.swd.swd.user.fragment;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import in.ac.bits_hyderabad.swd.swd.user.activity.OrderGoodie;

public class User_GoodiesFragment extends Fragment implements GoodiesAdapter.itemClicked {

    RecyclerView rvGoodiesList;
    RecyclerView.Adapter mAdaptor;
    RecyclerView.LayoutManager mLayoutManager;
    ProgressDialog dialog;
    ArrayList<Goodies> goodies;
    SwipeRefreshLayout swipeRefresh;
    boolean LIMITED_GOODIE=false;
    String previous;

    public static User_GoodiesFragment newInstance(String uid, String id_no){
        User_GoodiesFragment f = new User_GoodiesFragment();
        Bundle args=new Bundle();
        args.putString("uid",uid);
        args.putString("id_no",id_no);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View view = inflater.inflate(R.layout.fragment_goodies, parent, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        swipeRefresh=view.findViewById(R.id.swipeRefresh);
        swipeRefresh.setRefreshing(true);
        dialog=new ProgressDialog(getContext());
        dialog.setMessage("Loading...");
        dialog.setCanceledOnTouchOutside(false);
        goodies=new ArrayList<Goodies>();
        loadGoodies();//define your goodies array list here
        Log.e("goodie array list",goodies.toString());
        rvGoodiesList=view.findViewById(R.id.rvGoodiesList);
        rvGoodiesList.bringToFront();
        rvGoodiesList.setHasFixedSize(false);
        mLayoutManager =new LinearLayoutManager(this.getActivity());
        rvGoodiesList.setLayoutManager(mLayoutManager);
        mAdaptor=new GoodiesAdapter(getActivity(),this,goodies);
        rvGoodiesList.setAdapter(mAdaptor);
        mAdaptor.notifyDataSetChanged();


        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                goodies.clear();
                mAdaptor.notifyDataSetChanged();
                loadGoodies();
            }
        });
    }

    @Override
    public void onItemClicked(int index) {

        dialog.show();
        String u_id=this.getArguments().getString("uid");
        getPreviousData(u_id,goodies.get(index).getId(),index);

    }
    public  void loadGoodies()
    {
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
                        String price="₹ "+obj.getString("price");
                        String size_chart=obj.getString("link");
                        String xs=obj.getString("xs");
                        String s=obj.getString("s");
                        String m=obj.getString("m");
                        String l=obj.getString("l");
                        String xl=obj.getString("xl");
                        String xxl=obj.getString("xxl");
                        String xxxl=obj.getString("xxxl");
                        String qut=obj.getString("qut");
                        String min_amount=obj.getString("min_amount");
                        String max_amount=obj.getString("max_amount");
                        String max_quantity=obj.getString("max_quantity");
                        String custom=obj.getString("custom");
                        String view_uid=obj.getString("view_uid");
                        String host_name=obj.getString("hoster_name");
                        String mobile=obj.getString("hoster_mob_num");

                        /*{"g_id":"1000","name":"Duplicate ID Card","hosted_by":"Student Welfare Division","img":"bits_logo.png", "link":"","active":"1","xs":"0","s":"0","m":"0","l":"0","xl":"0","xxl":"0","xxxl":"0","qut":"0","min_amount":"0","max_amount":"0","max_quantity":"0","price":"75","closing_datetime":"2019-05-14 00:00:00","delivery_date":"0000-00-00","custom":"0","acceptance":"0","hoster_name":"Student Welfare Division","hoster_mob_num":"","view_uid":"prasanth","uploaded_on":"2019-04-14 15:08:37"} */
                        goodies.add(new Goodies(id,name,host,image,price,size_chart,xs,s,m,l,xl,xxl,xxxl,qut,min_amount,max_amount,max_quantity,custom,view_uid,host_name,mobile));
                        Log.e("added", obj.toString());
                    }
                    mAdaptor.notifyDataSetChanged();
                    swipeRefresh.setRefreshing(false);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                    Log.e("exc: ",e.toString());
                    swipeRefresh.setRefreshing(false);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                Toast.makeText(getActivity(), "Please check your Internet connection!", Toast.LENGTH_SHORT).show();
                swipeRefresh.setRefreshing(false);
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

    private void getPreviousData(String u_id, String g_id, int index) {


        RequestQueue queue = Volley.newRequestQueue(getContext());

        StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.BASE_URL), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("LoginResponse: ", response);

                try {

                    JSONObject obj = new JSONObject(response);
                    int itemsLeft = LIMITED_GOODIE ? Integer.parseInt(obj.getString("items_left")) : 99999999;
                    String type = obj.getString("type");
                    Log.e("type",type);
                    JSONObject previousdetails = new JSONObject();
                    if (type.equals("first_time")) {

                        previousdetails.put("first_time",true);

                    } else {
                        previousdetails = obj.getJSONObject("previous_details");
                        Log.e("previous", previousdetails.toString());
                        previousdetails.put("first_time",false);
                    }

                    previous=previousdetails.toString();
                    Intent intent=new Intent(getActivity(),OrderGoodie.class);
                    intent.putExtra("goodieClicked",goodies.get(index));
                    intent.putExtra("previous",previous);
                    intent.putExtra("items_left",itemsLeft);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        startActivity(intent, ActivityOptions.makeCustomAnimation(getContext(),R.xml.slide_in_right,R.xml.slide_in_right).toBundle());
                        dialog.cancel();
                    }
                    else{
                        startActivity(intent);
                        dialog.cancel();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                Toast.makeText(getContext(), "Please check your Internet connection!", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();

                map.put("tag", "goodie_selected");
                map.put("id", u_id);
                map.put("g_id", g_id);
                return map;

            }
        };


        queue.add(request);

    }

}

package in.ac.bits_hyderabad.swd.swd.user.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

import in.ac.bits_hyderabad.swd.swd.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessMenu extends Fragment {


    private String day;
    int mess=0;
    private LinearLayout llMenu;
    private String uid;
    private SwipeRefreshLayout swipeRefresh;

    public MessMenu(String day, String uid) {
        this.day=day;
        this.uid=uid;
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

        llMenu.setVisibility(View.GONE);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        swipeRefresh=view.findViewById(R.id.swipeRefreshMenu);
        swipeRefresh.setRefreshing(true);

        getMessNo(uid);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getMessNo(uid);

            }
        });

        
    }

    public void getMessMenu(final String day, final int messNo){

        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.BASE_URL), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONArray menu_array=new JSONArray(response);
                    for(int i=0;i<menu_array.length();i++)
                    {
                        JSONObject object = menu_array.getJSONObject(i);
                        if(!object.get("mess").toString().equals(messNo+""))
                            continue;
                        if(object.get("Day").toString().equals(day))
                        {
                            tvDay.setText(day);
                            tvBreakfast.setText(object.get("Breakfast").toString().trim());
                            tvLunch.setText(object.get("Lunch").toString().trim());
                            tvSnacks.setText(object.get("Tiffin").toString().trim());
                            tvDinner.setText(object.get("Dinner").toString().trim());
                             break;
                        }
                    }
                    llMenu.setVisibility(View.VISIBLE);
                    swipeRefresh.setRefreshing(false);
                }
                catch (JSONException e)
                {
                    swipeRefresh.setRefreshing(false);
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                Toast.makeText(getContext(), "Please check your Internet connection!", Toast.LENGTH_SHORT).show();
                swipeRefresh.setRefreshing(false);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("tag", "mess_menu");
                return params;

            }
        };


        queue.add(request);
    }
    public void getMessNo(final String uid)
    {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.BASE_URL), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONObject object=new JSONObject(response);
                    if(object.get("error").toString().equals("false")) {
                        JSONObject newObject = (JSONObject) object.get("data");
                        String mess_no_string = newObject.get("mess").toString();
                        mess= Integer.parseInt(mess_no_string);

                        if(!(mess!=1&&mess!=2))
                            getMessMenu(day,mess);
                        else
                        {
                            swipeRefresh.setRefreshing(false);
                            Toast.makeText(getContext(),"You may not be registered to either of the Mess",Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Sorry! something went wrong. We will be back soon!!",Toast.LENGTH_SHORT).show();
                    }
                }
                catch (JSONException e)
                {
                    swipeRefresh.setRefreshing(false);
                    Toast.makeText(getActivity(),"Sorry! something went wrong. We will be back soon",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                swipeRefresh.setRefreshing(false);
                Toast.makeText(getContext(), "Please check your Internet connection!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("tag", "mess_req");
                params.put("id",uid);
                return params;

            }
        };


        queue.add(request);
    }

}

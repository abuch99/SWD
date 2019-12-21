package in.ac.bits_hyderabad.swd.swd.user.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import in.ac.bits_hyderabad.swd.swd.helper.Notice;
import in.ac.bits_hyderabad.swd.swd.helper.NoticesAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class User_NoticeFragment extends Fragment implements NoticesAdapter.itemClicked {

    RecyclerView rvNoticesList;
    RecyclerView.Adapter mAdaptor;
    RecyclerView.LayoutManager mLayoutManager;
    ProgressDialog dialog;
    ArrayList<Notice> notices;
    SwipeRefreshLayout swipeRefreshNotices;

    public static User_NoticeFragment newInstance(String uid, String id_no, String password){
        User_NoticeFragment f = new User_NoticeFragment();
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
        return inflater.inflate(R.layout.fragment_notices, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        swipeRefreshNotices=view.findViewById(R.id.swipeRefreshNotices);
        swipeRefreshNotices.setRefreshing(true);
        dialog=new ProgressDialog(getContext());
        dialog.setMessage("Loading...");
        dialog.setCanceledOnTouchOutside(false);
        notices=new ArrayList<>();
        loadNotices();//define your notices array list here
        rvNoticesList=view.findViewById(R.id.rvNoticesList);
        rvNoticesList.bringToFront();
        rvNoticesList.setHasFixedSize(false);
        mLayoutManager =new LinearLayoutManager(this.getActivity());
        rvNoticesList.setLayoutManager(mLayoutManager);
        mAdaptor=new NoticesAdapter(getActivity(),this,notices);
        mAdaptor.setHasStableIds(true);
        rvNoticesList.setAdapter(mAdaptor);
        mAdaptor.notifyDataSetChanged();


        swipeRefreshNotices.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                notices.clear();
                mAdaptor.notifyDataSetChanged();
                loadNotices();
            }
        });
    }

    void loadNotices(){

        String u_id=this.getArguments().getString("uid");
        String password=this.getArguments().getString("password");

        RequestQueue queue = Volley.newRequestQueue(getContext());

        StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.BASE_URL), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject obj=jsonArray.getJSONObject(i);
                        String pid=obj.getString("pid");
                        String uid=obj.getString("uid");
                        String uname=obj.getString("uname");
                        String category=obj.getString("category");
                        String title=obj.getString("title");
                        String subtitle=obj.getString("subtitle");
                        String body=obj.getString("body");
                        String link=obj.getString("link");
                        String image=obj.getString("image");
                        String expiry=obj.getString("expiry");
                        String utime=obj.getString("utime");



                        /*{"g_id":"1000","name":"Duplicate ID Card","hosted_by":"Student Welfare Division","img":"bits_logo.png", "link":"","active":"1","xs":"0","s":"0","m":"0","l":"0","xl":"0","xxl":"0","xxxl":"0","qut":"0","min_amount":"0","max_amount":"0","max_quantity":"0","price":"75","closing_datetime":"2019-05-14 00:00:00","delivery_date":"0000-00-00","custom":"0","acceptance":"0","hoster_name":"Student Welfare Division","hoster_mob_num":"","view_uid":"prasanth","uploaded_on":"2019-04-14 15:08:37"} */
                        notices.add(new Notice(pid,uid,uname,category,title,subtitle,body,link,image,expiry,utime));
                    }
                    mAdaptor.notifyDataSetChanged();
                    swipeRefreshNotices.setRefreshing(false);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                    swipeRefreshNotices.setRefreshing(false);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Please check your Internet connection!", Toast.LENGTH_SHORT).show();
                swipeRefreshNotices.setRefreshing(false);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("tag", "notices");
                params.put("id",u_id);
                params.put("pwd",password);
                return params;

            }
        };


        queue.add(request);
    }
    @Override
    public void onItemClicked(int index) {


    }
}

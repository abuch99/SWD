package in.ac.bits_hyderabad.swd.swd.user.fragment;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import in.ac.bits_hyderabad.swd.swd.R;

public class User_MessFragment extends Fragment {


    TabLayout tabLayoutMess;
    ViewPager viewPagerMess;
    MessMenuAdapter adapter;

    String day_today,day_tomorrow;

    Date today_date,tomorrow_date;
    String uid;

    Dialog dialog;
    DatePicker datePickerGrace;
    MaterialButton btnGraceDateSubmit;
    ProgressDialog dialogProgress;


    SharedPreferences prefs;

    public User_MessFragment(String uid){
        this.uid=uid;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View view = inflater.inflate(R.layout.mess_fragment, parent, false);

        tabLayoutMess=view.findViewById(R.id.tabLayoutMess);
        viewPagerMess=view.findViewById(R.id.viewPagerMess);
        adapter=new MessMenuAdapter(getChildFragmentManager());

        dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.grace_dialog);
        dialog.setCanceledOnTouchOutside(false);
        btnGraceDateSubmit=dialog.findViewById(R.id.btnGraceDateSubmit);
        datePickerGrace=dialog.findViewById(R.id.graceDatePicker);

        dialogProgress=new ProgressDialog(getActivity());
        dialogProgress.setCancelable(false);
        dialogProgress.setMessage("Applying for grace.. Please wait!");
        dialogProgress.create();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {



        Calendar calendar=Calendar.getInstance();
        today_date=calendar.getTime();
        Calendar calendar_tom=Calendar.getInstance();
        calendar_tom.add(Calendar.DATE,1);
        tomorrow_date=calendar_tom.getTime();

        day_today=new SimpleDateFormat("EEEE", Locale.ENGLISH).format(today_date.getTime());
        day_tomorrow=new SimpleDateFormat("EEEE",Locale.ENGLISH).format(tomorrow_date.getTime());
        datePickerGrace.setMinDate(tomorrow_date.getTime());
        Calendar calendar_MaxDate=Calendar.getInstance();
        calendar_MaxDate.add(Calendar.YEAR,1);
        datePickerGrace.setMaxDate(calendar_MaxDate.getTime().getTime());

        Log.e("Days for Mess", day_today+"  and  "+day_tomorrow);

        adapter.addFragment(new MessMenu(day_today,uid),"TODAY");
        adapter.addFragment(new MessMenu(day_tomorrow,uid),"TOMORROW");
        Log.e("uid",uid);
        viewPagerMess.setAdapter(adapter);
        tabLayoutMess.setupWithViewPager(viewPagerMess);

    }

    public class MessMenuAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public MessMenuAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.grace_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.grace:
            {
                applyGrace();
                break;
            }
        }
        return true;
    }

    public void applyGrace(){

        dialog.show();

        btnGraceDateSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String day = new DecimalFormat("00").format(datePickerGrace.getDayOfMonth());
                String month = new DecimalFormat("00").format(datePickerGrace.getMonth() + 1);
                String year = new DecimalFormat("0000").format(datePickerGrace.getYear());

                dialog.dismiss();


                dialogProgress.show();

                datePickerGrace.setMinDate(tomorrow_date.getTime());
                Calendar calendar_MaxDate=Calendar.getInstance();
                calendar_MaxDate.add(Calendar.YEAR,1);
                datePickerGrace.setMaxDate(calendar_MaxDate.getTime().getTime());

                String date_for_grace=day+"-"+month+"-"+year;
                if(date_for_grace.charAt(0)>='0'&&date_for_grace.charAt(0)<='9') {
                    sendRequestforGrace(uid, date_for_grace);
                }
                else {
                    dialogProgress.cancel();
                    Toast.makeText(getActivity(),"Something went wrong, kindly contact SWD",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    public void sendRequestforGrace(final String uid, final String date_for_grace){


        Log.e("id",uid);
        RequestQueue queue = Volley.newRequestQueue(getContext());

        StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.BASE_URL), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("GraceResponse: ", response);

                dialogProgress.cancel();
                try{
                    JSONObject object=new JSONObject(response);
                        Toast.makeText(getActivity(),object.getString("msg"),Toast.LENGTH_LONG).show();
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Toast.makeText(getActivity(),"Something went wrong, kindly contact SWD",Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                dialogProgress.cancel();
                Toast.makeText(getActivity(), "Please check your Internet connection!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("tag", "mess_grace");
                params.put("id",uid);
                params.put("date",date_for_grace);
                return params;

            }
        };


        queue.add(request);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

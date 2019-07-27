package in.ac.bits_hyderabad.swd.swd.user.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
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
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import in.ac.bits_hyderabad.swd.swd.R;
import in.ac.bits_hyderabad.swd.swd.helper.customSpan;

public class User_MessFragment extends Fragment {


    private TabLayout tabLayoutMess;
    private ViewPager viewPagerMess;
    private MessMenuAdapter adapter;
    private String uid;
    private Dialog dialog;
    private MaterialButton btnGraceDateSubmit;
    private ProgressDialog dialogProgress;
    private MaterialCalendarView graceDatePicker;
    private ArrayList<CalendarDay> currentGraces;


    String day_today,day_tomorrow;

    Date today_date,tomorrow_date;

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
        View view = inflater.inflate(R.layout.fragment_mess, parent, false);

        currentGraces=new ArrayList<>();
        tabLayoutMess=view.findViewById(R.id.tabLayoutMess);
        viewPagerMess=view.findViewById(R.id.viewPagerMess);
        adapter=new MessMenuAdapter(getChildFragmentManager());

        dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_grace);
        dialog.setCanceledOnTouchOutside(false);
        btnGraceDateSubmit=dialog.findViewById(R.id.btnGraceDateSubmit);
        graceDatePicker=dialog.findViewById(R.id.graceDatePicker);
        dialogProgress=new ProgressDialog(getActivity());
        dialogProgress.setCancelable(false);
        dialogProgress.setMessage("Loading... Please wait!");
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
        day_tomorrow=new SimpleDateFormat("EEEE", Locale.ENGLISH).format(tomorrow_date.getTime());
        Calendar calendar_MaxDate=Calendar.getInstance();
        calendar_MaxDate.add(Calendar.YEAR,1);


        CalendarDay calendarDayToday=CalendarDay.today();
        Log.e("month",calendarDayToday.getMonth()+"");
        if(calendarDayToday.getMonth()>7){
            graceDatePicker.state().edit().setMinimumDate(CalendarDay.from(calendarDayToday.getYear(),8,1));
            graceDatePicker.state().edit().setMinimumDate(CalendarDay.from(calendarDayToday.getYear()+1,1,10));
            Log.e("month>7","");
        }
        else {
            Log.e("month<7","");
            graceDatePicker.state().edit().setMinimumDate(CalendarDay.from(calendarDayToday.getYear(),1,1))
            .setMaximumDate(CalendarDay.from(calendarDayToday.getYear(),8,10)).commit();
        }




        Log.e("Days for Mess", day_today+"  and  "+day_tomorrow);
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
                requestPreviousGrace();
                break;
            }
        }
        return true;
    }

    public void requestPreviousGrace(){

        dialogProgress.show();
        sendRequestforGrace(uid,null);

        /*dialog.show();


        */
    }
    public void sendRequestforGrace(final String uid, final String date_for_grace){


        Log.e("id",uid);
        RequestQueue queue = Volley.newRequestQueue(getContext());

        StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.BASE_URL), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("GraceResponse: ", response);

                if(date_for_grace!=null) {
                    try {
                        dialogProgress.cancel();
                        JSONObject object = new JSONObject(response);
                        Toast.makeText(getActivity(), object.getString("msg"), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Something went wrong, kindly contact SWD", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    try {
                        JSONObject object = new JSONObject(response);
                        JSONArray array=object.getJSONArray("current");
                        for(int i=0; i<array.length();i++){
                            String string=array.getJSONObject(i).getString("date");
                            CalendarDay date=CalendarDay.from(Integer.parseInt(string.substring(0,4)),Integer.parseInt(string.substring(5,7)),Integer.parseInt(string.substring(8,10)));
                            currentGraces.add(date);
                        }
                        applyGrace(currentGraces);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Something went wrong, kindly contact SWD", Toast.LENGTH_LONG).show();
                    }
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
                if(date_for_grace!=null)
                params.put("date",date_for_grace);
                return params;

            }
        };

        request.setTag("MessGrace");
        queue.add(request);


    }

    public void applyGrace(ArrayList<CalendarDay> previousGrace){

        Log.e("previous graces",previousGrace.toString());
        graceDatePicker.addDecorator(new EventDecorator(Color.rgb(200,200,200),previousGrace));

        dialogProgress.cancel();
        dialog.show();

        btnGraceDateSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CalendarDay date_selected=graceDatePicker.getSelectedDate();

                String year = new DecimalFormat("0000").format(date_selected.getYear());
                String month =  new DecimalFormat("00").format(date_selected.getMonth());
                String day =  new DecimalFormat("00").format(date_selected.getDay());

                dialog.dismiss();
                dialogProgress.show();

                String date_for_grace=day+"-"+month+"-"+year;

                Log.e("date slected",date_for_grace);

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
    public class EventDecorator implements DayViewDecorator {

        private final int color;
        private final HashSet<CalendarDay> dates;

        public EventDecorator(int color, Collection<CalendarDay> dates) {
            this.color = color;
            this.dates = new HashSet<>(dates);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new customSpan(25,color));
            view.setDaysDisabled(true);
        }
    }

    @Override
    public void onPause() {
        Log.e("mess frag","onPause");
        super.onPause();
    }

    @Override
    public void onDestroy() {
        Log.e("mess frag","onDestroy");
        super.onDestroy();
    }

    @Override
    public void onStop() {
        Log.e("mess frag","onstop");
        super.onStop();

    }
}

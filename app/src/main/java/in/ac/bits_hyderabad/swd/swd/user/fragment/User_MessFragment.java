package in.ac.bits_hyderabad.swd.swd.user.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
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
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import in.ac.bits_hyderabad.swd.swd.R;
import in.ac.bits_hyderabad.swd.swd.user.activity.User_Nav;

import static android.content.Context.MODE_PRIVATE;

public class User_MessFragment extends Fragment {


    TabLayout tabLayoutMess;
    ViewPager viewPagerMess;
    MessMenuAdapter adapter;

    String day_today,day_tomorrow;

    Date today_date,tomorrow_date;
    Context context;
    public  int mess=1;
    String uid;

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

        Log.e("Days for Mess", day_today+"  and  "+day_tomorrow);

        adapter.addFragment(new MessMenu(day_today,uid),"TODAY");
        adapter.addFragment(new MessMenu(day_tomorrow,uid),"TOMORROW");

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
                break;
            }
        }
        return true;
    }


}

package in.ac.bits_hyderabad.swd.swd.user.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import in.ac.bits_hyderabad.swd.swd.R;
import in.ac.bits_hyderabad.swd.swd.helper.ConnectAdapter;
import in.ac.bits_hyderabad.swd.swd.helper.PersonAdapter;

public class User_ConnectFragment extends Fragment implements PersonAdapter.itemClicked {

    Toolbar toolbarConnect;
    TabLayout tabLayout;
    ViewPager viewPager;
    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_connect, container, false);

//        toolbarConnect = rootView.findViewById(R.id.toolbarConnect);
        tabLayout = rootView.findViewById(R.id.tabLayout);
        viewPager = rootView.findViewById(R.id.viewPager);

        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);

        return rootView;
    }

    public void setupViewPager(ViewPager viewPager){
        ConnectAdapter adapter = new ConnectAdapter(getFragmentManager());


        adapter.addFragment(new SWD_ConnectFragment(),"SWD");
        adapter.addFragment(new SU_ConnectFragment(),"SUC");
        adapter.addFragment(new CRC_ConnectFragment(),"CRC");
        adapter.addFragment(new SMC_ConnectFragment(),"SMC");
        adapter.addFragment(new EC_ConnectFragment(),"EC");
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(int intent_action, String data) {
        switch (intent_action){
            case 0:
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:"+data));//change the number
                    startActivity(callIntent);
                break;

            case 1:
                Intent intentMail = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", data+"@hyderabad.bits-pilani.ac.in", null));
                startActivity(intentMail);
                break;

        }
    }

}

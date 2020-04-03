package in.ac.bits_hyderabad.swd.swd.user.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import in.ac.bits_hyderabad.swd.swd.R;

public class User_ConnectFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager2 viewPager;

    private String[] titles = new String[]{"SWD", "SUC", "CRC", "SMC", "EC"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_connect, container, false);

        tabLayout = rootView.findViewById(R.id.tabLayout);
        viewPager = rootView.findViewById(R.id.viewPager);

        viewPager.setAdapter(new ConnectStateAdapter(getActivity()));
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(titles[position])).attach();

        return rootView;
    }

    private class ConnectStateAdapter extends FragmentStateAdapter {

        final int PAGE_COUNT = 5;

        public ConnectStateAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 1:
                    return new OrgConnectFragment(OrgConnectFragment.Companion.getTYPE_SUC());
                case 2:
                    return new OrgConnectFragment(OrgConnectFragment.Companion.getTYPE_CRC());
                case 3:
                    return new OrgConnectFragment(OrgConnectFragment.Companion.getTYPE_SMC());
                case 4:
                    return new OrgConnectFragment(OrgConnectFragment.Companion.getTYPE_EC());
                case 0:
                default:
                    return new OrgConnectFragment(OrgConnectFragment.Companion.getTYPE_SWD());
            }
        }

        @Override
        public int getItemCount() {
            return PAGE_COUNT;
        }
    }

}

package in.ac.bits_hyderabad.swd.swd.user.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import in.ac.bits_hyderabad.swd.swd.R;

public class BusTimingsFragment extends Fragment {


    public BusTimingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bus_timings, container, false);
    }

}

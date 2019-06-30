package in.ac.bits_hyderabad.swd.swd.user.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.ac.bits_hyderabad.swd.swd.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessMenu extends Fragment {


    public MessMenu() {
        // Required empty public constructor
    }

    TextView tvDay, tvBreakfast, tvLunch, tvSnacks, tvDinner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mess_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        tvDay=view.findViewById(R.id.tvDay);
        tvBreakfast=view.findViewById(R.id.tvBreakfast);
        tvLunch=view.findViewById(R.id.tvLunch);
        tvSnacks=view.findViewById(R.id.tvSnacks);
        tvDinner=view.findViewById(R.id.tvDinner);

        
    }
}

package in.ac.bits_hyderabad.swd.swd.user.fragment;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;


import in.ac.bits_hyderabad.swd.swd.R;
import in.ac.bits_hyderabad.swd.swd.user.activity.User_Nav;

public class User_HomeFragment extends Fragment  {

    View view;
    Button btnMess,btnGoodies,btnContact;

    NavigationView navigationView;

    Fragment fragment;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        view = inflater.inflate(R.layout.home_fragment, parent, false);

        btnContact=view.findViewById(R.id.btnContact);
        btnGoodies=view.findViewById(R.id.btnGoodies);
        btnMess=view.findViewById(R.id.btnMess);

        //btnMess.setVisibility(View.INVISIBLE);


        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "swdnucleus@hyderabad.bits-pilani.ac.in", null));
                startActivity(Intent.createChooser(intent, "Choose an Email client :"));

            }
        });
        btnGoodies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new User_GoodiesFragment();
                ((User_Nav)getActivity()).replaceFragment(fragment,"goodies",getString(R.string.goodies_title));
            }
        });
        btnMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new User_MessFragment();
                ((User_Nav)getActivity()).replaceFragment(fragment,"mess",getString(R.string.mess_title));
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }




    /*public void replaceFragment (Fragment someFragment){
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.layout_frame, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }*/



   /*@Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnContact:
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "swdnucleus@hyderabad.bits-pilani.ac.in", null));
                startActivity(Intent.createChooser(intent, "Choose an Email client :"));

                break;


//                actionBar.setTitle(R.string.mess_title)
//              navigationView.setCheckedItem(R.id.mess);
 //               fragment=new User_MessFragment();
   //             manager.beginTransaction().replace(R.id.layout_frame,fragment,"mess").commit();

            case R.id.btnMess:
                Fragment fragment = new User_MessFragment();
                replaceFragment(fragment);
                actionBar.setTitle(R.string.mess_title);
                break;

            case R.id.btnGoodies:
                fragment = new User_GoodiesFragment();
                replaceFragment(fragment);
                actionBar.setTitle(R.string.goodies_title);
                break;

        }

    }*/
}

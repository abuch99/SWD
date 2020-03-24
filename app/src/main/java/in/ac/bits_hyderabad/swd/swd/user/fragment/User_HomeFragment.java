package in.ac.bits_hyderabad.swd.swd.user.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import in.ac.bits_hyderabad.swd.swd.R;
import in.ac.bits_hyderabad.swd.swd.user.activity.Profile;
import in.ac.bits_hyderabad.swd.swd.user.activity.User_Login;

import static android.content.Context.MODE_PRIVATE;

public class User_HomeFragment extends Fragment  {

    View view;
    Button btnMess,btnGoodies,btnContact;
    NavigationView navigationView;
    Fragment fragment;

    ImageView myProfileImageView, logoutImageView;

    private CardView cvTD,cvERP,cvOPAC;
    private ExtendedFloatingActionButton fabContactUs;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        view = inflater.inflate(R.layout.fragment_home, parent, false);

        cvTD=view.findViewById(R.id.cvTD);
        cvERP=view.findViewById(R.id.cvERP);
        cvOPAC=view.findViewById(R.id.cvOPAC);

        myProfileImageView = view.findViewById(R.id.myProfileImageView);
        logoutImageView = view.findViewById(R.id.logoutImageView);

        cvOPAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(getString(R.string.OPAC_URL)));
                startActivity(intent);
            }
        });
        cvERP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(getString(R.string.ERP_URL)));
                startActivity(intent);
            }
        });
        cvTD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(getString(R.string.TD_URL)));
                startActivity(intent);
            }
        });
        myProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Profile.class);
                startActivity(intent);
            }
        });
        logoutImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getContext().getSharedPreferences("USER_LOGIN_DETAILS", MODE_PRIVATE).edit();
                editor.clear();
                editor.commit();
                startActivity(new Intent(getActivity(), User_Login.class));
                getActivity().finish();
            }
        });

        //btnContact=view.findViewById(R.id.btnContact);
        //btnGoodies=view.findViewById(R.id.btnGoodies);
        //btnMess=view.findViewById(R.id.btnMess);

        //btnMess.setVisibility(View.INVISIBLE);

//changes in home frag
/*      btnContact.setOnClickListener(new View.OnClickListener() {
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
                ((MainActivity)getActivity()).fragment=new User_GoodiesFragment();
                ((MainActivity)getActivity()).replaceFragment(((MainActivity)getActivity()).fragment,"goodies",getString(R.string.goodies_title));
                ((MainActivity) getActivity()).navigationView.setCheckedItem(R.id.goodies);
            }
        });
        btnMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).fragment=new User_MessFragment();
                ((MainActivity)getActivity()).replaceFragment(((MainActivity)getActivity()).fragment,"mess",getString(R.string.mess_title));
                ((MainActivity) getActivity()).navigationView.setCheckedItem(R.id.mess);
            }
        });
*/
        return view;
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

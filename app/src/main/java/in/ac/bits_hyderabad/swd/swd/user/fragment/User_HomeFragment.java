package in.ac.bits_hyderabad.swd.swd.user.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import in.ac.bits_hyderabad.swd.swd.R;
import in.ac.bits_hyderabad.swd.swd.user.activity.User_Nav;

public class User_HomeFragment extends Fragment  {

    View view;
    Button btnMess,btnGoodies,btnContact;
    NavigationView navigationView;
    Fragment fragment;

    private CardView cvTD,cvERP,cvOPAC;
    private FloatingActionButton fabContactUs;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        view = inflater.inflate(R.layout.home_fragment, parent, false);

        cvTD=view.findViewById(R.id.cvTD);
        cvERP=view.findViewById(R.id.cvERP);
        cvOPAC=view.findViewById(R.id.cvOPAC);
        fabContactUs=view.findViewById(R.id.fabContactUs);

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
        fabContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "swdnucleus@hyderabad.bits-pilani.ac.in", null));
                startActivity(Intent.createChooser(intent, "Choose an Email client :"));
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
                ((User_Nav)getActivity()).fragment=new User_GoodiesFragment();
                ((User_Nav)getActivity()).replaceFragment(((User_Nav)getActivity()).fragment,"goodies",getString(R.string.goodies_title));
                ((User_Nav) getActivity()).navigationView.setCheckedItem(R.id.goodies);
            }
        });
        btnMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((User_Nav)getActivity()).fragment=new User_MessFragment();
                ((User_Nav)getActivity()).replaceFragment(((User_Nav)getActivity()).fragment,"mess",getString(R.string.mess_title));
                ((User_Nav) getActivity()).navigationView.setCheckedItem(R.id.mess);
            }
        });
*/
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

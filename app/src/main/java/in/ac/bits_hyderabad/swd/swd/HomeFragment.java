package in.ac.bits_hyderabad.swd.swd;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment implements View.OnClickListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View view = inflater.inflate(R.layout.home_fragment, parent, false);

        Button contact = (Button) view.findViewById(R.id.contact);
        contact.setOnClickListener(this);

        Button mess = (Button) view.findViewById(R.id.mess);
        mess.setOnClickListener(this);

        Button goodies = (Button) view.findViewById(R.id.goodies);
        goodies.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.contact:
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "swdnucleus@hyderabad.bits-pilani.ac.in", null));
                startActivity(Intent.createChooser(intent, "Choose an Email client :"));

                break;

            case R.id.mess:
                Fragment fragment = new MessFragment();
                replaceFragment(fragment);
                break;

            case R.id.goodies:
                fragment = new GoodiesFragment();
                replaceFragment(fragment);
                break;

        }
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.layout_frame, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
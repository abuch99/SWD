package in.ac.bits_hyderabad.swd.swd.user.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import in.ac.bits_hyderabad.swd.swd.R;
import in.ac.bits_hyderabad.swd.swd.user.activity.Profile;
import in.ac.bits_hyderabad.swd.swd.user.activity.User_Login;

import static android.content.Context.MODE_PRIVATE;

public class User_HomeFragment extends Fragment  {

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, parent, false);

        CardView cvTD = view.findViewById(R.id.cvTD);
        CardView cvERP = view.findViewById(R.id.cvERP);
        CardView cvOPAC = view.findViewById(R.id.cvOPAC);

        ImageView myProfileImageView = view.findViewById(R.id.myProfileImageView);
        ImageView logoutImageView = view.findViewById(R.id.logoutImageView);

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
                new MaterialAlertDialogBuilder(getContext())
                        .setTitle("Sign out")
                        .setMessage("Are you sure you want to sign out?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences.Editor editor = getContext().getSharedPreferences("USER_LOGIN_DETAILS", MODE_PRIVATE).edit();
                                editor.clear();
                                editor.apply();
                                startActivity(new Intent(getActivity(), User_Login.class));
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });
        return view;
    }

}

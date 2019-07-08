package in.ac.bits_hyderabad.swd.swd.user.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.ac.bits_hyderabad.swd.swd.R;

public class User_DocFragment extends Fragment {

    TextView tvBonafied, tvNoc , tvVacation, tvGoodCharacter, tvMedical;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View view = inflater.inflate(R.layout.doc_fragment, parent, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvBonafied=view.findViewById(R.id.layoutBonafied).findViewById(R.id.tvDoc_name);
        tvBonafied.setText(getResources().getString(R.string.doc_Bonafide));

        tvNoc=view.findViewById(R.id.layoutNoObjection).findViewById(R.id.tvDoc_name);
        tvNoc.setText(getResources().getString(R.string.doc_NOC));

        tvGoodCharacter=view.findViewById(R.id.layoutGoodCharacter).findViewById(R.id.tvDoc_name);
        tvGoodCharacter.setText(getResources().getString(R.string.doc_GoodChar));

        tvMedical=view.findViewById(R.id.layoutMedInsurance).findViewById(R.id.tvDoc_name);
        tvMedical.setText(getResources().getString(R.string.doc_Medical));

        tvVacation=view.findViewById(R.id.layoutVacationLetter).findViewById(R.id.tvDoc_name);
        tvVacation.setText(getResources().getString(R.string.doc_Vacation));


    }
}

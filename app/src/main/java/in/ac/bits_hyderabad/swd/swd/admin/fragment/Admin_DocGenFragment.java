package in.ac.bits_hyderabad.swd.swd.admin.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.security.auth.callback.Callback;

import in.ac.bits_hyderabad.swd.swd.R;
import in.ac.bits_hyderabad.swd.swd.admin.activity.Admin_Nav;
import in.ac.bits_hyderabad.swd.swd.databaseconnection.APIService;
import in.ac.bits_hyderabad.swd.swd.databaseconnection.APIUtils;
import in.ac.bits_hyderabad.swd.swd.databaseconnection.responseclasses.AuthenticationData;
import in.ac.bits_hyderabad.swd.swd.user.activity.User_Login;
import in.ac.bits_hyderabad.swd.swd.user.fragment.User_MessFragment;

import retrofit2.Call;
import retrofit2.Response;

public class Admin_DocGenFragment extends Fragment implements View.OnClickListener {

    private APIService docGenUsingApi;
    Button admin_bonafide;
    EditText enterid_get;
    String enterid;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View view = inflater.inflate(R.layout.admin_doc_fragment, parent, false);

        admin_bonafide = view.findViewById(R.id.admin_bonafide);
        admin_bonafide.setOnClickListener(this);

        enterid_get = (EditText) view.findViewById(R.id.admin_doc_id);
        enterid = enterid_get.getText().toString();

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.admin_bonafide:
                final String name = "bonafide";



        }
    }
}


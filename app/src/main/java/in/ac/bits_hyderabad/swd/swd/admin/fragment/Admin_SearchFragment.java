package in.ac.bits_hyderabad.swd.swd.admin.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import in.ac.bits_hyderabad.swd.swd.R;
import in.ac.bits_hyderabad.swd.swd.user.activity.User_Nav;

public class Admin_SearchFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View view = inflater.inflate(R.layout.admin_search_fragment, parent, false);

//        Spinner spinner = (Spinner) findViewById(R.id.admin_search_spinner);
//        final ArrayAdapter<CharSequence> admin_spinner_adapter = ArrayAdapter.createFromResource(this,
//                R.array.admin_search_spinner, android.R.layout.simple_spinner_item);
//        admin_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(admin_spinner_adapter);
//
//        Spinner admin_spinner = (Spinner) findViewById(R.id.admin_search_spinner);
//        admin_spinner.setPrompt(admin_spinner_adapter.getItem(0));
//
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//
//                String str = parentView.getItemAtPosition(position).toString();
//                Intent intent = new Intent(getApplicationContext(), User_Nav.class);
//                intent.putExtra("message", str);
//
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//            }
//
//        });
        return view;
    }
}


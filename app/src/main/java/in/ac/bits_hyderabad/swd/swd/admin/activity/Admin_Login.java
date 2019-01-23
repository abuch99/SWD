package in.ac.bits_hyderabad.swd.swd.admin.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import in.ac.bits_hyderabad.swd.swd.R;
import in.ac.bits_hyderabad.swd.swd.user.activity.User_Nav;

public class Admin_Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        Spinner spinner = (Spinner) findViewById(R.id.admin_login_spinner);
        final ArrayAdapter<CharSequence> admin_spinner_adapter = ArrayAdapter.createFromResource(this,
                R.array.admin_spinner, android.R.layout.simple_spinner_item);
        admin_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(admin_spinner_adapter);

        Button mButton;

        mButton = (Button) findViewById(R.id.admin_login_submit);
        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Login.this, Admin_Nav.class);
                startActivity(intent);
            }
        });

        Spinner admin_spinner = (Spinner) findViewById(R.id.admin_login_spinner);
        admin_spinner.setPrompt(admin_spinner_adapter.getItem(0));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                String str = parentView.getItemAtPosition(position).toString();
                Intent intent = new Intent(getApplicationContext(), User_Nav.class);
                intent.putExtra("message", str);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });
    }
}

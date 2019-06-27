package in.ac.bits_hyderabad.swd.swd.user.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import in.ac.bits_hyderabad.swd.swd.R;

public class Splash_Activity extends AppCompatActivity {

    final int SPLASH_SCREEN_TIMEOUT=1500;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ProgressBar progressBar=(ProgressBar) findViewById(R.id.progressLoader);
        Sprite threeBounce =new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent =new Intent(Splash_Activity.this,User_Login.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN_TIMEOUT);
    }
}

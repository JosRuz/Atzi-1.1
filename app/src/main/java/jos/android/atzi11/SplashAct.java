package jos.android.atzi11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import jos.android.atzi11.utils.FirebaseUtil;

public class SplashAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (FirebaseUtil.isLoggedIn()){
                    startActivity(new Intent(SplashAct.this, MainActivity.class));
                }else {
                    startActivity(new Intent(SplashAct.this, LoginConCelular.class));
                }
                finish();
            }
        },1000);
    }
}
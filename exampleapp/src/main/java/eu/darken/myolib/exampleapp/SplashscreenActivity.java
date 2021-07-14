package eu.darken.myolib.exampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class SplashscreenActivity extends AppCompatActivity {

    private final int DELAY = 3000;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
//        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashscreenActivity.this, MenuActivity.class);
                SplashscreenActivity.this.startActivity(intent);
                SplashscreenActivity.this.finish();
            }
        }, DELAY);
    }
}

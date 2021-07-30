package eu.darken.myolib.exampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private Button btnMenuBelajar;
    private Button btnMyo;
    private Button btnAbout;
    static boolean myoConnect = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnMenuBelajar = findViewById(R.id.btn_belajar);
        btnMyo = findViewById(R.id.btn_koneksi);
        btnAbout = findViewById(R.id.btn_about);

        if (myoConnect) {
            btnMyo.setVisibility(View.GONE);
        }

        btnMenuBelajar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MenuBelajarActivity.class);
                MenuActivity.this.startActivity(intent);
            }
        });

        btnMyo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                MenuActivity.this.startActivity(intent);
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, IntroductionActivity.class);
                MenuActivity.this.startActivity(intent);
            }
        });
    }
}

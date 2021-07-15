package eu.darken.myolib.exampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class MenuActivity extends AppCompatActivity {

    private Button btnMenuBelajar;
    private Button btnMyo;
    static boolean myoConnect = false;
    private ToggleButton btnNotif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnMenuBelajar = findViewById(R.id.btn_belajar);
        btnMyo = findViewById(R.id.btn_koneksi);
        btnNotif = findViewById(R.id.btn_notif);

        if (myoConnect){
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

        btnNotif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    btnNotif.setTextOn("Notifikasi Menyala");
                }
                else{
                    btnNotif.setTextOff("Notifikasi Mati");
                }
            }
        });
    }
}

package eu.darken.myolib.exampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private Button btnMenuBelajar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnMenuBelajar = findViewById(R.id.btn_belajar);

        btnMenuBelajar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, BelajarActivity.class);
                MenuActivity.this.startActivity(intent);
            }
        });
    }
}

package eu.darken.myolib.exampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class ChoosingActivity extends AppCompatActivity {

    private ImageButton withTool;
    private ImageButton withoutTool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alat);

        withTool = findViewById(R.id.btn_alat);
        withoutTool = findViewById(R.id.btn_tanpa_alat);

        withTool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChoosingActivity.this, MainActivity.class);
                ChoosingActivity.this.startActivity(intent);
            }
        });

        withoutTool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChoosingActivity.this, MainActivity.class);
                ChoosingActivity.this.startActivity(intent);
            }
        });

    }
}

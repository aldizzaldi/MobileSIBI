package eu.darken.myolib.exampleapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;

import static eu.darken.myolib.exampleapp.MyoInfoView.dataSensor;
import static eu.darken.myolib.exampleapp.MyoInfoView.record;

public class NextActivity extends AppCompatActivity {

    private Button buttonRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        buttonRecord = findViewById(R.id.btn_record);

//        myoInfoView = new MyoInfoView(this);

        TextView textView = findViewById(R.id.sensor);
        TextView textView2 = findViewById(R.id.sensor2);

//        final MyoInfoView infoView = (MyoInfoView) LayoutInflater.from(this).inflate(R.layout.view_myoinfo, mContainer, false);



        buttonRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                record();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("data_sensor", dataSensor + "");
                    }
                }, 4000);

            }
        });


    }
}

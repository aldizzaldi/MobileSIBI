package eu.darken.myolib.exampleapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import java.util.Calendar;

//import eu.darken.myolib.exampleapp.alarm.AlarmReceiver;
import eu.darken.myolib.exampleapp.alarm.NotificationReceiver;

public class MenuActivity extends AppCompatActivity {

    private Button btnMenuBelajar;
    private Button btnMyo;
    private Button btnAbout;
    static boolean myoConnect = false;
    private ToggleButton btnNotif;

//    private AlarmReceiver alarmReceiver = new AlarmReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnMenuBelajar = findViewById(R.id.btn_belajar);
        btnMyo = findViewById(R.id.btn_koneksi);
        btnAbout = findViewById(R.id.btn_about);
//        btnNotif = findViewById(R.id.btn_notif);
//        btntest = findViewById(R.id.btn_coba);

//        setButton();

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

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, IntroductionActivity.class);
                MenuActivity.this.startActivity(intent);
            }
        });

//        btntest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar calendar = Calendar.getInstance();
//
//                calendar.set(Calendar.HOUR_OF_DAY,23);
//                calendar.set(Calendar.MINUTE, 45);
//                calendar.set(Calendar.SECOND, 1);
//
//                Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
//                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
//            }
//        });

//        btnNotif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    btnNotif.setTextOn("Notifikasi Menyala");
//                    alarmReceiver.setDailyReminder(MenuActivity.this,AlarmReceiver.TYPE_DAILY,"Belajar gerakan SIBI lagi yuk !");
//                }
//                else{
//                    alarmReceiver.cancelAlarm(MenuActivity.this);
//                    btnNotif.setTextOff("Notifikasi Mati");
//                }
//                saveChange(isChecked);
//            }
//        });
    }

//    private void setButton(){
//        btnNotif.setChecked(SharedPrefUtils.getBooleanSharedPref("daily", false));
//    }
//
//    private void saveChange(Boolean value){
//        SharedPrefUtils.setBooleanSharedPref("daily", value);
//    }
}

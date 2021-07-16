package eu.darken.myolib.exampleapp;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import eu.darken.myolib.exampleapp.data.api.ApiRetrofit;
import eu.darken.myolib.exampleapp.data.api.DataSensorResponse;
import eu.darken.myolib.exampleapp.data.api.IApiEndPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static eu.darken.myolib.exampleapp.MyoInfoView.dataSensorFloat;
import static eu.darken.myolib.exampleapp.MyoInfoView.record;

public class PopUpRecordActivity extends AppCompatActivity {
    TextView tvRecording, tvHasil, tvTimer;
    private final IApiEndPoint apiEndPoint = ApiRetrofit.getInstance().create(IApiEndPoint.class);
    String hasilAPI[];
    String dataSensorString;
    public int counter;
    String huruf;
    String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MjIsImlkX3VzZXIiOjEsInByb2plY3RfbmFtZSI6Im15byJ9.jij3Pb9mQiX1JqmoDAtxgHrNnWJndmOZF5f3PyCsHbc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_record);
//        getSupportActionBar().hide();

        tvRecording = findViewById(R.id.tv_recording);
        tvHasil = findViewById(R.id.tv_hasil);
        tvTimer = findViewById(R.id.tv_timer);
//        tvSalahBenar = findViewById(R.id.tv_hasil_benar_salah);

        tvHasil.setVisibility(View.GONE);
//        tvSalahBenar.setVisibility(View.GONE);

        huruf = getIntent().getStringExtra("VALUE");

        record();

        new CountDownTimer(30000, 1000){
            public void onTick(long millisUntilFinished){
                tvTimer.setText(String.valueOf(counter));
                counter++;
            }
            public  void onFinish(){
                tvTimer.setVisibility(View.GONE);
            }
        }.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sendAPI();
                tvTimer.setVisibility(View.GONE);
            }
        }, 3500);

    }

    public void sendAPI(){
        apiEndPoint.myo(token, dataSensorFloat).enqueue(new Callback<DataSensorResponse>() {
            @Override
            public void onResponse(Call<DataSensorResponse> call, Response<DataSensorResponse> response) {
                if(response.isSuccessful()){
                    hasilAPI = response.body().getValues();
                    Log.d("hasil_api", response.body().getMessage() + "/" + hasilAPI);
                    tvRecording.setVisibility(View.GONE);
                    tvHasil.setVisibility(View.VISIBLE);
//                    tvSalahBenar.setVisibility(View.VISIBLE);

                    if (huruf.equalsIgnoreCase(hasilAPI[0])){
//                        tvSalahBenar.setText("Jawabanmu BENAR");
//                        tvSalahBenar.setTextColor(0x06CE27);

                        tvHasil.setText("Hasil dari gerakanmu adalah " + hasilAPI[0].toUpperCase() + " gerakanmu sudah tepat");
                    }
                    else {
//                        tvSalahBenar.setText("Jawabanmu SALAH");
//                        tvSalahBenar.setTextColor(0xDB0A2D);
                        tvHasil.setText("Hasil dari gerakanmu adalah " + hasilAPI[0].toUpperCase() + " gerakanmu belum tepat");
                    }
                }
                else{
                    tvRecording.setVisibility(View.GONE);
                    tvHasil.setVisibility(View.VISIBLE);
                    tvHasil.setText("Error, silahkan coba lagi");
                    Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();
                    Log.d("hasil_api_gagal", "ERROR");
                }
            }

            @Override
            public void onFailure(Call<DataSensorResponse> call, Throwable throwable) {
                tvRecording.setVisibility(View.GONE);
                tvHasil.setVisibility(View.VISIBLE);
                tvHasil.setText("Error, silahkan coba lagi");
                Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_SHORT).show();
                Log.d("hasil_api_gagal1", throwable.getMessage());
            }
        });
    }
}

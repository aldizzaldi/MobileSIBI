package eu.darken.myolib.exampleapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
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

import static eu.darken.myolib.exampleapp.MyoInfoView.dataSensor;
import static eu.darken.myolib.exampleapp.MyoInfoView.record;

public class PopUpRecordActivity extends AppCompatActivity {
    TextView tvRecording, tvHasil, tvSalahBenar;
    private final IApiEndPoint apiEndPoint = ApiRetrofit.getInstance().create(IApiEndPoint.class);
    String hasilAPI;
    String dataSensorString;
    String huruf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_record);
//        getSupportActionBar().hide();

        tvRecording = findViewById(R.id.tv_recording);
        tvHasil = findViewById(R.id.tv_hasil);
        tvSalahBenar = findViewById(R.id.tv_hasil_benar_salah);

        tvHasil.setVisibility(View.GONE);
        tvSalahBenar.setVisibility(View.GONE);

        huruf = getIntent().getStringExtra("VALUE");

        record();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("data_sensor", dataSensor + "");
                sendAPI();
            }
        }, 3500);

    }

    public void sendAPI(){
        apiEndPoint.myo(dataSensor).enqueue(new Callback<DataSensorResponse>() {
            @Override
            public void onResponse(Call<DataSensorResponse> call, Response<DataSensorResponse> response) {
                if(response.isSuccessful()){
                    hasilAPI = response.body().getValues();
                    Log.d("hasil_api", response.body().getMessage() + "/" + hasilAPI);
                    tvRecording.setVisibility(View.GONE);
                    tvHasil.setVisibility(View.VISIBLE);
                    tvSalahBenar.setVisibility(View.VISIBLE);
                    tvHasil.setText("Hasil dari gerakanmu adalah " + hasilAPI.toUpperCase());
                    if (huruf.equalsIgnoreCase(hasilAPI)){
                        tvSalahBenar.setText("Jawabanmu BENAR");
                        tvSalahBenar.setTextColor(0x06CE27);
                    }
                    else {
                        tvSalahBenar.setText("Jawabanmu SALAH");
                        tvSalahBenar.setTextColor(0xDB0A2D);
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();
                    Log.d("hasil_api_gagal", "ERROR");
                }
            }

            @Override
            public void onFailure(Call<DataSensorResponse> call, Throwable throwable) {
                Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_SHORT).show();
                Log.d("hasil_api_gagal1", throwable.getMessage());
            }
        });
    }
}

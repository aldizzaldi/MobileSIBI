package eu.darken.myolib.exampleapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import static eu.darken.myolib.exampleapp.MenuActivity.myoConnect;


public class VideoViewActivity extends AppCompatActivity {
    TextView tvHuruf, tvMencoba1, tvMencoba2;
    Button btnRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        btnRecord = findViewById(R.id.btn_coba_gerakan);
        tvHuruf = findViewById(R.id.tv_huruf);
        tvMencoba1 = findViewById(R.id.tv_ingin_mencoba1);
        tvMencoba2 = findViewById(R.id.tv_ingin_mencoba2);

        if (!myoConnect){
            tvMencoba1.setVisibility(View.GONE);
            tvMencoba2.setVisibility(View.GONE);
            btnRecord.setVisibility(View.GONE);
        }

        int tanda = getIntent().getIntExtra("TANDA",99);
        int rawFile = getIntent().getIntExtra("RAW_FILE", 0);
        final String huruf = getIntent().getStringExtra("ITEM");

        if (tanda == 0){
            getSupportActionBar().setTitle("Belajar Huruf");
            tvHuruf.setText("Huruf " + huruf);
        }
        else{
            getSupportActionBar().setTitle("Belajar Kata Dasar");
            tvHuruf.setText("Kata " + huruf);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        VideoView videoView = findViewById(R.id.videoView);
        String videoPath = "android.resource://" + getPackageName() + "/" + rawFile;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);



//        tvMencoba1 = findViewById(R.id.tv_ingin_mencoba1);
//        tvMencoba1.setVisibility(View.GONE);
//        tvMencoba2 = findViewById(R.id.tv_ingin_mencoba2);
//        tvMencoba2.setVisibility(View.GONE);


        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoViewActivity.this, PopUpRecordActivity.class);
                intent.putExtra("VALUE", huruf);
                VideoViewActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

package eu.darken.myolib.exampleapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class VideoViewActivity extends AppCompatActivity {
    TextView tvHuruf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        getSupportActionBar().setTitle("Belajar Huruf");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int rawFile = getIntent().getIntExtra("RAW_FILE", 0);
        String huruf = getIntent().getStringExtra("HURUF");

        VideoView videoView = findViewById(R.id.videoView);
        String videoPath = "android.resource://" + getPackageName() + "/" + rawFile;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);

        tvHuruf = findViewById(R.id.tv_huruf);
        tvHuruf.setText("Huruf " + huruf);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

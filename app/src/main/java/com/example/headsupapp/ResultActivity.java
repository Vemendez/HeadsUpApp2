package com.example.headsupapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//import android.widget.ListView;
import android.widget.TextView;

import com.example.headsupapp.R;
//import com.example.headsupapp.MainActivity;

//import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.util.Collections;

/**
 * Created by abhay on 7/5/17.
 */

public class ResultActivity extends AppCompatActivity{
    TextView correctStringCount;
    TextView skippedStringCount;
    String[] skippedStrings;
    String[] correctStrings;
    private static final int maxVolume = 10;

    MediaPlayer mp, mpApplause;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        final float volumeMusic = (float) (1 - (Math.log(maxVolume-10)/Math.log(maxVolume)));
        final float volumeApplause = (float) (1 - (Math.log(maxVolume-5)/Math.log(maxVolume)));
        mp = MediaPlayer.create(this, R.raw.finish);
        mp.start();
        mp.setVolume(volumeMusic, volumeMusic);
        mp.setLooping(true);
        mpApplause = MediaPlayer.create(this, R.raw.applause);
        Bundle b = getIntent().getExtras();
        correctStringCount = (TextView) findViewById(R.id.number_of_correct_strings);
        skippedStringCount = (TextView) findViewById(R.id.number_of_skipped_strings);
        correctStringCount.setText(String.valueOf(b.getInt("CorrectArrSize")));
        skippedStringCount.setText(String.valueOf(b.getInt("SkippedArrSize")));
        skippedStrings = b.getStringArray("SkippedStrings");
        correctStrings = b.getStringArray("CorrectStrings");
        if(Integer.parseInt(correctStringCount.getText().toString()) > Integer.parseInt(skippedStringCount.getText().toString())){
            mp.stop();
            mpApplause.start();
            mpApplause.setVolume(volumeApplause, volumeApplause);
            mpApplause.setLooping(true);
        }
//        ListView listView = (ListView) findViewById(R.id.strings_list_view);
//        TextView stringText = (TextView) listView.findViewById(R.id.string);
//        for (int i = 0; i < (skippedStrings.length + correctStrings.length); i++){
//            stringText.setText(skippedStrings[0]);
//            listView.addView(stringText);
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    @Override
    public void onBackPressed() {
        mp.stop();
        mpApplause.stop();
        Intent i = new Intent(ResultActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mp.setVolume(0.5f, 0.5f);
        mp.start();
//        mpApplause.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mp.pause();
        mpApplause.pause();
    }
}

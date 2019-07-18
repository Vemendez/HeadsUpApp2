package com.example.headsupapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.headsupapp.R;
import com.example.headsupapp.ResultActivity;
import com.example.headsupapp.ShakeDetector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import static com.example.headsupapp.R.id.textView;
import static com.example.headsupapp.R.id.timer;


/**
 * Created by abhay on 5/5/17.
 */

public class CategoryActivity extends AppCompatActivity implements SensorEventListener {
    private static final int maxVolume = 10;
    public static Context mContext;
    private static long total;
    public TextView textViewText;
    public Intent resultIntent;
    Sensor accelerometer;
    Sensor magnetometer;
    float[] mGravity;
    float[] mGeomagnetic;
    TextView timerText;
    TextView promptText;
    TextView threeTwoOneText;
    ArrayList doneStrArr = new ArrayList();
    ArrayList skippedStrArr = new ArrayList();
    String[] presentStr = null;
    ArrayList strArr = null;
    Random randomInt = new Random();
    View overlayView = null;
    MediaPlayer mpNew;
    MediaPlayer mpRed;
    MediaPlayer mpGreen;
    MediaPlayer mpTimeOut;
    MediaPlayer mpTick;
    MediaPlayer mpStart;
    //    Button exitButton = null;
    private SensorManager mSensorManager;
    private ShakeDetector mShakeDetector;
    CountDownTimer gamePauseTime = new CountDownTimer(total, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            total = millisUntilFinished;
            timerText.setText(millisUntilFinished / 1000 + " Seconds");
        }

        @Override
        public void onFinish() {
            textViewText.setText("Time Over!");
            textViewText.postDelayed(new Runnable() {
                @Override
                public void run() {
                    resultIntent.putExtra("Skipped", skippedStrArr.size());
                    resultIntent.putExtra("Correct", doneStrArr.size());
                    resultIntent.putExtra("SkippedStrings", skippedStrArr.toArray());
                    resultIntent.putExtra("CorrectStrings", doneStrArr.toArray());
                    mSensorManager.unregisterListener(mShakeDetector);
                    mContext.startActivity(resultIntent);
                    skippedStrArr.clear();
                    doneStrArr.clear();
                }
            }, 1500);
            timerText.setVisibility(View.GONE);
        }
    };
    CountDownTimer gameTime = new CountDownTimer(30000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            total = millisUntilFinished;
            timerText.setText(millisUntilFinished / 1000 + " Seconds");
        }

        @Override
        public void onFinish() {
            textViewText.setText("Time Over!");
            mpTimeOut.start();
            textViewText.postDelayed(new Runnable() {
                @Override
                public void run() {
                    resultIntent.putExtra("SkippedArrSize", skippedStrArr.size());
                    resultIntent.putExtra("CorrectArrSize", doneStrArr.size());
//                    resultIntent.putExtra("SkippedStrings", skippedStrArr.toArray());
//                    resultIntent.putExtra("CorrectStrings", doneStrArr.toArray());
                    mSensorManager.unregisterListener(mShakeDetector);
                    mContext.startActivity(resultIntent);
                    skippedStrArr.clear();
                    doneStrArr.clear();
                }
            }, 1500);
            timerText.setVisibility(View.GONE);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        mContext = this;
        setContentView(R.layout.category_activity);
        strArr = b.getStringArrayList(b.keySet().iterator().next());

        String catName = b.keySet().iterator().next().toLowerCase();
        int ch;
        String str = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open(catName+".txt")));
            while((ch = bufferedReader.read()) != -1){
                str += (char)ch;
            }
            String[] strArray = str.split(",");
            ArrayList strArr = new ArrayList(Arrays.asList(strArray));

        } catch (IOException e) {
            e.printStackTrace();
        }

        textViewText = (TextView) findViewById(textView);
        timerText = (TextView) findViewById(timer);
        mpGreen = MediaPlayer.create(mContext, R.raw.green);
        mpRed = MediaPlayer.create(mContext, R.raw.red);
        mpNew = MediaPlayer.create(mContext, R.raw.newname);
        mpTimeOut = MediaPlayer.create(mContext, R.raw.timeout);
        mpTick = MediaPlayer.create(mContext, R.raw.tick);
        mpStart = MediaPlayer.create(mContext, R.raw.start);
        resultIntent = new Intent(CategoryActivity.mContext, ResultActivity.class);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
//        exitButton = (Button) findViewById(R.id.exit_game_button);
        mShakeDetector = new ShakeDetector();
        Collections.shuffle(strArr);
        presentStr = new String[]{strArr.get(0).toString()};
        strArr.remove(0);
        textViewText.setText(presentStr[0]);
        overlayView = findViewById(R.id.overlay);
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake(int count) {
                mpGreen.setVolume(0.5f, 0.5f);
                mpGreen.start();
                Collections.shuffle(strArr);
                presentStr[0] = strArr.get(0).toString();
                doneStrArr.add(presentStr[0]);
                overlayView.setVisibility(View.VISIBLE);
                strArr.remove(0);
                textViewText.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        overlayView.setVisibility(View.GONE);
                        textViewText.setText(presentStr[0]);
                        mpNew.start();
                    }
                }, 1400);
            }
        });
        textViewText.setVisibility(View.GONE);
        timerText.setVisibility(View.GONE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP) || (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)) {
            mpRed.selectTrack(R.raw.red);
            skippedStrArr.add(textViewText.getText().toString());
            overlayView.setBackgroundColor(Color.parseColor("#80ff0004"));
            overlayView.setVisibility(View.VISIBLE);
            mpRed.start();
            textViewText.postDelayed(new Runnable() {
                @Override
                public void run() {
                    presentStr[0] = strArr.get(randomInt.nextInt(strArr.size())).toString();
                    overlayView.setVisibility(View.GONE);
                    overlayView.setBackgroundColor(Color.parseColor("#8000FF00"));
                    textViewText.setText(presentStr[0]);
                    mpNew.start();
                }
            }, 1400);
        }

        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//            builder.setTitle(R.string.app_name);
//            builder.setMessage("This will exit the game. Do you want to exit?");
//            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int id) {
//                    onDestroy();
//                    onBackPressed();
//                }
//            });
//            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            });
//            AlertDialog exitDialog = builder.create();
//            exitDialog.show();
            onGamePause();
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle(R.string.app_name);
            builder.setMessage("Game Paused! Do you want to continue?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    Log.d(">>>>", "onClick: " + total);
                    gamePauseTime = new CountDownTimer(total, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            total = millisUntilFinished;
                            timerText.setText(millisUntilFinished / 1000 + " Seconds");
                        }

                        @Override
                        public void onFinish() {
                            textViewText.setText("Time Over!");

                            textViewText.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    resultIntent.putExtra("Skipped", skippedStrArr.size());
                                    resultIntent.putExtra("Correct", doneStrArr.size());
                                    mSensorManager.unregisterListener(mShakeDetector);
                                    mContext.startActivity(resultIntent);
                                    skippedStrArr.clear();
                                    doneStrArr.clear();
                                }
                            }, 1500);
                            timerText.setVisibility(View.GONE);
                        }
                    }.start();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    onBackPressed();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        return true;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
//        final float volume = (float) (1 - (Math.log(maxVolume-5)/Math.log(maxVolume)));
        textViewText = (TextView) findViewById(textView);
        timerText = (TextView) findViewById(R.id.timer);
        threeTwoOneText = (TextView) findViewById(R.id.three_two_one_text);
        promptText = (TextView) findViewById(R.id.promptText);
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            mGravity = event.values;
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mGeomagnetic = event.values;
        if (mGravity != null && mGeomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                float azimut;
                azimut = orientation[2]; // orientation contains: azimut, pitch and roll
                if ((Math.toDegrees(azimut) <= -80 && Math.toDegrees(azimut) >= -90)) {
                    promptText.setVisibility(View.GONE);
                    textViewText.setVisibility(View.GONE);
                    timerText.setVisibility(View.GONE);
                    threeTwoOneText.setVisibility(View.VISIBLE);
                    new CountDownTimer(4000, 1000) {

                        @Override
                        public void onTick(long millisUntilFinished) {
                            mpTick.start();
                            threeTwoOneText.setText(String.valueOf(millisUntilFinished / 1000));
                        }

                        @Override
                        public void onFinish() {
                            mpStart.start();
//                            mpStart.setVolume(volume, volume);
                            threeTwoOneText.setText("Start!");
                        }
                    }.start();
                    mSensorManager.unregisterListener(this);

                    textViewText.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startGame();
                        }
                    }, 5000);

                } else {
//                    textViewText.setText("");
//                    timerText.setText("");
                    textViewText.setVisibility(View.GONE);
                    timerText.setVisibility(View.GONE);
                    promptText.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void startGame() {
        threeTwoOneText.setVisibility(View.GONE);
//        exitButton.setVisibility(View.VISIBLE);
        textViewText.setVisibility(View.VISIBLE);
        timerText.setVisibility(View.VISIBLE);
        promptText.setVisibility(View.GONE);
        gameTime.start();
    }

    protected void onGamePause() {
        gameTime.cancel();
        gamePauseTime.cancel();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mSensorManager.unregisterListener(this);
        
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(CategoryActivity.this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(CategoryActivity.this, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mShakeDetector, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}


package com.jawline.exercises.faceyoga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {


    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        CountDownTimer countDownTimer;

//        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsFile", 0);
//        this.sharedPreferences = sharedPreferences;
//        if (sharedPreferences.getBoolean("my_first_time", true)) {
//            countDownTimer = new a();
//        } else {
//            Log.i("IsFirstTimeUser", "NOPE");
//            countDownTimer = new b();
//        }
//        countDownTimer.start();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsFile", 0);
        this.sharedPreferences = sharedPreferences;

        if (sharedPreferences.getBoolean("isFirstTimeUser", true)) {
            countDownTimer = new FirstTimeUserTimer();
        } else {
            Log.i("IsFirstTimeUser", "NOPE");
            countDownTimer = new ReturningUserTimer();
        }
        countDownTimer.start();

    }

//    public class a extends CountDownTimer {
//        public a() {
//            super(1000, 1000);
//        }
//
//        public final void onFinish() {
//            sharedPreferences.edit().putBoolean("my_first_time", false).commit();
//            startActivity(new Intent(SplashActivity.this, ContinueActivity.class));
//            finish();
//        }
//
//        public final void onTick(long j9) {
//        }
//    }

//    public class b extends CountDownTimer {
//        public b() {
//            super(1000, 1000);
//        }
//
//        public final void onFinish() {
//            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
//
//        public final void onTick(long j9) {
//        }
//    }


    public class FirstTimeUserTimer extends CountDownTimer {
        public FirstTimeUserTimer() {
            super(1000, 1000);
        }

        public final void onFinish() {
            sharedPreferences.edit().putBoolean("isFirstTimeUser", false).commit();
            startActivity(new Intent(SplashActivity.this, ContinueActivity.class));
            finish();
        }

        public final void onTick(long millisUntilFinished) {
        }
    }

    public class ReturningUserTimer extends CountDownTimer {
        public ReturningUserTimer() {
            super(1000, 1000);
        }

        public final void onFinish() {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        public final void onTick(long millisUntilFinished) {
        }
    }
}
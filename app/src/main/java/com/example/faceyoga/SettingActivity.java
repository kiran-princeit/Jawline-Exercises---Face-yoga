package com.example.faceyoga;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {
    private long mLastClickTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        findViewById(R.id.ivBack).setOnClickListener(view -> {
            onBackPressed();

        });
        findViewById(R.id.llPrivacyPolicy).setOnClickListener(view -> {
//            String privacyUrl = GlobalVar.appData.getprivacyurl();
//            openPrivacyPolicy(privacyUrl);

        });


        findViewById(R.id.llRateUs).setOnClickListener(view -> {
            final String appName = getPackageName();
            try {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id="
                                + appName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id="
                                + appName)));
            }
        });

        findViewById(R.id.llShare).setOnClickListener(view -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            Intent ishare = new Intent(Intent.ACTION_SEND);
            ishare.setType("text/plain");
            ishare.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.app_name) + " - http://play.google.com/store/apps/details?id=" + getPackageName());
            startActivity(ishare);
        });

        findViewById(R.id.llAboutApp).setOnClickListener(view -> {
            infoDialog();
        });
    }
    private void infoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_about, null);
        builder.setView(dialogView);

        AlertDialog infodialog = builder.create();
        infodialog.setCancelable(false);
        infodialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        TextView txt_app_version = dialogView.findViewById(R.id.txt_app_version);
        Button tv_ok = dialogView.findViewById(R.id.btnAbout);

        try {
            // Get package manager
            PackageManager packageManager = getPackageManager();
            // Get package info (version info)
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);

            // Get the app version name
            String versionName = packageInfo.versionName;
            // Get the app version code (if needed)
            int versionCode = packageInfo.versionCode;

            // Display the version name or version code
            Log.d("AppVersion", "Version Name: " + versionName);
            Log.d("AppVersion", "Version Code: " + versionCode);
            txt_app_version.setText(versionName);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        tv_ok.setOnClickListener(view -> {
            infodialog.cancel();
        });


        Window window = infodialog.getWindow();
        if (window != null) {
            // Set the desired width (in pixels or dp)
            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.85); // 85% of screen width
            window.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        }
        infodialog.show();

    }

}
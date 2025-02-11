package com.jawline.exercises.faceyoga;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_MEDIA_IMAGES;
import static android.Manifest.permission.READ_MEDIA_VIDEO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.jawline.exercises.faceyoga.fragments.ArticleFragment;
import com.jawline.exercises.faceyoga.fragments.ActivityFragment;
import com.jawline.exercises.faceyoga.fragments.ProgressFragment;
import com.jawline.exercises.faceyoga.fragments.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    public static SQLiteDatabase database;
    public static MainActivity isWorkoutRunning;
    private Dialog dialog;
    public com.google.android.material.bottomsheet.BottomSheetDialog I;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        setContentView(R.layout.activity_main);

        if (isPermissionsGranted()) {
        } else {
            takePermission();
        }
        isWorkoutRunning = this;
        SQLiteDatabase openOrCreateDatabase = openOrCreateDatabase("CHALLENGE DAYS", 0, (SQLiteDatabase.CursorFactory) null);
        database = openOrCreateDatabase;
        openOrCreateDatabase.execSQL("CREATE TABLE IF NOT EXISTS challengeDays (day INT(2))");
        getSupportFragmentManager().beginTransaction().replace(R.id.containerFrameLayout, new HomeFragment()).commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        bottomNavigationView.setItemIconTintList(null);

        findViewById(R.id.ivSetting).setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, SettingActivity.class));
        });
        showRatingDialog();

    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        Fragment selectedFragment = null;
        int itemId = item.getItemId();
        if (itemId == R.id.nav_premium) {
            ((TextView) findViewById(R.id.tVAppname)).setText(R.string.app_name);
            ((TextView) findViewById(R.id.tvWel)).setText(R.string.welcome_to);
            selectedFragment = new HomeFragment();
        } else if (itemId == R.id.nav_exercises) {
            ((TextView) findViewById(R.id.tVAppname)).setText(R.string.app_name);
            ((TextView) findViewById(R.id.tvWel)).setText(R.string.welcome_to);
            selectedFragment = new ActivityFragment();
        } else if (itemId == R.id.nav_insights) {
            ((TextView) findViewById(R.id.tVAppname)).setText(R.string.progressdes);
            ((TextView) findViewById(R.id.tvWel)).setText(R.string.progress);
            selectedFragment = new ProgressFragment();
        } else if (itemId == R.id.nav_article) {
            ((TextView) findViewById(R.id.tVAppname)).setText(R.string.articledes);
            ((TextView) findViewById(R.id.tvWel)).setText(R.string.article);
            selectedFragment = new ArticleFragment();
        }

        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.containerFrameLayout, selectedFragment).commit();
        }
        return true;
    };

    private boolean isPermissionsGranted() {

        if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            int read_storage1 = ContextCompat.checkSelfPermission(this, READ_MEDIA_IMAGES);
            int write_storage1 = ContextCompat.checkSelfPermission(this, READ_MEDIA_VIDEO);
            int camera1 = ContextCompat.checkSelfPermission(this, CAMERA);
            return read_storage1 == PackageManager.PERMISSION_GRANTED && write_storage1 == PackageManager.PERMISSION_GRANTED && camera1 == PackageManager.PERMISSION_GRANTED;

        } else {
            int read_storage = ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE);
            int write_storage = ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE);
            int camera = ContextCompat.checkSelfPermission(this, CAMERA);
            return read_storage == PackageManager.PERMISSION_GRANTED && write_storage == PackageManager.PERMISSION_GRANTED && camera == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void takePermission() {

        if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this, new String[]{READ_MEDIA_IMAGES, READ_MEDIA_VIDEO, CAMERA}, 100);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, CAMERA}, 101);
        }
    }

    public final void onBackPressed() {

        if (isFinishing()) {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } else {
            ExitDialog();
        }

    }

    public final void ExitDialog() {
        dialog = new Dialog(MainActivity.this, R.style.CustomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_exit);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Window window = dialog.getWindow();

        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.CENTER);
        }

        AppCompatButton btnYes = dialog.findViewById(R.id.btnYes);
        AppCompatButton btnNo = dialog.findViewById(R.id.btnNo);

        btnNo.setOnClickListener(v -> {
            dialog.dismiss();
        });

        btnYes.setOnClickListener(v -> {
            finishAffinity();
            dialog.dismiss();
        });

        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private void showRatingDialog() {
        try {
            SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
            int counter = app_preferences.getInt("counter", 0);
            int RunEvery = 8;
            if (counter != 0 && counter % RunEvery == 0) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.dialog_rating, null);
                builder.setView(dialogView);

                AppCompatButton btnSubmit = dialogView.findViewById(R.id.btnRate);
                AppCompatButton btnCancel = dialogView.findViewById(R.id.btnNo);

                AlertDialog exitDialog = builder.create();
                exitDialog.setCancelable(false);
                exitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exitDialog.cancel();
                    }
                });
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("market://details?id=" + getPackageName());
                        Intent openPlayStore = new Intent(Intent.ACTION_VIEW, uri);
                        try {
                            startActivity(openPlayStore);
                        } catch (ActivityNotFoundException e) {
                            Toast.makeText(MainActivity.this, " unable to find market app", Toast.LENGTH_LONG).show();
                        }
                        exitDialog.dismiss();
                    }
                });

                exitDialog.show();

                Window window = exitDialog.getWindow();
                if (window != null) {
                    int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.85); // 85% of screen width
                    window.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);
                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                }

            }
            SharedPreferences.Editor editor = app_preferences.edit();
            editor.putInt("counter", ++counter);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
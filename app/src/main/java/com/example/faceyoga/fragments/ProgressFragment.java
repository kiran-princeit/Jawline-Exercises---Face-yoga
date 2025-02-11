package com.example.faceyoga.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.faceyoga.CaptureImageActivity;
import com.example.faceyoga.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.io.File;
import java.text.SimpleDateFormat;

//import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.content.Context;

import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

public class ProgressFragment extends Fragment implements OnDateSelectedListener {
    private ShapeableImageView progressImageView;

    private AppCompatTextView totalWorkoutDayNumbersTV;
    private static final int PERMISSION_REQUEST_CODE = 214;
    private String currentDate;
    private String selectedDate;

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        Log.i("Selected Date", "Selected date object: " + date.getDate());

        if (date.getDate() != null) {
            LocalDate localDate = date.getDate();
            ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
            Instant threetenInstant = zonedDateTime.toInstant();

            java.time.Instant javaInstant = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                javaInstant = java.time.Instant.ofEpochMilli(threetenInstant.toEpochMilli());
            }

            Date selectedDateFormatted = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                selectedDateFormatted = Date.from(javaInstant);
            }
            Log.i("Formatted Selected Date", "Formatted selected date: " + selectedDateFormatted);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
            String formattedDate = sdf.format(selectedDateFormatted);  // Format the Date object
            loadImageForDate(formattedDate);
        } else {
            Log.e("Date Error", "Selected date is null!");
        }
    }

    private class CaptureImageClickListener implements View.OnClickListener {
        private final View view;
        public CaptureImageClickListener(View view) {
            this.view = view;
        }

        @Override
        public void onClick(View v) {
            Context context = view.getContext();
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                captureImage();
            } else {
                requestPermissions(new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                }, PERMISSION_REQUEST_CODE);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);
        MaterialCalendarView calendarView = view.findViewById(R.id.calendarView);
        setupCalendarView(calendarView);
        progressImageView = view.findViewById(R.id.progressImageView);
        totalWorkoutDayNumbersTV = view.findViewById(R.id.totalWorkoutDayNumbersTV);
        progressImageView.setOnClickListener(new CaptureImageClickListener(view));
        calendarView.setOnDateChangedListener(this);
        return view;
    }

    private void setupCalendarView(MaterialCalendarView calendarView) {
        Calendar.getInstance();
        MaterialCalendarView.StateBuilder stateBuilder = calendarView.state().edit();
        stateBuilder.setFirstDayOfWeek(DayOfWeek.of(Calendar.MONDAY));
        stateBuilder.setMinimumDate(CalendarDay.from(2025, 1, 1))
                .setMaximumDate(CalendarDay.from(2030, 12, 31))
                .setCalendarDisplayMode(CalendarMode.WEEKS)
                .commit();

        calendarView.setSelectionColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        currentDate = dateFormat.format(new Date());
        selectedDate = currentDate;

        String[] dateParts = currentDate.split("/");
        int year = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int day = Integer.parseInt(dateParts[2]);

        calendarView.setSelectedDate(CalendarDay.from(year, month, day));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            captureImage();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateWorkoutDays();
    }

    private void updateWorkoutDays() {
        SharedPreferences preferences = requireContext().getSharedPreferences("Total Workout Days", Context.MODE_PRIVATE);
        int totalDays = preferences.getInt("Total Workout Days", 0);
        totalWorkoutDayNumbersTV.setText(String.valueOf(totalDays));
        loadImageForDate(selectedDate);
    }

    private void captureImage() {
        if (selectedDate.equals(currentDate)) {
            startActivity(new Intent(requireContext(), CaptureImageActivity.class));
        } else {
            Toast.makeText(requireContext(), "You cannot capture a picture for another day", Toast.LENGTH_SHORT).show();
        }
    }


    private void loadImageForDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
            Date parsedDate = sdf.parse(date);
            String formattedDate = sdf.format(parsedDate);  // Ensure it's in the correct format

            Log.i("Formatted Date", "Formatted date for query: " + formattedDate);

            SQLiteDatabase db = requireActivity().openOrCreateDatabase("ImagePathDatabase", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS imagePath (date VARCHAR, path VARCHAR)");

            Cursor cursor = db.rawQuery("SELECT * FROM imagePath WHERE date = ?", new String[]{formattedDate});
            if (cursor.moveToFirst()) {
                String imagePath = cursor.getString(cursor.getColumnIndex("path"));
                Log.i("Fetched Image Path", "Fetched image path: " + imagePath);

                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    Glide.with(requireContext())
                            .load(imageFile)
                            .diskCacheStrategy(DiskCacheStrategy.ALL) // Cache all versions of the image
                            .into(progressImageView);
                } else {
                    Log.e("Image Load", "Image file doesn't exist at path: " + imagePath);
                    Glide.with(requireContext()).load(R.drawable.iv_defaltcapture).into(progressImageView);  // Set default image
                }
            } else {
                Log.e("Database Query", "No image found for date: " + date);
                Toast.makeText(requireContext(), "No picture available for the selected date", Toast.LENGTH_SHORT).show();
                Glide.with(requireContext()).load(R.drawable.iv_defaltcapture).into(progressImageView);  // Set default image
            }

            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


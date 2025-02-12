package com.jawline.exercises.faceyoga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.jawline.exercises.faceyoga.adapter.TwentyOneDayAdapter;
import com.jawline.exercises.faceyoga.model.Shedule;

import java.util.ArrayList;

public class TwentyOneDaysChallengeActivity extends AppCompatActivity {
    public static String exerciseName= "Day 1";
    public static int[] exerciseImages;
    public static int[] exerciseDurations;
    public static String[] exerciseTitles;
    public RecyclerView recyclerView;
    TextView tvSelectedShedule,tvTotalExercisesTime;

    TextView tvday1,tvday2,tvday3,tvday4,tvday5,tvday6,tvday7,tvday8,tvday9,tvday10,tvday11,tvday12,tvday13,tvday14,tvday15,tvday16,tvday17,tvday18,tvday19,tvday20,tvday21;

    public LinearLayout dayOneLayout, dayTwoLayout, dayThreeLayout, dayFourLayout, dayFiveLayout, daySixLayout, daySevenLayout, dayEightLayout, dayNineLayout, dayTenLayout, dayElevenLayout, dayTwelveLayout, dayThirteenLayout, dayFourteenLayout, dayFifteenLayout, daySixteenLayout, daySeventeenLayout, dayEighteenLayout, dayNineteenLayout, dayTwentyLayout, dayTwentyOneLayout;
    public LinearLayout[] daysArray;
    public TextView[] dayTextViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setStatusBarColor(getColor(R.color.colorPrimary));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twenty_one_days_challenge);

        this.dayOneLayout = findViewById(R.id.day1);
        this.dayTwoLayout = findViewById(R.id.day2);
        this.dayThreeLayout = findViewById(R.id.day3);
        this.dayFourLayout = findViewById(R.id.day4);
        this.dayFiveLayout = findViewById(R.id.day5);
        this.daySixLayout = findViewById(R.id.day6);
        this.daySevenLayout = findViewById(R.id.day7);
        this.dayEightLayout = findViewById(R.id.day8);
        this.dayNineLayout = findViewById(R.id.day9);
        this.dayTenLayout = findViewById(R.id.day10);
        this.dayElevenLayout = findViewById(R.id.day11);
        this.dayTwelveLayout = findViewById(R.id.day12);
        this.dayThirteenLayout = findViewById(R.id.day13);
        this.dayFourteenLayout = findViewById(R.id.day14);
        this.dayFifteenLayout = findViewById(R.id.day15);
        this.daySixteenLayout = findViewById(R.id.day16);
        this.daySeventeenLayout = findViewById(R.id.day17);
        this.dayEighteenLayout = findViewById(R.id.day18);
        this.dayNineteenLayout = findViewById(R.id.day19);
        this.dayTwentyLayout = findViewById(R.id.day20);
        this.dayTwentyOneLayout = findViewById(R.id.day21);


        this.tvday1 = findViewById(R.id.tvday1);
        this.tvday2 = findViewById(R.id.tvday2);
        this.tvday3 = findViewById(R.id.tvday3);
        this.tvday4 = findViewById(R.id.tvday4);
        this.tvday5 = findViewById(R.id.tvday5);
        this.tvday6 = findViewById(R.id.tvday6);
        this.tvday7 = findViewById(R.id.tvday7);
        this.tvday8 = findViewById(R.id.tvday8);
        this.tvday9 = findViewById(R.id.tvday9);
        this.tvday10 = findViewById(R.id.tvday10);
        this.tvday11 = findViewById(R.id.tvday11);
        this.tvday12 = findViewById(R.id.tvday12);
        this.tvday13 = findViewById(R.id.tvday13);
        this.tvday14 = findViewById(R.id.tvday14);
        this.tvday15 = findViewById(R.id.tvday15);
        this.tvday16 = findViewById(R.id.tvday16);
        this.tvday17 = findViewById(R.id.tvday17);
        this.tvday18 = findViewById(R.id.tvday18);
        this.tvday19 = findViewById(R.id.tvday19);
        this.tvday20 = findViewById(R.id.tvday20);
        this.tvday21 = findViewById(R.id.tvday21);


        recyclerView = findViewById(R.id.rv_twentyoneday);
        tvTotalExercisesTime = findViewById(R.id.tvTotalExercisesTime);

        findViewById(R.id.iv_close).setOnClickListener(view -> {
            finish();
        });

        this.daysArray = new LinearLayout[]{
                dayOneLayout, dayTwoLayout, dayThreeLayout, dayFourLayout, dayFiveLayout,
                daySixLayout, daySevenLayout, dayEightLayout, dayNineLayout, dayTenLayout,
                dayElevenLayout, dayTwelveLayout, dayThirteenLayout, dayFourteenLayout,
                dayFifteenLayout, daySixteenLayout, daySeventeenLayout, dayEighteenLayout,
                dayNineteenLayout, dayTwentyLayout, dayTwentyOneLayout
        };


        this.dayTextViews = new TextView[] {
                tvday1, tvday2, tvday3, tvday4, tvday5, tvday6, tvday7, tvday8,
                tvday9, tvday10, tvday11, tvday12, tvday13, tvday14, tvday15,
                tvday16, tvday17, tvday18, tvday19, tvday20, tvday21
        };


        for (int i = 0; i < daysArray.length; i++) {
            final int day = i + 1;
            daysArray[i].setOnClickListener(view -> startExerciseForDay(day));
        }
        tvSelectedShedule = findViewById(R.id.tvSelectedSheduleact);
        tvSelectedShedule.setText(exerciseName);
        initializeExerciseData();
        startExerciseForDay(1);
    }

    public void startExerciseForDay(int day) {
        exerciseName = "Day " + day;
        tvSelectedShedule.setText(exerciseName);
        initializeExerciseData();
        calculateTotalExercisesAndTime(exerciseDurations, tvTotalExercisesTime);


        updateRecyclerView();

        for (int i = 0; i < dayTextViews.length; i++) {
            if (i == day - 1) {
                dayTextViews[i].setBackgroundResource(R.drawable.round_pink);
                dayTextViews[i].setTextColor(Color.WHITE);
            } else {
                dayTextViews[i].setBackgroundResource(R.drawable.default_background);
                dayTextViews[i].setTextColor(Color.parseColor("#000000"));
            }
        }
    }


    public void calculateTotalExercisesAndTime(int[] exerciseDurations, TextView totalExercisesTextView) {
        int totalExercises = exerciseDurations.length;
        int totalTime = 0;

        for (int duration : exerciseDurations) {
            totalTime += duration;
        }
        int totalTimeInMinutes = totalTime / 60;
        String totalExercisesText = String.format("%02d Exercise | %d min", totalExercises, totalTimeInMinutes);
        totalExercisesTextView.setText(totalExercisesText);
    }

    private void initializeExerciseData() {
        if (exerciseName.equals("Day 1")) {
            exerciseTitles = new String[]{"Blowfish", "Hug The Forehead", "The Owl", "Tough Kiss", "Cheek Sculptor", "Firm Up Soggy Cheeks"};
            exerciseImages = new int[]{R.drawable.blowfish, R.drawable.hug_the_forehead, R.drawable.the_owl, R.drawable.tough_kiss, R.drawable.cheek_sculptor, R.drawable.firm_up_soggy_cheeks};
            exerciseDurations = new int[]{30, 45, 30, 30, 45, 30};
        } else if (exerciseName.equals("Day 2")) {
            exerciseTitles = new String[]{"Surprise Me", "Hug The Forehead", "Cheek Sculptor", "Firm Up Soggy Cheeks", "The Owl", "Sharpen Your Nose"};
            exerciseImages = new int[]{R.drawable.surprise_me, R.drawable.hug_the_forehead, R.drawable.cheek_sculptor, R.drawable.firm_up_soggy_cheeks, R.drawable.the_owl, R.drawable.sharpen_your_nose};
            exerciseDurations = new int[]{45, 45, 30, 30, 30, 30};
        } else if (exerciseName.equals("Day 3")) {
            exerciseTitles = new String[]{"Blowfish", "Tighten Your Eye Area", "Surprise Me", "The Owl", "Sharpen Your Nose", "Lip Tuck"};
            exerciseImages = new int[]{R.drawable.blowfish, R.drawable.tighten_your_eye_area, R.drawable.surprise_me, R.drawable.the_owl, R.drawable.sharpen_your_nose, R.drawable.lip_tuck};
            exerciseDurations = new int[]{45, 30, 45, 30, 45, 45};
        } else if (exerciseName.equals("Day 4")) {
            exerciseTitles = new String[]{"Blowfish", "The Eyebrow Lift", "Relax Your Eye Muscles", "Cheek Sculptor", "Nose to Cheeks", "Surprise Me", "Hug The Forehead"};
            exerciseImages = new int[]{R.drawable.blowfish, R.drawable.the_eyebrow_lift, R.drawable.relax_your_eye_muscles, R.drawable.cheek_sculptor, R.drawable.smooth_transition_of_nose_to_cheeks, R.drawable.surprise_me, R.drawable.hug_the_forehead};
            exerciseDurations = new int[]{45, 30, 30, 45, 45, 45, 45};
        } else if (exerciseName.equals("Day 5")) {
            exerciseTitles = new String[]{"Lion Pose", "Blowfish", "The Owl", "Tighten Your Eye Area", "Cheek Sculptor", "Nose to Cheeks", "Sharpen Your Nose"};
            exerciseImages = new int[]{R.drawable.lion_pose, R.drawable.blowfish, R.drawable.the_owl, R.drawable.tighten_your_eye_area, R.drawable.cheek_sculptor, R.drawable.smooth_transition_of_nose_to_cheeks, R.drawable.sharpen_your_nose};
            exerciseDurations = new int[]{30, 45, 45, 30, 30, 45, 45};
        } else if (exerciseName.equals("Day 6")) {
            exerciseTitles = new String[]{"Lion Pose", "For Horizontal Wrinkles", "Tough Kiss", "The Handy Face Lift", "Firm Up Soggy Cheeks", "Lip Tuck", "Surprise Me"};
            exerciseImages = new int[]{R.drawable.lion_pose, R.drawable.for_horizontal_wrinkles, R.drawable.tough_kiss, R.drawable.the_handy_face_lift, R.drawable.firm_up_soggy_cheeks, R.drawable.lip_tuck, R.drawable.surprise_me};
            exerciseDurations = new int[]{45, 30, 45, 45, 45, 45, 45};
        } else if (exerciseName.equals("Day 7")) {
            exerciseTitles = new String[]{"Blowfish", "Lion Pose", "Tighten Your Eye Area", "For Horizontal Wrinkles", "Lip Tuck", "Nose to Cheeks", "Surprise Me"};
            exerciseImages = new int[]{R.drawable.blowfish, R.drawable.lion_pose, R.drawable.tighten_your_eye_area, R.drawable.for_horizontal_wrinkles, R.drawable.lip_tuck, R.drawable.smooth_transition_of_nose_to_cheeks, R.drawable.surprise_me};
            exerciseDurations = new int[]{45, 45, 45, 45, 60, 45, 60};
        } else if (exerciseName.equals("Day 8")) {
            exerciseTitles = new String[]{"Lion Pose", "The Owl", "Relax Your Eye Muscles", "Sharpen Your Nose", "Cheek Sculptor", "Firm Up Soggy Cheeks", "Tough Kiss"};
            exerciseImages = new int[]{R.drawable.lion_pose, R.drawable.the_owl, R.drawable.relax_your_eye_muscles, R.drawable.sharpen_your_nose, R.drawable.cheek_sculptor, R.drawable.firm_up_soggy_cheeks, R.drawable.tough_kiss};
            exerciseDurations = new int[]{45, 45, 45, 60, 60, 60, 60};
        } else if (exerciseName.equals("Day 9")) {
            exerciseTitles = new String[]{"Blowfish", "The Owl", "Wrinkle Relaxer", "For Horizontal Wrinkles", "Tough Kiss", "Cheek Sculptor", "Surprise Me"};
            exerciseImages = new int[]{R.drawable.blowfish, R.drawable.the_owl, R.drawable.wrinkle_relaxer, R.drawable.for_horizontal_wrinkles, R.drawable.tough_kiss, R.drawable.cheek_sculptor, R.drawable.surprise_me};
            exerciseDurations = new int[]{60, 60, 60, 60, 60, 70, 60};
        } else if (exerciseName.equals("Day 10")) {
            exerciseTitles = new String[]{"Hug The Forehead", "Lion Pose", "The Owl", "Wrinkle Relaxer", "Lip Tuck", "Nose to Cheeks", "Jowl Buster", "Surprise Me"};
            exerciseImages = new int[]{R.drawable.hug_the_forehead, R.drawable.lion_pose, R.drawable.the_owl, R.drawable.wrinkle_relaxer, R.drawable.lip_tuck, R.drawable.smooth_transition_of_nose_to_cheeks, R.drawable.jowl_buster, R.drawable.surprise_me};
            exerciseDurations = new int[]{60, 60, 60, 60, 60, 70, 60, 60};
        } else if (exerciseName.equals("Day 11")) {
            exerciseTitles = new String[]{"Lion Pose", "Hug The Forehead", "The Owl", "Relax Your Eye Muscles", "Cheek Sculptor", "Sharpen Your Nose", "Tough Kiss", "Blowfish"};
            exerciseImages = new int[]{R.drawable.lion_pose, R.drawable.hug_the_forehead, R.drawable.the_owl, R.drawable.relax_your_eye_muscles, R.drawable.cheek_sculptor, R.drawable.sharpen_your_nose, R.drawable.tough_kiss, R.drawable.blowfish};
            exerciseDurations = new int[]{70, 80, 60, 60, 60, 70, 60, 70};
        } else if (exerciseName.equals("Day 12")) {
            exerciseTitles = new String[]{"Blowfish", "Lion Pose", "Hug The Forehead", "Lip Tuck", "Nose to Cheeks", "Firm Up Soggy Cheeks", "The Handy Face Lift", "Jowl Buster"};
            exerciseImages = new int[]{R.drawable.blowfish, R.drawable.lion_pose, R.drawable.hug_the_forehead, R.drawable.lip_tuck, R.drawable.smooth_transition_of_nose_to_cheeks, R.drawable.firm_up_soggy_cheeks, R.drawable.the_handy_face_lift, R.drawable.jowl_buster};
            exerciseDurations = new int[]{80, 80, 90, 80, 90, 70, 60, 60};
        } else if (exerciseName.equals("Day 13")) {
            exerciseTitles = new String[]{"Tough Kiss", "The Owl", "Tighten Your Eye Area", "Wrinkle Relaxer", "Cheek Sculptor", "Firm Up Soggy Cheeks", "Surprise Me", "For Horizontal Wrinkles", "Jowl Buster"};
            exerciseImages = new int[]{R.drawable.tough_kiss, R.drawable.the_owl, R.drawable.tighten_your_eye_area, R.drawable.wrinkle_relaxer, R.drawable.cheek_sculptor, R.drawable.firm_up_soggy_cheeks, R.drawable.surprise_me, R.drawable.for_horizontal_wrinkles, R.drawable.jowl_buster};
            exerciseDurations = new int[]{70, 70, 80, 70, 80, 80, 80, 80, 60};
        } else if (exerciseName.equals("Day 14")) {
            exerciseTitles = new String[]{"Lion Pose", "Tighten Your Eye Area", "For Horizontal Wrinkles", "Relax Your Eye Muscles", "Smooth Out Smile Lines", "Nose to Cheeks", "Lip Tuck", "The Handy Face Lift", "Jowl Buster"};
            exerciseImages = new int[]{R.drawable.lion_pose, R.drawable.tighten_your_eye_area, R.drawable.for_horizontal_wrinkles, R.drawable.relax_your_eye_muscles, R.drawable.smooth_out_smile_lines, R.drawable.smooth_transition_of_nose_to_cheeks, R.drawable.lip_tuck, R.drawable.the_handy_face_lift, R.drawable.jowl_buster};
            exerciseDurations = new int[]{90, 60, 70, 60, 60, 80, 90, 90, 70};
        } else if (exerciseName.equals("Day 15")) {
            exerciseTitles = new String[]{"Blowfish", "Tough Kiss", "Hug The Forehead", "Tighten Your Eye Area", "The Owl", "Cheek Sculptor", "Firm Up Soggy Cheeks", "Surprise Me", "The Handy Face Lift"};
            exerciseImages = new int[]{R.drawable.blowfish, R.drawable.tough_kiss, R.drawable.hug_the_forehead, R.drawable.tighten_your_eye_area, R.drawable.the_owl, R.drawable.cheek_sculptor, R.drawable.firm_up_soggy_cheeks, R.drawable.surprise_me, R.drawable.the_handy_face_lift};
            exerciseDurations = new int[]{90, 70, 90, 60, 80, 70, 70, 90, 90};
        } else if (exerciseName.equals("Day 16")) {
            exerciseTitles = new String[]{"Lion Pose", "Hug The Forehead", "For Horizontal Wrinkles", "The Owl", "Wrinkle Relaxer", "Firm Up Soggy Cheeks", "Sharpen Your Nose", "Tough Kiss", "Jowl Buster"};
            exerciseImages = new int[]{R.drawable.lion_pose, R.drawable.hug_the_forehead, R.drawable.for_horizontal_wrinkles, R.drawable.the_owl, R.drawable.wrinkle_relaxer, R.drawable.firm_up_soggy_cheeks, R.drawable.sharpen_your_nose, R.drawable.tough_kiss, R.drawable.jowl_buster};
            exerciseDurations = new int[]{90, 90, 70, 80, 80, 80, 60, 80, 60};
        } else if (exerciseName.equals("Day 17")) {
            exerciseTitles = new String[]{"Lion Pose", "Tough Kiss", "For Horizontal Wrinkles", "Hug The Forehead", "Lip Tuck", "Cheek Sculptor", "Firm Up Soggy Cheeks", "Surprise Me", "The Handy Face Lift"};
            exerciseImages = new int[]{R.drawable.lion_pose, R.drawable.tough_kiss, R.drawable.for_horizontal_wrinkles, R.drawable.hug_the_forehead, R.drawable.lip_tuck, R.drawable.cheek_sculptor, R.drawable.firm_up_soggy_cheeks, R.drawable.surprise_me, R.drawable.the_handy_face_lift};
            exerciseDurations = new int[]{90, 90, 90, 90, 90, 80, 60, 70, 90};
        } else if (exerciseName.equals("Day 18")) {
            exerciseTitles = new String[]{"Blowfish", "For Horizontal Wrinkles", "The Eyebrow Lift", "Tighten Your Eye Area", "Relax Your Eye Muscles", "Firm Up Soggy Cheeks", "Tough Kiss", "Sharpen Your Nose", "Jowl Buster"};
            exerciseImages = new int[]{R.drawable.blowfish, R.drawable.for_horizontal_wrinkles, R.drawable.the_eyebrow_lift, R.drawable.tighten_your_eye_area, R.drawable.relax_your_eye_muscles, R.drawable.firm_up_soggy_cheeks, R.drawable.tough_kiss, R.drawable.sharpen_your_nose, R.drawable.jowl_buster};
            exerciseDurations = new int[]{100, 80, 60, 60, 60, 80, 90, 60, 70};
        } else if (exerciseName.equals("Day 19")) {
            exerciseTitles = new String[]{"Lion Pose", "Surprise Me", "The Owl", "Tighten Your Eye Area", "Hug The Forehead", "Wrinkle Relaxer", "Nose to Cheeks", "Blowfish", "Tough Kiss", "The Handy Face Lift"};
            exerciseImages = new int[]{R.drawable.lion_pose, R.drawable.surprise_me, R.drawable.the_owl, R.drawable.tighten_your_eye_area, R.drawable.hug_the_forehead, R.drawable.wrinkle_relaxer, R.drawable.smooth_transition_of_nose_to_cheeks, R.drawable.blowfish, R.drawable.tough_kiss, R.drawable.the_handy_face_lift};
            exerciseDurations = new int[]{90, 100, 80, 80, 90, 70, 80, 90, 90, 90};
        } else if (exerciseName.equals("Day 20")) {
            exerciseTitles = new String[]{"Lion Pose", "The Owl", "Hug The Forehead", "For Horizontal Wrinkles", "Lip Tuck", "Nose to Cheeks", "Firm Up Soggy Cheeks", "Cheek Sculptor", "Blowfish", "Surprise Me"};
            exerciseImages = new int[]{R.drawable.lion_pose, R.drawable.the_owl, R.drawable.hug_the_forehead, R.drawable.for_horizontal_wrinkles, R.drawable.lip_tuck, R.drawable.smooth_transition_of_nose_to_cheeks, R.drawable.firm_up_soggy_cheeks, R.drawable.cheek_sculptor, R.drawable.blowfish, R.drawable.surprise_me};
            exerciseDurations = new int[]{100, 80, 90, 90, 90, 90, 80, 80, 100, 90};
        } else if (exerciseName.equals("Day 21")) {
            exerciseTitles = new String[]{"Blowfish", "Surprise Me", "Lion Pose", "Hug The Forehead", "Smooth Out Smile Lines", "Relax Your Eye Muscles", "Lip Tuck", "Firm Up Soggy Cheeks", "Cheek Sculptor", "Jowl Buster"};
            exerciseImages = new int[]{R.drawable.blowfish, R.drawable.surprise_me, R.drawable.lion_pose, R.drawable.hug_the_forehead, R.drawable.smooth_out_smile_lines, R.drawable.relax_your_eye_muscles, R.drawable.lip_tuck, R.drawable.firm_up_soggy_cheeks, R.drawable.cheek_sculptor, R.drawable.jowl_buster};
            exerciseDurations = new int[]{100, 100, 60, 90, 90, 60, 90, 80, 80, 90};
        } else if (exerciseName.equals("Face Yoga For Cheeks")) {
            exerciseTitles = new String[]{"Blowfish", "Cheek Sculptor", "Firm Up Soggy Cheeks", "Lip Tuck", "Smooth Out Smile Lines", "The Handy Face Lift", "Nose to Cheeks", "Surprise Me"};
            exerciseImages = new int[]{R.drawable.blowfish, R.drawable.cheek_sculptor, R.drawable.firm_up_soggy_cheeks, R.drawable.lip_tuck, R.drawable.smooth_out_smile_lines, R.drawable.the_handy_face_lift, R.drawable.smooth_transition_of_nose_to_cheeks, R.drawable.surprise_me};
            exerciseDurations = new int[]{60, 60, 60, 90, 60, 90, 90, 60};
        } else if (exerciseName.equals("Face Yoga For Eyes")) {
            exerciseTitles = new String[]{"Lion Pose", "The Eyebrow Lift", "Tighten Your Eye Area", "For Horizontal Wrinkles", "The Owl", "Relax Your Eye Muscles", "Wrinkle Relaxer"};
            exerciseImages = new int[]{R.drawable.lion_pose, R.drawable.the_eyebrow_lift, R.drawable.tighten_your_eye_area, R.drawable.for_horizontal_wrinkles, R.drawable.the_owl, R.drawable.relax_your_eye_muscles, R.drawable.wrinkle_relaxer};
            exerciseDurations = new int[]{60, 60, 60, 70, 60, 45, 60};
        }
    }

    private void updateRecyclerView() {
        ArrayList arrayList = new ArrayList();
        int i9 = 0;

        while (true) {
            String[] strArr = exerciseTitles;
            if (i9 < strArr.length) {
                Shedule hVar = new Shedule();
                hVar.name = strArr[i9];
                hVar.duration = exerciseDurations[i9];
                hVar.id = exerciseImages[i9];
                arrayList.add(hVar);
                i9++;
            } else {
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(new TwentyOneDayAdapter(getBaseContext(), arrayList));
                return;
            }
        }
    }

    public void startExercise(View view) {
        Intent intent = new Intent(this, WorkoutActivity.class);
        intent.putExtra("Which Exercise Schedule", exerciseName);
        intent.putExtra("Workout Name", exerciseTitles);
        intent.putExtra("Workout Images", exerciseImages);
        intent.putExtra("Workout Reps", exerciseDurations);
        intent.putExtra("Color", (String) null);
        startActivity(intent);
    }
}
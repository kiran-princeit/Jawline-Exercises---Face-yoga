package com.jawline.exercises.faceyoga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jawline.exercises.faceyoga.adapter.ActivityScheduleAdapter;
import com.jawline.exercises.faceyoga.model.Shedule;

import java.util.ArrayList;

public class ActivityScheduleActivity extends AppCompatActivity {
    public static String exerciseName;
    public static int[] exerciseImages;
    public static int[] exerciseDurations;
    public static String[] exerciseTitles;
    public RecyclerView recyclerView;
    ImageView imageView;
    TextView tvSelectedShedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_activity);

        exerciseName = getIntent().getStringExtra("Which Exercise");
        String stringExtra = getIntent().getStringExtra("Image");
        stringExtra.equals("Challenge");

        Log.e("Yoga", "onCreate: " + stringExtra);

        int i10 = stringExtra.equals("Cheeks Yoga") ? R.drawable.iv_cheekyoga : R.drawable.iv_twentyonedays;
        if (stringExtra.equals("Eyes Yoga")) {
            i10 = R.drawable.iv_eyeyoga;
        }
        try {
            imageView = findViewById(R.id.imageViewToolbar);
            Glide.with(this).load(Integer.valueOf(i10)).into(imageView);
        } catch (Exception e10) {
            e10.printStackTrace();
        }

        String description = getIntent().getStringExtra("description");
        String result1 = getIntent().getStringExtra("result1");
        String result2 = getIntent().getStringExtra("result2");

        tvSelectedShedule = findViewById(R.id.tvSelectedSheduleact);
        tvSelectedShedule.setText(exerciseName);
        initializeExerciseData();

        TextView tvDescription = findViewById(R.id.tvDescription);
        TextView tvResult2 = findViewById(R.id.tvResult2);
        TextView tvResult1 = findViewById(R.id.tvResult1);

        tvDescription.setText(description);
        tvResult1.setText(result1);
        tvResult2.setText(result2);

        findViewById(R.id.iv_close).setOnClickListener(view -> {
            finish();
        });
    }

    private void initializeExerciseData() {
        if (exerciseName.equals("Full Face Yoga Day 1")) {
            exerciseTitles = new String[]{"Blowfish", "Hug The Forehead", "The Owl", "Tough Kiss", "Cheek Sculptor", "Firm Up Soggy Cheeks"};
            exerciseImages = new int[]{R.drawable.blowfish, R.drawable.hug_the_forehead, R.drawable.the_owl, R.drawable.tough_kiss, R.drawable.cheek_sculptor, R.drawable.firm_up_soggy_cheeks};
            exerciseDurations = new int[]{30, 45, 30, 30, 45, 30};
        } else if (exerciseName.equals("Full Face Yoga Day 2")) {
            exerciseTitles = new String[]{"Surprise Me", "Hug The Forehead", "Cheek Sculptor", "Firm Up Soggy Cheeks", "The Owl", "Sharpen Your Nose"};
            exerciseImages = new int[]{R.drawable.surprise_me, R.drawable.hug_the_forehead, R.drawable.cheek_sculptor, R.drawable.firm_up_soggy_cheeks, R.drawable.the_owl, R.drawable.sharpen_your_nose};
            exerciseDurations = new int[]{45, 45, 30, 30, 30, 30};
        } else if (exerciseName.equals("Full Face Yoga Day 3")) {
            exerciseTitles = new String[]{"Blowfish", "Tighten Your Eye Area", "Surprise Me", "The Owl", "Sharpen Your Nose", "Lip Tuck"};
            exerciseImages = new int[]{R.drawable.blowfish, R.drawable.tighten_your_eye_area, R.drawable.surprise_me, R.drawable.the_owl, R.drawable.sharpen_your_nose, R.drawable.lip_tuck};
            exerciseDurations = new int[]{45, 30, 45, 30, 45, 45};
        } else if (exerciseName.equals("Full Face Yoga Day 4")) {
            exerciseTitles = new String[]{"Blowfish", "The Eyebrow Lift", "Relax Your Eye Muscles", "Cheek Sculptor", "Nose to Cheeks", "Surprise Me", "Hug The Forehead"};
            exerciseImages = new int[]{R.drawable.blowfish, R.drawable.the_eyebrow_lift, R.drawable.relax_your_eye_muscles, R.drawable.cheek_sculptor, R.drawable.smooth_transition_of_nose_to_cheeks, R.drawable.surprise_me, R.drawable.hug_the_forehead};
            exerciseDurations = new int[]{45, 30, 30, 45, 45, 45, 45};
        } else if (exerciseName.equals("Full Face Yoga Day 5")) {
            exerciseTitles = new String[]{"Lion Pose", "Blowfish", "The Owl", "Tighten Your Eye Area", "Cheek Sculptor", "Nose to Cheeks", "Sharpen Your Nose"};
            exerciseImages = new int[]{R.drawable.lion_pose, R.drawable.blowfish, R.drawable.the_owl, R.drawable.tighten_your_eye_area, R.drawable.cheek_sculptor, R.drawable.smooth_transition_of_nose_to_cheeks, R.drawable.sharpen_your_nose};
            exerciseDurations = new int[]{30, 45, 45, 30, 30, 45, 45};
        } else if (exerciseName.equals("Full Face Yoga Day 6")) {
            exerciseTitles = new String[]{"Lion Pose", "For Horizontal Wrinkles", "Tough Kiss", "The Handy Face Lift", "Firm Up Soggy Cheeks", "Lip Tuck", "Surprise Me"};
            exerciseImages = new int[]{R.drawable.lion_pose, R.drawable.for_horizontal_wrinkles, R.drawable.tough_kiss, R.drawable.the_handy_face_lift, R.drawable.firm_up_soggy_cheeks, R.drawable.lip_tuck, R.drawable.surprise_me};
            exerciseDurations = new int[]{45, 30, 45, 45, 45, 45, 45};
        } else if (exerciseName.equals("Full Face Yoga Day 7")) {
            exerciseTitles = new String[]{"Blowfish", "Lion Pose", "Tighten Your Eye Area", "For Horizontal Wrinkles", "Lip Tuck", "Nose to Cheeks", "Surprise Me"};
            exerciseImages = new int[]{R.drawable.blowfish, R.drawable.lion_pose, R.drawable.tighten_your_eye_area, R.drawable.for_horizontal_wrinkles, R.drawable.lip_tuck, R.drawable.smooth_transition_of_nose_to_cheeks, R.drawable.surprise_me};
            exerciseDurations = new int[]{45, 45, 45, 45, 60, 45, 60};
        } else if (exerciseName.equals("Full Face Yoga Day 8")) {
            exerciseTitles = new String[]{"Lion Pose", "The Owl", "Relax Your Eye Muscles", "Sharpen Your Nose", "Cheek Sculptor", "Firm Up Soggy Cheeks", "Tough Kiss"};
            exerciseImages = new int[]{R.drawable.lion_pose, R.drawable.the_owl, R.drawable.relax_your_eye_muscles, R.drawable.sharpen_your_nose, R.drawable.cheek_sculptor, R.drawable.firm_up_soggy_cheeks, R.drawable.tough_kiss};
            exerciseDurations = new int[]{45, 45, 45, 60, 60, 60, 60};
        } else if (exerciseName.equals("Full Face Yoga Day 9")) {
            exerciseTitles = new String[]{"Blowfish", "The Owl", "Wrinkle Relaxer", "For Horizontal Wrinkles", "Tough Kiss", "Cheek Sculptor", "Surprise Me"};
            exerciseImages = new int[]{R.drawable.blowfish, R.drawable.the_owl, R.drawable.wrinkle_relaxer, R.drawable.for_horizontal_wrinkles, R.drawable.tough_kiss, R.drawable.cheek_sculptor, R.drawable.surprise_me};
            exerciseDurations = new int[]{60, 60, 60, 60, 60, 70, 60};
        } else if (exerciseName.equals("Full Face Yoga Day 10")) {
            exerciseTitles = new String[]{"Hug The Forehead", "Lion Pose", "The Owl", "Wrinkle Relaxer", "Lip Tuck", "Nose to Cheeks", "Jowl Buster", "Surprise Me"};
            exerciseImages = new int[]{R.drawable.hug_the_forehead, R.drawable.lion_pose, R.drawable.the_owl, R.drawable.wrinkle_relaxer, R.drawable.lip_tuck, R.drawable.smooth_transition_of_nose_to_cheeks, R.drawable.jowl_buster, R.drawable.surprise_me};
            exerciseDurations = new int[]{60, 60, 60, 60, 60, 70, 60, 60};
        } else if (exerciseName.equals("Full Face Yoga Day 11")) {
            exerciseTitles = new String[]{"Lion Pose", "Hug The Forehead", "The Owl", "Relax Your Eye Muscles", "Cheek Sculptor", "Sharpen Your Nose", "Tough Kiss", "Blowfish"};
            exerciseImages = new int[]{R.drawable.lion_pose, R.drawable.hug_the_forehead, R.drawable.the_owl, R.drawable.relax_your_eye_muscles, R.drawable.cheek_sculptor, R.drawable.sharpen_your_nose, R.drawable.tough_kiss, R.drawable.blowfish};
            exerciseDurations = new int[]{70, 80, 60, 60, 60, 70, 60, 70};
        } else if (exerciseName.equals("Full Face Yoga Day 12")) {
            exerciseTitles = new String[]{"Blowfish", "Lion Pose", "Hug The Forehead", "Lip Tuck", "Nose to Cheeks", "Firm Up Soggy Cheeks", "The Handy Face Lift", "Jowl Buster"};
            exerciseImages = new int[]{R.drawable.blowfish, R.drawable.lion_pose, R.drawable.hug_the_forehead, R.drawable.lip_tuck, R.drawable.smooth_transition_of_nose_to_cheeks, R.drawable.firm_up_soggy_cheeks, R.drawable.the_handy_face_lift, R.drawable.jowl_buster};
            exerciseDurations = new int[]{80, 80, 90, 80, 90, 70, 60, 60};
        } else if (exerciseName.equals("Full Face Yoga Day 13")) {
            exerciseTitles = new String[]{"Tough Kiss", "The Owl", "Tighten Your Eye Area", "Wrinkle Relaxer", "Cheek Sculptor", "Firm Up Soggy Cheeks", "Surprise Me", "For Horizontal Wrinkles", "Jowl Buster"};
            exerciseImages = new int[]{R.drawable.tough_kiss, R.drawable.the_owl, R.drawable.tighten_your_eye_area, R.drawable.wrinkle_relaxer, R.drawable.cheek_sculptor, R.drawable.firm_up_soggy_cheeks, R.drawable.surprise_me, R.drawable.for_horizontal_wrinkles, R.drawable.jowl_buster};
            exerciseDurations = new int[]{70, 70, 80, 70, 80, 80, 80, 80, 60};
        } else if (exerciseName.equals("Full Face Yoga Day 14")) {
            exerciseTitles = new String[]{"Lion Pose", "Tighten Your Eye Area", "For Horizontal Wrinkles", "Relax Your Eye Muscles", "Smooth Out Smile Lines", "Nose to Cheeks", "Lip Tuck", "The Handy Face Lift", "Jowl Buster"};
            exerciseImages = new int[]{R.drawable.lion_pose, R.drawable.tighten_your_eye_area, R.drawable.for_horizontal_wrinkles, R.drawable.relax_your_eye_muscles, R.drawable.smooth_out_smile_lines, R.drawable.smooth_transition_of_nose_to_cheeks, R.drawable.lip_tuck, R.drawable.the_handy_face_lift, R.drawable.jowl_buster};
            exerciseDurations = new int[]{90, 60, 70, 60, 60, 80, 90, 90, 70};
        } else if (exerciseName.equals("Full Face Yoga Day 15")) {
            exerciseTitles = new String[]{"Blowfish", "Tough Kiss", "Hug The Forehead", "Tighten Your Eye Area", "The Owl", "Cheek Sculptor", "Firm Up Soggy Cheeks", "Surprise Me", "The Handy Face Lift"};
            exerciseImages = new int[]{R.drawable.blowfish, R.drawable.tough_kiss, R.drawable.hug_the_forehead, R.drawable.tighten_your_eye_area, R.drawable.the_owl, R.drawable.cheek_sculptor, R.drawable.firm_up_soggy_cheeks, R.drawable.surprise_me, R.drawable.the_handy_face_lift};
            exerciseDurations = new int[]{90, 70, 90, 60, 80, 70, 70, 90, 90};
        } else if (exerciseName.equals("Full Face Yoga Day 16")) {
            exerciseTitles = new String[]{"Lion Pose", "Hug The Forehead", "For Horizontal Wrinkles", "The Owl", "Wrinkle Relaxer", "Firm Up Soggy Cheeks", "Sharpen Your Nose", "Tough Kiss", "Jowl Buster"};
            exerciseImages = new int[]{R.drawable.lion_pose, R.drawable.hug_the_forehead, R.drawable.for_horizontal_wrinkles, R.drawable.the_owl, R.drawable.wrinkle_relaxer, R.drawable.firm_up_soggy_cheeks, R.drawable.sharpen_your_nose, R.drawable.tough_kiss, R.drawable.jowl_buster};
            exerciseDurations = new int[]{90, 90, 70, 80, 80, 80, 60, 80, 60};
        } else if (exerciseName.equals("Full Face Yoga Day 17")) {
            exerciseTitles = new String[]{"Lion Pose", "Tough Kiss", "For Horizontal Wrinkles", "Hug The Forehead", "Lip Tuck", "Cheek Sculptor", "Firm Up Soggy Cheeks", "Surprise Me", "The Handy Face Lift"};
            exerciseImages = new int[]{R.drawable.lion_pose, R.drawable.tough_kiss, R.drawable.for_horizontal_wrinkles, R.drawable.hug_the_forehead, R.drawable.lip_tuck, R.drawable.cheek_sculptor, R.drawable.firm_up_soggy_cheeks, R.drawable.surprise_me, R.drawable.the_handy_face_lift};
            exerciseDurations = new int[]{90, 90, 90, 90, 90, 80, 60, 70, 90};
        } else if (exerciseName.equals("Full Face Yoga Day 18")) {
            exerciseTitles = new String[]{"Blowfish", "For Horizontal Wrinkles", "The Eyebrow Lift", "Tighten Your Eye Area", "Relax Your Eye Muscles", "Firm Up Soggy Cheeks", "Tough Kiss", "Sharpen Your Nose", "Jowl Buster"};
            exerciseImages = new int[]{R.drawable.blowfish, R.drawable.for_horizontal_wrinkles, R.drawable.the_eyebrow_lift, R.drawable.tighten_your_eye_area, R.drawable.relax_your_eye_muscles, R.drawable.firm_up_soggy_cheeks, R.drawable.tough_kiss, R.drawable.sharpen_your_nose, R.drawable.jowl_buster};
            exerciseDurations = new int[]{100, 80, 60, 60, 60, 80, 90, 60, 70};
        } else if (exerciseName.equals("Full Face Yoga Day 19")) {
            exerciseTitles = new String[]{"Lion Pose", "Surprise Me", "The Owl", "Tighten Your Eye Area", "Hug The Forehead", "Wrinkle Relaxer", "Nose to Cheeks", "Blowfish", "Tough Kiss", "The Handy Face Lift"};
            exerciseImages = new int[]{R.drawable.lion_pose, R.drawable.surprise_me, R.drawable.the_owl, R.drawable.tighten_your_eye_area, R.drawable.hug_the_forehead, R.drawable.wrinkle_relaxer, R.drawable.smooth_transition_of_nose_to_cheeks, R.drawable.blowfish, R.drawable.tough_kiss, R.drawable.the_handy_face_lift};
            exerciseDurations = new int[]{90, 100, 80, 80, 90, 70, 80, 90, 90, 90};
        } else if (exerciseName.equals("Full Face Yoga Day 20")) {
            exerciseTitles = new String[]{"Lion Pose", "The Owl", "Hug The Forehead", "For Horizontal Wrinkles", "Lip Tuck", "Nose to Cheeks", "Firm Up Soggy Cheeks", "Cheek Sculptor", "Blowfish", "Surprise Me"};
            exerciseImages = new int[]{R.drawable.lion_pose, R.drawable.the_owl, R.drawable.hug_the_forehead, R.drawable.for_horizontal_wrinkles, R.drawable.lip_tuck, R.drawable.smooth_transition_of_nose_to_cheeks, R.drawable.firm_up_soggy_cheeks, R.drawable.cheek_sculptor, R.drawable.blowfish, R.drawable.surprise_me};
            exerciseDurations = new int[]{100, 80, 90, 90, 90, 90, 80, 80, 100, 90};
        } else if (exerciseName.equals("Full Face Yoga Day 21")) {
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

        recyclerView = (RecyclerView) findViewById(R.id.rv_exercise);
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
                recyclerView.setAdapter(new ActivityScheduleAdapter(getBaseContext(), arrayList));
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
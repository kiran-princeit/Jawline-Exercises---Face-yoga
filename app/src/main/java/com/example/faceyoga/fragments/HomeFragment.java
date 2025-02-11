package com.example.faceyoga.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.example.faceyoga.HomeScheduleActivity;
import com.example.faceyoga.R;

public class HomeFragment extends Fragment implements View.OnClickListener {
    public AppCompatTextView fullFaceYogaTextView, tvDuration;
    public int workoutDay;
    public String workoutRoutine;
    public int imageResource;
    public View rootView;
    String description = "";
    String result1 = "";
    String result2 = "";

    public AppCompatImageView fullFaceYogaImage, tensionReliefImage, goodMorningYogaImage, beforeBedYogaImage, elevenMinYogaImage, reduceWrinklesYogaImage, antiAgingYogaImage, faceTappingYogaImage, glowingSkinYogaImage, guaShaMassageImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);


        fullFaceYogaTextView = rootView.findViewById(R.id.fullFaceYogaRoutineTextView);
        fullFaceYogaImage = rootView.findViewById(R.id.fullFaceYogaRoutineImageView);
        tvDuration = rootView.findViewById(R.id.tvDuration);
        tensionReliefImage = rootView.findViewById(R.id.tensionReliefImageView);
        goodMorningYogaImage = rootView.findViewById(R.id.goodMorningFaceYogaImageView);
        beforeBedYogaImage = rootView.findViewById(R.id.beforeBedFaceYogaImageView);
        elevenMinYogaImage = rootView.findViewById(R.id.elevenMinuteFaceYogaImageView);
        reduceWrinklesYogaImage = rootView.findViewById(R.id.faceYogaToReduceWrinklesImageView);
        antiAgingYogaImage = rootView.findViewById(R.id.antiAgingFaceYogaImageView);
        faceTappingYogaImage = rootView.findViewById(R.id.faceTappingImageView);
        glowingSkinYogaImage = rootView.findViewById(R.id.faceYogaForGlowingSkinImageView);
        guaShaMassageImage = rootView.findViewById(R.id.guaShaMassageImageView);


        rootView.findViewById(R.id.softerForeheadLinesCardView).setOnClickListener(this);
        rootView.findViewById(R.id.eyeCirclesCardView).setOnClickListener(this);
        rootView.findViewById(R.id.browSmootherCardView).setOnClickListener(this);
        rootView.findViewById(R.id.jawUnlockerCardView).setOnClickListener(this);
        rootView.findViewById(R.id.neckMassageCardView).setOnClickListener(this);
        rootView.findViewById(R.id.removeDoubleChinCardView).setOnClickListener(this);
        rootView.findViewById(R.id.fullFaceYogaRoutineCardView).setOnClickListener(this);
        rootView.findViewById(R.id.tensionReliefFaceYogaCardView).setOnClickListener(this);
        rootView.findViewById(R.id.goodMorningFaceYogaCardView).setOnClickListener(this);
        rootView.findViewById(R.id.beforeBedFaceYogaCardView).setOnClickListener(this);
        rootView.findViewById(R.id.elevenMinuteFaceYogaCardView).setOnClickListener(this);
        rootView.findViewById(R.id.reduceWrinklesFaceYogaCardView).setOnClickListener(this);
        rootView.findViewById(R.id.antiAgingFaceYogaCardView).setOnClickListener(this);
        rootView.findViewById(R.id.faceTappingCardView).setOnClickListener(this);
        rootView.findViewById(R.id.guaShaMassageCardView).setOnClickListener(this);
        rootView.findViewById(R.id.faceYogaForGlowingSkinCardView).setOnClickListener(this);

        workoutDay = this.rootView.getContext().getSharedPreferences("Challenge Workout Day Of", 0).getInt("WorkoutChallengeName", 1);
//        workoutDay++;
        workoutRoutine = "Full Face Yoga Routine Day ";
        fullFaceYogaTextView.setText("Day" + Integer.toString(workoutDay));


        rootView.findViewById(R.id.fullFaceYogaRoutineCardView).setOnClickListener(view -> {
            HomeFragment.this.imageResource = R.drawable.iv_yoga_routinday;
            description = getString(R.string.face_yoga_routine);
            result1 = getString(R.string.face_yoga_routine_result_one);
            result2 = getString(R.string.face_yoga_routine_result_two);

            Intent intent = new Intent(HomeFragment.this.rootView.getContext(), HomeScheduleActivity.class);
            intent.putExtra("Selected Workout", HomeFragment.this.workoutRoutine);
            intent.putExtra("whichChallengeDay", HomeFragment.this.workoutDay);
            intent.putExtra("Image For Toolbar", HomeFragment.this.imageResource);
            intent.putExtra("description", description);
            intent.putExtra("result1", result1);
            intent.putExtra("result2", result2);
            HomeFragment.this.startActivity(intent);
        });





        return rootView;
    }

    @Override
    public void onClick(View view) {
        String routineName = (String) view.getTag();
        openWorkoutSchedulePremium(routineName);
    }





    public void openWorkoutSchedulePremium(String routineName) {
        switch (routineName) {
            case "Tension Relief Face Yoga":
                imageResource = R.drawable.iv_tension_relief;
                description = getString(R.string.tension_relief);
                result1 = getString(R.string.tension_relief_result_one);
                result2 = getString(R.string.tension_relief_result_two);
                break;
            case "Good Morning Face Yoga":
                imageResource = R.drawable.iv_goodmorning;
                description = getString(R.string.morning);
                result1 = getString(R.string.morning_result_one);
                result2 = getString(R.string.morning_result_two);
                break;
            case "Before Bed Face Yoga":
                imageResource = R.drawable.iv_beforbad;
                description = getString(R.string.before_bed);
                result1 = getString(R.string.before_bed_result_one);
                result2 = getString(R.string.before_bed_result_two);
                break;
            case "11-Minute Face Yoga":
                imageResource = R.drawable.iv_elevenmin;
                description = getString(R.string.eleven_minute);
                result1 = getString(R.string.eleven_minute_result_one);
                result2 = getString(R.string.eleven_minute_result_two);
                break;
            case "Reduce Wrinkles":
                imageResource = R.drawable.iv_winkles;
                description = getString(R.string.reduce_wrinkles);
                result1 = getString(R.string.reduce_wrinkles_result_one);
                result2 = getString(R.string.reduce_wrinkles_result_two);
                break;
            case "Anti Aging Face Yoga":
                imageResource = R.drawable.iv_antianiging;
                description = getString(R.string.anti_aging);
                result1 = getString(R.string.anti_aging_result_one);
                result2 = getString(R.string.anti_aging_result_two);
                break;
            case "Face Tapping":
                imageResource = R.drawable.iv_tapping;
                description = getString(R.string.face_tappingdes);
                result1 = getString(R.string.face_tapping_result_one);
                result2 = getString(R.string.face_tapping_result_two);
                break;
            case "Glowing Skin":
                imageResource = R.drawable.iv_glowingskin;
                description = getString(R.string.glowing_skin);
                result1 = getString(R.string.glowing_skin_result_one);
                result2 = getString(R.string.glowing_skin_result_two);
                break;
            case "Gua-Sha Massage":
                imageResource = R.drawable.iv_guasha;
                description = getString(R.string.gua_sha);
                result1 = getString(R.string.gua_sha_result_one);
                result2 = getString(R.string.gua_sha_result_two);
                break;
            case "Softer Forehead Lines":
                imageResource = R.drawable.iv_head;
                description = getString(R.string.softening_forehead_lines);
                result1 = getString(R.string.softening_forehead_lines_result_one);
                result2 = getString(R.string.softening_forehead_lines_result_two);
                break;
            case "Eye Circles":
                imageResource = R.drawable.iv_eye;
                description = getString(R.string.eye_circle_yoga);
                result1 = getString(R.string.eye_circle_yoga_result_one);
                result2 = getString(R.string.eye_circle_yoga_result_two);

                break;
            case "Brow Smoother":
                imageResource = R.drawable.iv_brow;
                description = getString(R.string.brow_smoother_des);
                result1 = getString(R.string.brow_smoother_result_one);
                result2 = getString(R.string.brow_smoother_result_two);
                break;
            case "JawLine":
                imageResource = R.drawable.iv_jaw;
                description = getString(R.string.jawline);
                result1 = getString(R.string.jawline_result_one);
                result2 = getString(R.string.jawline_result_two);
                break;
            case "Neck Massage":
                imageResource = R.drawable.iv_neck;
                description = getString(R.string.neck_massage_des);
                result1 = getString(R.string.neck_massage_des_result_one);
                result2 = getString(R.string.neck_massage_des_result_two);
                break;
            case "Remove Double Chin":
                imageResource = R.drawable.iv_doublechin;
                description = getString(R.string.double_chin);
                result1 = getString(R.string.double_chin_result_one);
                result2 = getString(R.string.double_chin_result_two);
                break;
            default:
                imageResource = R.drawable.iv_default;

        }

        Intent intent = new Intent(rootView.getContext(), HomeScheduleActivity.class);
        intent.putExtra("Selected Workout", routineName);
        intent.putExtra("Image For Toolbar", imageResource);
        intent.putExtra("description", description);
        intent.putExtra("result1", result1);
        intent.putExtra("result2", result2);
        startActivity(intent);
    }
}

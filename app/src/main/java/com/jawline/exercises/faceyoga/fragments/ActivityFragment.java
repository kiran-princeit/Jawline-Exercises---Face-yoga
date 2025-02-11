package com.jawline.exercises.faceyoga.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jawline.exercises.faceyoga.ActivityScheduleActivity;
import com.jawline.exercises.faceyoga.R;
import com.jawline.exercises.faceyoga.TwentyOneDaysChallengeActivity;


public class ActivityFragment extends Fragment {
    public Context context;
    String description = "";
    String result1 = "";
    String result2 = "";
    public int imageResource;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_activity, container, false);

        context = getActivity();

        inflate.findViewById(R.id.cheeksYogaImageView).setOnClickListener(view -> {
            Intent intent = new Intent(context, ActivityScheduleActivity.class);
            intent.putExtra("Which Exercise", "Face Yoga For Cheeks");
            intent.putExtra("Image", "Cheeks Yoga");
            intent.putExtra("description", getString(R.string.cheek_lift));
            intent.putExtra("result1", getString(R.string.cheek_lift_result_one));
            intent.putExtra("result2", getString(R.string.cheek_lift_result_two));
            startActivity(intent);
        });
        inflate.findViewById(R.id.eyesYogaImageView).setOnClickListener(view -> {
            imageResource = R.drawable.iv_eyeyoga;
            description = getString(R.string.eye_lift);
            result1 = getString(R.string.eye_lift_result_one);
            result2 = getString(R.string.eye_lift_result_two);
            Intent intent2 = new Intent(context, ActivityScheduleActivity.class);
            intent2.putExtra("Which Exercise", "Face Yoga For Eyes");
            intent2.putExtra("Image", "Eyes Yoga");
            intent2.putExtra("description", description);
            intent2.putExtra("result1", result1);
            intent2.putExtra("result2", result2);
            startActivity(intent2);
        });
        inflate.findViewById(R.id.challengeImageView).setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), TwentyOneDaysChallengeActivity.class));
        });
        return inflate;
    }
}
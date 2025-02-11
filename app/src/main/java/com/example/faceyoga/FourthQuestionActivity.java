package com.example.faceyoga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class FourthQuestionActivity extends AppCompatActivity {
    public ArrayList<String> selectedSkinTypes;
    public TextView tvCombination, tvDry, tvOily, tvNormal, tvSensitive;

    TextView selectedTextView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth_question);

        selectedSkinTypes = new ArrayList<>();
        tvNormal = findViewById(R.id.tvNormal);
        tvOily = findViewById(R.id.tvOily);
        tvDry = findViewById(R.id.tvDry);
        tvCombination = findViewById(R.id.tvCombination);
        tvSensitive = findViewById(R.id.tvSensitive);

        selectedTextView = tvSensitive;
        selectedSkinTypes.add("Sensitive");
        selectBackground(tvSensitive);

        tvNormal.setOnClickListener(view -> toggleSelection(tvNormal, "Normal"));
        tvOily.setOnClickListener(view -> toggleSelection(tvOily, "Oily"));
        tvDry.setOnClickListener(view -> toggleSelection(tvDry, "Dry"));
        tvCombination.setOnClickListener(view -> toggleSelection(tvCombination, "Combination"));
        tvSensitive.setOnClickListener(view -> toggleSelection(tvSensitive, "Sensitive"));

        findViewById(R.id.iv_close).setOnClickListener(view -> onBackPressed());
        findViewById(R.id.btnSkinType).setOnClickListener(view -> {
            startActivity(new Intent(FourthQuestionActivity.this, MainActivity.class));
        });

    }

    private void toggleSelection(TextView selectedView, String skinType) {
        if (selectedTextView != null) {
            unselectBackground(selectedTextView);
            selectedSkinTypes.clear();
        }

        selectBackground(selectedView);
        selectedSkinTypes.add(skinType);
        selectedTextView = selectedView;
    }


    private void selectBackground(TextView textView) {
        textView.setBackgroundDrawable(getDrawable(R.drawable.ripple_effect_button_with_stroke_green));
        textView.setTextColor(getColor(R.color.colorPrimary));
    }

    private void unselectBackground(TextView textView) {
        textView.setBackgroundDrawable(getDrawable(R.drawable.ripple_effect_button_with_stroke));
        textView.setTextColor(getColor(R.color.text_light));
    }
}
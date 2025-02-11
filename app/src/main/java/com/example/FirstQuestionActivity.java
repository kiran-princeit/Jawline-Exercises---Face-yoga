package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.faceyoga.AgeSelectActivity;
import com.example.faceyoga.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FirstQuestionActivity extends AppCompatActivity {
    public ArrayList<String> selectedSkinTypes;
    TextView tvNever, tvRegularly, tvLongTime;
    private TextView selectedTextView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_question);

        selectedSkinTypes = new ArrayList<>();
        tvNever = findViewById(R.id.tvNever);
        tvLongTime = findViewById(R.id.tvLongTime);
        tvRegularly = findViewById(R.id.tvRegularly);

        selectedTextView = tvLongTime;
        selectedSkinTypes.add("Long time ago");
        selectBackground(tvLongTime);

        tvNever.setOnClickListener(view -> handleSelection(tvNever, "Never"));
        tvLongTime.setOnClickListener(view -> handleSelection(tvLongTime, "Long time ago"));
        tvRegularly.setOnClickListener(view -> handleSelection(tvRegularly, "Yes I Do Regularly"));

        findViewById(R.id.iv_close).setOnClickListener(view -> onBackPressed());


        findViewById(R.id.btnContinue).setOnClickListener(view -> {
            startActivity(new Intent(FirstQuestionActivity.this, AgeSelectActivity.class));
        });
    }


    private void selectBackground(TextView textView) {
        textView.setBackgroundDrawable(getDrawable(R.drawable.ripple_effect_button_with_stroke_green));
        textView.setTextColor(getColor(R.color.colorPrimary));
    }

    private void unselectBackground(TextView textView) {
        textView.setBackgroundDrawable(getDrawable(R.drawable.ripple_effect_button_with_stroke));
        textView.setTextColor(getColor(R.color.text_light));
    }

    private void handleSelection(TextView selectedView, String skinType) {
        // Unselect the previously selected option
        if (selectedTextView != null) {
            unselectBackground(selectedTextView);
            selectedSkinTypes.clear();
        }

        // Select the new option
        selectBackground(selectedView);
        selectedSkinTypes.add(skinType);
        selectedTextView = selectedView; // Update the selected view
    }

}
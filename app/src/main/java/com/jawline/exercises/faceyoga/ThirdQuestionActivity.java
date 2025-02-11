package com.jawline.exercises.faceyoga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import nl.bryanderidder.themedtogglebuttongroup.ThemedButton;
import nl.bryanderidder.themedtogglebuttongroup.ThemedToggleButtonGroup;

public class ThirdQuestionActivity extends AppCompatActivity {

    public ArrayList<String> selectedFaceParts;
    ThemedToggleButtonGroup themedToggleButtonGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_question);

        themedToggleButtonGroup = findViewById(R.id.themedToggleButtonGroup);

        List<ThemedButton> selectedButtons = this.themedToggleButtonGroup.getSelectedButtons();
        this.selectedFaceParts = new ArrayList<>();
        for (ThemedButton text : selectedButtons) {
            this.selectedFaceParts.add(text.getText());
        }

        findViewById(R.id.iv_close).setOnClickListener(view -> {
            onBackPressed();
        });
        findViewById(R.id.btnContinueFocus).setOnClickListener(view -> {

            startActivity(new Intent(ThirdQuestionActivity.this, FourthQuestionActivity.class));

        });
    }


}
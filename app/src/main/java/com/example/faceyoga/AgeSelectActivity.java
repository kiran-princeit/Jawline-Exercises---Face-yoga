package com.example.faceyoga;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.example.faceyoga.adapter.NumberAdapter;

import java.util.ArrayList;
import java.util.List;

public class AgeSelectActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NumberAdapter adapter;
    private List<Integer> numberList;
    private LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_select);

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        TextView centerTextView = findViewById(R.id.centerTextView);
        numberList = new ArrayList<>();
        for (int i = 10; i <= 50; i++) {
            numberList.add(i);
        }

        adapter = new NumberAdapter(this, numberList);
        recyclerView.setAdapter(adapter);


        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);


        int defaultSelectedPosition = numberList.indexOf(18);
        if (defaultSelectedPosition != -1) {
            recyclerView.scrollToPosition(defaultSelectedPosition);
            adapter.setSelectedPosition(defaultSelectedPosition);
            centerTextView.setText(String.valueOf(numberList.get(defaultSelectedPosition)));
        }

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    View centerView = snapHelper.findSnapView(layoutManager);
//                    int pos = layoutManager.getPosition(centerView);
//                    adapter.setSelectedPosition(pos);
//
//                    // Update the center text
//                    TextView centerTextView = findViewById(R.id.centerTextView);
//                    centerTextView.setText(String.valueOf(numberList.get(pos)));
//                }
//            }
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    View centerView = snapHelper.findSnapView(layoutManager);
                    if (centerView != null) {
                        int pos = layoutManager.getPosition(centerView);
                        if (pos == 0 || pos == numberList.size() - 1) {
                            recyclerView.smoothScrollToPosition(pos);
                        }

                        adapter.setSelectedPosition(pos);
                        centerTextView.setText(String.valueOf(numberList.get(pos)));
                    }
                }
            }


        });

        findViewById(R.id.btnContinueAge).setOnClickListener(view -> {
            startActivity(new Intent(AgeSelectActivity.this, ThirdQuestionActivity.class));

        });
        findViewById(R.id.iv_close).setOnClickListener(view -> {
            onBackPressed();
        });

    }
}
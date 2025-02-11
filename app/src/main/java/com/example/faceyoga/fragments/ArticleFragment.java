package com.example.faceyoga.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.faceyoga.ActivityArticleRead;
import com.example.faceyoga.R;

public class ArticleFragment extends Fragment implements View.OnClickListener {

    public View view;

    public ArticleFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getActivity().getWindow().getDecorView().setSystemUiVisibility(8192);
        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorBackground));
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_article, container, false);
        view = inflate;

        ((ImageView) view.findViewById( R.id.imgFaceYogaImprovement)).setOnClickListener(this);
        ((ImageView) view.findViewById( R.id.imgFaceWorkout)).setOnClickListener(this);
        ((ImageView) view.findViewById( R.id.imgFaceYogaBenefits)).setOnClickListener(this);
        ((ImageView) view.findViewById( R.id.imgBestFoodForSkin)).setOnClickListener(this);
        ((ImageView) view.findViewById( R.id.imgDoesFaceYogaWork)).setOnClickListener(this);
        return this.view;
    }

    public final void openArticle(String str, int i9) {
        Intent intent = new Intent(this.view.getContext(), ActivityArticleRead.class);
        intent.putExtra("Article Title", str);
        intent.putExtra("Article Image", i9);
        startActivity(intent);
    }


    @Override
    public void onClick(View view) {
        String str = "";
        int i9 = 0;
        if (view.getId() == R.id.imgFaceYogaImprovement) {
            str = getString(R.string.title_can_face_yoga_improve_your_appearance);
            i9 = R.drawable.improve_image;
        } else if (view.getId() == R.id.imgDoesFaceYogaWork) {
            str = getString(R.string.title_does_face_yoga_actually_works);
            i9 = R.drawable.works_image;
        } else if (view.getId() == R.id.imgFaceWorkout) {
            str = getString(R.string.title_does_your_face_need_a_workout);
            i9 = R.drawable.need_workout;
        } else if (view.getId() == R.id.imgFaceYogaBenefits) {
            str = getString(R.string.title_face_yoga_and_its_benefits);
            i9 = R.drawable.benefits_image;
        } else if (view.getId() == R.id.imgBestFoodForSkin) {
            str = getString(R.string.title_best_food_for_healthy_and_glowing_skin);
            i9 = R.drawable.food_image;
        } else {
            return;
        }

        openArticle(str, i9);
    }

}
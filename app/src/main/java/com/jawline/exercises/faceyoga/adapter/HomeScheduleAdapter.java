package com.jawline.exercises.faceyoga.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jawline.exercises.faceyoga.R;
import com.jawline.exercises.faceyoga.model.Exercise;

import java.util.List;

public class HomeScheduleAdapter extends RecyclerView.Adapter<HomeScheduleAdapter.MyViewHolder> {
    private List<Exercise> exerciseList;
    Context context;

    public HomeScheduleAdapter(Context context, List<Exercise> exerciseList) {
        this.context = context;
        this.exerciseList = exerciseList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_shedle, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Exercise exercise = exerciseList.get(position);
        holder.titleTextView.setText(exercise.getName());
        holder.durationTextView.setText( exercise.getDuration() + " Seconds");
        Glide.with(context).load(Integer.valueOf(exercise.getImage())).into(holder.ivWorkout);

    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView durationTextView;
        public AppCompatImageView ivWorkout;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            linearLayout = itemView.findViewById(R.id.recycleItemsExercisePremium);
            titleTextView = itemView.findViewById(R.id.tvExerciseSchedule);
            durationTextView = itemView.findViewById(R.id.tvExerciseTime);
            ivWorkout = itemView.findViewById(R.id.ivWorkout);
        }
    }
}

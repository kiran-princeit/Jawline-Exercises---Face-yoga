package com.example.faceyoga.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faceyoga.ActivityScheduleActivity;
import com.example.faceyoga.R;
import com.example.faceyoga.model.Shedule;

import java.util.List;

public class ActivityScheduleAdapter extends RecyclerView.Adapter<ActivityScheduleAdapter.MyViewHolder> {

    public List<Shedule> sheduleList;
    public Context context;


    public ActivityScheduleAdapter(Context context, List<Shedule> sheduleList) {
        this.context = context;
        this.sheduleList = sheduleList;
    }

    @NonNull
    @Override
    public ActivityScheduleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_shedule, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityScheduleAdapter.MyViewHolder holder, int position) {
        Shedule shedule = this.sheduleList.get(position);
        holder.exerciseSheduleTextView.setText(shedule.name);
        holder.workoutImageView.setImageResource(shedule.getImage());
        Log.e("Yoga", "onBindViewHolder: " + shedule.id);

        holder.exerciseRepsTextView.setText(Integer.toString(shedule.duration) + " Seconds");
        String str = ActivityScheduleActivity.exerciseName;
        if (!str.equals("Face Yoga For Cheeks")) {
            str.equals("Face Yoga For Eyes");
        }
    }

    @Override
    public int getItemCount() {
        return sheduleList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView workoutImageView;
        TextView exerciseSheduleTextView, exerciseRepsTextView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            workoutImageView = itemView.findViewById(R.id.workoutImageView);
            exerciseSheduleTextView = itemView.findViewById(R.id.tvActivitShedule);
            exerciseRepsTextView = itemView.findViewById(R.id.tvExerciseReps);
        }
    }
}

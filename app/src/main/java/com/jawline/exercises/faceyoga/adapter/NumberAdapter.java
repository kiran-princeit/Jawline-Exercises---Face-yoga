package com.jawline.exercises.faceyoga.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jawline.exercises.faceyoga.R;

import java.util.List;

public class NumberAdapter extends RecyclerView.Adapter<NumberAdapter.NumberViewHolder> {
    private Context context;
    private List<Integer> numbers;
    private int selectedPosition = -1;

    public NumberAdapter(Context context, List<Integer> numbers) {
        this.context = context;
        this.numbers = numbers;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_number, parent, false);
        return new NumberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder holder, int position) {
        int number = numbers.get(position);
        holder.textView.setText(String.valueOf(number));

        if (position == selectedPosition) {
            holder.textView.setTextSize(60);
            holder.textView.setTextColor(context.getResources().getColor(R.color.colorPrimary));

            TextView centerTextView = ((Activity) context).findViewById(R.id.centerTextView);
            centerTextView.setText(String.valueOf(number));  // Update center text
        } else {
            holder.textView.setTextSize(46);
            holder.textView.setTextColor(context.getResources().getColor(R.color.colorPink));
        }
    }

    @Override
    public int getItemCount() {
        return numbers.size();
    }

    public static class NumberViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public NumberViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}

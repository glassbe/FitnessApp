package com.example.fitnessapp.ui.exercises;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.R;

public class ExerciseAdapter extends ListAdapter<Exercise, ExerciseAdapter.ExerciseHolder> {

    private OnItemClickListener listener;

    protected ExerciseAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Exercise> DIFF_CALLBACK = new DiffUtil.ItemCallback<Exercise>() {
        @Override
        public boolean areItemsTheSame(@NonNull Exercise oldItem, @NonNull Exercise newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Exercise oldItem, @NonNull Exercise newItem) {
            return oldItem.getTitle().equals(newItem.getTitle())
                    && oldItem.getDescription().equals(newItem.getDescription());
//                  && (oldItem.getPriority() == newItem.getPriority());
        }
    };


    @NonNull
    @Override
    public ExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_excersise, parent, false);
        return new ExerciseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseHolder holder, int position) {
        Exercise currentNote = getItem(position);
        holder.textViewTitle.setText(currentNote.getTitle());
        holder.textViewDescription.setText(currentNote.getDescription());
//        holder.textViewPriority.setText(String.valueOf(currentNote.getPriority()));
    }

    public Exercise getExerciseAt(int position) {
        return getItem(position);
    }


    class ExerciseHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;

        public ExerciseHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Exercise exercise);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}

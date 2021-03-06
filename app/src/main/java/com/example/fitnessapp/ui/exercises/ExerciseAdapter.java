package com.example.fitnessapp.ui.exercises;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.fitnessapp.R;
import com.example.fitnessapp.db.Entity.Exercise;

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
            return oldItem.getName().equals(newItem.getName())                        //Compare String
                    && oldItem.getDescription().equals(newItem.getDescription())        //Compare String
                    && oldItem.getPicturePath().equals(newItem.getPicturePath());              //Compare List
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
        String muscleString = "";

        Exercise currentExercise = getItem(position);
        holder.textViewTitle.setText(currentExercise.getName());
        holder.textViewDescription.setText(currentExercise.getDescription());

//        boolean firstTime = true;
//        for(String muscle : currentExercise.getMuscleGroups()){
//            if(firstTime){
//                muscleString = muscle;
//                firstTime = false;
//            }
//            else
//                muscleString += "| " + muscle;
//        }
////        muscleString = muscleString.substring(2);
//        holder.textViewPriority.setText(muscleString);

        Glide.with(holder.itemView.getContext())
                .load(Uri.parse("file:///android_asset/"+currentExercise.getPicturePath().get(0)))
//                .centerCrop()
//                .apply(new RequestOptions().override(50, 50))
                .fitCenter()
                .placeholder(R.drawable.placeholder_exercise)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.img_error_no_pic_exercise)
                .into(holder.mImageViewPhoto);


    }

    public Exercise getExerciseAt(int position) {
        return getItem(position);
    }


    //==================================================
    //================= Holder Class ===================
    //==================================================
    class ExerciseHolder extends RecyclerView.ViewHolder {
        private final TextView textViewTitle;
        private final TextView textViewDescription;
        private final TextView textViewPriority;
        private final ImageView mImageViewPhoto;

        public ExerciseHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.status_day_text_view_date);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);
            mImageViewPhoto = itemView.findViewById(R.id.img_view_photo);

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

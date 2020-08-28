package com.example.fitnessapp.ui.coach;

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
import com.example.fitnessapp.db.Entity.StatusUpdate;
import com.example.fitnessapp.ui.exercises.ExerciseAdapter;

public class StatusUpdateAdapter extends ListAdapter<StatusUpdate, StatusUpdateAdapter.StatusHolder> {

    private StatusUpdateAdapter.OnItemClickListener listener;


    protected StatusUpdateAdapter() {
        super(DIFF_CALLBACK);
    }


    private static final DiffUtil.ItemCallback<StatusUpdate> DIFF_CALLBACK = new DiffUtil.ItemCallback<StatusUpdate>() {
        @Override
        public boolean areItemsTheSame(@NonNull StatusUpdate oldItem, @NonNull StatusUpdate newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull StatusUpdate oldItem, @NonNull StatusUpdate newItem) {
//            return oldItem.get().equals(newItem.getName())                        //Compare String
//                    && oldItem.getDescription().equals(newItem.getDescription())        //Compare String
//                    && oldItem.getPicturePath().equals(newItem.getPicturePath());              //Compare List
            return true;
        }
    };



    @NonNull
    @Override
    public StatusUpdateAdapter.StatusHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_status_day, parent, false);
        return new StatusHolder(itemView);
    }



    @Override
    public void onBindViewHolder(@NonNull StatusUpdateAdapter.StatusHolder holder, int position) {
        String muscleString = "";

        StatusUpdate selectedStatus = getItem(position);
        holder.textViewDate.setText(selectedStatus.getTimestamp().toString());
        holder.textViewWeight.setText("Weight: " + String.valueOf(selectedStatus.getWeight()) + " kg");
        holder.textViewEnergyLevel.setText("Energy: " + String.valueOf(selectedStatus.getEnergieLevel()) + " / 100");

        Glide.with(holder.itemView.getContext())
                .load(Uri.parse("file:///android_asset/"+ selectedStatus.getPicturePath()))
//                .centerCrop()
//                .apply(new RequestOptions().override(50, 50))
                .fitCenter()
                .placeholder(R.drawable.placeholder_exercise)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.img_error_no_pic_exercise)
                .into(holder.mImageViewPhoto);

    }

    public StatusUpdate getExerciseAt(int position) {
        return getItem(position);
    }




    //==================================================
    //================= Holder Class ===================
    //==================================================
    class StatusHolder extends RecyclerView.ViewHolder {
        private final TextView textViewDate;
        private final TextView textViewWeight;
        private final TextView textViewEnergyLevel;
        private final ImageView mImageViewPhoto;

        public StatusHolder(View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.status_day_text_view_date);
            textViewWeight = itemView.findViewById(R.id.status_day_text_view_weight);
            textViewEnergyLevel = itemView.findViewById(R.id.status_day_text_view_energy_level);
            mImageViewPhoto = itemView.findViewById(R.id.status_day_current_img);

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
        void onItemClick(StatusUpdate status);
    }

    public void setOnItemClickListener(StatusUpdateAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }


}

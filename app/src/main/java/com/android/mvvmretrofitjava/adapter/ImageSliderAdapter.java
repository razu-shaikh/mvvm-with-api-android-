package com.android.mvvmretrofitjava.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.mvvmretrofitjava.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.MyViewHolder> {
    private Context context;
   private String[] sliderImages;

    public ImageSliderAdapter(Context context, String[] sliderImages) {
        this.context = context;
        this.sliderImages = sliderImages;
    }

    @NonNull
    @Override
    public ImageSliderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_container_slide_image, parent, false);
        return new ImageSliderAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSliderAdapter.MyViewHolder holder, int position) {

        Glide.with(context)
                .load(this.sliderImages[position])
                .apply(RequestOptions.centerCropTransform())
                .into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        if(this.sliderImages != null) {
            return this.sliderImages.length;
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView)itemView.findViewById(R.id.slide_image);

        }
    }


}
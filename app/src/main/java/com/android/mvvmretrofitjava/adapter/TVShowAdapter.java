package com.android.mvvmretrofitjava.adapter;

import android.content.Context;
import android.icu.text.LocaleDisplayNames;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.mvvmretrofitjava.R;
import com.android.mvvmretrofitjava.listener.TVShowListener;
import com.android.mvvmretrofitjava.model.TVShow;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class TVShowAdapter extends RecyclerView.Adapter<TVShowAdapter.MyViewHolder> {
    private Context context;
    private List<TVShow> tvShows;
    private TVShowListener clickListener;

    public TVShowAdapter(Context context, List<TVShow> movieList, TVShowListener tvShowListener ) {
        this.context = context;
        this.tvShows = movieList;
        this.clickListener = tvShowListener;
    }

    public void setTvShows(List<TVShow> tvShows) {
        this.tvShows = tvShows;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TVShowAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowAdapter.MyViewHolder holder, int position) {
        holder.textName.setText(this.tvShows.get(position).getName());
        holder.textStarted.setText(this.tvShows.get(position).getStartDate());
        holder.textNetwork.setText(this.tvShows.get(position).getNetwork());
        holder.textStatus.setText(this.tvShows.get(position).getStatus());

        Glide.with(context)
                .load(this.tvShows.get(position).getThumbnail())
                .apply(RequestOptions.centerCropTransform())
                .into(holder.imageView);

        TVShow tvShow = tvShows.get(position);
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onTVShowClicked(tvShow);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(this.tvShows != null) {
            return this.tvShows.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textName,textStarted,textNetwork,textStatus;
        RoundedImageView imageView;
        LinearLayout parentLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            textName = (TextView)itemView.findViewById(R.id.textName);
            textStarted = (TextView)itemView.findViewById(R.id.textStarted);
            textNetwork = (TextView)itemView.findViewById(R.id.textNetwork);
            textStatus = (TextView)itemView.findViewById(R.id.textStatus);
            imageView = (RoundedImageView)itemView.findViewById(R.id.imageView);
            parentLayout = (LinearLayout) itemView.findViewById(R.id.parentLayout);

        }
    }


}

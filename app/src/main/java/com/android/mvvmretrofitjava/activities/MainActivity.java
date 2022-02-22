package com.android.mvvmretrofitjava.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.mvvmretrofitjava.R;
import com.android.mvvmretrofitjava.adapter.TVShowAdapter;
import com.android.mvvmretrofitjava.listener.TVShowListener;
import com.android.mvvmretrofitjava.model.TVShow;
import com.android.mvvmretrofitjava.responses.TVShowResponse;
import com.android.mvvmretrofitjava.viewmodel.MostPopularTVShowsViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TVShowListener {

    private List<TVShow> tvShows = new ArrayList<>();
    private TVShowAdapter adapter;
    private MostPopularTVShowsViewModel viewModel;
    private ProgressBar progressBar;
    private ProgressBar progress_bar_more;
    private ImageView search_bar;
    private int currentPage=1;
    private int currentTotalPage=1;
    private int currentItems, totalItems,scrollOutItems;
    private boolean isScrolling = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressBar = findViewById(R.id.progress_bar);
        progress_bar_more = findViewById(R.id.progress_bar_more);
        search_bar = findViewById(R.id.search_bar);


        doInitialization();



       /* viewModel.getMoviesListObserver().observe(this, new Observer<List<TVShow>>() {
            @Override
            public void onChanged(List<TVShow> movieModels) {
                if(movieModels != null) {
                    movieModelList = movieModels;
                    adapter.setMovieList(movieModels);
                    noresult.setVisibility(View.GONE);
                } else {
                    noresult.setVisibility(View.VISIBLE);
                }
            }
        });*/

    }

    /*@Override
    public void onMovieClick(TVShow movie) {
        Toast.makeText(this, "Clicked Movie Name is : " +movie.getTitle(), Toast.LENGTH_SHORT).show();
    }*/

    private void doInitialization(){

        progressBar.setVisibility(View.VISIBLE);

        viewModel = new ViewModelProvider(this).get(MostPopularTVShowsViewModel.class);

        RecyclerView tvRecyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        tvRecyclerView.setLayoutManager(layoutManager);
        adapter =  new TVShowAdapter(this, tvShows,this);
        tvRecyclerView.setAdapter(adapter);

        getMostPopularTVShows();

        tvRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling =true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currentItems = layoutManager.getChildCount();
                totalItems= layoutManager.getItemCount();
                scrollOutItems = layoutManager.findFirstVisibleItemPosition();

                if ( isScrolling && (currentItems+scrollOutItems == totalItems)){
                    Log.d("currentPage",currentPage+"");
                    isScrolling=false;
                    currentPage++;
                    progress_bar_more.setVisibility(View.VISIBLE);
                    getMostPopularTVShows();

                }
            }
        });

        search_bar.setOnClickListener(view -> {startActivity(new Intent(getApplicationContext(), SearchActivity.class));});

    }


    private void getMostPopularTVShows(){
        Log.d("currentPage1",currentPage+"");
        viewModel.getMostPopularTVShows(currentPage).observe(this, new Observer<TVShowResponse>() {
            @Override
            public void onChanged(TVShowResponse tvShowResponse) {

                if (tvShowResponse != null){
                    currentTotalPage = tvShowResponse.getPages();

                    if (tvShowResponse.getTvShows() != null){
                        int oldCount = tvShows.size();
                        // adapter.setTvShows(tvShowResponse.getTvShows()); or
                        tvShows.addAll(tvShowResponse.getTvShows());
                        adapter.notifyItemRangeInserted(oldCount,tvShows.size());
                        progressBar.setVisibility(View.GONE);
                        progress_bar_more.setVisibility(View.GONE);
                    }

                }

            }
        });


   }

    @Override
    public void onTVShowClicked(TVShow tvShow) {
        Intent intent = new Intent(getApplicationContext(),TVShowDetailsActivity.class);
        intent.putExtra("id",tvShow.getId());
        Log.d("iddddd1",tvShow.getId()+"");
        intent.putExtra("name",tvShow.getName());
        intent.putExtra("startDate",tvShow.getStartDate());
        intent.putExtra("country",tvShow.getCountry());
        intent.putExtra("network",tvShow.getNetwork());
        intent.putExtra("status",tvShow.getStatus());
        startActivity(intent);
    }
}
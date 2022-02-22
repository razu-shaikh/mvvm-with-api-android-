package com.android.mvvmretrofitjava.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.mvvmretrofitjava.R;
import com.android.mvvmretrofitjava.adapter.TVShowAdapter;
import com.android.mvvmretrofitjava.listener.TVShowListener;
import com.android.mvvmretrofitjava.model.TVShow;
import com.android.mvvmretrofitjava.viewmodel.SearchViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppCompatActivity implements TVShowListener {

    private SearchViewModel searchViewModel;
    private List<TVShow> tvShows = new ArrayList<>();
    private TVShowAdapter adapter;
    private int currentPage = 1;
    private int totalAvailablePage = 1;
    private ProgressBar progressBar;
    private Timer timer;

    private EditText inputSearch;
    private ImageView imageSearch;
    private RecyclerView tvShowRecycleView;

    private int currentItems, totalItems,scrollOutItems;
    private boolean isScrolling = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
    }

    private void init() {
        inputSearch = (EditText)findViewById(R.id.inputSearch);
        imageSearch = (ImageView) findViewById(R.id.imageSearch);
        tvShowRecycleView = (RecyclerView)findViewById(R.id.tvShowRecycleView);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        tvShowRecycleView.setHasFixedSize(true);
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        tvShowRecycleView.setLayoutManager(layoutManager);
        adapter = new TVShowAdapter( this,tvShows,this);
        tvShowRecycleView.setAdapter(adapter);

       inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(timer != null){
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().trim().isEmpty()){
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    currentPage = 1;
                                    totalAvailablePage = 1;
                                    searchTvShow(editable.toString());
                                }
                            });
                        }
                    }, 800);
                }else{
                    tvShows.clear();
                    adapter.notifyDataSetChanged();
                }
            }
        });
        tvShowRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    searchTvShow(inputSearch.getText().toString());

                }
            }
        });
        inputSearch.requestFocus();
    }


    private void searchTvShow(String query) {
        progressBar.setVisibility(View.VISIBLE);
        searchViewModel.searchTvShow(query, currentPage).observe(this, tvShowResponse -> {
            if (tvShowResponse != null) {
                totalAvailablePage = tvShowResponse.getPages();
                if (tvShowResponse.getTvShows() != null) {
                    int oldCount = tvShows.size();
                    tvShows.addAll(tvShowResponse.getTvShows());
                    adapter.notifyItemRangeInserted(oldCount, tvShows.size());
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    public void onTVShowClicked(TVShow tvShow) {
        Intent intent = new Intent(getApplicationContext(), TVShowDetailsActivity.class);
        intent.putExtra("id",tvShow.getId());
        intent.putExtra("name",tvShow.getName());
        intent.putExtra("startDate",tvShow.getStartDate());
        intent.putExtra("country",tvShow.getCountry());
        intent.putExtra("network",tvShow.getNetwork());
        intent.putExtra("status",tvShow.getStatus());
        startActivity(intent);
    }



}
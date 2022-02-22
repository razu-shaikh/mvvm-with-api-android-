package com.android.mvvmretrofitjava.viewmodel;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.mvvmretrofitjava.model.TVShow;
import com.android.mvvmretrofitjava.network.APIService;
import com.android.mvvmretrofitjava.network.ApiClient;
import com.android.mvvmretrofitjava.repositories.MostPopularTVShowRepositories;
import com.android.mvvmretrofitjava.responses.TVShowResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostPopularTVShowsViewModel extends ViewModel {

    private MostPopularTVShowRepositories mostPopularTVShowRepositories;

    public MostPopularTVShowsViewModel() {
        mostPopularTVShowRepositories = new MostPopularTVShowRepositories();
    }

    public LiveData<TVShowResponse> getMostPopularTVShows(int page){
       return mostPopularTVShowRepositories.getMostPopularTVShows(page);
    }

}

package com.android.mvvmretrofitjava.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.android.mvvmretrofitjava.repositories.MostPopularTVShowRepositories;
import com.android.mvvmretrofitjava.repositories.TVShowDetailsRepository;
import com.android.mvvmretrofitjava.responses.TVShowDetailsResponse;
import com.android.mvvmretrofitjava.responses.TVShowResponse;

public class TVShowDetailsViewModel extends ViewModel {

    private TVShowDetailsRepository tvShowDetailsRepository;

    public TVShowDetailsViewModel() {
        tvShowDetailsRepository = new TVShowDetailsRepository();
    }

    public LiveData<TVShowDetailsResponse> getTVShowDetails(String  tvShowId){
        return tvShowDetailsRepository.getTVShowDetails(tvShowId);
    }

}

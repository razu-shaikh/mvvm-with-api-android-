package com.android.mvvmretrofitjava.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.android.mvvmretrofitjava.repositories.SearchTvShowRepository;
import com.android.mvvmretrofitjava.responses.TVShowResponse;

public class SearchViewModel extends ViewModel {
    private SearchTvShowRepository searchTvShowRepository;

    public SearchViewModel() {
        searchTvShowRepository = new SearchTvShowRepository();
    }

    public LiveData<TVShowResponse> searchTvShow(String query, int page){
        return searchTvShowRepository.searchTvShow(query, page);
    }
}
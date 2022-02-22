package com.android.mvvmretrofitjava.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.mvvmretrofitjava.network.APIService;
import com.android.mvvmretrofitjava.network.ApiClient;
import com.android.mvvmretrofitjava.responses.TVShowResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchTvShowRepository {
    private APIService apiService;

    public SearchTvShowRepository() {
        apiService = ApiClient.getRetroClient().create(APIService.class);
    }

    public LiveData<TVShowResponse> searchTvShow(String query, int page){
        MutableLiveData<TVShowResponse> data = new MutableLiveData<>();
        apiService.searchTvShow(query, page).enqueue(new Callback<TVShowResponse>() {
            @Override
            public void onResponse(Call<TVShowResponse> call, Response<TVShowResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<TVShowResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
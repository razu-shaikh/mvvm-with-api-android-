package com.android.mvvmretrofitjava.repositories;

import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.mvvmretrofitjava.model.TVShow;
import com.android.mvvmretrofitjava.network.APIService;
import com.android.mvvmretrofitjava.network.ApiClient;
import com.android.mvvmretrofitjava.responses.TVShowResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostPopularTVShowRepositories {

    private APIService apiService;

    public MostPopularTVShowRepositories() {
        apiService = ApiClient.getRetroClient().create(APIService.class);
    }

    public LiveData<TVShowResponse> getMostPopularTVShows(int page){
        MutableLiveData<TVShowResponse> data = new MutableLiveData<>();
        apiService.getMostPopularTVShows(page).enqueue(new Callback<TVShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<TVShowResponse> call,@NonNull Response<TVShowResponse> response) {
               data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TVShowResponse> call, @NonNull Throwable t) {
              data.setValue(null);
            }
        });
      return data;
    }
}

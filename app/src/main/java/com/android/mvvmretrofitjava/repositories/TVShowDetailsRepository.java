package com.android.mvvmretrofitjava.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.android.mvvmretrofitjava.network.APIService;
import com.android.mvvmretrofitjava.network.ApiClient;
import com.android.mvvmretrofitjava.responses.TVShowDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVShowDetailsRepository {

    private APIService apiService;

    public TVShowDetailsRepository() {
        apiService = ApiClient.getRetroClient().create(APIService.class);
    }

    public LiveData<TVShowDetailsResponse> getTVShowDetails(String tvShowId){
        MutableLiveData<TVShowDetailsResponse> data = new MutableLiveData<>();
        apiService.getTVShowDetails(tvShowId).enqueue(new Callback<TVShowDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<TVShowDetailsResponse> call, @NonNull Response<TVShowDetailsResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TVShowDetailsResponse> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}

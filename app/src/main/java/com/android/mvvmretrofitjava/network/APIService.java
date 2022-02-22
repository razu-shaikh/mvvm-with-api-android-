package com.android.mvvmretrofitjava.network;

import com.android.mvvmretrofitjava.responses.TVShowDetailsResponse;
import com.android.mvvmretrofitjava.responses.TVShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET("most-popular")
    Call<TVShowResponse> getMostPopularTVShows(@Query("page") int page);

    @GET("show-details")
    Call<TVShowDetailsResponse> getTVShowDetails(@Query("q") String tvShowId);

    @GET("search")
    Call<TVShowResponse> searchTvShow(@Query("q") String query, @Query("page") int page);
}

package com.android.mvvmretrofitjava.responses;

import com.android.mvvmretrofitjava.model.TVShowDetails;
import com.google.gson.annotations.SerializedName;

public class TVShowDetailsResponse {
    @SerializedName("tvShow")
    private TVShowDetails tvShowDetails;

    public TVShowDetails getTvShowDetails() {
        return tvShowDetails;
    }
}

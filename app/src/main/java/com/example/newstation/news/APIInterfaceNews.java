package com.example.newstation.news;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterfaceNews {
    @GET("top-headlines")
    Call<ResponseModelNews> getLatestNews(@Query("sources") String source, @Query("API_KEY") String apiKey);
}


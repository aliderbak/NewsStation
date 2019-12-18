package com.example.newstation.news;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterfaceNews {
    @GET("top-headlines")
    Observable<ResponseModelNews> getLatestNews(@Query("top_headlines") String topHeadlines
            ,@Query("sources") String source,
                                                @Query("API_KEY") String apiKey);
}


package com.softaai.randomimagerenderers.remote;

import retrofit2.http.GET;

/**
 * Created by Amol Pawar on 15-05-2019.
 * softAai Apps
 */
public interface RandomImageApi {

    @GET("svc/mostpopular/v2/mostviewed/all-sections/{period}.json?}")
    Call<ArticleResponse> getAllArticles(@Path("period") String period, @Query(value = "api-key", encoded = true) String apiKey);

}

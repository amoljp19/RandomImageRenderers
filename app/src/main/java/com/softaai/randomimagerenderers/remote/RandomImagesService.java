package com.softaai.randomimagerenderers.remote;

import com.softaai.randomimagerenderers.model.RandomImageResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Amol Pawar on 15-05-2019.
 * softAai Apps
 */
public interface RandomImagesService {

    @GET("/v2/list")
    Call<List<RandomImageResponse>> getRandomImagesResponse(@Query("page") int page, @Query("limit") int limit);

}

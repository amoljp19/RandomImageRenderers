package com.softaai.randomimagerenderers.remote;

import com.softaai.randomimagerenderers.model.RandomImageResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.softaai.randomimagerenderers.remote.RetrofitClientInstance.getRetrofitInstance;

/**
 * Created by Amol Pawar on 15-05-2019.
 * softAai Apps
 */
public class ResponseManager {
    public Retrofit client;

    public ResponseManager() {
        client = getRetrofitInstance();
    }

    public void getRandomImages(final ApiCallback<RandomImageResponse> callback) {
        RandomImagesService service = client.create(RandomImagesService.class);
        service.getRandomImagesResponse(2, 100).enqueue(new Callback<List<RandomImageResponse>>() {
            @Override
            public void onResponse(Call<List<RandomImageResponse>> call, Response<List<RandomImageResponse>> response) {
                if (response.isSuccessful()) {
                    callback.success(response.body());
                } else {
                    callback.failure(response.code());
                }
            }

            @Override
            public void onFailure(Call<List<RandomImageResponse>> call, Throwable t) {
                callback.failure(500);
            }
        });
    }


    public interface ApiCallback<T> {
        void success(List<RandomImageResponse> response);
        void failure(int responseCode);
    }
}

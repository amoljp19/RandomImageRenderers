package com.softaai.randomimagerenderers.remote;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

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
public class ResponseManager extends PageKeyedDataSource<Integer, RandomImageResponse> {

    public static final int LIMIT = 100;
    private static final int PAGE = 2;


    public Retrofit client;

    public ResponseManager() {
        client = getRetrofitInstance();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, RandomImageResponse> callback) {
        RandomImagesService service = client.create(RandomImagesService.class);
        service.getRandomImagesResponse(PAGE, 30).enqueue(new Callback<List<RandomImageResponse>>() {
            @Override
            public void onResponse(Call<List<RandomImageResponse>> call, Response<List<RandomImageResponse>> response) {
                if (response.body() != null) {
                    callback.onResult(response.body(), null, PAGE + 1);
                }
            }

            @Override
            public void onFailure(Call<List<RandomImageResponse>> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, RandomImageResponse> callback) {

        RandomImagesService service = client.create(RandomImagesService.class);
        service.getRandomImagesResponse(params.key, 30).enqueue(new Callback<List<RandomImageResponse>>() {
            @Override
            public void onResponse(Call<List<RandomImageResponse>> call, Response<List<RandomImageResponse>> response) {
                Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
                if (response.body() != null) {
                    callback.onResult(response.body(), adjacentKey);
                }
            }

            @Override
            public void onFailure(Call<List<RandomImageResponse>> call, Throwable t) {

            }
        });


    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, RandomImageResponse> callback) {

        RandomImagesService service = client.create(RandomImagesService.class);
        service.getRandomImagesResponse(params.key, 30).enqueue(new Callback<List<RandomImageResponse>>() {
            @Override
            public void onResponse(Call<List<RandomImageResponse>> call, Response<List<RandomImageResponse>> response) {
                if (response.body() != null) {
                    callback.onResult(response.body(), params.key + 1);
                }
            }

            @Override
            public void onFailure(Call<List<RandomImageResponse>> call, Throwable t) {

            }
        });

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

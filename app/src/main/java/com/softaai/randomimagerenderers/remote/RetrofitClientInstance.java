package com.softaai.randomimagerenderers.remote;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Amol Pawar on 15-05-2019.
 * softAai Apps
 */

public class RetrofitClientInstance {

    private static Retrofit retrofit;
    private static final String URL = "https://picsum.photos";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}

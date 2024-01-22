package com.danny.bookexplorer.api.huggingface;

import com.danny.bookexplorer.api.ElasticAPI;


import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ModelClient {
    private static final String BASE_URL = "https://api-inference.huggingface.co/";


    public static ModelAPI modelAPI;

    public static ModelAPI getModelAPIClient(){
        Interceptor requestInterceptor = chain -> {
            Request originalRequest = chain.request();

            // Thêm các header vào originalRequest
            Request newRequest = originalRequest.newBuilder()
                    .addHeader("Authorization", "Bearer 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")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .build();

            return chain.proceed(newRequest);
        };

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build();

        return new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ModelAPI.class);

    }
}

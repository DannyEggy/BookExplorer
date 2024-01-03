package com.danny.bookexplorer.api;

import okhttp3.Credentials;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ElasticClient {
    private static final String BASE_URL = "https://a301c101161e418e9de728c9d97c679a.us-central1.gcp.cloud.es.io";
    private static final String CLOUD_ID = "7877ff1e9a7b4812baafff7e0f47bc83:dXMtY2VudHJhbDEuZ2NwLmNsb3VkLmVzLmlvOjQ0MyRhMzAxYzEwMTE2MWU0MThlOWRlNzI4YzlkOTdjNjc5YSQwMjFlZWFiYWU3ZTk0M2E3OWJhYjU4M2ZlOGQzYjkwMA==";
    private static final String USERNAME = "elastic";
    private static final String PASSWORD = "HXOQcTUsJQ71Zstv4nynOaV7";

    public static ElasticAPI elasticAPI;

    public static ElasticAPI getElasticAPIClient(){
        Interceptor requestInterceptor = chain -> {
            Request originalRequest = chain.request();

            // Thêm các header vào originalRequest
            Request newRequest = originalRequest.newBuilder()
                    .addHeader("Authorization", Credentials.basic(USERNAME, PASSWORD))
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
                .create(ElasticAPI.class);

    }

}

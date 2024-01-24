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
    private static final String BASE_URL = "https://9e0ef91e415c44e497bd484874099e92.us-central1.gcp.cloud.es.io";
    private static final String CLOUD_ID = "15858ac8d7ab4d099dad7912514e73df:dXMtY2VudHJhbDEuZ2NwLmNsb3VkLmVzLmlvOjQ0MyQ5ZTBlZjkxZTQxNWM0NGU0OTdiZDQ4NDg3NDA5OWU5MiQ0MWM0ZjBhMmJlODc0MTc1OTFjMzUyNGYzNmYzN2I2MQ==";
    private static final String USERNAME = "elastic";
    private static final String PASSWORD = "BqMnkSKwGlpBv3QlqMl7qY1k";

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

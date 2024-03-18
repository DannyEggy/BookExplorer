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
    private static final String BASE_URL = "https://0ac38a544abf4aa4829730b6f59f1eaa.us-central1.gcp.cloud.es.io";
    private static final String CLOUD_ID = "b1150c390a264b4aad50ec61924c2759:dXMtY2VudHJhbDEuZ2NwLmNsb3VkLmVzLmlvOjQ0MyQwYWMzOGE1NDRhYmY0YWE0ODI5NzMwYjZmNTlmMWVhYSQ4ODU3ODYzZGNlNTY0Nzg0OTdjNDFjMWE2MGI1ZTM0Nw==";
    private static final String USERNAME = "elastic";
    private static final String PASSWORD = "qb6HUcCRaLGOqJguXD19uM23";

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

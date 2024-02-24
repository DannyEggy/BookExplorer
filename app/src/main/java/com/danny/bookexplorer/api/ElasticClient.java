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
    private static final String BASE_URL = "https://4163ed3b60ad431e9b7a565187950e51.us-central1.gcp.cloud.es.io:443";
    private static final String CLOUD_ID = "d1f3e3a951a246f3b30888091e28866b:dXMtY2VudHJhbDEuZ2NwLmNsb3VkLmVzLmlvJDQxNjNlZDNiNjBhZDQzMWU5YjdhNTY1MTg3OTUwZTUxJDA5ZTgyMGQ3YzgwMDQ2M2Y4MGQ3Y2RiZjg5YWM5ZmYy";
    private static final String USERNAME = "elastic";
    private static final String PASSWORD = "86844ZAb0EqM3HVYuKujd1qo";

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

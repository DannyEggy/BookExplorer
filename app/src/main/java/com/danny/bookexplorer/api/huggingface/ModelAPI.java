package com.danny.bookexplorer.api.huggingface;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ModelAPI {
    @POST("models/sentence-transformers/all-MiniLM-L6-v2")
    Single<double[]> query(@Body String data);

}

package com.palapol.tsp_sesaminar_team02.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
//    private static Retrofit retrofit = null;
    public static Retrofit getClient(String url){
        OkHttpClient okHttpClient = com.kingkarn.projectbeaconapplication.UnsafeOkHttpClient.getUnsafeOkHttpClient();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
//        if (retrofit == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        //}
        return retrofit;
    }
}

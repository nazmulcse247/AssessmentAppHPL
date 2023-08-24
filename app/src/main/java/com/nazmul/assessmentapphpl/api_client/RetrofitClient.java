package com.nazmul.assessmentapphpl.api_client;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static Retrofit retrofit;

    public static Retrofit GET_RETROFIT_CLIENT() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        Interceptor interceptor = chain -> {
            Request original = chain.request();

            Request request = original.newBuilder()
                    .header("Content-Type","application/json")
                    .header("Accept","application/x-www-form-urlencoded")
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        };

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS) // connect timeout
                .writeTimeout(59, TimeUnit.SECONDS) // write timeout
                .readTimeout(59, TimeUnit.SECONDS) // read timeout
                .addInterceptor(interceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build();
        String baseUrl = "http://vps.hplbd.com/";

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient)
                    .build();
        }
        return retrofit;
    }

}

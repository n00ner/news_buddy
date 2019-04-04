package com.n00ner.newsbuddy.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.n00ner.newsbuddy.BaseApp;
import com.n00ner.newsbuddy.Utils;

import java.io.IOException;

import io.reactivex.Single;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {

    @GET("/rss/search")
    Single<String> getNews(@Query("q") String query, @Query("hl") String lang);

    public static NewsApi create() {

        long cacheSize = 5 * 1024 * 1024;
        Cache tempCache = new Cache(BaseApp.getAppContext().getCacheDir(), cacheSize);

        OkHttpClient client = new OkHttpClient.Builder()
                .cache(tempCache).addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        if (Utils.hasNetwork(BaseApp.getAppContext())) {
                            request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build();
                        } else {
                            request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                        }
                        return chain.proceed(request);
                    }
                }).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://news.google.com")
                .build().create(NewsApi.class);

    }
}

package com.example.pokedex.data.client;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;

@Singleton
public class ClientConfig {
    private final String baseUrl = "https://pokeapi.co/api/v2/";
    private final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .retryOnConnectionFailure(false)
            .connectTimeout(30,TimeUnit.SECONDS)
            .readTimeout(30,TimeUnit.SECONDS)
            .writeTimeout(30,TimeUnit.SECONDS).build();

    @Inject
    public ClientConfig(){
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
}

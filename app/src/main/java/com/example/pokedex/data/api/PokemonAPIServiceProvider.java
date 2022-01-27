package com.example.pokedex.data.api;

import com.example.pokedex.data.client.ClientConfig;
import com.example.pokedex.data.model.NamedApiResources;
import com.example.pokedex.data.model.Pokemon;
import com.example.pokedex.data.util.NamedApiResourceJsonDeserializer;
import com.example.pokedex.data.util.PokemonJsonDeserializer;
import com.google.gson.GsonBuilder;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.adapter.guava.GuavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class PokemonAPIServiceProvider {

    @Provides
    @Inject
    public PokemonAPIService getPokemonApiService(ClientConfig clientConfig){
        return new Retrofit.Builder()
                .baseUrl(clientConfig.getBaseUrl())
                .addCallAdapterFactory(GuavaCallAdapterFactory.create())
                .addConverterFactory(buildGsonConverter())
                .client(clientConfig.getOkHttpClient())
                .build()
                .create(PokemonAPIService.class);
    }

    private GsonConverterFactory buildGsonConverter(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(NamedApiResources.class, new NamedApiResourceJsonDeserializer());
        gsonBuilder.registerTypeAdapter(Pokemon.class, new PokemonJsonDeserializer());
        return GsonConverterFactory.create(gsonBuilder.create());
    }
}

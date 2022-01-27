package com.example.pokedex.data.api;

import com.example.pokedex.data.model.NamedApiResources;
import com.example.pokedex.data.model.Pokemon;
import com.google.common.util.concurrent.ListenableFuture;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokemonAPIService {
    @GET("pokemon/")
    ListenableFuture<NamedApiResources> getPokemonResourcesWithLimitAndOffset(@Query("limit") int limit, @Query("offset") int offset);

    @GET("pokemon/{id}/")
    Call<Pokemon> getPokemonById(@Path("id") int id);

    @GET("pokemon/{name}/")
    Call<Pokemon> getPokemonByName(@Path("name") String name);
}

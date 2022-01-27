package com.example.pokedex.data.client;

import androidx.annotation.NonNull;

import com.example.pokedex.data.DataResult;
import com.example.pokedex.data.ResponseStatus;
import com.example.pokedex.data.api.PokemonAPIService;
import com.example.pokedex.data.api.PokemonAPIServiceProvider;
import com.example.pokedex.data.model.NamedApiResource;
import com.example.pokedex.data.model.NamedApiResources;
import com.example.pokedex.data.model.Pokemon;
import com.google.common.util.concurrent.ListenableFuture;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

public class PokemonAPIClient {
    private static PokemonAPIService pokemonAPIService;

    @Inject
    public PokemonAPIClient(PokemonAPIService apiService){
        pokemonAPIService = apiService;
    }

    public ListenableFuture<NamedApiResources> getPokemonResourcesWithLimitAndOffset(int limit, int offset){
        return pokemonAPIService.getPokemonResourcesWithLimitAndOffset(limit, offset);
    }

    public void getPokemonById(int id, DataResult.Callback<Pokemon> callback){
        Call<Pokemon> callAsync = pokemonAPIService.getPokemonById(id);
        callAsync.enqueue(createRetrofitCallback(callback));
    }

    public void getPokemonByName(String name, DataResult.Callback<Pokemon> callback){
        if (name != null){
            Call<Pokemon> callAsync = pokemonAPIService.getPokemonByName(name.toLowerCase());
            callAsync.enqueue(createRetrofitCallback(callback));
        }
    }

    private <T> retrofit2.Callback<T> createRetrofitCallback(DataResult.Callback<T> dataResultCallback){
        return new retrofit2.Callback<T>() {
            @Override
            public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                ResponseStatus responseStatus;
                if (response.isSuccessful()){
                    responseStatus = new ResponseStatus(response.code(), true);
                } else{
                    responseStatus = new ResponseStatus(response.code(), false, response.message());
                }

                dataResultCallback.onResult(new DataResult<>(response.body(), responseStatus));
            }

            @Override
            public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                ResponseStatus responseStatus = new ResponseStatus(t.getMessage());
                dataResultCallback.onResult(new DataResult<>(null, responseStatus));
            }
        };
    }
}

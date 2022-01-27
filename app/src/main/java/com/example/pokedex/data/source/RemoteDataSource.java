package com.example.pokedex.data.source;

import com.example.pokedex.data.DataResult;
import com.example.pokedex.data.client.ClientConfig;
import com.example.pokedex.data.client.PokemonAPIClient;
import com.example.pokedex.data.model.NamedApiResources;
import com.example.pokedex.data.model.Pokemon;
import com.google.common.util.concurrent.ListenableFuture;

import javax.inject.Inject;


public class RemoteDataSource implements DataSource{
    private final PokemonAPIClient apiClient;

    @Inject
    public RemoteDataSource(PokemonAPIClient apiClient){
        this.apiClient = apiClient;
    }

    public void getPokemonById(int id, DataResult.Callback<Pokemon> callback){
        apiClient.getPokemonById(id, callback);
    }

    public void getPokemonByName(String name, DataResult.Callback<Pokemon> callback){
        apiClient.getPokemonByName(name, callback);
    }

    public ListenableFuture<NamedApiResources> getPokemonResourcesWithLimitAndOffset(int limit, int offset){
        return apiClient.getPokemonResourcesWithLimitAndOffset(limit, offset);
    }
}

package com.example.pokedex.data;

import androidx.paging.ListenableFuturePagingSource;

import com.example.pokedex.data.model.NamedApiResource;
import com.example.pokedex.data.model.NamedApiResources;
import com.example.pokedex.data.model.Pokemon;
import com.example.pokedex.data.source.DataSource;
import com.example.pokedex.data.source.PokemonResourcePagingSource;
import com.example.pokedex.data.source.RemoteDataSource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataRepository{
    private final DataSource mRemoteDataSource;
    private final ListenableFuturePagingSource<Integer, NamedApiResource> mPagingResource;

    @Inject
    public DataRepository(DataSource remoteDataSource, ListenableFuturePagingSource<Integer, NamedApiResource> pagingSource) {
        mRemoteDataSource = remoteDataSource;
        mPagingResource = pagingSource;
    }

    public void getPokemonById(int id, DataResult.Callback<Pokemon> callback){
        mRemoteDataSource.getPokemonById(id, callback);
    }

    public void getPokemonByName(String name, DataResult.Callback<Pokemon> callback){
        mRemoteDataSource.getPokemonByName(name, callback);
    }

    public ListenableFuturePagingSource<Integer, NamedApiResource> getPokemonResourcePagingResource(){
        return mPagingResource;
    }
}

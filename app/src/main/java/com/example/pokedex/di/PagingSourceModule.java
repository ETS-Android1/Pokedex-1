package com.example.pokedex.di;

import androidx.paging.ListenableFuturePagingSource;

import com.example.pokedex.data.model.NamedApiResource;
import com.example.pokedex.data.source.PokemonResourcePagingSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.scopes.ViewModelScoped;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class PagingSourceModule {

    @Binds
    @Singleton
    public abstract ListenableFuturePagingSource<Integer, NamedApiResource> providePagingSource(PokemonResourcePagingSource pagingSource);
}

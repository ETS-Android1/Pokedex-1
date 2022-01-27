package com.example.pokedex.di;

import androidx.paging.ListenableFuturePagingSource;

import com.example.pokedex.data.model.NamedApiResource;
import com.example.pokedex.data.source.DataSource;
import com.example.pokedex.data.source.PokemonResourcePagingSource;
import com.example.pokedex.data.source.RemoteDataSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class DataSourceModule {
    @Binds
    @Singleton
    public abstract DataSource provideDataSource(RemoteDataSource remoteDataSource);
}

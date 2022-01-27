package com.example.pokedex.domain.request;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pokedex.data.DataRepository;
import com.example.pokedex.data.DataResult;
import com.example.pokedex.data.model.Pokemon;
import com.example.pokedex.domain.event.Event;

import javax.inject.Inject;

public class SearchPokemonRequest {
    private final MutableLiveData<Event<DataResult<Pokemon>>> mPokemon = new MutableLiveData<>();
    private final DataRepository dataRepository;

    @Inject
    public SearchPokemonRequest(DataRepository dataRepository){
        this.dataRepository = dataRepository;
    }

    public LiveData<Event<DataResult<Pokemon>>> getPokemonLiveData(){
        return mPokemon;
    }

    public void requestPokemonSearch(String pokemonName){
        dataRepository.getPokemonByName(pokemonName, result -> mPokemon.postValue(new Event<>(result)));
    }
}

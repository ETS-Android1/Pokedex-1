package com.example.pokedex.domain.request;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pokedex.data.DataRepository;
import com.example.pokedex.data.DataResult;
import com.example.pokedex.data.model.Pokemon;

import javax.inject.Inject;

public class PokemonDetailRequest {
    private final MutableLiveData<DataResult<Pokemon>> mPokemon = new MutableLiveData<>();
    private final DataRepository dataRepository;

    @Inject
    public PokemonDetailRequest(DataRepository dataRepository){
        this.dataRepository = dataRepository;
    }

    public LiveData<DataResult<Pokemon>> getPokemonLiveData(){
        return mPokemon;
    }

    public void requestPokemonDetail(String pokemonName){
        dataRepository.getPokemonByName(pokemonName, mPokemon::postValue);
    }
}

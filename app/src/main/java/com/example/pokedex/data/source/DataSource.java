package com.example.pokedex.data.source;

import com.example.pokedex.data.DataResult;
import com.example.pokedex.data.model.NamedApiResources;
import com.example.pokedex.data.model.Pokemon;

public interface DataSource {
    void getPokemonById(int id, DataResult.Callback<Pokemon> callback);

    void getPokemonByName(String name, DataResult.Callback<Pokemon> callback);
}

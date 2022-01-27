package com.example.pokedex.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class Pokemon{
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("abilities")
    private List<Ability> abilities;
    @SerializedName("moves")
    private List<Move> moves;
    @SerializedName("stats")
    private List<Stat> stats;
    @SerializedName("types")
    private List<PokemonType> pokemonTypes;

    public Pokemon(int id, String name, List<Ability> abilities, List<Move> moves, List<Stat> stats, List<PokemonType> pokemonTypes) {
        this.id = id;
        this.name = name;
        this.abilities = abilities;
        this.moves = moves;
        this.stats = stats;
        this.pokemonTypes = pokemonTypes;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public List<Stat> getStats() {
        return stats;
    }

    public List<PokemonType> getPokemonTypes() {
        return pokemonTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pokemon pokemon = (Pokemon) o;
        return id == pokemon.id && Objects.equals(name, pokemon.name) && Objects.equals(abilities, pokemon.abilities) && Objects.equals(moves, pokemon.moves) && Objects.equals(stats, pokemon.stats) && Objects.equals(pokemonTypes, pokemon.pokemonTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, abilities, moves, stats, pokemonTypes);
    }
}



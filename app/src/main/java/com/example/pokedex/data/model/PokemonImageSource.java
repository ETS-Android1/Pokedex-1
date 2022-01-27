package com.example.pokedex.data.model;

import java.util.Arrays;
import java.util.List;

public class PokemonImageSource {
    public static final String POKEMON_IMAGE_BASE_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/";
    public static final String IMAGE_FORMAT = ".png";

    public static String getUrl(NamedApiResource pokemonApiResource){
        try{
            return getUrl(extractIdFromPokemonApiResourceUrl(pokemonApiResource.getUrl()));
        } catch(Exception ex){
            return "";
        }
    }

    private static int extractIdFromPokemonApiResourceUrl(String pokemonApiResourceUrl){
        List<String> urlInParts = Arrays.asList(pokemonApiResourceUrl.split("/"));
        int id = Integer.parseInt(urlInParts.get(urlInParts.size() - 1));
        return id;
    }

    public static String getUrl(int id){
        return POKEMON_IMAGE_BASE_URL + id + IMAGE_FORMAT;
    }
}

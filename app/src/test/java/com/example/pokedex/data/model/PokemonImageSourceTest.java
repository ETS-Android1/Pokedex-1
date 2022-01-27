package com.example.pokedex.data.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class PokemonImageSourceTest {
    @Test
    public void getUrlByPokemonApiResource(){
        String apiResourceUrl = "https://pokeapi.co/api/v2/pokemon/2/";
        String expectedImageSourceUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/2.png";

        String actual = PokemonImageSource.getUrl(new NamedApiResource("", apiResourceUrl));

        assertThat(actual, is(expectedImageSourceUrl));
    }

    @Test
    public void getUrlByPokemonApiResource_withInvalidApiResourceUrl(){
        String invalidApiResourceUrl = "https://pokeapi.co/api/v2/";
        String expectedImageSourceUrl = "";

        String actual = PokemonImageSource.getUrl(new NamedApiResource("", invalidApiResourceUrl));

        assertThat(actual, is(expectedImageSourceUrl));
    }

    @Test
    public void getUrlById(){
        int id = 10;
        String expectedImageSourceUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/" + id + ".png";

        String actual = PokemonImageSource.getUrl(id);

        assertThat(actual, is(expectedImageSourceUrl));
    }
}

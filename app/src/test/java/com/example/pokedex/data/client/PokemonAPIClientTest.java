package com.example.pokedex.data.client;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import com.example.pokedex.data.DataResult;
import com.example.pokedex.data.api.PokemonAPIService;
import com.example.pokedex.data.model.Pokemon;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PokemonAPIClientTest {
    @Mock private PokemonAPIService pokemonAPIService;

    @InjectMocks private PokemonAPIClient apiClient;

    @Captor private ArgumentCaptor<Integer> integerCaptor;
    @Captor private ArgumentCaptor<String> stringCaptor;

    @Test
    public void getPokemonResourcesWithLimitAndOffset(){
        int expectedLimit = 5;
        int expectedOffset = 0;

        apiClient.getPokemonResourcesWithLimitAndOffset(expectedLimit, expectedOffset);

        verify(pokemonAPIService)
                .getPokemonResourcesWithLimitAndOffset(integerCaptor.capture(), integerCaptor.capture());
        int actualLimit = integerCaptor.getAllValues().get(0);
        int actualOffset = integerCaptor.getAllValues().get(1);
        assertThat(actualOffset, is(expectedOffset));
        assertThat(actualLimit, is(expectedLimit));
    }

    @Test(expected = NullPointerException.class)
    public void getPokemonById(){
        int expectedId = 3;
        DataResult.Callback<Pokemon> callback = result -> {
        };

        apiClient.getPokemonById(expectedId, callback);

        verify(pokemonAPIService).getPokemonById(integerCaptor.capture());
        int actualId = integerCaptor.getValue();
        assertThat(actualId, is(expectedId));
    }

    @Test(expected = NullPointerException.class)
    public void getPokemonByName(){
        String expectedName = "bulbasaur";
        DataResult.Callback<Pokemon> callback = result -> {
        };

        apiClient.getPokemonByName(expectedName, callback);

        verify(pokemonAPIService).getPokemonByName(stringCaptor.capture());
        String actualName = stringCaptor.getValue();
        assertThat(actualName, is(expectedName));
    }

    @Test
    public void getPokemonByName_nullInput(){
        String expectedName = null;
        DataResult.Callback<Pokemon> callback = result -> {
        };

        apiClient.getPokemonByName(expectedName, callback);

        verifyNoInteractions(pokemonAPIService);
    }

    @Test(expected = NullPointerException.class)
    public void getPokemonByName_convertNameToSmallLetter(){
        String name = "POKEmon";
        String expectedName = name.toLowerCase();
        DataResult.Callback<Pokemon> callback = result -> {
        };

        apiClient.getPokemonByName(expectedName, callback);

        verify(pokemonAPIService).getPokemonByName(stringCaptor.capture());
        String actualName = stringCaptor.getValue();
        assertThat(actualName, is(expectedName));
    }
}

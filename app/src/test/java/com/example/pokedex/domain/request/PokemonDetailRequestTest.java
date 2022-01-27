package com.example.pokedex.domain.request;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.pokedex.data.DataRepository;
import com.example.pokedex.data.DataResult;
import com.example.pokedex.data.ResponseStatus;
import com.example.pokedex.data.model.Pokemon;
import com.example.pokedex.util.LiveDataTestUtil;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PokemonDetailRequestTest {
    @Rule public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock private DataRepository dataRepository;

    @InjectMocks private PokemonDetailRequest pokemonDetailRequest;

    @Captor private ArgumentCaptor<String> stringCaptor;
    @Captor private ArgumentCaptor<DataResult.Callback<Pokemon>> callbackCaptor;

    @Test
    public void requestPokemonDetail(){
        String expectedPokemonName = "pikachu";

        pokemonDetailRequest.requestPokemonDetail(expectedPokemonName);

        verify(dataRepository).getPokemonByName(stringCaptor.capture(), any());
        String actualPokemonName = stringCaptor.getValue();
        assertThat(actualPokemonName, is(expectedPokemonName));
    }

    @Test
    public void requestPokemonDetail_setPokemonLiveData() throws InterruptedException {
        Pokemon pokemon = new Pokemon(0, "pikachu", null, null, null, null);
        ResponseStatus responseStatus = new ResponseStatus(200, true);
        DataResult<Pokemon> expectedDataResult = new DataResult<>(pokemon, responseStatus);

        pokemonDetailRequest.requestPokemonDetail(pokemon.getName());

        verify(dataRepository).getPokemonByName(anyString(), callbackCaptor.capture());
        callbackCaptor.getValue().onResult(new DataResult<>(pokemon, responseStatus));
        DataResult<Pokemon> actualDataResult = LiveDataTestUtil.getValue(pokemonDetailRequest.getPokemonLiveData());
        assertThat(actualDataResult, is(equalTo(expectedDataResult)));
    }
}

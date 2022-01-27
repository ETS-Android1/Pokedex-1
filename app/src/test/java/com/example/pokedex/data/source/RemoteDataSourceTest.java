package com.example.pokedex.data.source;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.pokedex.data.DataResult;
import com.example.pokedex.data.client.PokemonAPIClient;
import com.example.pokedex.data.model.Pokemon;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RemoteDataSourceTest {
    @Mock private PokemonAPIClient mClient;

    @InjectMocks private RemoteDataSource mRemoteDataSource;

    @Captor private ArgumentCaptor<Integer> mIntegerCaptor;

    @Captor private ArgumentCaptor<String> mStringCaptor;

    @Captor private ArgumentCaptor<DataResult.Callback<Pokemon>> mPokemonCallbackCaptor;

    @Test
    public void getPokemonById(){
        int expectedId = 5;
        DataResult.Callback<Pokemon> expectedCallback = result -> {
        };

        mRemoteDataSource.getPokemonById(expectedId, expectedCallback);

        verify(mClient).getPokemonById(
                mIntegerCaptor.capture(), mPokemonCallbackCaptor.capture());
        int actualId = mIntegerCaptor.getValue();
        DataResult.Callback<Pokemon> actualCallback = mPokemonCallbackCaptor.getValue();
        assertThat(actualId, is(expectedId));
        assertThat(actualCallback, is(equalTo(expectedCallback)));
    }

    @Test
    public void getPokemonByName(){
        String expectedName = "charizard";
        DataResult.Callback<Pokemon> expectedCallback = result -> {
        };

        mRemoteDataSource.getPokemonByName(expectedName, expectedCallback);

        verify(mClient).getPokemonByName(
                mStringCaptor.capture(), mPokemonCallbackCaptor.capture());
        String actualName = mStringCaptor.getValue();
        DataResult.Callback<Pokemon> actualCallback = mPokemonCallbackCaptor.getValue();
        assertThat(actualName, is(expectedName));
        assertThat(actualCallback, is(equalTo(expectedCallback)));
    }
}

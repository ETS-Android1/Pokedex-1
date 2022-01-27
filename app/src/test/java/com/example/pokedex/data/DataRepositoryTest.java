package com.example.pokedex.data;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.pokedex.data.model.Pokemon;
import com.example.pokedex.data.source.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DataRepositoryTest {

    @Mock private DataSource mDataSource;

    @InjectMocks private DataRepository mDataRepository;

    @Captor private ArgumentCaptor<Integer> mIntegerCaptor;

    @Captor private ArgumentCaptor<String> mStringCaptor;

    @Captor private ArgumentCaptor<DataResult.Callback<Pokemon>> mPokemonCallbackCaptor;

    @Test
    public void getPokemonById(){
        int expectedId = 5;
        DataResult.Callback<Pokemon> expectedCallback = result -> {
        };

        mDataRepository.getPokemonById(expectedId, expectedCallback);

        verify(mDataSource).getPokemonById(
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

        mDataRepository.getPokemonByName(expectedName, expectedCallback);

        verify(mDataSource).getPokemonByName(
                mStringCaptor.capture(), mPokemonCallbackCaptor.capture());
        String actualName = mStringCaptor.getValue();
        DataResult.Callback<Pokemon> actualCallback = mPokemonCallbackCaptor.getValue();
        assertThat(actualName, is(expectedName));
        assertThat(actualCallback, is(equalTo(expectedCallback)));
    }
}

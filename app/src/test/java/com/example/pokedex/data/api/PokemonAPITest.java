package com.example.pokedex.data.api;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import com.example.pokedex.data.ResponseStatus;
import com.example.pokedex.data.TestingJsonData;
import com.example.pokedex.data.client.PokemonAPIClient;
import com.example.pokedex.data.model.Ability;
import com.example.pokedex.data.model.Move;
import com.example.pokedex.data.model.NamedApiResource;
import com.example.pokedex.data.model.NamedApiResources;
import com.example.pokedex.data.model.Pokemon;
import com.example.pokedex.data.model.PokemonType;
import com.example.pokedex.data.model.Stat;
import com.example.pokedex.data.util.NamedApiResourceJsonDeserializer;
import com.example.pokedex.data.util.PokemonJsonDeserializer;
import com.google.gson.GsonBuilder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import mockwebserver3.MockResponse;
import mockwebserver3.MockWebServer;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.guava.GuavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@RunWith(MockitoJUnitRunner.class)
public class PokemonAPITest {

    private final MockWebServer mockWebServer = new MockWebServer();
    private final String TESTING_URL = mockWebServer.url("/").toString();
    private final int TIMEOUT = 5;
    private final Pokemon TESTING_POKEMON = getTestingPokemon();
    private PokemonAPIClient mClient;

    @Before
    public void setup() throws IOException{
        PokemonAPIService mPokemonAPIService = createPokemonApiService();
        mClient = new PokemonAPIClient(mPokemonAPIService);
        mockWebServer.start();
    }

    private PokemonAPIService createPokemonApiService(){
        return new Retrofit.Builder()
                .baseUrl(TESTING_URL)
                .addCallAdapterFactory(GuavaCallAdapterFactory.create())
                .addConverterFactory(buildGsonConverter())
                .client(new OkHttpClient())
                .build()
                .create(PokemonAPIService.class);
    }

    private GsonConverterFactory buildGsonConverter(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(NamedApiResources.class, new NamedApiResourceJsonDeserializer());
        gsonBuilder.registerTypeAdapter(Pokemon.class, new PokemonJsonDeserializer());
        return GsonConverterFactory.create(gsonBuilder.create());
    }

    @After
    public void tearDown() throws IOException{
        mockWebServer.shutdown();
    }

    private Pokemon getTestingPokemon(){
        int pokemonId = 10;
        String pokemonName = "caterpie";
        List<Ability> abilities = new ArrayList<>();
        abilities.add(new Ability(new NamedApiResource("shield-dust", "https://pokeapi.co/api/v2/ability/19/")));
        abilities.add(new Ability(new NamedApiResource("run-away", "https://pokeapi.co/api/v2/ability/50/")));
        List<Move> moves = new ArrayList<>();
        moves.add(new Move(new NamedApiResource("tackle", "https://pokeapi.co/api/v2/move/33/")));
        moves.add(new Move(new NamedApiResource("string-shot", "https://pokeapi.co/api/v2/move/81/")));
        List<PokemonType> types = new ArrayList<>();
        types.add(new PokemonType(new NamedApiResource("bug", "https://pokeapi.co/api/v2/type/7/")));
        List<Stat> stats = new ArrayList<>();
        stats.add(new Stat(new NamedApiResource("hp", "https://pokeapi.co/api/v2/stat/1/"), 45));
        stats.add(new Stat(new NamedApiResource("attack", "https://pokeapi.co/api/v2/stat/2/"), 30));
        return new Pokemon(pokemonId, pokemonName, abilities, moves, stats, types);
    }

    @Test
    public void getPokemonById_successReturn() {
        String mockedResponseBody = TestingJsonData.ofPokemonInString(TESTING_POKEMON);
        mockSuccessServerResponse(mockedResponseBody);
        AtomicBoolean isTaskFinished = new AtomicBoolean(false);

        mClient.getPokemonById(TESTING_POKEMON.getId(), actualResponse -> {

            assertThat(actualResponse.getResponseStatus().isSuccess(), is(true));
            assertThat(actualResponse.getResult(), is(equalTo(TESTING_POKEMON)));
            isTaskFinished.set(true);
        });

        await().atMost(TIMEOUT, TimeUnit.SECONDS).untilTrue(isTaskFinished);
    }

    @Test
    public void getPokemonById_errorReturn() {
        int expectedErrResponseCode = HttpURLConnection.HTTP_UNAVAILABLE;
        mockErrorResponseFromServer(expectedErrResponseCode);
        AtomicBoolean isTaskFinished = new AtomicBoolean(false);

        mClient.getPokemonById(TESTING_POKEMON.getId(), actualResponse -> {

            ResponseStatus actualResponseStatus = actualResponse.getResponseStatus();
            assertThat(actualResponseStatus.isSuccess(), is(false));
            assertThat(actualResponseStatus.getResponseCode(), is(equalTo(expectedErrResponseCode)));
            assertThat(actualResponseStatus.getResponseCodeMessage(),
                    is(ResponseStatus.getResponseCodeMessage(expectedErrResponseCode)));
            isTaskFinished.set(true);
        });

        await().atMost(TIMEOUT, TimeUnit.SECONDS).untilTrue(isTaskFinished);
    }

    @Test
    public void getPokemonByName_successReturn() {
        String mockedResponseBody = TestingJsonData.ofPokemonInString(TESTING_POKEMON);
        mockSuccessServerResponse(mockedResponseBody);
        AtomicBoolean isTaskFinished = new AtomicBoolean(false);

        mClient.getPokemonByName(TESTING_POKEMON.getName(), actualResponse -> {

            assertThat(actualResponse.getResponseStatus().isSuccess(), is(true));
            assertThat(actualResponse.getResult(), is(equalTo(TESTING_POKEMON)));
            isTaskFinished.set(true);
        });

        await().atMost(TIMEOUT, TimeUnit.SECONDS).untilTrue(isTaskFinished);
    }

    @Test
    public void getPokemonByName_errorReturn() {
        int expectedErrResponseCode = HttpURLConnection.HTTP_UNAVAILABLE;
        mockErrorResponseFromServer(expectedErrResponseCode);
        AtomicBoolean isTaskFinished = new AtomicBoolean(false);

        mClient.getPokemonByName(TESTING_POKEMON.getName(), actualResponse -> {

            ResponseStatus actualResponseStatus = actualResponse.getResponseStatus();
            assertThat(actualResponseStatus.isSuccess(), is(false));
            assertThat(actualResponseStatus.getResponseCode(), is(equalTo(expectedErrResponseCode)));
            assertThat(actualResponseStatus.getResponseCodeMessage(),
                    is(ResponseStatus.getResponseCodeMessage(expectedErrResponseCode)));
            assertThat(actualResponse.getResult(), is(nullValue()));
            isTaskFinished.set(true);
        });

        await().atMost(TIMEOUT, TimeUnit.SECONDS).untilTrue(isTaskFinished);
    }

    private void mockSuccessServerResponse(String responseBody){
        MockResponse mockedResponse = new MockResponse()
                .setBody(responseBody)
                .setResponseCode(HttpURLConnection.HTTP_OK);
        mockWebServer.enqueue(mockedResponse);
    }

    private void mockErrorResponseFromServer(int errorStatusCode){
        MockResponse mockedResponse = new MockResponse()
                .setResponseCode(errorStatusCode);
        mockWebServer.enqueue(mockedResponse);
    }
}

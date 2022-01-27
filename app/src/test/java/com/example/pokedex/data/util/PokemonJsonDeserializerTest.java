package com.example.pokedex.data.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.example.pokedex.data.TestingJsonData;
import com.example.pokedex.data.model.Ability;
import com.example.pokedex.data.model.Move;
import com.example.pokedex.data.model.NamedApiResource;
import com.example.pokedex.data.model.Pokemon;
import com.example.pokedex.data.model.PokemonType;
import com.example.pokedex.data.model.Stat;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class PokemonJsonDeserializerTest {
    @Mock private Type mType;

    @Mock private JsonDeserializationContext mContext;

    private PokemonJsonDeserializer mPokemonJsonDeserializer;

    @Before
    public void setup(){
        mPokemonJsonDeserializer = new PokemonJsonDeserializer();
    }

    @Test
    public void parsePokemonIdFromJson(){
        int expectedPokemonId = 1;
        JsonElement testingJsonData = TestingJsonData.ofPokemonId(expectedPokemonId);

        Pokemon pokemon = mPokemonJsonDeserializer.deserialize(testingJsonData, mType, mContext);

        assertThat(pokemon.getId(), is(expectedPokemonId));
    }

    @Test
    public void parsePokemonNameFromJson(){
        String expectedPokemonName = "bulbasaur";
        JsonElement testingJsonData = TestingJsonData.ofPokemonName(expectedPokemonName);

        Pokemon pokemon = mPokemonJsonDeserializer.deserialize(testingJsonData, mType, mContext);

        assertThat(pokemon.getName(), is(expectedPokemonName));
    }

    @Test
    public void parsePokemonAbilitiesFromJson(){
        Ability expectedAbility1 = new Ability(new NamedApiResource("overgrow", "https://pokeapi.co/api/v2/ability/65/"));
        Ability expectedAbility2 = new Ability(new NamedApiResource("chlorophyll", "https://pokeapi.co/api/v2/ability/34/"));
        List<Ability> expectedAbilityList = new ArrayList<>();
        expectedAbilityList.add(expectedAbility1);
        expectedAbilityList.add(expectedAbility2);
        JsonElement testingJsonData = TestingJsonData.ofPokemonAbilities(expectedAbilityList);

        Pokemon pokemon = mPokemonJsonDeserializer.deserialize(testingJsonData, mType, mContext);

        assertThat(pokemon.getAbilities(), is(equalTo(expectedAbilityList)));
    }

    @Test
    public void parseEmptyAbilityList(){
        List<Ability> emptyList = new ArrayList<>();
        JsonElement testingJsonData = TestingJsonData.ofPokemonAbilities(emptyList);

        Pokemon pokemon = mPokemonJsonDeserializer.deserialize(testingJsonData, mType, mContext);

        assertThat(pokemon.getAbilities().isEmpty(), is(true));
    }

    @Test
    public void parsePokemonMovesFromJson(){
        Move expectedMove1 = new Move(new NamedApiResource("razor-wind", "https://pokeapi.co/api/v2/move/13/"));
        Move expectedMove2 = new Move(new NamedApiResource("tackle", "https://pokeapi.co/api/v2/move/33/"));
        List<Move> expectedMoveList = new ArrayList<>();
        expectedMoveList.add(expectedMove1);
        expectedMoveList.add(expectedMove2);
        JsonElement testingJsonData = TestingJsonData.ofPokemonMoves(expectedMoveList);

        Pokemon pokemon = mPokemonJsonDeserializer.deserialize(testingJsonData, mType, mContext);

        assertThat(pokemon.getMoves(), is(equalTo(expectedMoveList)));
    }

    @Test
    public void parseEmptyPokemonMovesFromJson(){
        List<Move> emptyList = new ArrayList<>();
        JsonElement testingJsonData = TestingJsonData.ofPokemonMoves(emptyList);

        Pokemon pokemon = mPokemonJsonDeserializer.deserialize(testingJsonData, mType, mContext);

        assertThat(pokemon.getMoves().isEmpty(), is(true));
    }

    @Test
    public void parsePokemonStatsFromJson(){
        Stat expectedStat1 = new Stat(new NamedApiResource("hp", "https://pokeapi.co/api/v2/stat/1/"), 45);
        Stat expectedStat2 = new Stat(new NamedApiResource("attack", "https://pokeapi.co/api/v2/stat/2/"), 30);
        List<Stat> expectedStatList = new ArrayList<>();
        expectedStatList.add(expectedStat1);
        expectedStatList.add(expectedStat2);
        JsonElement testingJsonData = TestingJsonData.ofPokemonStats(expectedStatList);

        Pokemon pokemon = mPokemonJsonDeserializer.deserialize(testingJsonData, mType, mContext);

        assertThat(pokemon.getStats(), is(equalTo(expectedStatList)));
    }

    @Test
    public void parsePokemonTypesFromJson(){
        PokemonType expectedPokemonType1 = new PokemonType(new NamedApiResource("grass", "https://pokeapi.co/api/v2/type/12/"));
        PokemonType expectedPokemonType2 = new PokemonType(new NamedApiResource("poison", "https://pokeapi.co/api/v2/type/4/"));
        List<PokemonType> expectedPokemonTypeList = new ArrayList<>();
        expectedPokemonTypeList.add(expectedPokemonType1);
        expectedPokemonTypeList.add(expectedPokemonType2);
        JsonElement testingJsonData = TestingJsonData.ofPokemonTypes(expectedPokemonTypeList);

        Pokemon pokemon = mPokemonJsonDeserializer.deserialize(testingJsonData, mType, mContext);

        assertThat(pokemon.getPokemonTypes(), is(equalTo(expectedPokemonTypeList)));
    }
}

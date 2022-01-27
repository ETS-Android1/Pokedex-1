package com.example.pokedex.data.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.example.pokedex.data.TestingJsonData;
import com.example.pokedex.data.model.NamedApiResource;
import com.example.pokedex.data.model.NamedApiResources;
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
public class NamedApiResourceJsonDeserializerTest {
    @Mock private Type mType;

    @Mock private JsonDeserializationContext mContext;

    private NamedApiResourceJsonDeserializer mNamedApiResourceJsonDeserializer;

    @Before
    public void setup(){
        mNamedApiResourceJsonDeserializer = new NamedApiResourceJsonDeserializer();
    }

    @Test
    public void parseNextApiResourcesUrlFromJson(){
        String expectedNextApiResourcesURL = "https://pokeapi.co/api/v2/pokemon/?offset=3&limit=3";
        JsonElement testingJsonData = TestingJsonData.ofNextApiResouceUrl(expectedNextApiResourcesURL);

        NamedApiResources actualNamedApiResources = mNamedApiResourceJsonDeserializer.deserialize(testingJsonData, mType, mContext);

        assertThat(actualNamedApiResources.getNextApiResourceUrl(), is(equalTo(expectedNextApiResourcesURL)));
    }

    @Test
    public void parsePrevApiResourcesUrlFromJson(){
        String expectedPrevApiResourcesURL = "https://pokeapi.co/api/v2/pokemon/?offset=0&limit=3";
        JsonElement testingJsonData = TestingJsonData.ofPrevApiResourceUrl(expectedPrevApiResourcesURL);

        NamedApiResources actualNamedApiResources = mNamedApiResourceJsonDeserializer.deserialize(testingJsonData, mType, mContext);

        assertThat(actualNamedApiResources.getPrevApiResourceUrl(), is(equalTo(expectedPrevApiResourcesURL)));
    }

    @Test
    public void parseNamedApiResourcesFromJson(){
        NamedApiResource namedApiResource1 = new NamedApiResource("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/");
        NamedApiResource namedApiResource2 = new NamedApiResource("ivysaur", "https://pokeapi.co/api/v2/pokemon/2/");
        NamedApiResource namedApiResource3 = new NamedApiResource("venusaur", "https://pokeapi.co/api/v2/pokemon/3/");
        List<NamedApiResource> expectedNamedApiResourceList = new ArrayList<>();
        expectedNamedApiResourceList.add(namedApiResource1);
        expectedNamedApiResourceList.add(namedApiResource2);
        expectedNamedApiResourceList.add(namedApiResource3);
        JsonElement testingJsonData = TestingJsonData.ofNamedApiResourceList(expectedNamedApiResourceList);

        NamedApiResources actualNamedApiResources = mNamedApiResourceJsonDeserializer.deserialize(testingJsonData, mType, mContext);

        assertThat(actualNamedApiResources.getList(), is(equalTo(expectedNamedApiResourceList)));
    }

    @Test
    public void parseEmptyNamedApiResourceList(){
        List<NamedApiResource> emptyList = new ArrayList<>();
        JsonElement testingJsonData = TestingJsonData.ofNamedApiResourceList(emptyList);

        NamedApiResources actualNamedApiResources = mNamedApiResourceJsonDeserializer.deserialize(testingJsonData, mType, mContext);

        assertThat(actualNamedApiResources.getList().isEmpty(), is(true));
    }
}

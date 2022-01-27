package com.example.pokedex.data.util;

import com.example.pokedex.data.model.Pokemon;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;


public class PokemonJsonDeserializer implements JsonDeserializer<Pokemon> {

    @Override
    public Pokemon deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        try{
            return gson.fromJson(json, Pokemon.class);
        } catch (Exception ex){
            throw new JsonParseException(ex.getMessage(), ex);
        }
    }
}

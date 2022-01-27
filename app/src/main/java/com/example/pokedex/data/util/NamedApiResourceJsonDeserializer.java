package com.example.pokedex.data.util;

import com.example.pokedex.data.model.NamedApiResources;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class NamedApiResourceJsonDeserializer implements JsonDeserializer<NamedApiResources> {

    @Override
    public NamedApiResources deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        try{
            return gson.fromJson(json, NamedApiResources.class);
        } catch(Exception ex){
            throw new JsonParseException(ex.getMessage(), ex);
        }
    }
}

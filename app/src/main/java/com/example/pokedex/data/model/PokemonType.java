package com.example.pokedex.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class PokemonType {
    @SerializedName("type")
    private NamedApiResource typeInfo;

    public PokemonType(NamedApiResource typeInfo) {
        this.typeInfo = typeInfo;
    }

    public NamedApiResource getTypeInfo() {
        return typeInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PokemonType that = (PokemonType) o;
        return Objects.equals(typeInfo, that.typeInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeInfo);
    }
}

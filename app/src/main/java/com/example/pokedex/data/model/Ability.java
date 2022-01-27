package com.example.pokedex.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Ability {
    @SerializedName("ability")
    private NamedApiResource abilityInfo;

    public NamedApiResource getAbilityInfo() {
        return abilityInfo;
    }

    public Ability(NamedApiResource abilityInfo) {
        this.abilityInfo = abilityInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ability ability = (Ability) o;
        return Objects.equals(abilityInfo, ability.abilityInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(abilityInfo);
    }
}

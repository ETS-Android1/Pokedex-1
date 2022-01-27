package com.example.pokedex.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Stat {
    @SerializedName("stat")
    private NamedApiResource statInfo;
    @SerializedName("base_stat")
    private int value;
    public final int maxLimit = 255;

    public Stat(NamedApiResource statInfo, int value) {
        this.statInfo = statInfo;
        this.value = value;
    }

    public NamedApiResource getStatInfo() {
        return statInfo;
    }

    public void setStatInfo(NamedApiResource statInfo) {
        this.statInfo = statInfo;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getMaxLimit() {
        return maxLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stat stat = (Stat) o;
        return value == stat.value && Objects.equals(statInfo, stat.statInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statInfo, value);
    }
}

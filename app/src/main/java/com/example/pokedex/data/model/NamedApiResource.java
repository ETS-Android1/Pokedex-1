package com.example.pokedex.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class NamedApiResource{
    @SerializedName("name")
    private String name;
    @SerializedName("url")
    private String url;

    public NamedApiResource(String name, String url){
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NamedApiResource that = (NamedApiResource) o;
        return Objects.equals(name, that.name) && Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url);
    }
}

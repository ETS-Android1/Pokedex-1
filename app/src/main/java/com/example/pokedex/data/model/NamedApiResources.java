package com.example.pokedex.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class NamedApiResources {
    @SerializedName("results")
    private List<NamedApiResource> namedApiResourceList;
    @SerializedName("next")
    private String nextApiResourceUrl;
    @SerializedName("previous")
    private String prevApiResourceUrl;

    public NamedApiResources(String nextApiResourceUrl, String prevApiResourceUrl, List<NamedApiResource> namedApiResourceList) {
        this.nextApiResourceUrl = nextApiResourceUrl;
        this.prevApiResourceUrl = prevApiResourceUrl;
        this.namedApiResourceList = namedApiResourceList;
    }

    public String getNextApiResourceUrl() {
        return nextApiResourceUrl;
    }

    public String getPrevApiResourceUrl() {
        return prevApiResourceUrl;
    }

    public List<NamedApiResource> getList() {
        return namedApiResourceList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NamedApiResources that = (NamedApiResources) o;
        return Objects.equals(namedApiResourceList, that.namedApiResourceList) && Objects.equals(nextApiResourceUrl, that.nextApiResourceUrl) && Objects.equals(prevApiResourceUrl, that.prevApiResourceUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namedApiResourceList, nextApiResourceUrl, prevApiResourceUrl);
    }
}

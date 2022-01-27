package com.example.pokedex.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Move {
    @SerializedName("move")
    private NamedApiResource moveInfo;

    public Move(NamedApiResource moveInfo) {
        this.moveInfo = moveInfo;
    }

    public NamedApiResource getMoveInfo() {
        return moveInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return Objects.equals(moveInfo, move.moveInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(moveInfo);
    }
}

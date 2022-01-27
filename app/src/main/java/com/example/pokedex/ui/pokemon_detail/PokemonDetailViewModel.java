package com.example.pokedex.ui.pokemon_detail;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.lifecycle.ViewModel;

import com.example.pokedex.data.model.Stat;
import com.example.pokedex.domain.request.PokemonDetailRequest;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class PokemonDetailViewModel extends ViewModel {
    public final PokemonDetailRequest mPokemonDetailRequest;
    public final ObservableField<String> mPokemonName = new ObservableField<>();
    public final ObservableField<String> mPokemonImageUrl = new ObservableField<>();
    public final ObservableField<String> mPokemonAbilities = new ObservableField<>();
    public final ObservableField<String> mPokemonMoves = new ObservableField<>();
    public final ObservableField<String> mPokemonTypes = new ObservableField<>();
    public final ObservableList<Stat> mPokemonStats = new ObservableArrayList<>();
    public final ObservableBoolean isDataLoading = new ObservableBoolean();

    @Inject
    public PokemonDetailViewModel(PokemonDetailRequest mPokemonDetailRequest){
        this.mPokemonDetailRequest = mPokemonDetailRequest;
    }
}
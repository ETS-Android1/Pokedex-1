package com.example.pokedex.ui.pokemon_detail;

import androidx.activity.OnBackPressedCallback;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pokedex.data.model.Pokemon;
import com.example.pokedex.databinding.PokemonDetailFragmentBinding;
import com.example.pokedex.ui.adapter.PokemonStatsAdapter;

import java.util.Objects;
import java.util.stream.Collectors;

import dagger.hilt.android.AndroidEntryPoint;
import kotlin.collections.ArrayDeque;

@AndroidEntryPoint
public class PokemonDetailFragment extends Fragment {

    private PokemonDetailViewModel mViewModel;
    private PokemonDetailFragmentBinding mPokemonDetailFragmentBinding;

    public static PokemonDetailFragment newInstance() {
        return new PokemonDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(PokemonDetailViewModel.class);
        mPokemonDetailFragmentBinding = PokemonDetailFragmentBinding.inflate(inflater, container, false);
        if (savedInstanceState == null){
            String pokemonName = PokemonDetailFragmentArgs.fromBundle(getArguments()).getPokemonName();
            String pokemonImageUrl = PokemonDetailFragmentArgs.fromBundle(getArguments()).getPokemonImageUrl();
            mViewModel.mPokemonName.set(pokemonName);
            mViewModel.mPokemonImageUrl.set(pokemonImageUrl);
            mViewModel.mPokemonDetailRequest.requestPokemonDetail(pokemonName);
        }
        mPokemonDetailFragmentBinding.setViewModel(mViewModel);
        setupStatRecyclerView();
        return mPokemonDetailFragmentBinding.getRoot();
    }

    private void setupStatRecyclerView(){
        RecyclerView recyclerView = mPokemonDetailFragmentBinding.pokemonStatsRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new PokemonStatsAdapter(mViewModel.mPokemonStats));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.isDataLoading.set(true);
        mViewModel.mPokemonDetailRequest.getPokemonLiveData().observe(getViewLifecycleOwner(), result -> {
            if(result.getResponseStatus().isSuccess()){
                Pokemon pokemon = result.getResult();
                mViewModel.mPokemonTypes.set(pokemon.getPokemonTypes().stream().map(x -> x.getTypeInfo().getName()).collect(Collectors.joining(", ")));
                mViewModel.mPokemonMoves.set(pokemon.getMoves().stream().map(x -> x.getMoveInfo().getName()).collect(Collectors.joining(", ")));
                if(mViewModel.mPokemonMoves.get() == null || mViewModel.mPokemonMoves.get().isEmpty()){
                    mViewModel.mPokemonMoves.set("None");
                }
                mViewModel.mPokemonStats.clear();
                mViewModel.mPokemonStats.addAll(pokemon.getStats());
                Objects.requireNonNull(mPokemonDetailFragmentBinding.pokemonStatsRecyclerView
                        .getAdapter()).notifyItemRangeInserted(0, pokemon.getStats().size());
                mViewModel.mPokemonAbilities.set(pokemon.getAbilities().stream().map(x -> x.getAbilityInfo().getName()).collect(Collectors.joining(", ")));
                if(mViewModel.mPokemonAbilities.get() == null || mViewModel.mPokemonAbilities.get().isEmpty()){
                    mViewModel.mPokemonAbilities.set("None");
                }
                mViewModel.isDataLoading.set(false);
            }
        });

        setOnBackPressedCallbackWith(view);
    }

    private void setOnBackPressedCallbackWith(View view){
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                ArrayDeque<NavBackStackEntry> a = Navigation.findNavController(view).getBackQueue();
                Navigation.findNavController(view).popBackStack();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }
}
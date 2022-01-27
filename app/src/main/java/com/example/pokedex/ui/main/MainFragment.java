package com.example.pokedex.ui.main;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pokedex.R;
import com.example.pokedex.data.model.PokemonImageSource;
import com.example.pokedex.databinding.MainFragmentBinding;
import com.example.pokedex.domain.event.EventObserver;
import com.example.pokedex.ui.adapter.NamedApiResourceAdapter;
import com.example.pokedex.ui.adapter.PagingLoadStateAdapter;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainFragment extends Fragment {

    private MainFragmentBinding mFragmentBinding;
    private MainViewModel mViewModel;
    private NamedApiResourceAdapter mPagingAdapter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mFragmentBinding = MainFragmentBinding.inflate(inflater, container, false);
        mFragmentBinding.setView(this);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mFragmentBinding.setViewmodel(mViewModel);
        setupRecyclerView();
        setHasOptionsMenu(true);
        return mFragmentBinding.getRoot();
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = mFragmentBinding.recyclerView;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.grid_column_count)));
        mPagingAdapter = new NamedApiResourceAdapter(new NamedApiResourceAdapter.NamedApiResourceComparator());
        recyclerView.setAdapter(mPagingAdapter.withLoadStateFooter(new PagingLoadStateAdapter(view -> mPagingAdapter.retry())));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.isDataLoading.set(true);
        mViewModel.getLiveData().observe(getViewLifecycleOwner(), pagingData -> {
                    mPagingAdapter.submitData(getViewLifecycleOwner().getLifecycle(), pagingData);
                    mViewModel.isDataLoading.set(false);
                }
        );
        mViewModel.mSearchPokemonRequest.getPokemonLiveData().observe(getViewLifecycleOwner(),
                new EventObserver<>(result ->
                {
                    if (result.getResponseStatus().isSuccess() && result.getResult() != null) {
                        String pokemonImageUrl = PokemonImageSource.getUrl(result.getResult().getId());
                        MainFragmentDirections.ActionToPokemonDetailFragment actionToPokemonDetailFragment
                                = MainFragmentDirections.actionToPokemonDetailFragment();
                        actionToPokemonDetailFragment.setPokemonName(result.getResult().getName());
                        actionToPokemonDetailFragment.setPokemonImageUrl(pokemonImageUrl);
                        NavController navController = Navigation.findNavController(view);
                        navController.navigate(actionToPokemonDetailFragment);

                    } else {
                        String errorMessage = result.getResponseStatus().getErrorMessage();
                        if (errorMessage == null || errorMessage.isEmpty()) {
                            errorMessage = result.getResponseStatus().getResponseCodeMessage();
                        }
                        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                        mViewModel.isDataLoading.set(false);
                    }

                }, getClass().getName()));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
        final SearchView searchView = (SearchView) searchViewItem.getActionView();
        searchView.setQueryHint(getString(R.string.pokemon_search_bar_hint));
        setQueryTextListenerOn(searchView);
    }

    private void setQueryTextListenerOn(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mViewModel.isDataLoading.set(true);
                mViewModel.mSearchPokemonRequest.requestPokemonSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }
}
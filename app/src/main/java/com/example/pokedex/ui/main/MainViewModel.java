package com.example.pokedex.ui.main;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import com.example.pokedex.data.DataRepository;
import com.example.pokedex.data.model.NamedApiResource;
import com.example.pokedex.domain.request.SearchPokemonRequest;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.CoroutineScope;

@HiltViewModel
public class MainViewModel extends ViewModel {
    public final SearchPokemonRequest mSearchPokemonRequest;
    private final LiveData<PagingData<NamedApiResource>> mNamedApiResourceLiveData;
    public final ObservableBoolean isDataLoading = new ObservableBoolean();

    @Inject
    public MainViewModel(DataRepository dataRepository, SearchPokemonRequest searchPokemonRequest){
        mSearchPokemonRequest = searchPokemonRequest;
        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);
        Pager<Integer, NamedApiResource> pager = new Pager<>(
                new PagingConfig(20),
                dataRepository::getPokemonResourcePagingResource);

        mNamedApiResourceLiveData = PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager), viewModelScope);
    }

    public LiveData<PagingData<NamedApiResource>> getLiveData(){
        return mNamedApiResourceLiveData;
    }
}
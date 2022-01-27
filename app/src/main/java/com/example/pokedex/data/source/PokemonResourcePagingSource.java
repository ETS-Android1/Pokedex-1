package com.example.pokedex.data.source;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.ListenableFuturePagingSource;
import androidx.paging.PagingState;

import com.example.pokedex.data.model.NamedApiResource;
import com.example.pokedex.data.model.NamedApiResources;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import retrofit2.HttpException;

public class PokemonResourcePagingSource extends ListenableFuturePagingSource<Integer, NamedApiResource> {
    @NonNull
    private final RemoteDataSource mRemoteDataSource;
    @NonNull
    private final Executor mBgExecutor = Executors.newSingleThreadExecutor();
    private static final String OFFSET_PARAM_NAME_IN_URL = "offset";

    @Inject
    public PokemonResourcePagingSource(@NonNull RemoteDataSource remoteDataSource) {
        mRemoteDataSource = remoteDataSource;
    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, NamedApiResource> pagingState) {
        int pageLoadSize = pagingState.getConfig().pageSize;
        Integer anchorPosition = pagingState.getAnchorPosition();
        if (anchorPosition == null) {
            return null;
        }

        LoadResult.Page<Integer, NamedApiResource> anchorPage = pagingState.closestPageToPosition(anchorPosition);
        if (anchorPage == null) {
            return null;
        }

        Integer prevOffset = anchorPage.getPrevKey();
        if (prevOffset != null) {
            return prevOffset + pageLoadSize;
        }

        Integer nextOffset = anchorPage.getNextKey();
        if (nextOffset != null) {
            return nextOffset - pageLoadSize;
        }

        return null;
    }

    @NonNull
    @Override
    public ListenableFuture<LoadResult<Integer, NamedApiResource>> loadFuture(@NonNull LoadParams<Integer> params) {

        ListenableFuture<LoadResult<Integer, NamedApiResource>> pageFuture =
                Futures.transform(getListenableFutureOfGetPokemonResources(params),
                        this::toLoadResult, mBgExecutor);

        ListenableFuture<LoadResult<Integer, NamedApiResource>> partialLoadResultFuture =
                Futures.catching(pageFuture, HttpException.class,
                        LoadResult.Error::new, mBgExecutor);

        return Futures.catching(partialLoadResultFuture,
                IOException.class, LoadResult.Error::new, mBgExecutor);
    }

    private ListenableFuture<NamedApiResources> getListenableFutureOfGetPokemonResources(LoadParams<Integer> params){
        Integer offset = params.getKey();
        if (offset == null) {
            offset = 0;
        }

        return mRemoteDataSource.getPokemonResourcesWithLimitAndOffset(params.getLoadSize(), offset);
    }

    private LoadResult<Integer, NamedApiResource> toLoadResult(@NonNull NamedApiResources response) {
        List<NamedApiResource> namedApiResourceList = response.getList();
        return new LoadResult.Page<>(namedApiResourceList,
                null, // Only paging forward.
                getNextOffsetFrom(response.getNextApiResourceUrl()),
                LoadResult.Page.COUNT_UNDEFINED,
                LoadResult.Page.COUNT_UNDEFINED);
    }

    private Integer getNextOffsetFrom(String nextApiResourceUrl){
        try{
            return tryGetNextOffsetFrom(nextApiResourceUrl);
        } catch (Exception ex){
            return null;
        }
    }

    private Integer tryGetNextOffsetFrom(String nextApiResourceUrl){
        if (nextApiResourceUrl != null && !nextApiResourceUrl.isEmpty()){
            String[] args = getArgsFromNextApiResourceUrl(nextApiResourceUrl);
            for(String arg : args){
                if(arg.contains(OFFSET_PARAM_NAME_IN_URL)){
                    return parseOffsetFromArg(arg);
                }
            }
        }

        return null;
    }

    private String[] getArgsFromNextApiResourceUrl(String nextApiResourceUrl){
        return nextApiResourceUrl.substring(nextApiResourceUrl.indexOf("?") + 1).split("&");
    }

    private int parseOffsetFromArg(String arg){
        return Integer.parseInt(arg.substring(arg.indexOf("=") + 1));
    }
}

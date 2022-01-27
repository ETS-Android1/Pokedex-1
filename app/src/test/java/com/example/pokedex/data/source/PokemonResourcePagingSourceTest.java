package com.example.pokedex.data.source;

import static junit.framework.TestCase.fail;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingConfig;
import androidx.paging.PagingSource;
import androidx.paging.PagingState;

import com.example.pokedex.data.model.NamedApiResource;
import com.example.pokedex.data.model.NamedApiResources;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@RunWith(MockitoJUnitRunner.class)
public class PokemonResourcePagingSourceTest {

    private final int TIMEOUT = 5;

    @Mock private RemoteDataSource mRemoteDataSource;

    @InjectMocks private PokemonResourcePagingSource mPagingSource;

    @Captor private ArgumentCaptor<Integer> mIntegerCaptor;

    @Test
    public void loadFuture_getPokemonResources() {
        int expectedPageLoadSize = 3;
        int expectedOffset = 0;
        PagingSource.LoadParams<Integer> loadParams =
                new PagingSource.LoadParams.Refresh<>(null, expectedPageLoadSize, true);
        mockRemoteDataSourceSuccessResponse();

        ListenableFuture<PagingSource.LoadResult<Integer, NamedApiResource>> listenableFutureTask =
                mPagingSource.loadFuture(loadParams);
        waitForLoadFutureTaskFinish(listenableFutureTask);

        verify(mRemoteDataSource)
                .getPokemonResourcesWithLimitAndOffset(mIntegerCaptor.capture(), mIntegerCaptor.capture());
        int actualLimit = mIntegerCaptor.getAllValues().get(0);
        int actualOffset = mIntegerCaptor.getAllValues().get(1);
        assertThat(actualOffset, is(expectedOffset));
        assertThat(actualLimit, is(expectedPageLoadSize));
    }

    private void waitForLoadFutureTaskFinish(ListenableFuture<PagingSource.LoadResult<Integer, NamedApiResource>> listenableFutureTask){
        AtomicBoolean isTaskFinished = new AtomicBoolean();
        Futures.addCallback(listenableFutureTask, new FutureCallback<PagingSource.LoadResult<Integer, NamedApiResource>>() {
            @Override
            public void onSuccess(@Nullable PagingSource.LoadResult<Integer, NamedApiResource> result) {
                isTaskFinished.set(true);
            }

            @Override
            public void onFailure(@NonNull Throwable t) {
                isTaskFinished.set(true);
            }
        }, Executors.newSingleThreadExecutor());

        await().atMost(TIMEOUT, TimeUnit.SECONDS).untilTrue(isTaskFinished);
    }

    @Test
    public void loadFuture_onSuccessLoadResult_loadTypeAppend() throws ExecutionException, InterruptedException {
        int expectedPageLoadSize = 3;
        int offset = 3;
        int expectedNextOffset = offset + expectedPageLoadSize;
        PagingSource.LoadResult<Integer, NamedApiResource> expectedResult =
                new PagingSource.LoadResult.Page<>(
                        getMockedNamedApiResourceList(expectedPageLoadSize, offset),
                        null,
                        expectedNextOffset,
                        PagingSource.LoadResult.Page.COUNT_UNDEFINED,
                        PagingSource.LoadResult.Page.COUNT_UNDEFINED);
        mockRemoteDataSourceSuccessResponse();
        PagingSource.LoadParams<Integer> loadParams = new PagingSource.LoadParams.Append<>(offset, expectedPageLoadSize, true);

        PagingSource.LoadResult<Integer, NamedApiResource> actualResult = mPagingSource.loadFuture(loadParams).get();

        assertThat(actualResult, is(equalTo(expectedResult)));
    }

    @Test
    public void loadFuture_onSuccessLoadResult_loadTypeRefresh() throws ExecutionException, InterruptedException {
        int expectedPageLoadSize = 3;
        int offset = 0;
        int expectedNextOffset = offset + expectedPageLoadSize;
        PagingSource.LoadResult<Integer, NamedApiResource> expectedResult =
                new PagingSource.LoadResult.Page<>(getMockedNamedApiResourceList(expectedPageLoadSize, offset),
                        null,
                        expectedNextOffset,
                        PagingSource.LoadResult.Page.COUNT_UNDEFINED,
                        PagingSource.LoadResult.Page.COUNT_UNDEFINED);
        mockRemoteDataSourceSuccessResponse();
        PagingSource.LoadParams<Integer> loadParams = new PagingSource.LoadParams.Refresh<>(null, expectedPageLoadSize, true);

        PagingSource.LoadResult<Integer, NamedApiResource> actualResult = mPagingSource.loadFuture(loadParams).get();

        assertThat(actualResult, is(equalTo(expectedResult)));
    }

    @Test
    public void loadFuture_onSuccessLoadResult_loadTypePrepend() throws ExecutionException, InterruptedException {
        int expectedPageLoadSize = 3;
        int offset = 0;
        int expectedNextOffset = offset + expectedPageLoadSize;
        PagingSource.LoadResult<Integer, NamedApiResource> expectedResult =
                new PagingSource.LoadResult.Page<>(getMockedNamedApiResourceList(expectedPageLoadSize, offset),
                        null,
                        expectedNextOffset,
                        PagingSource.LoadResult.Page.COUNT_UNDEFINED,
                        PagingSource.LoadResult.Page.COUNT_UNDEFINED);
        mockRemoteDataSourceSuccessResponse();
        PagingSource.LoadParams<Integer> loadParams = new PagingSource.LoadParams.Prepend<>(offset, expectedPageLoadSize, true);

        PagingSource.LoadResult<Integer, NamedApiResource> actualResult = mPagingSource.loadFuture(loadParams).get();

        assertThat(actualResult, is(equalTo(expectedResult)));
    }

    private void mockRemoteDataSourceSuccessResponse(){
        doAnswer(invocation -> {
            ExecutorService execService = Executors.newSingleThreadExecutor();
            ListeningExecutorService lExecService = MoreExecutors.listeningDecorator(execService);
            Object[] args = invocation.getArguments();
            int limit = (int) args[0];
            int offset = (int) args[1];
            List<NamedApiResource>  namedApiResourceList = getMockedNamedApiResourceList(limit, offset);
            String nextApiResourceUrl = "https://pokeapi.co/api/v2/pokemon/?offset="+(offset+limit)+"&limit="+limit;
            return lExecService.submit(() -> new NamedApiResources(nextApiResourceUrl, "", namedApiResourceList));
        }).when(mRemoteDataSource).getPokemonResourcesWithLimitAndOffset(anyInt(), anyInt());
    }

    @Test
    public void loadFuture_onErrorLoadResultCallback() {
        int expectedPageLoadSize = 3;
        int nextPageNumber = 3;
        mockRemoteDataSourceErrorResponse();
        PagingSource.LoadParams<Integer> loadParams = new PagingSource.LoadParams.Append<>(nextPageNumber, expectedPageLoadSize, true);

        ListenableFuture<PagingSource.LoadResult<Integer, NamedApiResource>> loadResult = mPagingSource.loadFuture(loadParams);

        AtomicBoolean isTaskFinished = new AtomicBoolean();
        Futures.addCallback(loadResult, new FutureCallback<PagingSource.LoadResult<Integer, NamedApiResource>>() {
            @Override
            public void onSuccess(@Nullable PagingSource.LoadResult<Integer, NamedApiResource> result) {
                fail("LoadFuture_onErrorLoadResultCallback : onFailure should be called instead of onSuccess");
                isTaskFinished.set(true);
            }

            @Override
            public void onFailure(@NonNull Throwable t) {
                isTaskFinished.set(true);
            }
        }, Executors.newSingleThreadExecutor());

        await().atMost(TIMEOUT, TimeUnit.SECONDS).untilTrue(isTaskFinished);
    }

    private void mockRemoteDataSourceErrorResponse(){
        doAnswer(invocation -> {
            ExecutorService execService = Executors.newSingleThreadExecutor();
            ListeningExecutorService lExecService = MoreExecutors.listeningDecorator(execService);
            return lExecService.submit(() -> null);
        }).when(mRemoteDataSource).getPokemonResourcesWithLimitAndOffset(anyInt(), anyInt());
    }

    @Test
    public void getRefreshKeyWithNullAnchorPosition_returnNull(){
        Integer anchorPosition = null;
        int pageLoadSize = 3;
        PagingState<Integer, NamedApiResource> state =
                new PagingState<>(getMockedPagesWithPageLoadSize(pageLoadSize), anchorPosition,
                        new PagingConfig(pageLoadSize), 0);

        Integer actualOffset = mPagingSource.getRefreshKey(state);

        assertThat(actualOffset, is(nullValue()));
    }

    @Test
    public void getRefreshKey_returnLastAccessedPageOffset_FromNextOffset(){
        int pageLoadSize = 3;
        Integer lastAccessedPageItemIndex = 2;
        int expectedOffset = 0;
        PagingState<Integer, NamedApiResource> state =
                new PagingState<>(getMockedPagesWithPageLoadSize(pageLoadSize), lastAccessedPageItemIndex,
                        new PagingConfig(pageLoadSize), 0);

        Integer actualOffset = mPagingSource.getRefreshKey(state);

        assertThat(actualOffset, is(expectedOffset));
    }

    @Test
    public void getRefreshKey_returnLastAccessedPageOffset_FromPrevOffset(){
        int pageLoadSize = 3;
        Integer lastAccessedPageItemIndex = 8;
        int expectedOffset = 6;
        PagingState<Integer, NamedApiResource> state =
                new PagingState<>(getMockedPagesWithPageLoadSize(pageLoadSize), lastAccessedPageItemIndex,
                        new PagingConfig(pageLoadSize), 0);

        Integer actualOffset = mPagingSource.getRefreshKey(state);

        assertThat(actualOffset, is(expectedOffset));
    }

    private List<PagingSource.LoadResult.Page<Integer, NamedApiResource>> getMockedPagesWithPageLoadSize(int pageLoadSize){
        List<PagingSource.LoadResult.Page<Integer, NamedApiResource>> pages = new ArrayList<>();
        int offset = 0;
        PagingSource.LoadResult.Page<Integer, NamedApiResource> page1 =
                new PagingSource.LoadResult.Page<>(getMockedNamedApiResourceList(pageLoadSize, offset),
                        null, offset += pageLoadSize, 0, 0);

        PagingSource.LoadResult.Page<Integer, NamedApiResource> page2 =
                new PagingSource.LoadResult.Page<>(getMockedNamedApiResourceList(pageLoadSize, offset),
                        0, offset += pageLoadSize, 0, 0);

        PagingSource.LoadResult.Page<Integer, NamedApiResource> page3 =
                new PagingSource.LoadResult.Page<>(getMockedNamedApiResourceList(pageLoadSize, offset),
                        offset - pageLoadSize, null, 0, 0);
        pages.add(page1);
        pages.add(page2);
        pages.add(page3);
        return pages;
    }

    private List<NamedApiResource> getMockedNamedApiResourceList(int limit, int offset){
        List<NamedApiResource>  namedApiResourceList = new ArrayList<>();
        for(int i = 1; i <= limit; i++){
            int resourceIndex = offset + i;
            namedApiResourceList.add(new NamedApiResource("bulbasaur", "https://pokeapi.co/api/v2/pokemon/" + resourceIndex +"/"));
        }

        return namedApiResourceList;
    }
}

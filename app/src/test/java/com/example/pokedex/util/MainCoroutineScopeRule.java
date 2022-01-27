package com.example.pokedex.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import kotlin.Deprecated;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.ExperimentalCoroutinesApi;
import kotlinx.coroutines.test.TestCoroutineDispatcher;
import kotlinx.coroutines.test.TestCoroutineScheduler;
import kotlinx.coroutines.test.TestCoroutineScope;
import kotlinx.coroutines.test.TestCoroutineScopeKt;
import kotlinx.coroutines.test.TestDispatchers;

@ExperimentalCoroutinesApi
public final class MainCoroutineScopeRule extends TestWatcher implements TestCoroutineScope {
    @NotNull
    private final TestCoroutineDispatcher dispatcher = new TestCoroutineDispatcher();
    private final TestCoroutineScope coroutineScope;

    protected void starting(@Nullable Description description) {
        super.starting(description);
        TestDispatchers.setMain(Dispatchers.INSTANCE, (CoroutineDispatcher)this.dispatcher);
    }

    protected void finished(@Nullable Description description) {
        super.finished(description);
        this.cleanupTestCoroutines();
        TestDispatchers.resetMain(Dispatchers.INSTANCE);
    }

    @NotNull
    public final TestCoroutineDispatcher getDispatcher() {
        return this.dispatcher;
    }

    public MainCoroutineScopeRule() {
        super();
        Intrinsics.checkNotNullParameter(dispatcher, "dispatcher");
        this.coroutineScope = TestCoroutineScopeKt.TestCoroutineScope((CoroutineContext)dispatcher);
    }

    @NotNull
    public CoroutineContext getCoroutineContext() {
        return this.coroutineScope.getCoroutineContext();
    }

    @NotNull
    public TestCoroutineScheduler getTestScheduler() {
        return this.coroutineScope.getTestScheduler();
    }

    /** @deprecated */
    @Deprecated(
            message = "Please call `runTest`, which automatically performs the cleanup, instead of using this function."
    )
    @ExperimentalCoroutinesApi
    public void cleanupTestCoroutines() {
        this.coroutineScope.cleanupTestCoroutines();
    }
}

/*
 *
 * Copyright 2022 MkSoo01
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.pokedex.data;

import java.util.Objects;

public class DataResult<T> {
    private final T result;
    private final ResponseStatus responseStatus;

    public DataResult(T result, ResponseStatus responseStatus) {
        this.result = result;
        this.responseStatus = responseStatus;
    }

    public T getResult() {
        return result;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataResult<?> that = (DataResult<?>) o;
        return Objects.equals(result, that.result) && Objects.equals(responseStatus, that.responseStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(result, responseStatus);
    }

    public interface Callback<T>{
        void onResult(DataResult<T> t);
    }
}

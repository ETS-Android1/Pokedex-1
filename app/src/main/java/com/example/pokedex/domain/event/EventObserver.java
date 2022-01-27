/*
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
 */

package com.example.pokedex.domain.event;

import androidx.lifecycle.Observer;

public class EventObserver<T> implements Observer<Event<T>> {

    private Listener<T> listener;
    private String scope;

    public EventObserver(Listener<T> listener, String scope) {
        this.listener = listener;
        this.scope = scope;
    }

    @Override
    public void onChanged(Event<T> event) {
        if (event != null){
            T content = event.getContentIfNotHandled(scope);
            if (content != null && listener != null){
                listener.onEventUnhandledContent(content);
            }
        }
    }

    public interface Listener<T> {
        void onEventUnhandledContent(T t);
    }
}

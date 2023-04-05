/*
 *    Copyright 2018-2023 virustotalop
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.clubobsidian.dynamicgui.core.debouncer;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class CaffeineDebouncer<K> implements Debouncer<K> {

    private final Cache<K, Long> cache;

    public CaffeineDebouncer(int debounceTime, @NotNull TimeUnit timeUnit) {
        this(debounceTime, timeUnit, Long.MAX_VALUE);
    }

    public CaffeineDebouncer(int debounceTime, @NotNull TimeUnit timeUnit, long maxCacheSize) {
        this.cache = Caffeine.newBuilder()
                .maximumSize(maxCacheSize)
                .expireAfterWrite(debounceTime, timeUnit)
                .build();
    }


    @Override
    public boolean containsKey(@NotNull K key) {
        return this.cache.getIfPresent(key) != null;
    }

    @Override
    public boolean canCache(@NotNull K key) {
        if (this.containsKey(key)) {
            return false;
        }
        this.cache.put(key, Instant.now().toEpochMilli());
        return true;
    }
}
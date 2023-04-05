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

import org.jetbrains.annotations.NotNull;

public interface Debouncer<K> {

    /**
     * @param key K to check the cache for
     * @return if the cache contains the specified key
     */
    boolean containsKey(@NotNull K key);

    /**
     * @param key K to save for debouncing
     * @return if the key was let through
     */
    boolean canCache(@NotNull K key);

}
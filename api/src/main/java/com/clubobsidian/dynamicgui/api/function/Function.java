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

package com.clubobsidian.dynamicgui.api.function;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.fuzzutil.StringFuzz;
import org.apache.commons.lang3.SerializationException;
import org.apache.commons.lang3.SerializationUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.*;

public abstract class Function implements Cloneable, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1492427006104061443L;

    private static Set<String> loadAliases(String name, Collection<String> aliases) {
        Set<String> aliasSet = new HashSet<>();
        aliasSet.add(name);
        for (String alias : aliases) {
            aliasSet.add(StringFuzz.normalize(alias));
        }
        return Collections.unmodifiableSet(aliasSet);
    }

    private final String name;
    private String data;
    private final boolean async;
    private FunctionOwner owner;
    private final Set<String> aliases;

    public Function(@NotNull String name, @NotNull String[] aliases, boolean async) {
        this(name, Arrays.asList(aliases), async);
    }

    public Function(@NotNull String name, @NotNull Collection<String> aliases, boolean async) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(aliases);
        this.name = StringFuzz.normalize(name);
        this.aliases = loadAliases(this.name, aliases);
        this.async = async;
    }

    public Function(@NotNull String name, boolean async) {
        this(name, new String[0], async);
    }

    public Function(@NotNull String... aliases) {
        this(aliases[0], aliases, false);
    }

    /**
     * Runs the function
     *
     * @param playerWrapper to run the function for
     * @return if the function ran
     * @throws Exception any uncaught exception
     */
    public abstract boolean function(PlayerWrapper<?> playerWrapper) throws Exception;

    /**
     * Gets if the function is an async function, if it should run off the main thread
     *
     * @return if the function is async
     */
    public boolean isAsync() {
        return this.async;
    }

    /**
     * Gets the function name
     *
     * @return the function's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * All the function's aliases
     *
     * @return the function's aliases
     */
    public Set<String> getAliases() {
        return this.aliases;
    }

    /**
     * Gets the functions 'data
     *
     * @return the function's data
     */
    @Nullable
    public String getData() {
        return this.data;
    }

    /**
     * Set the function's data
     *
     * @param data the data to set
     */
    public void setData(@Nullable String data) {
        this.data = data;
    }

    /**
     * The owner of the function
     *
     * @return the function's owner
     */
    public FunctionOwner getOwner() {
        return this.owner;
    }

    /**
     * Set the function's owner
     *
     * @param owner the owner of the function
     * @return false if an owner was already set or else true
     */
    public boolean setOwner(FunctionOwner owner) {
        if (this.owner != null) {
            return false;
        }
        this.owner = owner;
        return true;
    }

    /**
     * Performs a deep clone on the function
     *
     * @return the deep clone of the function
     */
    public Function clone() {
        try {
            return SerializationUtils.clone(this);
        } catch (SerializationException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Function)) return false;
        Function function = (Function) o;
        return async == function.async && name.equals(function.name) && aliases.equals(function.aliases);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, async, aliases);
    }
}
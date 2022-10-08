/*
 *    Copyright 2022 virustotalop and contributors.
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

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    private int index = -1;

    public Function(String name, String[] aliases, boolean async) {
        this(name, Arrays.asList(aliases), async);
    }

    public Function(String name, Collection<String> aliases, boolean async) {
        this.name = StringFuzz.normalize(name);
        this.aliases = loadAliases(this.name, aliases);
        this.async = async;
    }

    public Function(String name, boolean async) {
        this(name, new String[0], async);
    }

    public Function(String... aliases) {
        this(aliases[0], aliases, false);
    }

    public abstract boolean function(PlayerWrapper<?> playerWrapper) throws Exception;

    public boolean isAsync() {
        return this.async;
    }

    public String getName() {
        return this.name;
    }

    public Set<String> getAliases() {
        return this.aliases;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean setOwner(FunctionOwner owner) {
        if (this.owner != null) {
            return false;
        }
        this.owner = owner;
        return true;
    }

    public FunctionOwner getOwner() {
        return this.owner;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

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
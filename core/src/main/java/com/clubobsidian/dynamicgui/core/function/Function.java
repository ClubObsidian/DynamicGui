/*
 *    Copyright 2021 Club Obsidian and contributors.
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
package com.clubobsidian.dynamicgui.core.function;

import com.clubobsidian.dynamicgui.core.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.gui.FunctionOwner;
import com.clubobsidian.fuzzutil.StringFuzz;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

public abstract class Function implements Cloneable, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1492427006104061443L;

    private static String[] normalizeAliases(String[] aliases) {
        for(int i = 0; i < aliases.length; i++) {
            aliases[i] = StringFuzz.normalize(aliases[i]);
        }
        return aliases;
    }

    private final String name;
    private String data;
    private final boolean async;
    private FunctionOwner owner;
    private final String[] aliases;
    private int index = -1;

    public Function(String name, String[] aliases, boolean async) {
        this.name = StringFuzz.normalize(name);
        this.aliases = normalizeAliases(aliases);
        this.async = async;
    }

    public Function(String name, boolean async) {
        this(name, new String[0], async);
    }

    public Function(String... aliases) {
        this(aliases[0], aliases, false);
    }

    public Function(String name) {
        this(name, null);
    }

    public Function(Function function) {
        this(function.getName(), function.getData());
    }

    public abstract boolean function(PlayerWrapper<?> playerWrapper);

    public boolean isAsync() {
        return this.async;
    }

    public String getName() {
        return this.name;
    }

    public String[] getAliases() {
        return this.aliases;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean setOwner(FunctionOwner owner) {
        if(this.owner != null) {
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
        return SerializationUtils.clone(this);
    }
}
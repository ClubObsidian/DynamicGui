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

package com.clubobsidian.dynamicgui.bukkit.registry.replacer;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.registry.replacer.ReplacerRegistry;
import com.clubobsidian.dynamicgui.core.util.ReflectionUtil;
import org.bukkit.OfflinePlayer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PlaceholderApiReplacerRegistry implements ReplacerRegistry {

    private final Method setPlaceHolders;

    public PlaceholderApiReplacerRegistry() {
        this.setPlaceHolders = this.getPlaceholdersMethod();
    }

    @Override
    public String replace(PlayerWrapper<?> playerWrapper, String text) {
        try {
            return (String) this.setPlaceHolders.invoke(null, playerWrapper.getNative(), text);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Method getPlaceholdersMethod() {
        Class<?> clazz = ReflectionUtil.classForName("me.clip.placeholderapi.PlaceholderAPI");
        return ReflectionUtil.getMethodByParams(
                clazz,
                new String[]{"setPlaceholders"},
                OfflinePlayer.class,
                String.class
        );
    }
}
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
package com.clubobsidian.dynamicgui.bukkit.permission;

import com.clubobsidian.dynamicgui.core.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class VaultPermission implements Permission {

    private Class<?> permissionClass;
    private Object permission;

    private Method playerHas;
    private Method playerAdd;
    private Method playerRemove;

    @Override
    public boolean setup() {
        Plugin vault = Bukkit.getServer().getPluginManager().getPlugin("Vault");
        if(vault == null) {
            return false;
        }

        try {
            this.permissionClass = Class.forName("net.milkbowl.vault.permission.Permission");
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        if(this.permissionClass == null) {
            return false;
        }

        this.permission = Bukkit.getServer().getServicesManager().getRegistration(this.permissionClass).getProvider();
        return this.permission != null;
    }

    @Override
    public boolean hasPermission(PlayerWrapper<?> playerWrapper, String permission) {
        if(this.playerHas == null) {
            try {
                this.playerHas = this.permissionClass.getDeclaredMethod("playerHas", String.class, OfflinePlayer.class, String.class);
            } catch(NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
                return false;
            }
        }

        try {
            return (boolean) this.playerHas.invoke(this.permission, null, playerWrapper.getPlayer(), permission);
        } catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addPermission(PlayerWrapper<?> playerWrapper, String permission) {
        if(this.playerAdd == null) {
            try {
                this.playerAdd = this.permissionClass.getDeclaredMethod("playerAdd", String.class, OfflinePlayer.class, String.class);
            } catch(NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
                return false;
            }
        }
        try {
            return (boolean) this.playerAdd.invoke(this.permission, null, playerWrapper.getPlayer(), permission);
        } catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removePermission(PlayerWrapper<?> playerWrapper, String permission) {
        if(this.playerRemove == null) {
            try {
                this.playerRemove = this.permissionClass.getDeclaredMethod("playerRemove", String.class, OfflinePlayer.class, String.class);
            } catch(NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
                return false;
            }
        }

        try {
            return (boolean) this.playerRemove.invoke(this.permission, null, playerWrapper.getPlayer(), permission);
        } catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }
    }
}
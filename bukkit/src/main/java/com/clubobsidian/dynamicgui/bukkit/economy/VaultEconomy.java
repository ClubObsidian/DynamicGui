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

package com.clubobsidian.dynamicgui.bukkit.economy;

import com.clubobsidian.dynamicgui.api.economy.Economy;
import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;

public class VaultEconomy implements Economy {

    private Object economy;
    private Class<?> economyClass;
    private Method getBalanceMethod;
    private Method withdrawPlayerMethod;
    private Method depositPlayerMethod;

    @Override
    public boolean setup() {
        Plugin vault = Bukkit.getServer().getPluginManager().getPlugin("Vault");
        if (vault == null) {
            return false;
        }

        try {
            economyClass = Class.forName("net.milkbowl.vault.economy.Economy");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        if (this.economyClass == null) {
            return false;
        }

        this.economy = Bukkit.getServer().getServicesManager().getRegistration(this.economyClass).getProvider();
        return this.economy != null;
    }

    @Override
    public @NotNull BigDecimal getBalance(@NotNull PlayerWrapper<?> playerWrapper) {
        double balance = -1;
        if (this.getBalanceMethod == null) {
            try {
                this.getBalanceMethod = this.economyClass.getDeclaredMethod("getBalance", OfflinePlayer.class);
                this.getBalanceMethod.setAccessible(true);
            } catch (NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            }
        }

        try {
            balance = (double) this.getBalanceMethod.invoke(this.economy, playerWrapper.getNative());
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return new BigDecimal(balance);
    }

    @Override
    public boolean withdraw(@NotNull PlayerWrapper<?> playerWrapper, @NotNull BigDecimal amt) {
        if (amt.doubleValue() < 0)
            return false;

        if (this.withdrawPlayerMethod == null) {
            try {
                this.withdrawPlayerMethod = this.economyClass.getDeclaredMethod("withdrawPlayer", OfflinePlayer.class, double.class);
                this.withdrawPlayerMethod.setAccessible(true);
            } catch (NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            }
        }

        double amtDouble = amt.doubleValue();
        double balance = this.getBalance(playerWrapper).doubleValue();

        if (balance >= amtDouble) {
            try {
                this.withdrawPlayerMethod.invoke(this.economy, playerWrapper.getNative(), amtDouble);
                return true;
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public boolean deposit(@NotNull PlayerWrapper<?> playerWrapper, @NotNull BigDecimal amt) {
        if (amt.doubleValue() < 0)
            return false;

        if (this.depositPlayerMethod == null) {
            try {
                this.depositPlayerMethod = this.economyClass.getDeclaredMethod("depositPlayer", OfflinePlayer.class, double.class);
                this.depositPlayerMethod.setAccessible(true);
            } catch (NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            }
        }

        try {
            this.depositPlayerMethod.invoke(this.economy, playerWrapper.getNative(), amt.doubleValue());
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return true;
    }
}
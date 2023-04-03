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

package com.clubobsidian.dynamicgui.api.economy;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public interface Economy {

    /**
     * Setup the current economy implementation
     *
     * @return whether the economy was setup
     */
    boolean setup();

    /**
     * Gets the balance of a player
     *
     * @param playerWrapper the player wrapper to get the balance for
     * @return the balance for a given player
     */
    @NotNull BigDecimal getBalance(@NotNull PlayerWrapper<?> playerWrapper);

    /**
     * Withdraw from a player's balance
     *
     * @param playerWrapper the player wrapper to withdraw the balance for
     * @param amt           the amount with withdraw
     * @return whether the transaction was successful
     */
    boolean withdraw(@NotNull PlayerWrapper<?> playerWrapper, @NotNull BigDecimal amt);

    /**
     * Deposit into a player's balance
     *
     * @param playerWrapper the player wrapper to deposit the balance for
     * @param amt           the amount to deposit
     * @return whether the transaction was successful
     */
    boolean deposit(@NotNull PlayerWrapper<?> playerWrapper, @NotNull BigDecimal amt);

}
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

package com.clubobsidian.dynamicgui.core.test.mock.command;

import cloud.commandframework.ArgumentDescription;
import cloud.commandframework.arguments.CommandArgument;
import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.arguments.standard.BooleanArgument;
import com.clubobsidian.dynamicgui.core.test.mock.entity.player.MockPlayer;
import io.leangen.geantyref.TypeToken;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Collection;
import java.util.function.BiFunction;

public class MockPlayerArgument extends CommandArgument {
    public MockPlayerArgument(boolean required, @NonNull String name, @NonNull ArgumentParser parser, @NonNull String defaultValue, @NonNull TypeToken valueType, @Nullable BiFunction suggestionsProvider, @NonNull ArgumentDescription defaultDescription, @NonNull Collection argumentPreprocessors) {
        super(required, name, parser, defaultValue, valueType, suggestionsProvider, defaultDescription, argumentPreprocessors);
    }

    public static <C> MockPlayerArgument.Builder<C> builder(final @NonNull String name) {
        return new MockPlayerArgument.Builder<>(name);
    }

    public static class Builder<C> extends CommandArgument.Builder<C,MockPlayer> {

        protected Builder(@NonNull String name) {
            super(TypeToken.get(MockPlayer.class), name);
        }
    }
}

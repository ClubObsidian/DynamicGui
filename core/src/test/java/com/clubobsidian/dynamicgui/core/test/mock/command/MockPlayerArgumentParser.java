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

import com.clubobsidian.dynamicgui.api.command.cloud.CloudArgument;
import com.clubobsidian.dynamicgui.core.test.mock.entity.player.MockPlayer;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.component.CommandComponent;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.context.CommandInput;
import org.incendo.cloud.parser.ArgumentParseResult;
import org.incendo.cloud.parser.ArgumentParser;
import org.incendo.cloud.parser.ParserDescriptor;

public class MockPlayerArgumentParser<C> implements ArgumentParser<C, MockPlayer> {

    public static <C> @NonNull ParserDescriptor<C, MockPlayer> playerParser() {
        return ParserDescriptor.of(new MockPlayerArgumentParser<>(), MockPlayer.class);
    }


    public static <C> CommandComponent.@NonNull Builder<C, MockPlayer> playerComponent() {
        return CommandComponent.builder(CloudArgument.PLAYER_ARG_NAME, playerParser());
    }


    @Override
    public @NonNull ArgumentParseResult<@NonNull MockPlayer> parse(@NonNull CommandContext<@NonNull C> commandContext,
                                                                   @NonNull CommandInput commandInput) {
        return ArgumentParseResult.success(new MockPlayer());
    }
}

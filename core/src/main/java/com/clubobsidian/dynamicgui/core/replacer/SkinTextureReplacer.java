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

package com.clubobsidian.dynamicgui.core.replacer;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.replacer.Replacer;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SkinTextureReplacer extends Replacer {

    private static final String DEFAULT_SKIN_TEXTURE = "ewogICJ0aW1lc3RhbXAiIDogMTY0ODUzNDUwMjUwOCwKIC" +
            "AicHJvZmlsZUlkIiA6ICJlYzU2MTUzOGYzZmQ0NjFkYWZmNTA4NmIyMjE1NGJjZSIsCiAgInByb2ZpbGVOYW1lIiA" +
            "6ICJBbGV4IiwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4" +
            "dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzFhNGFmNzE4NDU1ZDRhYWI1MjhlN2E2MWY4NmZhMjVlNmEzNjlkM" +
            "Tc2OGRjYjEzZjdkZjMxOWE3MTNlYjgxMGIiCiAgICB9CiAgfQp9";

    private final Cache<UUID, String> textureCache = Caffeine
            .newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build();

    public SkinTextureReplacer(String toReplace) {
        super(toReplace);
    }

    @Override
    public String replacement(String text, PlayerWrapper<?> playerWrapper) {
        UUID uuid = playerWrapper.getUniqueId();
        String texture = this.textureCache.get(uuid, (key) -> playerWrapper.getSkinTexture());
        return texture == null ? DEFAULT_SKIN_TEXTURE : texture;
    }
}

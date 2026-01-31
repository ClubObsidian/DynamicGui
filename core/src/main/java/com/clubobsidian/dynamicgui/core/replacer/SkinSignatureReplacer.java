/*
 *    Copyright 2018-2025 virustotalop
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

public class SkinSignatureReplacer extends Replacer {

    private static final String DEFAULT_SKIN_SIGNATURE = "vF2P5kyi4osnhBPvMbb3xrwHE92AvZMNeVxo7in0vg/t1PHWOarltzngykZ7KJ/e1QekiD7qwOGv6yNjeINMXyG9kA/nB5wHQUDoZWCrf3WxuRqLRlJgBl3AbgIOEF6iqhXdwfdCZkumX+FmtUcesB3fRY5j+tMWNfu6nnJW2qVIOrand40oQi87NwTIXJY/9qXcxAJxUV4JLFDr06Xl6ZSGcRlZXVSKfjB4oaFeYFVs1554AW832Jmhb4UAgJkop4/+X1SU2kjCw928uxcybdU5f0toQGqgh2wzxs+0doz1YQIfjtgmW0HSwqVte6LuKKlJO9PcKejlIaHCAovs7AV/lG+yoMg9DiZKhA5cBgEWh2C7K2q6MvM+AHnC9vf3WbT8KPazu5c7gNxV9zI8yqfcTSIei2H4yk2milwiL5l0p4g6Q8wsbYZnuDdhMEuJh0VxMyNmDDuMUl9bwniMfHCWH76dmjNqYK4gQL9Rb34QgxWLgvkJBG8PPbBE/Ef8uvAE2YAsnWbr96MGqc5o1BdU7BR+kPgtkay9m7rvf3dKTZaqGIGbdVPOus9aRgwDanzDKpdpF1IHKLB5I+aHakznOgazjssX1O1sCdHK+SlvxC1vQbwwEI8QoBaXNxfMpbPyxbVXyJL+rsFFckmvN9cjH9InsHU/YgNcou5Z5pc=";

    private final Cache<UUID, String> textureCache = Caffeine
            .newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build();

    public SkinSignatureReplacer(String toReplace) {
        super(toReplace);
    }

    @Override
    public String replacement(String text, PlayerWrapper<?> playerWrapper) {
        UUID uuid = playerWrapper.getUniqueId();
        String texture = this.textureCache.get(uuid, (key) -> playerWrapper.getSkinSignature());
        return texture == null ? DEFAULT_SKIN_SIGNATURE : texture;
    }
}

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

package com.clubobsidian.dynamicgui.bukkit.util;

import com.mojang.brigadier.StringReader;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryOps;
import org.bukkit.craftbukkit.CraftRegistry;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public final class BukkitDataComponentUtil {

    private BukkitDataComponentUtil() {
        throw new UnsupportedOperationException("Cannot instantiate utility class");
    }


    public static ItemStack setComponents(ItemStack bukkitItemStack, Map<String, String> components) {
        try {
            NbtOps nbtOpsInstance = NbtOps.INSTANCE;
            RegistryAccess registry = CraftRegistry.getMinecraftRegistry();
            RegistryOps<Tag> registryOps = registry.createSerializationContext(nbtOpsInstance);
            net.minecraft.world.item.ItemStack nmsItemStack = CraftItemStack.unwrap(bukkitItemStack);
            for (Map.Entry<String, String> entry : components.entrySet()) {
                String entryKey = (entry.getKey().contains(":") ? entry.getKey() : "minecraft:" + entry.getKey())
                        .toLowerCase(Locale.ROOT);
                String entryValue = entry.getValue();
                StringReader typeStringReader = new StringReader(entryKey);
                Identifier identifier = Identifier.read(typeStringReader);
                set(nmsItemStack, identifier, registryOps, entryValue);
            }
            return nmsItemStack.getBukkitStack();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bukkitItemStack;
    }

    private static <T> void set(net.minecraft.world.item.ItemStack nmsItemStack,
                         Identifier identifier,
                         RegistryOps<Tag> registryOps,
                         String entryValue) {
        try {
            DataComponentType<T> type = (DataComponentType<T>) BuiltInRegistries
                    .DATA_COMPONENT_TYPE
                    .get(identifier)
                    .get()
                    .value();
            TagParser<Tag> tagParser = TagParser.create(registryOps);
            Tag tag = tagParser.parseFully(entryValue);
            T result = Objects.requireNonNull(type.codec()).parse(registryOps, tag).getOrThrow();
            nmsItemStack.set(type, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> getComponents(ItemStack bukkitItemStack) {
        try {
            Map<String, String> componentMap = new LinkedHashMap<>();
            NbtOps nbtOpsInstance = NbtOps.INSTANCE;
            RegistryAccess registry = CraftRegistry.getMinecraftRegistry();
            RegistryOps<Tag> registryOps = registry.createSerializationContext(nbtOpsInstance);
            net.minecraft.world.item.ItemStack nmsItemStack = CraftItemStack.unwrap(bukkitItemStack);
            for (TypedDataComponent<?> component : nmsItemStack.getComponents()) {
                DataComponentType<?> componentType = component.type();
                Object componentValue = component.value();
                Codec codec = componentType.codec();
                DataResult result = codec.encodeStart(registryOps, componentValue);
                Tag tag = (Tag) result.getOrThrow();
                Component chatComponent = NbtUtils.toPrettyComponent(tag);
                Identifier identifier = BuiltInRegistries.DATA_COMPONENT_TYPE.getKey(componentType);
                String key = identifier.toString();
                String value = chatComponent.getString();
                componentMap.put(key, value);
            }
            return componentMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
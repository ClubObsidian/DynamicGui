package com.clubobsidian.dynamicgui.bukkit.util;

import com.clubobsidian.dynamicgui.core.util.ReflectionUtil;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public final class BukkitDataComponentUtil {

    private static final Class<?> DATA_COMPONENT_HOLDER = ReflectionUtil
            .getClassIfExists("net.minecraft.core.component.DataComponentHolder");
    private static final Class<?> DATA_COMPONENT_TYPE = ReflectionUtil
            .getClassIfExists("net.minecraft.core.component.DataComponentType");
    private static final Class<?> REGISTRY_ACCESS = ReflectionUtil
            .getClassIfExists("net.minecraft.core.RegistryAccess");
    private static final Class<?> REGISTRY = ReflectionUtil
            .getClassIfExists("net.minecraft.core.Registry");
    private static final Class<?> REGISTRY_OPS = ReflectionUtil
            .getClassIfExists("net.minecraft.resources.RegistryOps");
    private static final Class<?> LOOKUP_PROVIDER = ReflectionUtil
            .getClassIfExists("net.minecraft.core.HolderLookup$Provider");
    private static final Class<?> MINECRAFT_SERVER = ReflectionUtil
            .getClassIfExists("net.minecraft.server.MinecraftServer");
    private static final Class<?> NBT_OPS = ReflectionUtil
            .getClassIfExists("net.minecraft.nbt.NbtOps");
    private static final Class<?> RESOURCE_LOCATION = ReflectionUtil
            .getClassIfExists("net.minecraft.resources.ResourceLocation");
    private static final Class<?> STRING_READER = ReflectionUtil
            .getClassIfExists("com.mojang.brigadier.StringReader");
    private static final Class<?> BUILT_IN_REGISTRIES = ReflectionUtil
            .getClassIfExists("net.minecraft.core.registries.BuiltInRegistries");
    private static final Class<?> TAG_PARSER = ReflectionUtil
            .getClassIfExists("net.minecraft.nbt.TagParser");
    private static final Class<?> CRAFT_ITEM_STACK = ReflectionUtil
            .getClassIfExists("org.bukkit.craftbukkit.inventory.CraftItemStack");
    private static final Class<?> NMS_ITEM_STACK = ReflectionUtil
            .getClassIfExists("net.minecraft.world.item.ItemStack");
    private static final Class<?> DECODER = ReflectionUtil
            .getClassIfExists("com.mojang.serialization.Decoder");
    private static final Class<?> DATA_RESULT = ReflectionUtil
            .getClassIfExists("com.mojang.serialization.DataResult");
    private static final Class<?> TYPED_DATA_COMPONENT = ReflectionUtil
            .getClassIfExists("net.minecraft.core.component.TypedDataComponent");
    private static final Class<?> NBT_UTILS = ReflectionUtil
            .getClassIfExists("net.minecraft.nbt.NbtUtils");
    private static final Class<?> COMPONENT = ReflectionUtil
            .getClassIfExists("net.minecraft.network.chat.Component");
    private static final Class<?> TAG = ReflectionUtil
            .getClassIfExists("net.minecraft.nbt.Tag");

    private static final Field NBT_OPS_INSTANCE =
            ReflectionUtil.getDeclaredField(NBT_OPS, "INSTANCE");
    private static final Field DATA_COMPONENT_TYPE_FIELD =
            ReflectionUtil.getDeclaredField(BUILT_IN_REGISTRIES, "DATA_COMPONENT_TYPE");

    private static final Method GET_REGISTRY_ACCESS = ReflectionUtil
            .getStaticMethod(MINECRAFT_SERVER, REGISTRY_ACCESS);
    private static final Method REGISTRY_CREATE_CONTEXT = ReflectionUtil
            .getMethod(LOOKUP_PROVIDER, "createSerializationContext");
    private static final Method UNWRAP = ReflectionUtil
            .getStaticMethod(CRAFT_ITEM_STACK, NMS_ITEM_STACK, ItemStack.class);
    private static final Method SET = ReflectionUtil
            .getMethod(NMS_ITEM_STACK, "set");
    private static final Method READ = ReflectionUtil
            .getStaticMethod(RESOURCE_LOCATION, RESOURCE_LOCATION, STRING_READER);
    private static final Method REGISTRY_GET = ReflectionUtil
            .getMethod(REGISTRY, "get");
    private static final Method CODEC_OR_THROW = ReflectionUtil
            .getMethod(DATA_COMPONENT_TYPE, "codecOrThrow");
    private static final Method PARSE = ReflectionUtil
            .getMethod(DECODER, "parse");
    private static final Method DATA_RESULT_GET_OR_THROW = ReflectionUtil
            .getMethod(DATA_RESULT, "getOrThrow");
    private static final Method GET_BUKKIT_STACK = ReflectionUtil
            .getMethod(NMS_ITEM_STACK, "getBukkitStack");
    private static final Method GET_COMPONENTS = ReflectionUtil
            .getMethod(NMS_ITEM_STACK, "getComponents");
    private static final Method TYPED_DATA_COMPONENT_TYPE = ReflectionUtil
            .getMethod(TYPED_DATA_COMPONENT, "type");
    private static final Method TYPED_DATA_COMPONENT_VALUE = ReflectionUtil
            .getMethod(TYPED_DATA_COMPONENT, "value");
    private static final Method ENCODE_VALUE = ReflectionUtil
            .getMethod(TYPED_DATA_COMPONENT, "encodeValue");
    private static final Method GET_KEY = ReflectionUtil
            .getMethod(REGISTRY, "getKey");
    private static final Method ENCODE_START = ReflectionUtil
            .getMethod(DATA_RESULT, "encodeStart");
    private static final Method TO_PRETTY_COMPONENT = ReflectionUtil
            .getStaticMethod(NBT_UTILS, COMPONENT, TAG);
    private static final Method COMPONENT_GET_STRING = ReflectionUtil
            .getMethod(COMPONENT, "getString");

    private static final Constructor<?> STRING_READER_CON =
            ReflectionUtil.getConstructor(STRING_READER, String.class);
    private static final Constructor<?> TAG_CON =
            ReflectionUtil.getConstructor(TAG_PARSER, STRING_READER);

    private BukkitDataComponentUtil() {
        throw new UnsupportedOperationException("Cannot instantiate utility class");
    }

    public static boolean usesDataComponents() {
        return DATA_COMPONENT_TYPE != null;
    }

    public static ItemStack setComponents(ItemStack bukkitItemStack, Map<String, String> components) {
        if (!usesDataComponents()) {
            return bukkitItemStack;
        }
        try {
            Object nbtOpsInstance = Objects.requireNonNull(NBT_OPS_INSTANCE).get(null);
            Object registry = Objects.requireNonNull(GET_REGISTRY_ACCESS).invoke(null);
            Object ops = Objects.requireNonNull(REGISTRY_CREATE_CONTEXT).invoke(registry, nbtOpsInstance);
            Object nmsItemStack = Objects.requireNonNull(UNWRAP).invoke(null, bukkitItemStack);
            for (Map.Entry<String, String> entry : components.entrySet()) {
                String entryKey = (entry.getKey().contains(":") ? entry.getKey() : "minecraft:" + entry.getKey())
                        .toLowerCase(Locale.ROOT);
                String entryValue = entry.getValue();
                Object typeStringReader = Objects.requireNonNull(STRING_READER_CON).newInstance(entryKey);
                Object resourceLocation = Objects.requireNonNull(READ).invoke(null, typeStringReader);
                Object dataComponentRegistry = Objects
                        .requireNonNull(DATA_COMPONENT_TYPE_FIELD)
                        .get(BUILT_IN_REGISTRIES);
                Object componentType = Objects
                        .requireNonNull(REGISTRY_GET)
                        .invoke(dataComponentRegistry, resourceLocation);
                Object tag = Objects
                        .requireNonNull(TAG_CON)
                        .newInstance(STRING_READER_CON.newInstance(entryValue));
                Object codec = Objects
                        .requireNonNull(CODEC_OR_THROW).invoke(componentType);
                Object parsed = Objects
                        .requireNonNull(PARSE)
                        .invoke(codec, ops, tag);
                Object result = Objects
                        .requireNonNull(DATA_RESULT_GET_OR_THROW)
                        .invoke(parsed);
                Objects.requireNonNull(SET).invoke(nmsItemStack, componentType, result);
            }
            return (ItemStack) Objects.requireNonNull(GET_BUKKIT_STACK).invoke(nmsItemStack);
        } catch (IllegalAccessException
                 | InvocationTargetException
                 | NullPointerException
                 | InstantiationException
                 | IllegalStateException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, String> getComponents(ItemStack bukkitItemStack) {
        try {
            Map<String, String> componentMap = new LinkedHashMap<>();
            Object nbtOpsInstance = Objects.requireNonNull(NBT_OPS_INSTANCE).get(null);
            Object registry = Objects.requireNonNull(GET_REGISTRY_ACCESS).invoke(null);
            Object ops = Objects.requireNonNull(REGISTRY_CREATE_CONTEXT).invoke(registry, nbtOpsInstance);
            Object dataComponentRegistry = Objects
                    .requireNonNull(DATA_COMPONENT_TYPE_FIELD)
                    .get(BUILT_IN_REGISTRIES);
            Object nmsItemStack = Objects.requireNonNull(UNWRAP).invoke(null, bukkitItemStack);
            Iterable<?> components = (Iterable<?>) Objects.requireNonNull(GET_COMPONENTS).invoke(nmsItemStack);
            for (Object component : components) {
                Object componentType = Objects.requireNonNull(TYPED_DATA_COMPONENT_TYPE).invoke(component);
                Object componentValue = Objects.requireNonNull(TYPED_DATA_COMPONENT_VALUE).invoke(component);
                Object codec = Objects.requireNonNull(CODEC_OR_THROW).invoke(componentType);
                Object dataResult = Objects.requireNonNull(ENCODE_START).invoke(codec, ops, componentValue);
                Object tag = Objects.requireNonNull(DATA_RESULT_GET_OR_THROW).invoke(dataResult);
                Object chatComponent = TO_PRETTY_COMPONENT.invoke(tag);
                Object typeLocation = GET_KEY.invoke(dataComponentRegistry, componentType);
                String key = typeLocation.toString();
                String value = (String) COMPONENT_GET_STRING.invoke(chatComponent);
                componentMap.put(key, value);
            }
            return componentMap;
        } catch (IllegalAccessException | InvocationTargetException | NullPointerException e) {
            throw new RuntimeException(e);
        }
    }
}

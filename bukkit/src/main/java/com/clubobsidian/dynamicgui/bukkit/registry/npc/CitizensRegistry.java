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

package com.clubobsidian.dynamicgui.bukkit.registry.npc;

import com.clubobsidian.dynamicgui.api.entity.EntityWrapper;
import com.clubobsidian.dynamicgui.core.registry.npc.NPC;
import com.clubobsidian.dynamicgui.core.registry.npc.NPCMeta;
import com.clubobsidian.dynamicgui.core.registry.npc.NPCRegistry;
import com.clubobsidian.dynamicgui.core.util.ReflectionUtil;
import org.bukkit.entity.Entity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CitizensRegistry implements NPCRegistry {
    private static final String PLUGIN_NAME = "Citizens";

    private final Class<?> npcClass;
    private Method getNPCRegistryMethod;
    private final Method getNPCMethod;
    private final Method getIdMethod;
    private final Object npcRegistry;

    public CitizensRegistry() {
        this.npcClass = ReflectionUtil.classForName("net.citizensnpcs.api.npc.NPC");
        this.npcRegistry = this.getNPCRegistry();
        this.getIdMethod = ReflectionUtil.getMethod(this.npcClass, "getId");
        this.getNPCMethod = ReflectionUtil.getMethodByParams(
                this.npcRegistry.getClass(),
                "getNPC", Entity.class
        );
    }

    private Object getNPCRegistry() {
        Class<?> citizensApiClass = ReflectionUtil.classForName("net.citizensnpcs.api.CitizensAPI");
        this.getNPCRegistryMethod = ReflectionUtil.getMethod(citizensApiClass, "getNPCRegistry");

        try {
            return this.getNPCRegistryMethod.invoke(null);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String getName() {
        return PLUGIN_NAME;
    }

    @Override
    public boolean isNPC(EntityWrapper<?> entityWrapper) {
        return this.getNPC(entityWrapper) != null;
    }

    @Override
    public NPC getNPC(EntityWrapper<?> entityWrapper) {
        try {
            Object npc = this.getNPCMethod.invoke(this.npcRegistry, entityWrapper.getEntity());
            if (npc == null) {
                return null;
            }

            int id = (int) this.getIdMethod.invoke(npc);
            NPCMeta meta = new NPCMeta(id, CitizensRegistry.PLUGIN_NAME);
            return new NPC(entityWrapper, meta);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
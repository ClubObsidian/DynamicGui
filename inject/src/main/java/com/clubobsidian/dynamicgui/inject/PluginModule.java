package com.clubobsidian.dynamicgui.inject;

import com.clubobsidian.dynamicgui.api.manager.entity.EntityManager;
import com.clubobsidian.dynamicgui.api.manager.inventory.InventoryManager;
import com.clubobsidian.dynamicgui.api.manager.inventory.ItemStackManager;
import com.clubobsidian.dynamicgui.api.manager.material.MaterialManager;
import com.clubobsidian.dynamicgui.api.manager.world.LocationManager;
import com.clubobsidian.dynamicgui.cloud.CloudManager;
import com.google.inject.Module;

public abstract class PluginModule implements Module {

    public abstract Class<? extends EntityManager> getEntityManager();

    public abstract Class<? extends InventoryManager> getInventoryManager();

    public abstract Class<? extends ItemStackManager> getItemStackManager();

    public abstract Class<? extends MaterialManager> getMaterialManager();

    public abstract Class<? extends LocationManager> getLocationManger();

    public abstract Class<? extends CloudManager> getCloudManager();

}

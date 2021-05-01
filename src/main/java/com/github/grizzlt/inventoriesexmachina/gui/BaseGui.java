package com.github.grizzlt.inventoriesexmachina.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseGui implements InventoryHolder
{
    /**
     * The inventory of this gui
     */
    protected Inventory inventory;

    /**
     * Whether this gui is updating (as invoked by {@link #update()}), true if this is the case, false otherwise. This
     * is used to indicate that inventory close events due to updating should be ignored.
     */
    private boolean isUpdating = false;

    /**
     * Listeners should only be registered once per plugin using the framework
     */
    private static boolean listenersHaveBeenRegistered = false;

    public BaseGui()
    {
        if (!listenersHaveBeenRegistered) {
            Bukkit.getPluginManager().registerEvents(new GuiListener(), JavaPlugin.getProvidingPlugin(getClass()));
            listenersHaveBeenRegistered = true;
        }
    }

    /**
     * Creates a new inventory defined by the implementing class
     * @return the new inventory
     */
    public abstract Inventory createInventory();

    /**
     * Shows this gui to a player
     * @param humanEntity
     */
    public abstract void show(HumanEntity humanEntity);

    public abstract void onClick(InventoryClickEvent event);

    public List<HumanEntity> getViewers()
    {
        return new ArrayList<>(getInventory().getViewers());
    }

    /**
     * Update the gui for everyone
     */
    public void update() {
        this.isUpdating = true;

        getViewers().forEach(this::show);

        if (!isUpdating)
            throw new AssertionError("Gui#isUpdating became false before Gui#update finished");

        isUpdating = false;
    }

    @Override
    public Inventory getInventory()
    {
        if (this.inventory == null) {
            inventory = createInventory();
        }

        return inventory;
    }

    /**
     * To check whether this gui is updating or not
     * @return
     */
    public boolean isUpdating()
    {
        return this.isUpdating;
    }
}

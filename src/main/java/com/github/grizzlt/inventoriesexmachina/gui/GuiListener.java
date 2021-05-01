package com.github.grizzlt.inventoriesexmachina.gui;

import com.github.grizzlt.inventoriesexmachina.gui.util.DefaultInventoryToGuiUtil;
import com.github.grizzlt.inventoriesexmachina.gui.util.InventoryToGuiUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GuiListener implements Listener
{
    private static InventoryToGuiUtil inventoryToGuiUtil = new DefaultInventoryToGuiUtil();

    public static void setInventoryToGuiUtil(InventoryToGuiUtil util)
    {
        inventoryToGuiUtil = util;
    }

    public static InventoryToGuiUtil getInventoryToGuiUtil()
    {
        return inventoryToGuiUtil;
    }

    private final Set<BaseGui> activeBaseGuiSet = new HashSet<>();

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event)
    {
        BaseGui gui = inventoryToGuiUtil.getGui(event.getInventory());

        if (gui == null) return;

        InventoryView view = event.getView();
        Inventory inventory = view.getInventory(event.getRawSlot());
        if (inventory == null) return;

        gui.onClick(event);
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryOpen(InventoryOpenEvent event)
    {
        BaseGui gui = inventoryToGuiUtil.getGui(event.getInventory());
        if (gui == null) return;

        this.activeBaseGuiSet.add(gui);
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClose(InventoryCloseEvent event)
    {
        BaseGui gui = inventoryToGuiUtil.getGui(event.getInventory());
        if (gui == null) return;

        this.activeBaseGuiSet.remove(gui);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPluginDisable(PluginDisableEvent event)
    {
        Plugin thisPlugin = JavaPlugin.getProvidingPlugin(getClass());
        if (event.getPlugin() != thisPlugin) return;

        for (BaseGui gui : new ArrayList<>(activeBaseGuiSet)) {
            Bukkit.getScheduler().runTask(thisPlugin, () -> {
                for (HumanEntity viewer : gui.getViewers()) {
                    viewer.closeInventory();
                }
            });
        }
    }
}

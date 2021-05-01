package com.github.grizzlt.inventoriesexmachina.gui.util;

import com.github.grizzlt.inventoriesexmachina.gui.BaseGui;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class DefaultInventoryToGuiUtil implements InventoryToGuiUtil
{
    @Override
    public BaseGui getGui(Inventory inventory)
    {
        InventoryHolder holder = inventory.getHolder();

        if (holder instanceof BaseGui) {
            return (BaseGui) holder;
        }

        return null;
    }
}

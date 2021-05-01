package com.github.grizzlt.inventoriesexmachina.gui.util;

import com.github.grizzlt.inventoriesexmachina.gui.BaseGui;
import org.bukkit.inventory.Inventory;

public interface InventoryToGuiUtil
{
    /**
     * If an inventory linked with a custom inventory, return the instance of that BaseGui
     *
     * @param inventory the inventory we want our {@link BaseGui} from
     * @return {@link BaseGui} that is associated with the inventory or null if it's not associated with a custom Gui
     */
    BaseGui getGui(Inventory inventory);
}

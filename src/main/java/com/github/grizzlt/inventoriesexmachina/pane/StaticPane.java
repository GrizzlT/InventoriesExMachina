package com.github.grizzlt.inventoriesexmachina.pane;

import com.github.grizzlt.inventoriesexmachina.gui.BaseGui;
import com.github.grizzlt.inventoriesexmachina.gui.GuiItem;
import com.github.grizzlt.inventoriesexmachina.gui.InventoryComponent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class StaticPane extends Pane
{
    private final Map<Integer, GuiItem> items = new HashMap<>();

    protected StaticPane(int x, int y, int width, int height)
    {
        super(x, y, width, height);
    }

    @Override
    public void renderToComponent(InventoryComponent inventoryComponent, int offsetX, int offsetY)
    {
        this.items.entrySet().stream().filter(entry -> entry.getValue().isVisible())
                .forEach(entry -> {
                    int itemX = entry.getKey() % this.width;
                    int itemY = (entry.getKey() - itemX) / width;

                    GuiItem item = entry.getValue();
                    inventoryComponent.setItem(item, this.x + itemX + offsetX, this.y + itemY + offsetY);
                });
    }

    @Override
    public boolean onClick(BaseGui gui, InventoryComponent inventoryComponent, InventoryClickEvent event, int offsetX, int offsetY)
    {
        ItemStack itemStack = event.getCurrentItem();
        if (itemStack == null) return false;

        GuiItem guiItem = findMatchingItem(this.items.values(), itemStack);
        if (guiItem == null) return false;

        guiItem.callAction(gui, event);

        return true;
    }

    public void addItem(GuiItem item, int x, int y)
    {
        this.items.put(y * this.width + x, item);
    }

    public void removeItem(GuiItem item)
    {
        this.items.values().removeIf(value -> value.getUuid().equals(item.getUuid()));
    }

    public void clear()
    {
        this.items.clear();
    }
}

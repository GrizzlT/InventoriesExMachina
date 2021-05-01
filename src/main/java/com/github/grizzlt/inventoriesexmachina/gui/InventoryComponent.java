package com.github.grizzlt.inventoriesexmachina.gui;

import com.github.grizzlt.inventoriesexmachina.pane.Pane;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InventoryComponent
{
    protected final List<Pane> panes = new ArrayList<>();

    private final int width, height;
    private final ItemStack[] items;

    /**
     * This should be the size of the inventory, necessary for correct event handling
     * @param width
     * @param height
     */
    public InventoryComponent(int width, int height)
    {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Sizes must be greater or equal to zero");
        }

        this.width = width;
        this.height = height;

        this.items = new ItemStack[width * height];
    }

    /**
     * Adds a new pane to the gui, FIFO idea, i.e. first added will be bottom layer
     * @param pane
     */
    public void addPane(Pane pane)
    {
        this.panes.add(pane);
    }

    public void placeItemsInInventory(Inventory inventory, int offset)
    {
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                inventory.setItem(y * width + x + offset, this.items[y * width + x]);
            }
        }
    }

    public void onClick(BaseGui gui, InventoryClickEvent event)
    {
        for (int i = this.panes.size() - 1; i >= 0; i--)
        {
            Pane pane = this.panes.get(i);
            int x = event.getSlot() % width;
            int y = (event.getSlot() - x) / width;

            if (pane.getX() <= x && pane.getX() + pane.getWidth() >= x && pane.getY() <= y && pane.getY() + pane.getHeight() >= y) {
                if (pane.onClick(gui, this, event, 0, 0)) {
                    break;
                }
            }
        }
    }

    public void renderSelf()
    {
        Arrays.fill(this.items, null);

        for (Pane pane : this.panes)
        {
            if (pane.isVisible())
                pane.renderToComponent(this, 0,  0);
        }
    }

    public void setItem(GuiItem guiItem, int x, int y)
    {
        this.items[y * width + x] = guiItem.getItemStack();
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }
}

package com.github.grizzlt.inventoriesexmachina.pane;

import com.github.grizzlt.inventoriesexmachina.gui.BaseGui;
import com.github.grizzlt.inventoriesexmachina.gui.GuiItem;
import com.github.grizzlt.inventoriesexmachina.gui.InventoryComponent;
import com.github.grizzlt.inventoriesexmachina.version.VersionManager;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collection;
import java.util.UUID;

public abstract class Pane
{
    /**
     * origin position (top left)
     */
    protected int x, y = 0;
    /**
     * area of pane
     */
    protected int width, height;
    /**
     * whether this pane should be rendered or not
     */
    private boolean isVisible = true;
    /**
     * unique marker
     */
    protected UUID uuid = UUID.randomUUID();

    protected Pane(int x, int y, int width, int height)
    {
        if (width == 0 || height == 0) {
            throw new IllegalArgumentException("Length and height of pane must be greater than zero");
        }

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected Pane(int width, int height)
    {
        this(0, 0, width, height);
    }

    /**
     * Displays the pane by setting all the items from this pane in the correct spots in the inventorycomponent
     * @param inventoryComponent
     * @param offsetX
     * @param offsetY
     */
    public abstract void renderToComponent(InventoryComponent inventoryComponent, int offsetX, int offsetY);

    /**
     * Fired when the player clicked on the gui
     * @param gui
     * @param inventoryComponent
     * @param event
     * @param offsetX
     * @param offsetY
     * @return whether this pane contains an item to click on, necessary for correct pane priority handling
     */
    public abstract boolean onClick(BaseGui gui, InventoryComponent inventoryComponent, InventoryClickEvent event, int offsetX, int offsetY);

    protected static <T extends GuiItem> T findMatchingItem(Collection<T> items, ItemStack item)
    {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return null;
        }

        //version fix
        UUID uuid = VersionManager.getVersionManager().getItemMetaUtil().getUuidFromMeta(meta, GuiItem.KEY_UUID);
        if (uuid == null) {
            return null;
        }

        return items.stream()
                .filter(guiItem -> guiItem.getUuid().equals(uuid))
                .findAny().orElse(null);
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public boolean isVisible()
    {
        return isVisible;
    }

    public void setVisible(boolean visible)
    {
        isVisible = visible;
    }

    public UUID getUuid()
    {
        return uuid;
    }
}

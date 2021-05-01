package com.github.grizzlt.inventoriesexmachina.gui;

import com.github.grizzlt.inventoriesexmachina.version.VersionManager;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GuiItem
{
    /**
     * Marks this itemstack as inventory item
     */
    public static final NamespacedKey KEY_UUID = new NamespacedKey(JavaPlugin.getProvidingPlugin(GuiItem.class), "IF-uuid");

    protected BiConsumer<BaseGui, InventoryClickEvent> action;

    private final ItemStack itemStack;

    private boolean isVisible = true;

    private UUID uuid = UUID.randomUUID();

    public GuiItem(ItemStack itemStack, BiConsumer<BaseGui, InventoryClickEvent> action)
    {
        this.action = action;
        this.itemStack = itemStack;

        this.applyUUID();
    }

    public GuiItem(ItemStack itemStack)
    {
        this(itemStack, null);
    }

    public void callAction(BaseGui gui, InventoryClickEvent event) {
        if (action == null) {
            return;
        }

        try {
            action.accept(gui, event);
        } catch (Throwable t) {
            Logger logger = JavaPlugin.getProvidingPlugin(getClass()).getLogger();
            logger.log(Level.SEVERE, "Exception while handling click event in inventory '"
                    + event.getView().getTitle() + "', slot=" + event.getSlot() + ", item=" + itemStack.getType(), t);
        }
    }

    public void applyUUID()
    {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        if (itemMeta == null) return;

        VersionManager.getVersionManager().getItemMetaUtil().setUuidToMeta(itemMeta, KEY_UUID, this.uuid);
        this.itemStack.setItemMeta(itemMeta);
    }

    public void setAction(BiConsumer<BaseGui, InventoryClickEvent> action)
    {
        this.action = action;
    }

    public ItemStack getItemStack()
    {
        return itemStack;
    }

    public UUID getUuid()
    {
        return uuid;
    }

    public boolean isVisible()
    {
        return isVisible;
    }

    public void setVisible(boolean visible)
    {
        isVisible = visible;
    }
}

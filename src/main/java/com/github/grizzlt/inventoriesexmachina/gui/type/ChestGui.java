package com.github.grizzlt.inventoriesexmachina.gui.type;

import com.github.grizzlt.inventoriesexmachina.gui.InventoryComponent;
import com.github.grizzlt.inventoriesexmachina.gui.NamedGui;
import com.github.grizzlt.inventoriesexmachina.pane.Pane;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class ChestGui extends NamedGui
{
    private InventoryComponent inventoryComponent;

    private final int rows;

    public ChestGui(String title, int rows)
    {
        super(title);

        if (!(rows >= 1 && rows <= 6)) {
            throw new IllegalArgumentException("Rows should be between 1 and 6");
        }

        this.rows = rows;

        this.inventoryComponent = new InventoryComponent(9, rows * 4);
    }

    @Override
    public Inventory createInventory(String title)
    {
        return Bukkit.createInventory(this, this.rows * 9, title);
    }

    @Override
    public void show(HumanEntity humanEntity)
    {
        this.getInventory().clear();

        this.inventoryComponent.renderSelf();

        this.inventoryComponent.placeItemsInInventory(this.getInventory(), 0);

        Bukkit.getScheduler().runTask(JavaPlugin.getProvidingPlugin(getClass()), () -> {
            humanEntity.openInventory(this.getInventory());
        });
    }

    @Override
    public void onClick(InventoryClickEvent event)
    {
        event.setCancelled(true);
        this.inventoryComponent.onClick(this,  event);
    }

    public void addPane(Pane pane)
    {
        this.inventoryComponent.addPane(pane);
    }

    public InventoryComponent getInventoryComponent()
    {
        return inventoryComponent;
    }
}

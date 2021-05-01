package com.github.grizzlt.inventoriesexmachina.gui;

import org.bukkit.inventory.Inventory;

public abstract class NamedGui extends BaseGui
{
    /**
     * This gui's title
     */
    private String title;

    /**
     * Constructor that takes in a title
     * @param title
     */
    public NamedGui(String title)
    {
        super();
        this.title = title;
    }

    /**
     * Creates an inventory with the given title
     *
     * @param title
     * @return
     */
    public abstract Inventory createInventory(String title);

    @Override
    public Inventory createInventory()
    {
        if (this.inventory == null) {
            this.inventory = createInventory(getTitle());
        }

        return inventory;
    }

    /**
     * Sets title and updates viewers to current title
     * @param title
     */
    public void setTitle(String title)
    {
        this.inventory = createInventory(title);
        this.title = title;

        this.update();
    }

    /**
     * Gets current title of this gui
     * @return
     */
    public String getTitle()
    {
        return this.title;
    }
}

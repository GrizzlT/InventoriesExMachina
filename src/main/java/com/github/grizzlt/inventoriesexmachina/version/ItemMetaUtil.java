package com.github.grizzlt.inventoriesexmachina.version;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public interface ItemMetaUtil
{
    UUID getUuidFromMeta(ItemMeta meta, NamespacedKey key);

    void setUuidToMeta(ItemMeta meta, NamespacedKey key, UUID uuid);
}

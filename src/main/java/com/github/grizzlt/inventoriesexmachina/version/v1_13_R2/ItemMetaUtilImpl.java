package com.github.grizzlt.inventoriesexmachina.version.v1_13_R2;

import com.github.grizzlt.inventoriesexmachina.version.ItemMetaUtil;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class ItemMetaUtilImpl implements ItemMetaUtil
{
    @Override
    public UUID getUuidFromMeta(ItemMeta meta, NamespacedKey key)
    {
        return meta.getCustomTagContainer().getCustomTag(key, UUIDTagType.INSTANCE);
    }

    @Override
    public void setUuidToMeta(ItemMeta meta, NamespacedKey key, UUID uuid)
    {
        meta.getCustomTagContainer().setCustomTag(key, UUIDTagType.INSTANCE, uuid);
    }
}

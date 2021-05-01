package com.github.grizzlt.inventoriesexmachina.version.v1_13_R2;

import com.github.grizzlt.inventoriesexmachina.version.ItemMetaUtil;
import com.github.grizzlt.inventoriesexmachina.version.VersionManager;

public class VersionManagerImpl extends VersionManager
{
    private final ItemMetaUtil itemMetaUtil = new ItemMetaUtilImpl();

    @Override
    public ItemMetaUtil getItemMetaUtil()
    {
        return itemMetaUtil;
    }
}

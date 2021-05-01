package com.github.grizzlt.inventoriesexmachina.version;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public abstract class VersionManager
{
    private static final Map<String, Supplier<VersionManager>> VERSION_MANAGERS = new HashMap<>();

    private static VersionManager cachedVersionManager = null;

    public abstract ItemMetaUtil getItemMetaUtil();

    /**
     * Get the VersionManager for the current server version
     * @return
     */
    public static VersionManager getVersionManager()
    {
        if (cachedVersionManager == null) {
            cachedVersionManager = VERSION_MANAGERS.get(getVersion()).get();
        }

        return cachedVersionManager;
    }

    /**
     * Return current nms version
     * @return
     */
    public static String getVersion()
    {
        String version = Bukkit.getServer().getClass().getPackage().getName();
        return  version.substring(version.lastIndexOf('.') + 1);
    }

    /**
     * Register new VersionManager for specific version
     * @param version
     * @param supplier
     */
    public static void registerVersionManager(String version, Supplier<VersionManager> supplier)
    {
        if (VERSION_MANAGERS.putIfAbsent(version, supplier) != null)
        {
            JavaPlugin.getProvidingPlugin(VersionManager.class).getLogger().warning("An already existing VersionManager got replaced! This shouldn't happen normally");
        }
    }

    static {
        //We don't use a method reference here (a lambda instead) to avoid class loading of VersionManagers for different versions
        //noinspection Convert2MethodRef
        registerVersionManager("v1_13_R2", () -> new com.github.grizzlt.inventoriesexmachina.version.v1_13_R2.VersionManagerImpl());
    }
}

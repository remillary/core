package org.kvlt.core.bukkit.utils;

import org.bukkit.configuration.Configuration;
import org.kvlt.core.bukkit.CorePlugin;
import org.kvlt.core.localization.Localization;

@Deprecated
public class BukkitLocale {

    private static Localization localization;

    public static void load() {
        Configuration config = CorePlugin.getPlugin().getConfig();
        String url = config.getString("localization-url");

        if (url == null || url.isEmpty()) {
            url = "http://s1.huesos228.com/lang";
        }

        localization = new Localization();
        localization.load(url, null);
    }

    public static String get(String lang, String key) {
        return localization.get(lang, key);
    }

    public static String get(String lang, String key, Object... format) {
        return localization.get(lang, key, format);
    }

}

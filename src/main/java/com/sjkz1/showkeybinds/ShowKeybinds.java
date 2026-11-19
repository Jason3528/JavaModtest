package com.sjkz1.showkeybinds;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Show Keybinds - shows the key bindings on the hot-bar and in inventories.
 * <p>
 * Client side only. The hot-bar and container overlays live in {@code client.HotbarOverlayHandler}
 * and {@code client.ContainerOverlayHandler}; both register themselves through
 * {@link Mod.EventBusSubscriber}.
 */
@Mod(modid = ShowKeybinds.MOD_ID,
        name = ShowKeybinds.NAME,
        version = ShowKeybinds.VERSION,
        clientSideOnly = true,
        acceptedMinecraftVersions = "[1.12.2]")
@Mod.EventBusSubscriber(modid = ShowKeybinds.MOD_ID, value = Side.CLIENT)
public class ShowKeybinds
{
    public static final String MOD_ID = "showkeybinds";
    public static final String NAME = "Show Keybinds";
    public static final String VERSION = "26.2.1";

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static boolean DEBUG = false;

    /**
     * Re-reads the config when it was changed in the in-game config screen.
     */
    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (MOD_ID.equals(event.getModID()))
        {
            ConfigManager.sync(MOD_ID, Config.Type.INSTANCE);
        }
    }
}

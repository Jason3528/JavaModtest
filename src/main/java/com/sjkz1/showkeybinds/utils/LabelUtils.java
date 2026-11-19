package com.sjkz1.showkeybinds.utils;

import com.sjkz1.showkeybinds.config.ShowKeybindsConfig;
import net.minecraft.client.settings.KeyBinding;

public final class LabelUtils
{
    /**
     * Typing this (or only spaces) as a custom label hides the label of that slot.
     * (Forge trims config values, so a lone space cannot be relied on to survive a save.)
     */
    public static final String HIDE_TOKEN = "<none>";

    private LabelUtils()
    {
    }

    /**
     * Resolves the text to draw for a hotbar/off-hand slot.
     * <p>
     * If the player configured custom text for the slot it is used exactly as typed.
     * Otherwise the label falls back to the slot's current keybind.
     *
     * @param index 0-8 for hotbar slots 1-9, 9 for the off-hand slot
     * @param key   the keybind to fall back to
     */
    public static String resolve(int index, KeyBinding key)
    {
        String custom = ShowKeybindsConfig.general.labels.get(index);
        if (!custom.isEmpty())
        {
            String trimmed = custom.trim();
            if (trimmed.isEmpty() || HIDE_TOKEN.equalsIgnoreCase(trimmed))
            {
                return "";
            }
            return custom;
        }
        return WordUtils.capitalize(WordUtils.convert(key.getDisplayName()));
    }

    /**
     * A scale is drawable if it is a finite number greater than zero. There is intentionally
     * no lower bound beyond that: any positive size is honoured. Zero, negative or NaN values
     * (which would cause a division by zero or mirrored text) simply hide the label.
     */
    public static boolean isDrawableScale(float scale)
    {
        return scale > 0.0F && !Float.isNaN(scale) && !Float.isInfinite(scale);
    }
}

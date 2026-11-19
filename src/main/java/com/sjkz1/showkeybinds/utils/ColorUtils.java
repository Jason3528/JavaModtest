package com.sjkz1.showkeybinds.utils;

import java.awt.Color;

/**
 * Colour helpers. Forge 1.12.2's config system has no colour picker, so colours are stored as
 * hex strings ("RRGGBB" or "AARRGGBB", optionally prefixed with "#" or "0x").
 */
public final class ColorUtils
{
    private ColorUtils()
    {
    }

    /**
     * @return the colour as ARGB. 6 digit values are made fully opaque. Anything that cannot
     * be parsed gives {@code fallback}.
     */
    public static int parse(String hex, int fallback)
    {
        if (hex == null)
        {
            return fallback;
        }
        String value = hex.trim();
        if (value.startsWith("#"))
        {
            value = value.substring(1);
        }
        else if (value.startsWith("0x") || value.startsWith("0X"))
        {
            value = value.substring(2);
        }

        try
        {
            if (value.length() == 6)
            {
                return 0xFF000000 | (int) Long.parseLong(value, 16);
            }
            if (value.length() == 8)
            {
                return (int) Long.parseLong(value, 16);
            }
        }
        catch (NumberFormatException ignored)
        {
            // fall through to the fallback
        }
        return fallback;
    }

    /**
     * @return a fully opaque ARGB colour that cycles through the rainbow every 2.5 seconds
     */
    public static int rainbow()
    {
        int rgb = Color.HSBtoRGB(System.currentTimeMillis() % 2500L / 2500.0F, 0.8F, 0.8F);
        return 0xFF000000 | (rgb & 0x00FFFFFF);
    }
}

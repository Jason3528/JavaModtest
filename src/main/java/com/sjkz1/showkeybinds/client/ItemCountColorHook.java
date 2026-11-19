package com.sjkz1.showkeybinds.client;

import com.sjkz1.showkeybinds.config.ShowKeybindsConfig;
import com.sjkz1.showkeybinds.utils.ColorUtils;

/**
 * Called from {@code RenderItem#renderItemOverlayIntoGUI} (the method that draws the stack size on
 * items). The call is inserted by {@code core.ShowKeybindsTransformer}; it replaces the mixin on
 * {@code GuiGraphicsExtractor#itemCount} of the modern versions.
 * <p>
 * Do not rename or change the signature without changing the transformer as well.
 */
public final class ItemCountColorHook
{
    private ItemCountColorHook()
    {
    }

    /**
     * @param original the colour vanilla would use (white)
     * @return the colour the item count should be drawn with
     */
    public static int itemCountColor(int original)
    {
        try
        {
            ShowKeybindsConfig.General cfg = ShowKeybindsConfig.general;
            if (cfg.rainBowItemText)
            {
                return ColorUtils.rainbow();
            }
            if (cfg.enableItemCountColor)
            {
                return ColorUtils.parse(cfg.itemCountColor, original);
            }
        }
        catch (Throwable ignored)
        {
            // Never break item rendering because of a colour.
        }
        return original;
    }
}

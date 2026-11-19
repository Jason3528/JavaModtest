package com.sjkz1.showkeybinds.utils;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;

public final class ScaledText
{
    private ScaledText()
    {
    }

    /**
     * Draws text with its top-left corner at (x, y) and the given scale.
     * <p>
     * The position is applied with a translation (not by dividing integer coordinates by the scale),
     * so there is no rounding error no matter how small the scale is.
     * <p>
     * The caller is responsible for the GL state (see {@link RenderState}).
     */
    public static void draw(FontRenderer font, String text, float x, float y,
                            float scale, int color, boolean shadow)
    {
        if (text == null || text.isEmpty() || !LabelUtils.isDrawableScale(scale))
        {
            return;
        }

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0.0F);
        GlStateManager.scale(scale, scale, 1.0F);
        font.drawString(text, 0.0F, 0.0F, color, shadow);
        GlStateManager.popMatrix();
    }
}

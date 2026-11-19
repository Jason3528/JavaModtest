package com.sjkz1.showkeybinds.utils;

import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

/**
 * Puts OpenGL into the state needed for flat 2D text drawn on top of items (no lighting,
 * no depth test, alpha blending) and puts it back exactly as it was afterwards.
 * <p>
 * Usage:
 * <pre>
 * RenderState state = RenderState.begin();
 * try { ... draw text ... } finally { state.end(); }
 * </pre>
 */
public final class RenderState
{
    private final boolean lighting;
    private final boolean depth;
    private final boolean blend;

    private RenderState(boolean lighting, boolean depth, boolean blend)
    {
        this.lighting = lighting;
        this.depth = depth;
        this.blend = blend;
    }

    public static RenderState begin()
    {
        RenderState state = new RenderState(
                GL11.glIsEnabled(GL11.GL_LIGHTING),
                GL11.glIsEnabled(GL11.GL_DEPTH_TEST),
                GL11.glIsEnabled(GL11.GL_BLEND));

        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.enableTexture2D();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        return state;
    }

    public void end()
    {
        if (!this.blend)
        {
            GlStateManager.disableBlend();
        }
        if (this.depth)
        {
            GlStateManager.enableDepth();
        }
        if (this.lighting)
        {
            GlStateManager.enableLighting();
        }
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }
}

package com.sjkz1.showkeybinds.client;

import com.sjkz1.showkeybinds.ShowKeybinds;
import com.sjkz1.showkeybinds.config.ShowKeybindsConfig;
import com.sjkz1.showkeybinds.utils.ColorUtils;
import com.sjkz1.showkeybinds.utils.LabelUtils;
import com.sjkz1.showkeybinds.utils.RenderState;
import com.sjkz1.showkeybinds.utils.ScaledText;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Draws the key names on top of the in-game hot-bar (and next to the off-hand slot).
 * <p>
 * Replaces the {@code Gui#extractItemHotbar} mixin of the modern versions: Forge 1.12.2 fires
 * {@link RenderGameOverlayEvent.Post} for {@link RenderGameOverlayEvent.ElementType#HOTBAR}
 * right after the hot-bar and its items were rendered.
 */
@Mod.EventBusSubscriber(modid = ShowKeybinds.MOD_ID, value = Side.CLIENT)
public final class HotbarOverlayHandler
{
    private HotbarOverlayHandler()
    {
    }

    @SubscribeEvent
    public static void onRenderHotbar(RenderGameOverlayEvent.Post event)
    {
        if (event.getType() != RenderGameOverlayEvent.ElementType.HOTBAR)
        {
            return;
        }

        ShowKeybindsConfig.General cfg = ShowKeybindsConfig.general;
        boolean drawHotbar = cfg.enableHotBarText;
        boolean drawOffhand = cfg.offHandText;
        if (!drawHotbar && !drawOffhand)
        {
            return;
        }

        Minecraft mc = Minecraft.getMinecraft();
        // The spectator hot-bar is a different GUI and has no key text.
        if (mc.playerController != null && mc.playerController.isSpectator())
        {
            return;
        }
        Entity view = mc.getRenderViewEntity();
        if (!(view instanceof EntityPlayer))
        {
            return;
        }
        EntityPlayer player = (EntityPlayer) view;

        ScaledResolution resolution = event.getResolution();
        int centerX = resolution.getScaledWidth() / 2;
        int height = resolution.getScaledHeight();

        int color = cfg.rainBowText
                ? ColorUtils.rainbow()
                : ColorUtils.parse(cfg.hotBarTextColor, 0xFFFFFFFF);

        FontRenderer font = mc.fontRenderer;
        RenderState state = RenderState.begin();
        try
        {
            if (drawHotbar)
            {
                KeyBinding[] keys = mc.gameSettings.keyBindsHotbar;
                for (int index = 0; index < keys.length && index < 9; index++)
                {
                    // The item of slot "index" is drawn at (centerX - 88 + index * 20, height - 19)
                    ScaledText.draw(font,
                            LabelUtils.resolve(index, keys[index]),
                            centerX - 87 + index * 20,
                            height - 18,
                            cfg.getScale(index),
                            color,
                            cfg.shadowedText);
                }
            }

            if (drawOffhand && !player.getHeldItemOffhand().isEmpty())
            {
                EnumHandSide offhandSide = player.getPrimaryHand().opposite();
                int x = offhandSide == EnumHandSide.LEFT ? centerX - 116 : centerX + 102;

                ScaledText.draw(font,
                        LabelUtils.resolve(9, mc.gameSettings.keyBindSwapHands),
                        x,
                        height - 19,
                        cfg.getScale(9),
                        color,
                        cfg.shadowedText);
            }
        }
        finally
        {
            state.end();
        }
    }
}

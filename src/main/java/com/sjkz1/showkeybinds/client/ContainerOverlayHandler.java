package com.sjkz1.showkeybinds.client;

import com.sjkz1.showkeybinds.ShowKeybinds;
import com.sjkz1.showkeybinds.config.ShowKeybindsConfig;
import com.sjkz1.showkeybinds.utils.ColorUtils;
import com.sjkz1.showkeybinds.utils.LabelUtils;
import com.sjkz1.showkeybinds.utils.RenderState;
import com.sjkz1.showkeybinds.utils.ScaledText;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Draws the key names on the hot-bar slots (and the off-hand slot) of every container screen.
 * <p>
 * Replaces the {@code AbstractContainerScreen#extractContents} mixin of the modern versions:
 * {@link GuiContainerEvent.DrawForeground} is fired after the slots were drawn but before the
 * dragged stack and the tooltips, with the GL matrix already moved to the top-left corner of the
 * GUI, so slot coordinates ({@code slot.xPos}, {@code slot.yPos}) can be used directly.
 */
@Mod.EventBusSubscriber(modid = ShowKeybinds.MOD_ID, value = Side.CLIENT)
public final class ContainerOverlayHandler
{
    /** Index used for the off-hand slot (0-8 are the hot-bar slots). */
    private static final int OFFHAND = 9;
    /** Index of the off-hand slot in InventoryPlayer (0-35 main, 36-39 armor, 40 off-hand). */
    private static final int OFFHAND_INVENTORY_INDEX = 40;

    // The creative inventory wraps the real slots, so its slots are matched by position.
    private static final int CREATIVE_HOTBAR_Y = 112;
    private static final int CREATIVE_HOTBAR_FIRST_X = 9;
    private static final int CREATIVE_OFFHAND_X = 35;
    private static final int CREATIVE_OFFHAND_Y = 20;

    private ContainerOverlayHandler()
    {
    }

    @SubscribeEvent
    public static void onDrawForeground(GuiContainerEvent.DrawForeground event)
    {
        ShowKeybindsConfig.Container container = ShowKeybindsConfig.container;
        if (!container.enableContainerText)
        {
            return;
        }

        ShowKeybindsConfig.General general = ShowKeybindsConfig.general;
        GuiContainer gui = event.getGuiContainer();
        boolean creative = gui instanceof GuiContainerCreative;
        Minecraft mc = Minecraft.getMinecraft();
        FontRenderer font = mc.fontRenderer;

        int color = container.rainBowText
                ? ColorUtils.rainbow()
                : ColorUtils.parse(container.containerTextColor, 0xFFFF55FF);

        RenderState state = RenderState.begin();
        try
        {
            for (Slot slot : gui.inventorySlots.inventorySlots)
            {
                if (!slot.isEnabled())
                {
                    continue;
                }

                if (ShowKeybinds.DEBUG)
                {
                    ScaledText.draw(font, String.valueOf(slot.xPos), slot.xPos, slot.yPos, 1.0F,
                            color, container.shadowedText);
                }

                int index = findKeyIndex(slot, creative);
                if (index < 0)
                {
                    continue;
                }
                if (index == OFFHAND && !general.offHandText)
                {
                    continue;
                }

                KeyBinding key = index == OFFHAND
                        ? mc.gameSettings.keyBindSwapHands
                        : mc.gameSettings.keyBindsHotbar[index];

                ScaledText.draw(font,
                        LabelUtils.resolve(index, key),
                        slot.xPos,
                        slot.yPos,
                        general.getScale(index),
                        color,
                        container.shadowedText);
            }
        }
        finally
        {
            state.end();
        }
    }

    /**
     * @return 0-8 if the slot is one of the player's hot-bar slots, {@link #OFFHAND} if it is the
     * player's off-hand slot, otherwise -1
     */
    private static int findKeyIndex(Slot slot, boolean creative)
    {
        // Only slots that show the player's own inventory can be hot-bar / off-hand slots.
        if (!(slot.inventory instanceof InventoryPlayer))
        {
            return -1;
        }

        if (creative)
        {
            if (slot.yPos == CREATIVE_HOTBAR_Y)
            {
                int offset = slot.xPos - CREATIVE_HOTBAR_FIRST_X;
                if (offset >= 0 && offset % 18 == 0 && offset / 18 < 9)
                {
                    return offset / 18;
                }
            }
            else if (slot.xPos == CREATIVE_OFFHAND_X && slot.yPos == CREATIVE_OFFHAND_Y)
            {
                return OFFHAND;
            }
            return -1;
        }

        int inventoryIndex = slot.getSlotIndex();
        if (inventoryIndex >= 0 && inventoryIndex < 9)
        {
            return inventoryIndex;
        }
        if (inventoryIndex == OFFHAND_INVENTORY_INDEX)
        {
            return OFFHAND;
        }
        return -1;
    }
}

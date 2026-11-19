package com.sjkz1.showkeybinds.config;

import com.sjkz1.showkeybinds.ShowKeybinds;
import net.minecraftforge.common.config.Config;

/**
 * Forge 1.12.2 annotation based config. Forge saves it to {@code config/showkeybinds.cfg} and
 * builds the in-game config screen (Mods -> Show Keybinds -> Config) automatically.
 * <p>
 * Colours are hex strings: "RRGGBB" or "AARRGGBB" (a leading "#" is allowed).
 */
@Config(modid = ShowKeybinds.MOD_ID, name = ShowKeybinds.MOD_ID, category = "")
@Config.LangKey("showkeybinds.config.title")
public class ShowKeybindsConfig
{
    @Config.Name("general")
    @Config.LangKey("showkeybinds.config.category.hotbar")
    public static General general = new General();

    @Config.Name("container")
    @Config.LangKey("showkeybinds.config.category.container")
    public static Container container = new Container();

    public static class General
    {
        @Config.LangKey("showkeybinds.config.general.enableHotBarText")
        public boolean enableHotBarText = true;

        @Config.LangKey("showkeybinds.config.general.offHandText")
        public boolean offHandText = false;

        @Config.LangKey("showkeybinds.config.general.shadowedText")
        public boolean shadowedText = true;

        @Config.LangKey("showkeybinds.config.general.rainBowText")
        public boolean rainBowText = false;

        @Config.Comment("Colour of the hot-bar key text. Hex: RRGGBB or AARRGGBB.")
        @Config.LangKey("showkeybinds.config.general.hotBarTextColor")
        public String hotBarTextColor = "FFFFFFFF";

        @Config.Comment("Colour of the item count text (only if 'Enable Item count color' is on). Hex: RRGGBB or AARRGGBB.")
        @Config.LangKey("showkeybinds.config.general.itemCountColor")
        public String itemCountColor = "FFFFFFFF";

        @Config.LangKey("showkeybinds.config.general.enableItemCountColor")
        public boolean enableItemCountColor = false;

        @Config.LangKey("showkeybinds.config.general.rainBowItemText")
        public boolean rainBowItemText = false;

        @Config.RangeDouble(min = 0.01D, max = 16.0D)
        @Config.LangKey("showkeybinds.config.general.keySlot1Scale")
        public float keySlot1Scale = 1.0F;

        @Config.RangeDouble(min = 0.01D, max = 16.0D)
        @Config.LangKey("showkeybinds.config.general.keySlot2Scale")
        public float keySlot2Scale = 1.0F;

        @Config.RangeDouble(min = 0.01D, max = 16.0D)
        @Config.LangKey("showkeybinds.config.general.keySlot3Scale")
        public float keySlot3Scale = 1.0F;

        @Config.RangeDouble(min = 0.01D, max = 16.0D)
        @Config.LangKey("showkeybinds.config.general.keySlot4Scale")
        public float keySlot4Scale = 1.0F;

        @Config.RangeDouble(min = 0.01D, max = 16.0D)
        @Config.LangKey("showkeybinds.config.general.keySlot5Scale")
        public float keySlot5Scale = 1.0F;

        @Config.RangeDouble(min = 0.01D, max = 16.0D)
        @Config.LangKey("showkeybinds.config.general.keySlot6Scale")
        public float keySlot6Scale = 1.0F;

        @Config.RangeDouble(min = 0.01D, max = 16.0D)
        @Config.LangKey("showkeybinds.config.general.keySlot7Scale")
        public float keySlot7Scale = 1.0F;

        @Config.RangeDouble(min = 0.01D, max = 16.0D)
        @Config.LangKey("showkeybinds.config.general.keySlot8Scale")
        public float keySlot8Scale = 1.0F;

        @Config.RangeDouble(min = 0.01D, max = 16.0D)
        @Config.LangKey("showkeybinds.config.general.keySlot9Scale")
        public float keySlot9Scale = 1.0F;

        @Config.RangeDouble(min = 0.01D, max = 16.0D)
        @Config.LangKey("showkeybinds.config.general.offHandKeySlotScale")
        public float offHandKeySlotScale = 1.0F;

        @Config.Name("labels")
        @Config.LangKey("showkeybinds.config.category.labels")
        public Labels labels = new Labels();

        /**
         * @param index 0-8 for hotbar slots 1-9, 9 for the off-hand slot
         * @return the text scale of that slot
         */
        public float getScale(int index)
        {
            switch (index)
            {
                case 0:
                    return this.keySlot1Scale;
                case 1:
                    return this.keySlot2Scale;
                case 2:
                    return this.keySlot3Scale;
                case 3:
                    return this.keySlot4Scale;
                case 4:
                    return this.keySlot5Scale;
                case 5:
                    return this.keySlot6Scale;
                case 6:
                    return this.keySlot7Scale;
                case 7:
                    return this.keySlot8Scale;
                case 8:
                    return this.keySlot9Scale;
                case 9:
                    return this.offHandKeySlotScale;
                default:
                    return 1.0F;
            }
        }
    }

    /**
     * Custom text for each hotbar slot (and the off-hand slot).
     * An empty value means "use the current keybind" (the default behaviour).
     * Type {@code <none>} to show nothing for that slot.
     */
    public static class Labels
    {
        @Config.LangKey("showkeybinds.config.labels.slot1")
        public String slot1 = "";
        @Config.LangKey("showkeybinds.config.labels.slot2")
        public String slot2 = "";
        @Config.LangKey("showkeybinds.config.labels.slot3")
        public String slot3 = "";
        @Config.LangKey("showkeybinds.config.labels.slot4")
        public String slot4 = "";
        @Config.LangKey("showkeybinds.config.labels.slot5")
        public String slot5 = "";
        @Config.LangKey("showkeybinds.config.labels.slot6")
        public String slot6 = "";
        @Config.LangKey("showkeybinds.config.labels.slot7")
        public String slot7 = "";
        @Config.LangKey("showkeybinds.config.labels.slot8")
        public String slot8 = "";
        @Config.LangKey("showkeybinds.config.labels.slot9")
        public String slot9 = "";
        @Config.LangKey("showkeybinds.config.labels.offHand")
        public String offHand = "";

        /**
         * @param index 0-8 for hotbar slots 1-9, 9 for the off-hand slot
         * @return the custom text, or an empty string if none is set
         */
        public String get(int index)
        {
            String value;
            switch (index)
            {
                case 0:
                    value = this.slot1;
                    break;
                case 1:
                    value = this.slot2;
                    break;
                case 2:
                    value = this.slot3;
                    break;
                case 3:
                    value = this.slot4;
                    break;
                case 4:
                    value = this.slot5;
                    break;
                case 5:
                    value = this.slot6;
                    break;
                case 6:
                    value = this.slot7;
                    break;
                case 7:
                    value = this.slot8;
                    break;
                case 8:
                    value = this.slot9;
                    break;
                case 9:
                    value = this.offHand;
                    break;
                default:
                    value = "";
                    break;
            }
            return value == null ? "" : value;
        }
    }

    public static class Container
    {
        @Config.LangKey("showkeybinds.config.container.enableContainerText")
        public boolean enableContainerText = true;

        @Config.LangKey("showkeybinds.config.container.shadowedText")
        public boolean shadowedText = true;

        @Config.LangKey("showkeybinds.config.container.rainBowText")
        public boolean rainBowText = false;

        @Config.Comment("Colour of the key text in inventories / containers. Hex: RRGGBB or AARRGGBB.")
        @Config.LangKey("showkeybinds.config.container.containerTextColor")
        public String containerTextColor = "FFFF55FF";

        @Config.RangeDouble(min = 0.01D, max = 16.0D)
        @Config.LangKey("showkeybinds.config.container.containerScale")
        public float containerScale = 1.0F;
    }
}

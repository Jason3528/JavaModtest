package com.sjkz1.showkeybinds.core;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

/**
 * Tiny coremod used only for the "item count colour" feature (vanilla 1.12.2 has no hook for
 * the colour of the stack size text). Everything else in the mod is plain Forge events.
 * <p>
 * If this coremod is not loaded (for example in a dev environment started without
 * {@code -Dfml.coreMods.load=com.sjkz1.showkeybinds.core.ShowKeybindsCore}) the mod still works,
 * only the item count colour options have no effect.
 * <p>
 * IMPORTANT: classes in this package must never reference Minecraft classes directly.
 */
@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.Name("ShowKeybindsCore")
// > 1000 so the transformer sees de-obfuscated class names
@IFMLLoadingPlugin.SortingIndex(1001)
@IFMLLoadingPlugin.TransformerExclusions({"com.sjkz1.showkeybinds.core."})
public class ShowKeybindsCore implements IFMLLoadingPlugin
{
    @Override
    public String[] getASMTransformerClass()
    {
        return new String[]{"com.sjkz1.showkeybinds.core.ShowKeybindsTransformer"};
    }

    @Override
    public String getModContainerClass()
    {
        return null;
    }

    @Override
    public String getSetupClass()
    {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data)
    {
    }

    @Override
    public String getAccessTransformerClass()
    {
        return null;
    }
}

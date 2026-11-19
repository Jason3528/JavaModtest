package com.sjkz1.showkeybinds.core;

import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * Makes the colour of the stack size text configurable.
 * <p>
 * Vanilla (1.12.2) draws the stack size in
 * <pre>RenderItem#renderItemOverlayIntoGUI(FontRenderer, ItemStack, int, int, String)</pre>
 * with <code>fr.drawStringWithShadow(text, x, y, 16777215)</code>. This transformer finds the
 * constant {@code 16777215} (white) inside that method and wraps it:
 * <pre>16777215  ->  ItemCountColorHook.itemCountColor(16777215)</pre>
 * <p>
 * The method is found by its descriptor, not by name, so it works with both MCP names (dev
 * environment) and SRG names (normal game) without any mapping lookup.
 */
public class ShowKeybindsTransformer implements IClassTransformer
{
    private static final Logger LOGGER = LogManager.getLogger("ShowKeybindsCore");

    private static final String TARGET_CLASS = "net.minecraft.client.renderer.RenderItem";
    private static final String TARGET_DESC =
            "(Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V";
    private static final int WHITE = 16777215;

    private static final String HOOK_OWNER = "com/sjkz1/showkeybinds/client/ItemCountColorHook";
    private static final String HOOK_NAME = "itemCountColor";
    private static final String HOOK_DESC = "(I)I";

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass)
    {
        if (basicClass == null || !TARGET_CLASS.equals(transformedName))
        {
            return basicClass;
        }

        try
        {
            ClassNode classNode = new ClassNode();
            new ClassReader(basicClass).accept(classNode, 0);

            int patched = 0;
            for (MethodNode method : classNode.methods)
            {
                if (!TARGET_DESC.equals(method.desc))
                {
                    continue;
                }

                for (AbstractInsnNode insn : method.instructions.toArray())
                {
                    if (insn instanceof LdcInsnNode)
                    {
                        Object constant = ((LdcInsnNode) insn).cst;
                        if (constant instanceof Integer && ((Integer) constant).intValue() == WHITE)
                        {
                            method.instructions.insert(insn,
                                    new MethodInsnNode(Opcodes.INVOKESTATIC, HOOK_OWNER, HOOK_NAME, HOOK_DESC, false));
                            patched++;
                        }
                    }
                }
            }

            if (patched == 0)
            {
                LOGGER.warn("Could not find the item count colour in {} - item count colour options will have no effect.",
                        TARGET_CLASS);
                return basicClass;
            }

            // Only an int is replaced by an int, so no new stack map frames are needed.
            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            classNode.accept(writer);
            LOGGER.info("Patched {} item count colour call(s) in {}", patched, TARGET_CLASS);
            return writer.toByteArray();
        }
        catch (Throwable t)
        {
            LOGGER.error("Failed to patch " + TARGET_CLASS + " - item count colour options will have no effect.", t);
            return basicClass;
        }
    }
}

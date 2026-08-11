package thaumicindustries2core.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;

import static thaumicindustries2core.ThaumicIndustries2Core.logger;

@SuppressWarnings("unused")
public class IllFittingTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (basicClass == null) return null;

        if (transformedName != null && transformedName.contains("PotionIllFitting"))
            logger.info("Found candidate class :", transformedName, "(raw name:", name, ")");

        if ("com.emoniph.witchery.brewing.potions.PotionIllFitting".equals(transformedName)) {
            logger.info(">>> Patching PotionIllFitting !");
            return patchPerformEffect(basicClass);
        }

        return basicClass;
    }

    private byte[] patchPerformEffect(byte[] basicClass) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(basicClass);
        classReader.accept(classNode, 0);

        boolean patched = false;

        for (MethodNode method : classNode.methods)
            if (("performEffect".equals(method.name) || "func_76394_a".equals(method.name))
                    && (method.desc.equals("(Lnet/minecraft/entity/EntityLivingBase;I)V")
                    || method.desc.equals("(Lsv;I)V"))) {

                logger.info("Found performEffect, emptying it... desc =", method.desc);

                method.instructions.clear();
                method.tryCatchBlocks.clear();
                if (method.localVariables != null) method.localVariables.clear();

                InsnList list = new InsnList();
                list.add(new InsnNode(Opcodes.RETURN));
                method.instructions.add(list);

                method.maxStack = 0;
                method.maxLocals = 3;
                patched = true;
                break;
            }

        if (!patched) {
            logger.warn("performEffect method not found in PotionIllFitting !");
            for (MethodNode m : classNode.methods) logger.info(" ->", m.name, m.desc);
        } else logger.info("PotionIllFitting successfully patched");

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classNode.accept(writer);
        return writer.toByteArray();
    }
}

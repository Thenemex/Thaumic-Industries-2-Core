package thaumicindustries2core.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;
import thaumicindustries2core.ThaumicIndustries2Core;

@SuppressWarnings("unused")
public class IllFittingTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (transformedName.equals("com.emoniph.witchery.brewing.potions.PotionIllFitting"))
            return patchPerformEffect(basicClass);
        return basicClass;
    }

    private byte[] patchPerformEffect(byte[] basicClass) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(basicClass);
        classReader.accept(classNode, 0);

        for (MethodNode method : classNode.methods)
            if (method.name.equals("performEffect") && method.desc.equals("(Lnet/minecraft/entity/EntityLivingBase;I)V")) {
                // Clearing method
                method.instructions.clear();
                method.tryCatchBlocks.clear();
                method.localVariables.clear();

                // Adding return instruction
                InsnList list = new InsnList();
                list.add(new InsnNode(Opcodes.RETURN));
                method.instructions.add(list);

                method.maxStack = 0;
                method.maxLocals = 3; // this + entity + amplifier

                ThaumicIndustries2Core.logger.info("Transforming PotionIllFitting class from witchery ...");
            }

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        classNode.accept(writer);
        return writer.toByteArray();
    }
}

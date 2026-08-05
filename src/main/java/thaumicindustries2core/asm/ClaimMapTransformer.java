package thaumicindustries2core.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.ListIterator;
import static thaumicindustries2core.ThaumicIndustries2Core.logger;

public class ClaimMapTransformer implements IClassTransformer {

    private static final String TARGET = "ftb.utils.mod.client.gui.claims.GuiClaimChunks";
    private static final float BG = 0.06F;
    private static final float BG_ALPHA = 0.62F;

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        if (!TARGET.equals(transformedName) && !TARGET.equals(name)) {
            return bytes;
        }
        return patch(bytes);
    }

    private static byte[] patch(byte[] bytes) {
        ClassNode node = new ClassNode();
        new ClassReader(bytes).accept(node, 0);

        boolean modified = false;
        for (MethodNode method : node.methods) {
            if ("drawBackground".equals(method.name) && "()V".equals(method.desc)) {
                modified |= patchDrawBackground(method);
            }
        }

        if (!modified) {
            ogger.warn("claim map: drawBackground not patched (method missing or layout changed)");
            return bytes;
        }

        logger.info("claim map: translucent background patch applied");
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        node.accept(writer);
        return writer.toByteArray();
    }

    private static boolean patchDrawBackground(MethodNode method) {
        boolean colorOk = recolorPanel(method);
        boolean skipOk = skipTerrainTexture(method);
        return colorOk && skipOk;
    }

    // solid -> semi-transparent
    private static boolean recolorPanel(MethodNode method) {
        ListIterator<AbstractInsnNode> it = method.instructions.iterator();
        while (it.hasNext()) {
            AbstractInsnNode a = it.next();
            if (!isFConst(a, 0F)) {
                continue;
            }
            AbstractInsnNode b = nextReal(a);
            AbstractInsnNode c = nextReal(b);
            AbstractInsnNode d = nextReal(c);
            AbstractInsnNode e = nextReal(d);
            if (!isFConst(b, 0F) || !isFConst(c, 0F) || !isFConst(d, 1F) || !isColorCall(e)) {
                continue;
            }
            if (!hasDrawBlankRectSoon(e)) {
                continue;
            }

            method.instructions.set(a, new LdcInsnNode(BG));
            method.instructions.set(b, new LdcInsnNode(BG));
            method.instructions.set(c, new LdcInsnNode(BG));
            method.instructions.set(d, new LdcInsnNode(BG_ALPHA));
            return true;
        }
        return false;
    }

    // pretend the map texture id is always -1 so the terrain never draw above the panel
    private static boolean skipTerrainTexture(MethodNode method) {
        AbstractInsnNode insn = method.instructions.getFirst();
        while (insn != null) {
            AbstractInsnNode m1 = nextReal(insn);
            AbstractInsnNode branch = nextReal(m1);
            if (isGetStatic(insn, "textureID", "I") && isInsn(m1, Opcodes.ICONST_M1) && isInsn(branch, Opcodes.IF_ICMPEQ) && pathHasTexturedRectBeforeLabel(branch)) {
                method.instructions.set(insn, new InsnNode(Opcodes.ICONST_M1));
                return true;
            }
            insn = insn.getNext();
        }
        return false;
    }

    private static boolean pathHasTexturedRectBeforeLabel(AbstractInsnNode ifIcmpeq) {
        if (!(ifIcmpeq instanceof JumpInsnNode)) {
            return false;
        }
        AbstractInsnNode target = ((JumpInsnNode) ifIcmpeq).label;
        AbstractInsnNode cur = ifIcmpeq.getNext();
        while (cur != null && cur != target) {
            if (isDrawTexturedRectD(cur)) {
                return true;
            }
            cur = cur.getNext();
        }
        return false;
    }

    private static boolean hasDrawBlankRectSoon(AbstractInsnNode from) {
        AbstractInsnNode cur = from;
        for (int i = 0; i < 40 && cur != null; i++) {
            if (isMethod(cur, Opcodes.INVOKESTATIC, "drawBlankRect", "(DDDDD)V")) {
                return true;
            }
            cur = cur.getNext();
        }
        return false;
    }

    private static boolean isFConst(AbstractInsnNode n, float value) {
        if (n == null) {
            return false;
        }
        if (value == 0F && n.getOpcode() == Opcodes.FCONST_0) {
            return true;
        }
        if (value == 1F && n.getOpcode() == Opcodes.FCONST_1) {
            return true;
        }
        if (n.getOpcode() == Opcodes.LDC && n instanceof LdcInsnNode) {
            Object cst = ((LdcInsnNode) n).cst;
            return cst instanceof Float && ((Float) cst).floatValue() == value;
        }
        return false;
    }

    private static boolean isColorCall(AbstractInsnNode n) {
        return isMethod(n, Opcodes.INVOKESTATIC, "color", "(FFFF)V");
    }

    private static boolean isDrawTexturedRectD(AbstractInsnNode n) {
        return isMethod(n, Opcodes.INVOKESTATIC, "drawTexturedRectD", "(DDDDDDDDD)V");
    }

    private static boolean isGetStatic(AbstractInsnNode n, String name, String desc) {
        if (n == null || n.getOpcode() != Opcodes.GETSTATIC || !(n instanceof FieldInsnNode)) {
            return false;
        }
        FieldInsnNode f = (FieldInsnNode) n;
        return name.equals(f.name) && desc.equals(f.desc);
    }

    private static boolean isMethod(AbstractInsnNode n, int opcode, String name, String desc) {
        if (n == null || n.getOpcode() != opcode || !(n instanceof MethodInsnNode)) {
            return false;
        }
        MethodInsnNode m = (MethodInsnNode) n;
        return name.equals(m.name) && desc.equals(m.desc);
    }

    private static boolean isInsn(AbstractInsnNode n, int opcode) {
        return n != null && n.getOpcode() == opcode;
    }

    private static AbstractInsnNode nextReal(AbstractInsnNode n) {
        if (n == null) {
            return null;
        }
        AbstractInsnNode cur = n.getNext();
        while (cur != null && cur.getOpcode() < 0) {
            cur = cur.getNext();
        }
        return cur;
    }
}

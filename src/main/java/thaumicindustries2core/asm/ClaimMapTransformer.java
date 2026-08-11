package thaumicindustries2core.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.ListIterator;

import static thaumicindustries2core.ThaumicIndustries2Core.logger;

@SuppressWarnings("unused") // ToDo Code review
public class ClaimMapTransformer implements IClassTransformer {

    private static final String FTBU_GUI = "ftb.utils.mod.client.gui.claims.GuiClaimChunks";
    private static final String SU_SELECTOR = "serverutils.lib.gui.misc.GuiChunkSelectorBase";
    private static final String SU_MAP = "serverutils.client.gui.BuiltinChunkMap";
    private static final String SU_COLOR4I = "serverutils/lib/icon/Color4I";

    private static final float BG = 0.06F;
    private static final float BG_ALPHA = 0.62F;
    private static final int BG_RGB = 15;
    private static final int BG_A = 158;

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        String id = transformedName != null ? transformedName : name;
        if (FTBU_GUI.equals(id) || FTBU_GUI.equals(name)) {
            return patchFtbu(bytes);
        }
        if (SU_SELECTOR.equals(id) || SU_SELECTOR.equals(name)) {
            return patchServerUtilitiesSelector(bytes);
        }
        if (SU_MAP.equals(id) || SU_MAP.equals(name)) {
            return patchServerUtilitiesMap(bytes);
        }
        return bytes;
    }

    private static byte[] patchFtbu(byte[] bytes) {
        ClassNode node = new ClassNode();
        new ClassReader(bytes).accept(node, 0);

        boolean modified = false;
        for (MethodNode method : node.methods) {
            if ("drawBackground".equals(method.name) && "()V".equals(method.desc)) {
                modified |= recolorFtbuPanel(method) && skipFtbuTerrainTexture(method);
            }
        }
        return finish(bytes, node, modified, "FTBU");
    }

    private static byte[] patchServerUtilitiesSelector(byte[] bytes) {
        ClassNode node = new ClassNode();
        new ClassReader(bytes).accept(node, 0);

        boolean modified = false;
        for (MethodNode method : node.methods) {
            if ("drawBackground".equals(method.name) && method.desc != null && method.desc.contains("Theme")) {
                modified |= recolorSuPanel(method);
            }
        }
        return finish(bytes, node, modified, "ServerUtilities panel");
    }

    private static byte[] patchServerUtilitiesMap(byte[] bytes) {
        ClassNode node = new ClassNode();
        new ClassReader(bytes).accept(node, 0);

        boolean modified = false;
        for (MethodNode method : node.methods) {
            if ("drawMap".equals(method.name)) {
                modified |= skipSuTerrainTexture(method);
            }
        }
        return finish(bytes, node, modified, "ServerUtilities terrain");
    }

    private static byte[] finish(byte[] original, ClassNode node, boolean modified, String label) {
        if (!modified) {
            logger.warn("Claim map (" + label + "): not patched (layout changed or missing)");
            return original;
        }
        logger.info("Claim map (" + label + "): translucent background patch applied");
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        node.accept(writer);
        return writer.toByteArray();
    }

    // --- FTBU---

    // black solid panel -> dark glass
    private static boolean recolorFtbuPanel(MethodNode method) {
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
            if (!hasMethodSoon(e, Opcodes.INVOKESTATIC, "drawBlankRect", "(DDDDD)V", 40)) {
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

    // pretend the map texture id is -1 so the terrain never draws above the panel
    private static boolean skipFtbuTerrainTexture(MethodNode method) {
        AbstractInsnNode insn = method.instructions.getFirst();
        while (insn != null) {
            AbstractInsnNode m1 = nextReal(insn);
            AbstractInsnNode branch = nextReal(m1);
            if (isGetStatic(insn, "textureID", "I")
                    && isInsn(m1, Opcodes.ICONST_M1)
                    && isInsn(branch, Opcodes.IF_ICMPEQ)
                    && pathHasDrawTexturedRectD(branch)) {
                method.instructions.set(insn, new InsnNode(Opcodes.ICONST_M1));
                return true;
            }
            insn = insn.getNext();
        }
        return false;
    }

    // --- GTNH ServerUtilities ---

    // Color4I.BLACK.draw -> Color4I.rgba(15,15,15,158).draw (BG_RGB & BG_A)
    private static boolean recolorSuPanel(MethodNode method) {
        AbstractInsnNode insn = method.instructions.getFirst();
        while (insn != null) {
            if (isGetStatic(insn, "BLACK", "L" + SU_COLOR4I + ";")
                    && hasMethodSoon(insn, Opcodes.INVOKEVIRTUAL, "draw", "(IIII)V", 24)) {
                InsnList repl = new InsnList();
                repl.add(new IntInsnNode(Opcodes.BIPUSH, BG_RGB));
                repl.add(new IntInsnNode(Opcodes.BIPUSH, BG_RGB));
                repl.add(new IntInsnNode(Opcodes.BIPUSH, BG_RGB));
                repl.add(new IntInsnNode(Opcodes.SIPUSH, BG_A));
                repl.add(new MethodInsnNode(
                        Opcodes.INVOKESTATIC,
                        SU_COLOR4I,
                        "rgba",
                        "(IIII)L" + SU_COLOR4I + ";",
                        false));
                method.instructions.insert(insn, repl);
                method.instructions.remove(insn);
                return true;
            }
            insn = insn.getNext();
        }
        return false;
    }

    // drop to not draw above the panel
    private static boolean skipSuTerrainTexture(MethodNode method) {
        AbstractInsnNode insn = method.instructions.getFirst();
        while (insn != null) {
            // (IIIILserverutils/lib/icon/Color4I;DDDD)V
            if (isMethod(insn, Opcodes.INVOKESTATIC, "drawTexturedRect", null)
                    && insn instanceof MethodInsnNode
                    && ((MethodInsnNode) insn).desc != null
                    && ((MethodInsnNode) insn).desc.contains("Color4I")
                    && ((MethodInsnNode) insn).desc.startsWith("(IIII")) {
                InsnList pops = new InsnList();
                // (tricky but is correct)
                pops.add(new InsnNode(Opcodes.POP2));
                pops.add(new InsnNode(Opcodes.POP2));
                pops.add(new InsnNode(Opcodes.POP2));
                pops.add(new InsnNode(Opcodes.POP2));
                pops.add(new InsnNode(Opcodes.POP));
                pops.add(new InsnNode(Opcodes.POP));
                pops.add(new InsnNode(Opcodes.POP));
                pops.add(new InsnNode(Opcodes.POP));
                pops.add(new InsnNode(Opcodes.POP));
                method.instructions.insert(insn, pops);
                method.instructions.remove(insn);
                return true;
            }
            insn = insn.getNext();
        }
        return false;
    }

    private static boolean pathHasDrawTexturedRectD(AbstractInsnNode ifIcmpeq) {
        if (!(ifIcmpeq instanceof JumpInsnNode)) {
            return false;
        }
        AbstractInsnNode target = ((JumpInsnNode) ifIcmpeq).label;
        AbstractInsnNode cur = ifIcmpeq.getNext();
        while (cur != null && cur != target) {
            if (isMethod(cur, Opcodes.INVOKESTATIC, "drawTexturedRectD", "(DDDDDDDDD)V")) {
                return true;
            }
            cur = cur.getNext();
        }
        return false;
    }

    private static boolean hasMethodSoon(AbstractInsnNode from, int opcode, String name, String desc, int maxSteps) {
        AbstractInsnNode cur = from;
        for (int i = 0; i < maxSteps && cur != null; i++) {
            if (isMethod(cur, opcode, name, desc)) {
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
        if (!name.equals(m.name)) {
            return false;
        }
        return desc == null || desc.equals(m.desc);
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

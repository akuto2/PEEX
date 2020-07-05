package akuto2.asm.transform;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;

public class TransformerObjHandler implements IClassTransformer, Opcodes{
	private static final String TARGETCLASSNAME = "moze_intel.projecte.gameObjs.ObjHandler";

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		if(!TARGETCLASSNAME.equals(transformedName))	return basicClass;

		try {
			ClassReader cr = new ClassReader(basicClass);
			ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
			cr.accept(new CustomVisitor(name, cw, transformedName), 8);
			basicClass = cw.toByteArray();
		}
		catch(Exception e) {
			throw new RuntimeException("failed : ObjHandlerTransformer loading", e);
		}

		return basicClass;
	}

	class CustomVisitor extends ClassVisitor{
		String owner;

		public CustomVisitor(String owner, ClassVisitor cv, String transformedName) {
			super(Opcodes.ASM4, cv);
			this.owner = owner;
		}

		static final String targetMethodName = "<clinit>";
		static final String targetMethodName2 = "register";

		@Override
		public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
			// ObjHandlerのフィールド書き換え
			if(targetMethodName.equals(FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(owner, name, desc))) {
				return new CustomMethodVisitor(api, super.visitMethod(access, name, desc, signature, exceptions));
			}

			if(targetMethodName2.equals(FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(owner, name, desc))) {
				return new CustomMethodVisitor2(api, super.visitMethod(access, name, desc, signature, exceptions));
			}

			return super.visitMethod(access, name, desc, signature, exceptions);
		}
	}

	class CustomMethodVisitor extends MethodVisitor{
		static final String targetFieldName = "condenserMk2";

		public CustomMethodVisitor(int api, MethodVisitor mv) {
			super(api, mv);
		}

		@Override
		public void visitFieldInsn(int opcode, String owner, String name, String desc) {
			// CondenserMk2PEEXに変更
			if(targetFieldName.equals(name)) {
				mv.visitTypeInsn(NEW, "akuto2/blocks/BlockCondenserMk2PEEX");
				mv.visitInsn(DUP);
				mv.visitMethodInsn(INVOKESPECIAL, "akuto2/blocks/BlockCondenserMk2PEEX", "<init>", "()V", false);
			}

			super.visitFieldInsn(opcode, owner, name, desc);
		}
	}

	class CustomMethodVisitor2 extends MethodVisitor{
		public CustomMethodVisitor2(int api, MethodVisitor mv) {
			super(api, mv);
		}

		@Override
		public void visitCode() {
			super.visitCode();

			// ここから追加処理
			mv.visitLdcInsn(Type.getType("Lakuto2/tiles/TileEntityCondenserMk2PEEX;"));
			mv.visitLdcInsn("CondenserMk2TilePEEX");
			mv.visitMethodInsn(INVOKESTATIC, "net/minecraftforge/fml/common/registry/GameRegistry", "registerTileEntity", "(Ljava/lang/Class;Ljava/lang/String;)V", false);
		}
	}
}

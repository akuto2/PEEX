package akuto2.peex.asm.transform;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;

public class TransformerCondenserContainer implements IClassTransformer, Opcodes{
	private static final String TARGETCLASSNAME = "moze_intel/projecte/gameObjs/container/CondenserContainer";

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
			throw new RuntimeException("failed : CondenserMk2ContainerTransformer loading", e);
		}

		return basicClass;
	}

	class CustomVisitor extends ClassVisitor{
		String owner;

		public CustomVisitor(String owner, ClassVisitor cv, String transformedName) {
			super(Opcodes.ASM4, cv);
			this.owner = owner;
		}

		static final String targetMethodName = "getProgressScaled";

		@Override
		public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
			if(targetMethodName.equals(FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(owner, name, desc))) {
				return new CustomMethodVisitor(api, super.visitMethod(access, name, desc, signature, exceptions));
			}

			return super.visitMethod(access, name, desc, signature, exceptions);
		}
	}

	class CustomMethodVisitor extends MethodVisitor{
		int cnt = 0;

		public CustomMethodVisitor(int api, MethodVisitor mv) {
			super(api, mv);
		}

		@Override
		public void visitInsn(int opcode) {
			if(opcode == IRETURN)
				cnt++;

			if(cnt == 3) {
				mv.visitVarInsn(ALOAD, 0);
				mv.visitFieldInsn(GETFIELD, "moze_intel/projecte/gameObjs/container/CondenserContainer", "displayEmc", "I");
				mv.visitInsn(I2L);
				mv.visitLdcInsn(new Long(102L));
				mv.visitInsn(LMUL);
				mv.visitVarInsn(ALOAD, 0);
				mv.visitFieldInsn(GETFIELD, "moze_intel/projecte/gameObjs/container/CondenserContainer", "requiredEmc", "I");
				mv.visitInsn(I2L);
				mv.visitInsn(LDIV);
				mv.visitInsn(L2I);
				return;
			}

			super.visitInsn(opcode);
		}
	}
}

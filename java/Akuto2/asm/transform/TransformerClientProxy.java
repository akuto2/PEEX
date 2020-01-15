package Akuto2.asm.transform;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;

public class TransformerClientProxy implements IClassTransformer, Opcodes{
	private static final String TARGETCLASSNAME = "moze_intel/projecte/proxies/ClientProxy";

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
			throw new RuntimeException("failed : ClientProxy loading", e);
		}
		return basicClass;
	}

	class CustomVisitor extends ClassVisitor{
		String owner;

		public CustomVisitor(String owner, ClassVisitor cv, String transformedName) {
			super(Opcodes.ASM4, cv);
			this.owner = owner;
		}

		static final String targetMethodName = "registerModels";

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
		static final String targetMethodName = "registerBlock";
		static final String targetDesc = "(LAkuto2/blocks/BlockCondenserMk2PEEX;)V";

		public CustomMethodVisitor(int api, MethodVisitor mv) {
			super(api, mv);
		}

		@Override
		public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
			if(targetMethodName.equals(name)) {
				cnt++;
				if(cnt == 5) {
					super.visitMethodInsn(opcode, "Akuto2/asm/PEEXCoreMethodList", "registerCondenserMk2", "()V", false);
					return;
				}
			}

			super.visitMethodInsn(opcode, owner, name, desc, itf);
		}
	}
}

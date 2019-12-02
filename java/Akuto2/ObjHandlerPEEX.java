package Akuto2;

import Akuto2.blocks.BlockAEGUEX;
import Akuto2.blocks.BlockCondenserMk3;
import net.minecraft.block.Block;

public class ObjHandlerPEEX {

	public static Block aeguEX_off;
	public static Block aeguEX_on;
	public static Block condenserMk3;

	public static void register() {
		aeguEX_off = new BlockAEGUEX(false);
		aeguEX_on = new BlockAEGUEX(true);
		condenserMk3 = new BlockCondenserMk3();
	}

	public static void addRecipe() {

	}
}

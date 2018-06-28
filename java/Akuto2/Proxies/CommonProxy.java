package Akuto2.Proxies;

import Akuto2.PlayerData.TransmutationMk2Props;
import net.minecraft.world.World;

public class CommonProxy {
	public CommonProxy(){}
	public void registerRenderInformation(){}

	public void registerTileEntitySpecialRenderer(){}

	public TransmutationMk2Props getClientTransmutationProps() {
		return null;
	}

	public World getClientWorld(){
		return null;
	}
}

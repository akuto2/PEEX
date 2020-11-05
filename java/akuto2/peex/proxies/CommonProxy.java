package akuto2.peex.proxies;

import akuto2.peex.playerdata.TransmutationMk2Props;
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

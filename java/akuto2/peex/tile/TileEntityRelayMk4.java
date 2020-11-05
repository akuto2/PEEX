package akuto2.peex.tile;

import moze_intel.projecte.gameObjs.tiles.RelayMK1Tile;

public class TileEntityRelayMk4 extends RelayMK1Tile {
	public TileEntityRelayMk4(){
		super(20, 100000000, 12800);
	}

	public TileEntityRelayMk4(int sizeInv, int maxEmc, int chargeRate){
		super(sizeInv, maxEmc, chargeRate);
	}

	@Override
	public String getInventoryName(){
		return "relay.Mk4";
	}
}

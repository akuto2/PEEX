package moze_intel.projecte.gameObjs.tiles;

import Akuto2.utils.Constants;

/**
 * パッケージが同じものしかprivateコンストラクタを使えないので仕方なくパッケージを変える
 */
public class TileEntityRelayMk4 extends RelayMK1Tile{
	public TileEntityRelayMk4() {
		super(21, Constants.RELAY_MK4_MAX, Constants.RELAY_MK4_RATE);
	}
}

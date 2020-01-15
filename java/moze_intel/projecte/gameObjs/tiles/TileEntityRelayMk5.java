package moze_intel.projecte.gameObjs.tiles;

import Akuto2.utils.Constants;

/**
 * パッケージが同じものしかprivateコンストラクタを使えないので仕方なくパッケージを変える
 */
public class TileEntityRelayMk5 extends RelayMK1Tile{
	public TileEntityRelayMk5() {
		super(21, Constants.RELAY_MK5_MAX, Constants.RELAY_MK5_RATE);
	}
}

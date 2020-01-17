package moze_intel.projecte.gameObjs.tiles;

import Akuto2.utils.Constants;

/**
 * パッケージが同じものしかprivateコンストラクタを使えないので仕方なくパッケージを変える
 */
public class TileEntityRelayFinal extends TileEntityRelayMk4{
	public TileEntityRelayFinal() {
		super(21, Constants.RELAY_FINAL_MAX, Constants.RELAY_FINAL_RATE);
	}
}

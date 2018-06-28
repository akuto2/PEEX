package Akuto2.TileEntity;

public class TileEntityRelayMk5 extends TileEntityRelayMk4 {
	public TileEntityRelayMk5(){
		super(20, 1000000000, 512000);
	}

	@Override
	public String getInventoryName(){
		return "relay.Mk5";
	}
}

package me.skincraft.hardcoregames.api;

public enum InventorySize {

	SMALLER(9), NORMAL(27), BIG(54);
	
	int inventorySize;
	
	InventorySize(int inventorySize) {
		this.inventorySize = inventorySize;
	}
	
	
	public int getSize(){
		return inventorySize;
	}
	
	
}

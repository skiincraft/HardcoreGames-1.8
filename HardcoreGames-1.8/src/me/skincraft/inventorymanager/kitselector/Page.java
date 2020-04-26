package me.skincraft.inventorymanager.kitselector;

public enum Page {
	
	PAGE_ONE("�aKits Prim�rios [1]", 1,	new String[] 
	{"�aKits Anteriores [0]","�aPr�ximos kits [2]"}), 
	
	PAGE_TWO("�aKits Prim�rios [2]" ,21, new String[] 
	{"�aKits Anteriores [1]","�aPr�ximos kits [3]"});
	
	String pagename; int slot; String[] carpets;
	
	Page(String pagename, int slot, String[] carpets){
		this.slot = slot;
		this.pagename = pagename;
		this.carpets = carpets;
	}
	public int getInt() {
		return slot;
	}
	public String getPageName() {
		return this.pagename;
	}
	public String[] getCarpets() {
		return this.carpets;
	}
}
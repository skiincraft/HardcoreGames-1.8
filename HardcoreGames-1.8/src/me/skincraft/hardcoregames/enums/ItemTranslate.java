package me.skincraft.hardcoregames.enums;

import org.bukkit.Material;

public enum ItemTranslate {
	
	Hand(Material.AIR, "As mãos"),
	
	DiamondSword(Material.DIAMOND_SWORD, "Espada de Diamante"),
	IronSword(Material.IRON_SWORD, "Espada de Ferro"),
	GoldSword(Material.GOLD_SWORD, "Espada de Ouro"),
	StoneSword(Material.STONE_SWORD, "Espada de Stone"),
	WoodSword(Material.WOOD_SWORD, "Espada de Madeira"),
	
	DiamondAxe(Material.DIAMOND_AXE, "Machado de Diamante"),
	IronAxe(Material.IRON_AXE, "Machado de Ferro"),
	GoldAxe(Material.GOLD_AXE, "Machado de Ouro"),
	StoneAxe(Material.STONE_AXE, "Machado de Stone"),
	WoodAxe(Material.WOOD_AXE, "Machado de Madeira"),
	
	DiamondSpade(Material.DIAMOND_SPADE, "Pá de Diamante"),
	IronSpade(Material.IRON_SPADE, "Pá de Ferro"),
	GoldSpade(Material.GOLD_SPADE, "Pá de Ouro"),
	StoneSpade(Material.STONE_SPADE, "Pá de Stone"),
	WoodSpade(Material.WOOD_SPADE, "Pá de Madeira"),
	
	DiamondPickaxe(Material.DIAMOND_PICKAXE, "Picareta de Diamante"),
	IronPickaxe(Material.IRON_PICKAXE, "Picareta de Ferro"),
	GoldPickaxe(Material.GOLD_PICKAXE, "Picareta de Ouro"),
	StonePickaxe(Material.STONE_PICKAXE, "Picareta de Stone"),
	WoodPickaxe(Material.WOOD_PICKAXE, "Picareta de Madeira"),
	
	Stick(Material.STICK, "Graveto"),
	Mapa(Material.MAP, "Mapa"),
	
	CogumeloRed(Material.RED_MUSHROOM, "Cogumelo"),
	CogumeloBrown(Material.BROWN_MUSHROOM, "Cogumelo"),
	Tigela(Material.BOWL, "Pote"),
	Bussola(Material.COMPASS, "Bussola");

	private Material mat;
	private String matname;
	
	private ItemTranslate(Material mate, String name) {
		this.mat = mate;
		this.matname = name;
	}

	public String getMatname() {
		return matname;
	}

	public Material getMat() {
		return mat;
	}
}

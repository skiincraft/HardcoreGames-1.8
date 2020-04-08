package me.skincraft.hardcoregames.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;

public class ItemList {
	
	static Material[] materialList;
	static String[] materialListName;
	
	public static List<Material> itemMaterial = new ArrayList<Material>();
	public static List<String> itemName = new ArrayList<String>();
	
	public static void mat(Material...mate) {
		materialList = mate;
		return;
	}
	
	public static void matName(String...mate) {
		materialListName = mate;
		return;
	}
	
	public static void sendList() {
		mat(ItemTranslate.DiamondSword.getMat(),
				ItemTranslate.DiamondAxe.getMat(),
				   ItemTranslate.DiamondPickaxe.getMat(),
				      ItemTranslate.DiamondSpade.getMat());
		
		matName(ItemTranslate.DiamondSword.getMatname(),
				   ItemTranslate.DiamondAxe.getMatname(),
				       ItemTranslate.DiamondPickaxe.getMatname(),
				          ItemTranslate.DiamondSpade.getMatname());
		
		itemMaterial.addAll(Arrays.asList(materialList));
		itemName.addAll(Arrays.asList(materialListName));
		
		mat(ItemTranslate.IronSword.getMat(),
				ItemTranslate.IronAxe.getMat(),
				   ItemTranslate.IronPickaxe.getMat(),
				      ItemTranslate.IronSpade.getMat());
		
		matName(ItemTranslate.IronSword.getMatname(),
				   ItemTranslate.IronAxe.getMatname(),
				       ItemTranslate.IronPickaxe.getMatname(),
				          ItemTranslate.IronSpade.getMatname());
		
		itemMaterial.addAll(Arrays.asList(materialList));
		itemName.addAll(Arrays.asList(materialListName));
		
		mat(ItemTranslate.GoldSword.getMat(),
				ItemTranslate.GoldAxe.getMat(),
				   ItemTranslate.GoldPickaxe.getMat(),
				      ItemTranslate.GoldSpade.getMat());
		
		matName(ItemTranslate.GoldSword.getMatname(),
				   ItemTranslate.GoldAxe.getMatname(),
				       ItemTranslate.GoldPickaxe.getMatname(),
				          ItemTranslate.GoldSpade.getMatname());
		
		itemMaterial.addAll(Arrays.asList(materialList));
		itemName.addAll(Arrays.asList(materialListName));
		
		mat(ItemTranslate.StoneSword.getMat(),
				ItemTranslate.StoneAxe.getMat(),
				   ItemTranslate.StonePickaxe.getMat(),
				      ItemTranslate.StoneSpade.getMat());
		
		matName(ItemTranslate.StoneSword.getMatname(),
				   ItemTranslate.StoneAxe.getMatname(),
				       ItemTranslate.StonePickaxe.getMatname(),
				          ItemTranslate.StoneSpade.getMatname());
		
		itemMaterial.addAll(Arrays.asList(materialList));
		itemName.addAll(Arrays.asList(materialListName));
		
		mat(ItemTranslate.WoodSword.getMat(),
				ItemTranslate.WoodAxe.getMat(),
				   ItemTranslate.WoodPickaxe.getMat(),
				      ItemTranslate.WoodSpade.getMat());
		
		matName(ItemTranslate.WoodSword.getMatname(),
				   ItemTranslate.WoodAxe.getMatname(),
				       ItemTranslate.WoodPickaxe.getMatname(),
				          ItemTranslate.WoodSpade.getMatname());		
		
		itemMaterial.addAll(Arrays.asList(materialList));
		itemName.addAll(Arrays.asList(materialListName));

	}

}

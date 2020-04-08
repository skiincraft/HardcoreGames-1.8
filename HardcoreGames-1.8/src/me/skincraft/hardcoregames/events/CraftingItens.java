package me.skincraft.hardcoregames.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class CraftingItens {

	public CraftingItens() {
		recraftEvent();
	}
	
	@SuppressWarnings("deprecation")
	public void recraftEvent() {

		ItemStack Resultado = new ItemStack(Material.MUSHROOM_SOUP);
		ItemMeta Cactos = Resultado.getItemMeta();
		Resultado.setItemMeta(Cactos);
		ShapelessRecipe CraftCactos = new ShapelessRecipe(Resultado);

		CraftCactos.addIngredient(1, Material.CACTUS);
		CraftCactos.addIngredient(1, Material.BOWL);
		Bukkit.getServer().addRecipe(CraftCactos);

		ItemMeta Flores = Resultado.getItemMeta();
		Resultado.setItemMeta(Flores);
		ShapelessRecipe CraftFlores = new ShapelessRecipe(Resultado);
		CraftFlores.addIngredient(1, Material.YELLOW_FLOWER);
		CraftFlores.addIngredient(1, Material.RED_ROSE);
		CraftFlores.addIngredient(1, Material.BOWL);
		Bukkit.getServer().addRecipe(CraftFlores);

		ItemMeta Flores1 = Resultado.getItemMeta();
		Resultado.setItemMeta(Flores1);
		ShapelessRecipe CraftFlores1 = new ShapelessRecipe(Resultado);
		CraftFlores1.addIngredient(2, Material.PUMPKIN_SEEDS);
		CraftFlores1.addIngredient(1, Material.BOWL);
		Bukkit.getServer().addRecipe(CraftFlores1);

		ItemMeta Flores11 = Resultado.getItemMeta();
		Resultado.setItemMeta(Flores11);
		ShapelessRecipe CraftFlores11 = new ShapelessRecipe(Resultado);
		CraftFlores11.addIngredient(1, Material.INK_SACK, 3);
		CraftFlores11.addIngredient(1, Material.BOWL);
		Bukkit.getServer().addRecipe(CraftFlores11);
	}

}

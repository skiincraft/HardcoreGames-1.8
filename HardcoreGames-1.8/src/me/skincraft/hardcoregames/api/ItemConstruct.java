package me.skincraft.hardcoregames.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ItemConstruct {

	@SuppressWarnings("unused")
	private Player player;
	private Inventory inventory;
	private PlayerInventory inv;

	private String itemname;
	private String[] lorelist;

	private Material material;
	private int tipo;
	private int quantidade;
	private int slot;

	private Enchantment encantamento;
	private int encantamentoLevel;

	private Color color;

	public ItemConstruct(Material mat, String nome, int type) {
		this.material = mat;
		if (nome != null) {
			this.itemname = nome;
		}

		this.tipo = type;
		this.quantidade = 0;

		this.player = null;
		this.inv = null;

		this.slot = 0;

		this.lorelist = null;
		this.encantamento = null;

		this.color = null;

	}
	
	public ItemConstruct(Material mat, String nome, int slot, Inventory inv, String...lore) {
		this.material = mat;
		if (nome != null) {
			this.itemname = nome;
		}

		this.tipo = 0;
		this.quantidade = 0;

		this.player = null;
		setInventory(inv);

		this.slot = slot;

		this.lorelist = lore;
		this.encantamento = null;

		this.color = null;

	}

	private ItemConstruct armorChcker() {
		if (material == Material.LEATHER_HELMET) {
			leathermeta();
			return this;
		}
		if (material == Material.LEATHER_CHESTPLATE) {
			leathermeta();
			return this;
		}
		if (material == Material.LEATHER_LEGGINGS) {
			leathermeta();
			return this;
		}
		if (material == Material.LEATHER_BOOTS) {
			leathermeta();
			return this;
		}
		return this;
	}

	public String[] arrays(String... strings) {

		return strings;

	}

	public ItemConstruct build() {
		armorChcker();
		checkInventory();

		ItemStack item = new ItemStack(material, quantidade, (short) tipo);
		ItemMeta itemmeta = item.getItemMeta();

		if (itemname != null) {
			itemmeta.setDisplayName(itemname);
		}

		if (lorelist != null) {
			List<String> desc = new ArrayList<>(Arrays.asList(lorelist));
			itemmeta.setLore(desc);
		}

		if (quantidade == 0) {
			item.setAmount(1);
		}

		if (encantamento != null) {
			item.addEnchantment(encantamento, encantamentoLevel);
		}

		item.setItemMeta(itemmeta);
		return this;
	}
	
	public ItemStack buildItemStack() {
		ItemStack item = new ItemStack(material, quantidade, (short) tipo);
		ItemMeta itemmeta = item.getItemMeta();

		if (itemname != null) {
			itemmeta.setDisplayName(itemname);
		}

		if (lorelist != null) {
			List<String> desc = new ArrayList<>(Arrays.asList(lorelist));
			itemmeta.setLore(desc);
		}

		if (quantidade == 0) {
			item.setAmount(1);
		}

		item.setItemMeta(itemmeta);
		return item;
	}

	private ItemConstruct checkInventory() {
		if (inv != null) {
			setItemInventory();
			return this;
		}
		if (inventory != null) {
			setItemInventory();
			return this;
		}
		return this;
	}

	private ItemConstruct leathermeta() {
		ItemStack item = new ItemStack(material, quantidade, (short) tipo);
		LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();

		if (itemname != null) {
			itemmeta.setDisplayName(itemname);
		}

		if (lorelist != null) {
			itemmeta.setLore(Arrays.asList(lorelist));
		}

		if (quantidade == 0) {
			item.setAmount(1);
		}

		if (encantamento != null) {
			item.addEnchantment(encantamento, encantamentoLevel);
		}

		if (color == null) {
			this.color = Color.WHITE;
		}

		item.setItemMeta(itemmeta);
		return this;
	}

	public ItemConstruct setColor(Color cor) {
		this.color = cor;
		return this;
	}

	public ItemConstruct setEncantamento(Enchantment encanto, int level) {
		this.encantamento = encanto;
		this.encantamentoLevel = level;
		return this;
	}

	public ItemConstruct setInventory(Inventory inv) {
		this.inventory = inv;
		return this;
	}

	private ItemConstruct setItemInventory() {

		ItemStack item = new ItemStack(material, quantidade, (short) tipo);
		ItemMeta itemmeta = item.getItemMeta();

		if (itemname != null) {
			itemmeta.setDisplayName(itemname);
		}

		if (lorelist != null) {
			List<String> desc = new ArrayList<>(Arrays.asList(lorelist));
			itemmeta.setLore(desc);
		}

		if (quantidade == 0) {
			item.setAmount(1);
		}

		if (encantamento != null) {
			item.addEnchantment(encantamento, encantamentoLevel);
		}

		item.setItemMeta(itemmeta);
		if (inv == null) {
			inventory.setItem(slot, item);
			return this;
		}

		inv.setItem(slot, item);
		return this;
	}

	public ItemConstruct setItemName(String name) {
		this.itemname = name;
		return this;
	}

	public ItemConstruct setLore(String... lore) {
		this.lorelist = lore;

		return this;
	}
	
	public ItemConstruct setLores(String[] lore) {
		this.lorelist = lore;

		return this;
	}

	public ItemConstruct setMaterial(Material mate) {
		this.material = mate;
		return this;
	}

	public ItemConstruct setPlayerInventory(Player player) {
		this.player = player;
		this.inv = player.getInventory();
		return this;
	}

	public ItemConstruct setQuantidade(int quantidade) {
		this.quantidade = quantidade;
		return this;
	}

	public ItemConstruct setSlot(int slot) {
		this.slot = slot;
		return this;
	}

	public ItemConstruct setType(int type) {
		this.tipo = type;
		return this;
	}

}

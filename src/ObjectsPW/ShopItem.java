package ObjectsPW;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import potionwars.GUI;
import utilsPW.GepUtil;

public class ShopItem {
	public String category;
	public ItemStack item;
	public List<ItemStack> recipe = new ArrayList<>();
	public ShopItem(String Category, ItemStack Item, ItemStack[] Recipe){
		category = Category;
		item = Item;
		for(ItemStack item:Recipe){
			recipe.add(item);
		}
	}
	public ItemStack shopItem(Player p){
		ItemStack Item = new ItemStack(item);
		ItemMeta meta = Item.getItemMeta();
		List<String> ret = new ArrayList<>();
		for(String s:meta.getLore()){
			ret.add(s);
		}
		ret.add(ChatColor.AQUA+"Πεφεος:");
		for(ItemStack s:recipe){
			ret.add(GUI.matToName(s.getType())+ChatColor.GRAY+" x"+GepUtil.boolCol(GUI.testitem(p, s.getType(), s.getAmount()))+s.getAmount());
		}
		meta.setLore(ret);
		Item.setItemMeta(meta);
		return Item;
	}
}

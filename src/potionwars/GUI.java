package potionwars;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import ObjectsPW.Ach;
import ObjectsPW.ShopItem;
import ObjectsPW.Volcano;
import utilsPW.ItemUtil;

public class GUI implements Listener {
	static HashMap<String, ItemStack[]> recipes = new HashMap<>();
	static ArrayList<ShopItem> shop = new ArrayList<>();
	static HashMap<String, ItemStack[][]> uprecs = new HashMap<>();
	static ArrayList<Ach> achs = new ArrayList<>();
	static void setdif(){
		achs.add(new Ach("irbot", ChatColor.AQUA+"���������� �������������", 1, new String[]{ChatColor.GRAY+"�� ���� �� ������..."}));
		achs.add(new Ach("voidfinalgod", ChatColor.AQUA+"���������� �� ��������", 2, new String[]{ChatColor.GRAY+"�������� �� ��������� ������, ����� ��������� ��������� ��������� �� 1."}));
		achs.add(new Ach("voidfinalgod", ChatColor.AQUA+"�������������� �������", 2, new String[]{ChatColor.GRAY+"�������� �� ��������� ������, ����� ��� ������ ���������."}));
		achs.add(new Ach("teamer", ChatColor.AQUA+"����� ������ �������", 2, new String[]{ChatColor.GRAY+"��������� ���-���� ��������� �� ������ 3."}));
		//achs.add(new Ach("fusroda", ChatColor.AQUA+"�������-������� ��������������", 3, new String[]{ChatColor.GRAY+"� ������� ���������� '���-��-��' �������� ������,",ChatColor.GRAY+"� �������� � ���� ���� �� �� ����������."}));
		//achs.add(new Ach("nodie", ChatColor.AQUA+"�� �������� ����! ��������������", 3, new String[]{ChatColor.GRAY+"��������, �� ������ �� ����."}));
		//achs.add(new Ach("lovevoid", ChatColor.AQUA+"����������� �������� ��������������", 4, new String[]{ChatColor.GRAY+"��������� �� ��������� ������ 5 ��� ������."}));
		achs.add(new Ach("mush", ChatColor.AQUA+"�� ��� �����!", 4, new String[]{ChatColor.GRAY+"�������� � ��������� 10 ������ ������."}));
		achs.add(new Ach("uspeh", ChatColor.AQUA+"����� � ������ ���...", 3, new String[]{ChatColor.GRAY+"������ ������, � �������� � ��������� �����",ChatColor.GRAY+"320 ������������ � ����������."}));
		achs.add(new Ach("fastvol", ChatColor.AQUA+"� ��� ������?", 4, new String[]{ChatColor.GRAY+"�������� ������ �����, ��� �� 1 ������."}));
		achs.add(new Ach("norun", ChatColor.AQUA+"��������", 4, new String[]{ChatColor.GRAY+"�������� �����, �� ��������� ���."}));
		achs.add(new Ach("nodievol", ChatColor.AQUA+"�������? ��, �� �������.", 3, new String[]{ChatColor.GRAY+"������������ ��� ������� 10 �����."}));
	}
	public static void setrecipes(){
		setdif();
		
		shop.add(new ShopItem("potion", ItemUtil.createPotion(Material.SPLASH_POTION, 1, ChatColor.AQUA + "���������", new String[]{ChatColor.YELLOW+"����...",ChatColor.LIGHT_PURPLE+"������ ������, �������������� ������."}, 50,50,255, null), new ItemStack[]{new ItemStack(Material.SULPHUR,2),new ItemStack(Material.SUGAR,4)}));
		shop.add(new ShopItem("potion", ItemUtil.createPotion(Material.SPLASH_POTION, 1, ChatColor.GRAY + "������ ������", new String[]{ChatColor.YELLOW+"������ ����� �� �����...",ChatColor.LIGHT_PURPLE+"������ ������, ��������� ������."}, 100,100,100, null), new ItemStack[]{new ItemStack(Material.NETHER_STAR,3),new ItemStack(Material.RED_MUSHROOM,5),new ItemStack(Material.REDSTONE,5)}));
		shop.add(new ShopItem("potion", ItemUtil.createPotion(Material.SPLASH_POTION, 1, ChatColor.WHITE + "�����", new String[]{ChatColor.YELLOW+"����� ���� �� ������...",ChatColor.LIGHT_PURPLE+"������ ������, ����������� ������."}, 255,255,255, null), new ItemStack[]{new ItemStack(Material.NETHER_STAR,5),new ItemStack(Material.SULPHUR,10),new ItemStack(Material.SUGAR,20)}));
		shop.add(new ShopItem("potion", ItemUtil.createPotion(Material.SPLASH_POTION, 1, ChatColor.RED + "���� I", new String[]{ChatColor.GREEN+"������� ����� minecraft."}, 100,0,0, new PotionEffect[]{new PotionEffect(PotionEffectType.HARM, 1, 0)}), new ItemStack[]{new ItemStack(Material.SULPHUR,2),new ItemStack(Material.RED_MUSHROOM,2)}));
		shop.add(new ShopItem("potion", ItemUtil.createPotion(Material.SPLASH_POTION, 1, ChatColor.RED + "���� II", new String[]{ChatColor.GREEN+"������� ����� minecraft."}, 75,0,0, new PotionEffect[]{new PotionEffect(PotionEffectType.HARM, 1, 1)}), new ItemStack[]{new ItemStack(Material.SULPHUR,5),new ItemStack(Material.RED_MUSHROOM,5)}));
		shop.add(new ShopItem("potion", ItemUtil.createPotion(Material.SPLASH_POTION, 1, ChatColor.DARK_RED + "���� III", new String[]{ChatColor.GREEN+"����� ������� ����� minecraft."}, 50,0,0, new PotionEffect[]{new PotionEffect(PotionEffectType.HARM, 1, 2)}), new ItemStack[]{new ItemStack(Material.NETHER_STAR,5),new ItemStack(Material.SULPHUR,10),new ItemStack(Material.RED_MUSHROOM,15)}));
		shop.add(new ShopItem("potion", ItemUtil.createPotion(Material.SPLASH_POTION, 1, ChatColor.DARK_GREEN + "������ I", new String[]{ChatColor.GREEN+"������� ����� minecraft."}, 0,125,0, new PotionEffect[]{new PotionEffect(PotionEffectType.POISON, 300, 0)}), new ItemStack[]{new ItemStack(Material.RED_MUSHROOM,3),new ItemStack(Material.SULPHUR,10)}));
		
		shop.add(new ShopItem("potion", ItemUtil.createPotion(Material.POTION, 1, ChatColor.BLUE + "����� ����", new String[]{ChatColor.YELLOW+"���� ��������...",ChatColor.LIGHT_PURPLE+"+50 ���� � ����. ������ ���� �� ������."}, 175,150,255, null), new ItemStack[]{new ItemStack(Material.NETHER_STAR,1),new ItemStack(Material.GLOWSTONE_DUST,10),new ItemStack(Material.SUGAR,20)}));
		shop.add(new ShopItem("potion", ItemUtil.createPotion(Material.POTION, 1, ChatColor.AQUA + "���������� ����", new String[]{ChatColor.YELLOW+"����-����, ��� ������...",ChatColor.LIGHT_PURPLE+"���� ����������������� � 2 ���� �������."}, 255,255,175, null), new ItemStack[]{new ItemStack(Material.NETHER_STAR,1),new ItemStack(Material.GLOWSTONE_DUST,15),new ItemStack(Material.SUGAR,50)}));
		shop.add(new ShopItem("potion", ItemUtil.createPotion(Material.POTION, 1, ChatColor.LIGHT_PURPLE + "����� I", new String[]{ChatColor.GREEN+"������� ����� minecraft."}, 175,100,100, new PotionEffect[]{new PotionEffect(PotionEffectType.REGENERATION, 600, 0)}), new ItemStack[]{new ItemStack(Material.SEEDS,2)}));
		shop.add(new ShopItem("potion", ItemUtil.createPotion(Material.POTION, 1, ChatColor.LIGHT_PURPLE + "����� II", new String[]{ChatColor.GREEN+"������� ����� minecraft."}, 200,100,100, new PotionEffect[]{new PotionEffect(PotionEffectType.REGENERATION, 600, 1)}), new ItemStack[]{new ItemStack(Material.SEEDS,7),new ItemStack(Material.SUGAR,3)}));
		shop.add(new ShopItem("potion", ItemUtil.createPotion(Material.POTION, 1, ChatColor.LIGHT_PURPLE + "����� III", new String[]{ChatColor.GREEN+"������� ����� minecraft."}, 225,100,100, new PotionEffect[]{new PotionEffect(PotionEffectType.REGENERATION, 600, 2)}), new ItemStack[]{new ItemStack(Material.SEEDS,15),new ItemStack(Material.SUGAR,5)}));
		shop.add(new ShopItem("potion", ItemUtil.createPotion(Material.POTION, 1, ChatColor.WHITE + "�������� I", new String[]{ChatColor.GREEN+"������� ����� minecraft."}, 150,150,200, new PotionEffect[]{new PotionEffect(PotionEffectType.SPEED, 600, 0)}), new ItemStack[]{new ItemStack(Material.SUGAR,3)}));
		
		shop.add(new ShopItem("magic", ItemUtil.create(Material.CLAY_BRICK, 1, 0, ChatColor.GOLD + "����", new String[]{ChatColor.YELLOW+"��� ��������?",ChatColor.LIGHT_PURPLE+"������ ��������� ��� ����� �� �����.",ChatColor.BLUE+"��������� 10 ����."}, null, null), new ItemStack[]{new ItemStack(Material.SEEDS,2),new ItemStack(Material.SUGAR,3)}));
		shop.add(new ShopItem("magic", ItemUtil.create(Material.SNOW_BALL, 1, 0, ChatColor.AQUA + "������ ���������", new String[]{ChatColor.YELLOW+"����...",ChatColor.LIGHT_PURPLE+"������ ��������� ������, �������������� ������.",ChatColor.BLUE+"��������� 20 ����.",ChatColor.RED+"����������: 0,5 ���."}, null, null), new ItemStack[]{new ItemStack(Material.SUGAR,3),new ItemStack(Material.SULPHUR,1),new ItemStack(Material.GLOWSTONE_DUST,1)}));
		shop.add(new ShopItem("magic", ItemUtil.create(Material.FEATHER, 1, 0, ChatColor.BLUE + "������", new String[]{ChatColor.YELLOW+"��������!",ChatColor.LIGHT_PURPLE+"������������ ��� � ����������� �������.",ChatColor.BLUE+"��������� 30 ����.",ChatColor.RED+"����������: 2 ���."}, null, null), new ItemStack[]{new ItemStack(Material.NETHER_STAR,1),new ItemStack(Material.GLOWSTONE_DUST,5)}));
		shop.add(new ShopItem("magic", ItemUtil.create(Material.PAPER, 1, 0, ChatColor.BLUE + "���-��-��", new String[]{ChatColor.YELLOW+"������� ���� ��� ���.",ChatColor.LIGHT_PURPLE+"������������ ���� ��-���� � ����������� �������.",ChatColor.BLUE+"��������� 50 ����.",ChatColor.RED+"����������: 2.5 ���."}, null, null), new ItemStack[]{new ItemStack(Material.NETHER_STAR,1),new ItemStack(Material.REDSTONE,5)}));
		shop.add(new ShopItem("magic", ItemUtil.create(Material.FIREBALL, 1, 0, ChatColor.GOLD + "�������", new String[]{ChatColor.YELLOW+"Magic! Fireball!",ChatColor.LIGHT_PURPLE+"��������� ������� � ����������� �������.",ChatColor.BLUE+"��������� 50 ����.",ChatColor.RED+"����������: 2 ���."}, null, null), new ItemStack[]{new ItemStack(Material.NETHER_STAR,1),new ItemStack(Material.BLAZE_POWDER,5)}));
		
		shop.add(new ShopItem("item", ItemUtil.createTool(Material.LEATHER_HELMET, ChatColor.GOLD + "����� ������", new String[]{ChatColor.YELLOW+"����� ���-���������!",ChatColor.LIGHT_PURPLE+"-10% ������� ���������� ��������."}, null, null), new ItemStack[]{new ItemStack(Material.GLOWSTONE_DUST,2),new ItemStack(Material.RED_MUSHROOM,2),new ItemStack(Material.SEEDS,2)}));
		shop.add(new ShopItem("item", ItemUtil.createTool(Material.LEATHER_LEGGINGS, ChatColor.GOLD + "����� �� 40 ���", new String[]{ChatColor.YELLOW+"����� ���-���������!",ChatColor.LIGHT_PURPLE+"-10% ������������� �����."}, null, null), new ItemStack[]{new ItemStack(Material.GLOWSTONE_DUST,4),new ItemStack(Material.RED_MUSHROOM,5),new ItemStack(Material.SEEDS,3)}));
		shop.add(new ShopItem("item", ItemUtil.createTool(Material.GOLD_HELMET, ChatColor.YELLOW + "��������� �����", new String[]{ChatColor.YELLOW+"������������ ������ �����.",ChatColor.LIGHT_PURPLE+"-30% ������� ���������� ��������.",ChatColor.LIGHT_PURPLE+"+10% ���� ������� ���� ��� �������������."}, null, null), new ItemStack[]{new ItemStack(Material.NETHER_STAR,2),new ItemStack(Material.GLOWSTONE_DUST,5),new ItemStack(Material.SEEDS,5)}));
		shop.add(new ShopItem("item", ItemUtil.createTool(Material.GOLD_LEGGINGS, ChatColor.YELLOW + "������� �����", new String[]{ChatColor.YELLOW+"��� �����������.",ChatColor.LIGHT_PURPLE+"-30% ������������� �����.",ChatColor.GOLD+"���������� �������� "+ChatColor.LIGHT_PURPLE+"���������� ������� �������."}, null, null), new ItemStack[]{new ItemStack(Material.NETHER_STAR,4),new ItemStack(Material.BLAZE_POWDER,5),new ItemStack(Material.SEEDS,10)}));
		shop.add(new ShopItem("item", ItemUtil.createTool(Material.DIAMOND_HELMET, ChatColor.AQUA + "�������� ����", new String[]{ChatColor.YELLOW+"� ������ �����?",ChatColor.LIGHT_PURPLE+"-50% ������� ���������� ��������."}, null, null), new ItemStack[]{new ItemStack(Material.NETHER_STAR,5),new ItemStack(Material.GLOWSTONE_DUST,10),new ItemStack(Material.SEEDS,15)}));
		shop.add(new ShopItem("item", ItemUtil.createTool(Material.DIAMOND_LEGGINGS, ChatColor.AQUA + "�������� ��������������", new String[]{ChatColor.YELLOW+"� ������ �������?",ChatColor.LIGHT_PURPLE+"-50% ������������� �����.",ChatColor.LIGHT_PURPLE+"���������� �����������."}, null, null), new ItemStack[]{new ItemStack(Material.NETHER_STAR,10),new ItemStack(Material.GLOWSTONE_DUST,20),new ItemStack(Material.SEEDS,50)}));
		
		recipes.put("poison",recipe(new Material[]{Material.RED_MUSHROOM,Material.SULPHUR},new Integer[]{3,10}));
		recipes.put("poison2",recipe(new Material[]{Material.RED_MUSHROOM,Material.SULPHUR,Material.REDSTONE},new Integer[]{10,15,5}));
		recipes.put("poison3",recipe(new Material[]{Material.RED_MUSHROOM,Material.SULPHUR,Material.NETHER_STAR},new Integer[]{15,20,5}));
		recipes.put("speed",recipe(new Material[]{Material.SUGAR},new Integer[]{3}));
		recipes.put("speed2",recipe(new Material[]{Material.SUGAR,Material.GLOWSTONE_DUST},new Integer[]{10,5}));
		recipes.put("speed3",recipe(new Material[]{Material.SUGAR,Material.BLAZE_POWDER,Material.NETHER_STAR},new Integer[]{25,5,5}));
		recipes.put("reg2",recipe(new Material[]{Material.SUGAR,Material.SEEDS},new Integer[]{5,15}));
		recipes.put("reg3",recipe(new Material[]{Material.SEEDS,Material.BLAZE_POWDER,Material.NETHER_STAR},new Integer[]{25,10,5}));
		
		recipes.put("grass",recipe(new Material[]{Material.SEEDS},new Integer[]{1}));
		recipes.put("stone",recipe(new Material[]{Material.SUGAR,Material.SULPHUR},new Integer[]{3,3}));
		recipes.put("sts",recipe(new Material[]{Material.SEEDS,Material.SUGAR,Material.SULPHUR},new Integer[]{8,4,5}));
		recipes.put("stp",recipe(new Material[]{Material.SEEDS,Material.SUGAR,Material.SULPHUR},new Integer[]{8,8,15}));
		recipes.put("chest",recipe(new Material[]{Material.NETHER_STAR,Material.GLOWSTONE_DUST,Material.REDSTONE},new Integer[]{2,10,10}));
		recipes.put("hand",recipe(new Material[]{Material.NETHER_STAR,Material.GLOWSTONE_DUST,Material.SUGAR},new Integer[]{5,10,30}));
		recipes.put("stick",recipe(new Material[]{Material.SUGAR,Material.SULPHUR,Material.RED_MUSHROOM},new Integer[]{5,5,5}));
		
		recipes.put("novoid1",recipe(new Material[]{Material.EMERALD,Material.SUGAR},new Integer[]{10, 10}));
		recipes.put("novoid2",recipe(new Material[]{Material.EMERALD,Material.GLOWSTONE_DUST},new Integer[]{15, 15}));
		recipes.put("novoid3",recipe(new Material[]{Material.EMERALD,Material.GLOWSTONE_DUST,Material.NETHER_STAR},new Integer[]{20, 20, 10}));
		uprecs.put("novoid", new ItemStack[][]{recipes.get("novoid1"),recipes.get("novoid2"),recipes.get("novoid3")});
		recipes.put("noclear1",recipe(new Material[]{Material.EMERALD,Material.SUGAR},new Integer[]{15, 10}));
		recipes.put("noclear2",recipe(new Material[]{Material.EMERALD,Material.GLOWSTONE_DUST},new Integer[]{25, 15}));
		recipes.put("noclear3",recipe(new Material[]{Material.EMERALD,Material.GLOWSTONE_DUST,Material.NETHER_STAR},new Integer[]{20, 20, 10}));
		uprecs.put("noclear", new ItemStack[][]{recipes.get("noclear1"),recipes.get("noclear2"),recipes.get("noclear3")});
		recipes.put("clearproc1",recipe(new Material[]{Material.EMERALD,Material.GLOWSTONE_DUST},new Integer[]{15, 10}));
		recipes.put("clearproc2",recipe(new Material[]{Material.EMERALD,Material.BLAZE_POWDER},new Integer[]{25, 15}));
		recipes.put("clearproc3",recipe(new Material[]{Material.EMERALD,Material.BLAZE_POWDER,Material.NETHER_STAR},new Integer[]{20, 20, 10}));
		uprecs.put("clearproc", new ItemStack[][]{recipes.get("clearproc1"),recipes.get("clearproc2"),recipes.get("clearproc3")});
		recipes.put("steal1",recipe(new Material[]{Material.EMERALD,Material.SEEDS},new Integer[]{10, 20}));
		recipes.put("steal2",recipe(new Material[]{Material.EMERALD,Material.REDSTONE,Material.GLOWSTONE_DUST},new Integer[]{15, 15, 15}));
		recipes.put("steal3",recipe(new Material[]{Material.EMERALD,Material.BLAZE_POWDER,Material.NETHER_STAR},new Integer[]{20, 20, 10}));
		uprecs.put("steal", new ItemStack[][]{recipes.get("steal1"),recipes.get("steal2"),recipes.get("steal3")});
		
		recipes.put("voldef1",recipe(new Material[]{Material.EMERALD,Material.REDSTONE,Material.RED_MUSHROOM},new Integer[]{15, 10, 15}));
		recipes.put("voldef2",recipe(new Material[]{Material.EMERALD,Material.REDSTONE,Material.RED_MUSHROOM,Material.NETHER_STAR},new Integer[]{25, 15, 20, 10}));
		recipes.put("voldef3",recipe(new Material[]{Material.EMERALD,Material.REDSTONE,Material.RED_MUSHROOM,Material.NETHER_STAR},new Integer[]{20, 20, 32, 25}));
		uprecs.put("voldef", new ItemStack[][]{recipes.get("voldef1"),recipes.get("voldef2"),recipes.get("voldef3")});
		
		recipes.put("whitep1",recipe(new Material[]{Material.SULPHUR},new Integer[]{2}));
		recipes.put("grayp1",recipe(new Material[]{Material.SUGAR},new Integer[]{2}));
		recipes.put("goldp1",recipe(new Material[]{Material.REDSTONE},new Integer[]{2}));
		recipes.put("redp1",recipe(new Material[]{Material.GLOWSTONE_DUST},new Integer[]{2}));
		recipes.put("seeds1",recipe(new Material[]{Material.RED_MUSHROOM},new Integer[]{1}));
		recipes.put("mus1",recipe(new Material[]{Material.SEEDS},new Integer[]{2}));
		recipes.put("em1",recipe(new Material[]{Material.NETHER_STAR},new Integer[]{2}));
		recipes.put("star1",recipe(new Material[]{Material.EMERALD},new Integer[]{2}));
		recipes.put("mes1",recipe(new Material[]{Material.SUGAR,Material.SULPHUR,Material.GLOWSTONE_DUST,Material.REDSTONE},new Integer[]{2,2,1,1}));
		
	}
	
	static ItemStack[] recipe(Material[] mat, Integer[] count){
		List<ItemStack> items = new ArrayList<>();
		for(int i=0;i<mat.length;i++){
			items.add(new ItemStack(mat[i],count[i]));
		}
		ItemStack[] retu = new ItemStack[items.size()];
		retu = items.toArray(retu);
		return retu;
	}
	public static void shop(Player p){
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.YELLOW + "�������");
		String[] lore1 = {
				ChatColor.YELLOW+"�������� ����� � ����� ������ ����!",
				ChatColor.GREEN+"�������, ����� �������.",
				};
		inv.setItem(10,ItemUtil.create(Material.CHEST, 1, (byte)0, ChatColor.GOLD + "������", lore1, null, null));
		String[] lore2 = {
				ChatColor.YELLOW+"�������� ���� ��������� ������.",
				ChatColor.GREEN+"�������, ����� �������.",
				};
		inv.setItem(12,ItemUtil.create(Material.EXP_BOTTLE, 1, (byte)0, ChatColor.GOLD + "������", lore2, null, null));
		p.openInventory(inv);
	}
	public static void SelectTeam(Player p){
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.LIGHT_PURPLE + "����� �������");
		int i=0;
		for(Volcano vol:main.vols){
			if(main.volp(p.getName())==null||!main.volp(p.getName()).equals(vol))inv.setItem(i, ItemUtil.create(Material.WOOL, 1, 0, vol.team, new String[]{ChatColor.YELLOW+""+vol.teammates.size()+" �������"}, null, null));
			if(main.volp(p.getName())!=null&&main.volp(p.getName()).equals(vol))inv.setItem(i, ItemUtil.create(Material.WOOL, 1, 0, vol.team, new String[]{ChatColor.YELLOW+""+vol.teammates.size()+" �������"}, new Enchantment[]{Enchantment.PROTECTION_ENVIRONMENTAL}, new int[]{10}));
			i++;
		}
		p.openInventory(inv);
	}
	public static void bonus(Player p){
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.YELLOW + "������");
		String selected = "";
		if(main.connection.GetStats(p.getName()).get(0).equals("bottle")){selected = ChatColor.AQUA+"�������!";}
		else{selected = ChatColor.GREEN+"����, ����� �������!";}
		String[] lore1 = {
				ChatColor.YELLOW+"������� ���� � 24 ��������� � �������.",
				ChatColor.LIGHT_PURPLE+"�����������: "+ChatColor.GREEN+"� ������� ������ ���-�� ���������.",
				ChatColor.GRAY+"������ � ������� �� ��������� ������ � ������ 1/4.",
				selected
				};
		inv.setItem(0,ItemUtil.create(Material.GLASS_BOTTLE, 1, (byte)0, ChatColor.GOLD + "�����������", lore1, null, null));
		
		if(main.connection.GetStats(p.getName()).get(0).equals("bridge")){selected = ChatColor.AQUA+"�������!";}
		else{selected = ChatColor.GREEN+"����, ����� �������!";}
		String[] lore2 = {
				ChatColor.YELLOW+"������� ���� � 5 ������������ �����!",
				ChatColor.LIGHT_PURPLE+"�����������: "+ChatColor.GREEN+"25% ���� �� �������� ����.",
				ChatColor.RED+"����������: "+ChatColor.YELLOW+"��������� 2 ������� ��������� "+ChatColor.GOLD+"'�������� ���������'.",
				selected
				};
		inv.setItem(1,ItemUtil.create(Material.CLAY_BRICK, 1, (byte)0, ChatColor.GOLD + "��������� ����", lore2, null, null));

		if(main.connection.GetStats(p.getName()).get(0).equals("star")){selected = ChatColor.AQUA+"�������!";}
		else{selected = ChatColor.GREEN+"����, ����� �������!";}
		String[] lore3 = {
				ChatColor.YELLOW+"������� ���� � 3 �������!",
				ChatColor.LIGHT_PURPLE+"�����������: "+ChatColor.GREEN+"�� ��������� ����� �� 0,5 ��� �������!",
				ChatColor.LIGHT_PURPLE+"��� ���� �����������: "+ChatColor.GREEN+"��� ���������� ���� ��� ���� ������ �����.",
				ChatColor.RED+"����������: "+ChatColor.YELLOW+"��������� 2 ������� ��������� "+ChatColor.DARK_RED+"'Dark Souls'.",
				selected
				};
		inv.setItem(2,ItemUtil.create(Material.NETHER_STAR, 1, (byte)0, ChatColor.GOLD + "������� ������", lore3, null, null));
		
		if(main.connection.GetStats(p.getName()).get(0).equals("ninja")){selected = ChatColor.AQUA+"�������!";}
		else{selected = ChatColor.GREEN+"����, ����� �������!";}
		String[] lore4 = {
				ChatColor.LIGHT_PURPLE+"�����������: "+ChatColor.GREEN+"��� ����������� �� ��������� ���������� ����� ����.",
				ChatColor.RED+"����������: "+ChatColor.YELLOW+"��������� 3 ������� ��������� "+ChatColor.DARK_RED+"'Dark Souls'.",
				selected
				};
		inv.setItem(3,ItemUtil.create(Material.FLINT, 1, (byte)0, ChatColor.GRAY + "������", lore4, null, null));
		
		p.openInventory(inv);
	}
	
	
	public static void cauldron(Player p){
		main.tutor(p, "opca", ChatColor.GREEN+"���, �������, ����� ������ ����� � ����. ����� �� ������: "+ChatColor.AQUA+"������ �����, ��������� ����������, ��������� ��������, � �������� ���� �� ���� �������.");
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.YELLOW + "����");
		String[] lore1 = {
				ChatColor.YELLOW+"����������, � �� ����� ����� ������� ���!",
				ChatColor.GREEN+"�������, ����� �������.",
				};
		inv.setItem(9,ItemUtil.create(Material.POTION, 1, (byte)0, ChatColor.GOLD + "�����", lore1, null, null));
		
		String[] lore2 = {
				ChatColor.YELLOW+"Its a call of magic!",
				ChatColor.GREEN+"�������, ����� �������.",
				};
		inv.setItem(11,ItemUtil.create(Material.BLAZE_ROD, 1, (byte)0, ChatColor.GOLD + "����������", lore2, null, null));
		
		String[] lore3 = {
				ChatColor.YELLOW+"�������, ���������� ����...",
				ChatColor.GREEN+"�������, ����� �������.",
				};
		inv.setItem(13,ItemUtil.create(Material.DIAMOND_CHESTPLATE, 1, (byte)0, ChatColor.GOLD + "��������", lore3, null, null));
		
		String[] lore4 = {
				ChatColor.YELLOW+"��� ������� ����������!",
				ChatColor.GREEN+"�������, ����� �������.",
				};
		inv.setItem(15,ItemUtil.create(Material.DIAMOND, 1, (byte)0, ChatColor.GOLD + "���������", lore4, null, null));
		
		String[] lore5 = {
				ChatColor.YELLOW+"��������� �������!",
				ChatColor.BLUE+"�������� ������ Unlocker-��, � ����.",
				};
		inv.setItem(17,ItemUtil.create(Material.SUGAR, 1, (byte)0, ChatColor.GOLD + "�����������", lore5, null, null));
		
		p.openInventory(inv);
	}
	
	
	
	public static void potions(Player p){
		main.tutor(p, "pots", ChatColor.GREEN+"��� ����� ��� ������ ����. ����� �����, � �������� ������ ����� ��������� ��������� �� ������ �������, �� � �� �������. "+ChatColor.YELLOW+"������� - ����� ������ �������. ��� ��� ������ �� ������ ������������. ������� �������? ��� ���: ������ - ��� ����� �������. "+ChatColor.RED+"�� ��������� �������� � �������� �����. �� ����� �������, ������ ������ ����, �������� � ��� �� ���� � �� ������.");
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.YELLOW + "�����");
		int i=0;
		for(ShopItem si:shop){
			if(si.category.equals("potion")){
				inv.setItem(i, si.shopItem(p));
				i++;
			}
		}
		if(i>0){
			p.openInventory(inv);
			return;
		}
		String[] lore4 = {
				ChatColor.YELLOW+"������� ����� minecraft.",
				};
		i++;
		inv.setItem(3,ItemUtil.createPotion(Material.SPLASH_POTION, 1, ChatColor.GOLD + "����� �����", genlore(recipes.get("dam1"),lore4), 100, 0, 0, null));
		String[] lore5 = {
				ChatColor.YELLOW+"������� ����� minecraft.",
				};
		i++;
		inv.setItem(4,ItemUtil.createPotion(Material.SPLASH_POTION, 1, ChatColor.GOLD + "����� ����� II", genlore(recipes.get("dam2"),lore5), 75, 0, 0, null));
		String[] lore6 = {
				ChatColor.YELLOW+"��� ����� ����� ����������!",
				};
		i++;
		inv.setItem(5,ItemUtil.createPotion(Material.SPLASH_POTION, 1, ChatColor.GOLD + "����� ����� III", genlore(recipes.get("dam3"),lore6), 50, 0, 0, null));
		
		String[] lore7 = {
				ChatColor.YELLOW+"������� ����� minecraft.",
				};
		i++;
		inv.setItem(6,ItemUtil.createPotion(Material.SPLASH_POTION, 1, ChatColor.GOLD + "������", genlore(recipes.get("poison"),lore7), 0, 125, 0, null));
		String[] lore8 = {
				ChatColor.YELLOW+"������� ����� minecraft.",
				};
		i++;
		inv.setItem(7,ItemUtil.createPotion(Material.SPLASH_POTION, 1, ChatColor.GOLD + "������ II", genlore(recipes.get("poison2"),lore8), 0, 100, 0, null));
		String[] lore9 = {
				ChatColor.YELLOW+"������� ����� minecraft.",
				};
		i++;
		inv.setItem(8,ItemUtil.createPotion(Material.SPLASH_POTION, 1, ChatColor.GOLD + "������ III", genlore(recipes.get("poison3"),lore9), 0, 75, 0, null));

		String[] lore10 = {
				ChatColor.YELLOW+"������� ����� minecraft.",
				};
		i++;
		inv.setItem(9,ItemUtil.createPotion(Material.POTION, 1, ChatColor.GOLD + "��������", genlore(recipes.get("speed"),lore10), 150, 150, 200, null));
		String[] lore11 = {
				ChatColor.YELLOW+"������� ����� minecraft.",
				};
		i++;
		inv.setItem(10,ItemUtil.createPotion(Material.POTION, 1, ChatColor.GOLD + "�������� II", genlore(recipes.get("speed2"),lore11), 175, 175, 225, null));
		String[] lore12 = {
				ChatColor.YELLOW+"�����!",
				};
		i++;
		inv.setItem(11,ItemUtil.createPotion(Material.POTION, 1, ChatColor.GOLD + "�������� III", genlore(recipes.get("speed3"),lore12), 200, 200, 255, null));
		String[] lore14 = {
				ChatColor.YELLOW+"������� ����� minecraft.",
				};
		i++;
		inv.setItem(13,ItemUtil.createPotion(Material.POTION, 1, ChatColor.GOLD + "����� II", genlore(recipes.get("reg2"),lore14), 200, 125, 125, null));
		String[] lore15 = {
				ChatColor.YELLOW+"����� ������!",
				};
		i++;
		inv.setItem(14,ItemUtil.createPotion(Material.POTION, 1, ChatColor.GOLD + "����� III", genlore(recipes.get("reg3"),lore15), 225, 150, 150, null));
		
		p.openInventory(inv);
	}
	
	
	
	public static void magic(Player p){
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.YELLOW + "����������");
		int i=0;
		for(ShopItem si:shop){
			if(si.category.equals("magic")){
				inv.setItem(i, si.shopItem(p));
				i++;
			}
		}
		p.openInventory(inv);
	}
	
	
	
	public static void items(Player p){
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.YELLOW + "��������");
		String[] lore1 = {ChatColor.YELLOW+"���������� ����� ����� ������ ������...",};
		inv.setItem(0,ItemUtil.create(Material.GRASS, 2, (byte)0, ChatColor.GOLD + "�����", genlore(recipes.get("grass"),lore1), null, null));
		
		String[] lore2 = {ChatColor.YELLOW+"��� ���� ��� �� �������...",};
		inv.setItem(1,ItemUtil.create(Material.STONE, 2, (byte)0, ChatColor.GOLD + "������", genlore(recipes.get("stone"),lore2), null, null));
		
		String[] lore3 = {ChatColor.YELLOW+"����� �����! ����� �����!",};
		inv.setItem(2,ItemUtil.create(Material.STONE_SPADE, 1, (byte)0, ChatColor.GOLD + "������", genlore(recipes.get("sts"),lore3), null, null));
		
		String[] lore4 = {ChatColor.YELLOW+"�� ����� ������!",};
		inv.setItem(3,ItemUtil.create(Material.STONE_PICKAXE, 1, (byte)0, ChatColor.GOLD + "�����", genlore(recipes.get("stp"),lore4), null, null));
		
		String[] lore5 = {ChatColor.YELLOW+"������ � ������� ������.",};
		inv.setItem(4,ItemUtil.create(Material.CHEST, 1, (byte)0, ChatColor.GOLD + "������", genlore(recipes.get("chest"),lore5), null, null));
		
		String[] lore6 = {ChatColor.YELLOW+"��� ����� ������ ���!",ChatColor.LIGHT_PURPLE+"��������� ������ �������� �����."};
		inv.setItem(5,ItemUtil.create(Material.GOLD_BARDING, 1, (byte)0, ChatColor.GOLD + "������� ������", genlore(recipes.get("hand"),lore6), null, null));
		
		String[] lore7 = {ChatColor.YELLOW+"�� ���� �� ��� ��..."};
		inv.setItem(6,ItemUtil.create(Material.STICK, 1, (byte)0, ChatColor.GOLD + "�����-���������", genlore(recipes.get("stick"),lore7), new Enchantment[]{Enchantment.KNOCKBACK}, new int[]{1}));
		int i=7;
		for(ShopItem si:shop){
			if(si.category.equals("item")){
				inv.setItem(i, si.shopItem(p));
				i++;
			}
		}
		p.openInventory(inv);
	}
	
	
	
	public static void team(Player p){
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.YELLOW + "���������");
		String[] lore1 = {ChatColor.YELLOW+"���-�� ���� �������...",ChatColor.GREEN+"+5%"+ChatColor.LIGHT_PURPLE+" ���� ������� � ���� �� ����,",ChatColor.LIGHT_PURPLE+"������ ��������� ����� �� �������."};
		inv.setItem(0,ItemUtil.create(Material.FEATHER, 1, (byte)0, ChatColor.AQUA + "��������� ���������", genloreup(p,uprecs.get("novoid"),lore1,"novoid"), null, null));
		
		String[] lore2 = {ChatColor.YELLOW+"� ��� ��� ������ ����!",ChatColor.GREEN+"+5%"+ChatColor.LIGHT_PURPLE+" ���� ��-������� ��������� ����� ������."};
		inv.setItem(1,ItemUtil.create(Material.CHEST, 1, (byte)0, ChatColor.AQUA + "����� �� ����", genloreup(p,uprecs.get("noclear"),lore2,"noclear"), null, null));
		
		String[] lore3 = {ChatColor.YELLOW+"��! �����!",ChatColor.GREEN+"+15%"+ChatColor.LIGHT_PURPLE+" ���������, ������� ������������� ����������",ChatColor.LIGHT_PURPLE+"����� ������."};
		inv.setItem(2,ItemUtil.create(Material.SEEDS, 1, (byte)0, ChatColor.AQUA + "�� �� ��������", genloreup(p,uprecs.get("clearproc"),lore3,"clearproc"), null, null));
		
		String[] lore4 = {ChatColor.YELLOW+"����, ���� � �����...",ChatColor.GREEN+"+1 ����/���"+ChatColor.LIGHT_PURPLE+" ���, ��� ���������"+ChatColor.LIGHT_PURPLE+" ����� � ��������"};
		inv.setItem(3,ItemUtil.create(Material.FIREBALL, 1, (byte)0, ChatColor.AQUA + "���", genloreup(p,uprecs.get("voldef"),lore4,"voldef"), null, null));
		
		String[] lore5 = {ChatColor.YELLOW+"��� ����������!",ChatColor.GREEN+"+15% "+ChatColor.LIGHT_PURPLE+" ����� ��� ��������."};
		inv.setItem(4,ItemUtil.create(Material.EMERALD, 1, (byte)0, ChatColor.AQUA + "���������", genloreup(p,uprecs.get("steal"),lore5,"steal"), null, null));
		
		p.openInventory(inv);
	}
	
	public static void ing(Player p){
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.YELLOW + "�����������");
		String[] lore1 = {ChatColor.YELLOW+"������� ��������, � ������...",};
		inv.setItem(0,ItemUtil.create(Material.SUGAR, 1, (byte)0, ChatColor.WHITE + "����� �������", genlore(recipes.get("whitep1"),lore1), null, null));
		String[] lore2 = {ChatColor.YELLOW+"������� ����������, � ������...",};
		inv.setItem(1,ItemUtil.create(Material.SULPHUR, 1, (byte)0, ChatColor.GRAY + "����� �������", genlore(recipes.get("grayp1"),lore2), null, null));
		String[] lore3 = {ChatColor.YELLOW+"�� ��, ��������, �������!",};
		inv.setItem(2,ItemUtil.create(Material.GLOWSTONE_DUST, 1, (byte)0, ChatColor.YELLOW + "������� �������", genlore(recipes.get("goldp1"),lore3), null, null));
		String[] lore4 = {ChatColor.YELLOW+"������ ������...",};
		inv.setItem(3,ItemUtil.create(Material.REDSTONE, 1, (byte)0, ChatColor.RED + "������� �������", genlore(recipes.get("redp1"),lore4), null, null));
		String[] lore5 = {ChatColor.YELLOW+"��� ��� ������� ������ �� ������...",};
		inv.setItem(4,ItemUtil.create(Material.SEEDS, 1, (byte)0, ChatColor.GREEN + "��������� �����", genlore(recipes.get("seeds1"),lore5), null, null));
		String[] lore6 = {ChatColor.YELLOW+"MARIO!",};
		inv.setItem(5,ItemUtil.create(Material.RED_MUSHROOM, 1, (byte)0, ChatColor.RED + "����", genlore(recipes.get("mus1"),lore6), null, null));
		String[] lore7 = {ChatColor.YELLOW+"�� ����� ��� ����...",};
		inv.setItem(6,ItemUtil.create(Material.EMERALD, 1, (byte)0, ChatColor.GREEN + "�������� ������������", genlore(recipes.get("em1"),lore7), null, null));
		String[] lore8 = {ChatColor.YELLOW+"�����-�� �������...",};
		inv.setItem(7,ItemUtil.create(Material.NETHER_STAR, 1, (byte)0, ChatColor.LIGHT_PURPLE + "������ ����� �����", genlore(recipes.get("star1"),lore8), null, null));
		String[] lore9 = {ChatColor.YELLOW+"�������, ������� ������...",};
		inv.setItem(8,ItemUtil.create(Material.BLAZE_POWDER, 1, (byte)0, ChatColor.GOLD + "���������� ��������", genlore(recipes.get("mes1"),lore9), null, null));
		
		p.openInventory(inv);
	}
	public static void achs(Player p){
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.BLUE + "������");
		for(int i=0;i<achs.size();i++){
			inv.setItem(i, achs.get(i).genAch(p));
		}
		p.openInventory(inv);
	}
	//TODO ������ ����� ����
	@EventHandler
	public void select(InventoryClickEvent e){
		if(e.getClickedInventory() != null) {
			Player p = (Player) e.getWhoClicked();
			if(e.getCurrentItem().getItemMeta()!=null){
				
				if(e.getInventory().getName().equalsIgnoreCase(ChatColor.YELLOW + "�������")){
					e.setCancelled(true);
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"������")){
						bonus(p);
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"������")){
						achs(p);
					}
				}
				if(e.getInventory().getName().equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "����� �������")){
					e.setCancelled(true);
					for(Volcano vol:main.vols){
						if(e.getCurrentItem().getItemMeta().getDisplayName().equals(vol.team)){
							if(main.volp(p.getName())!=null)main.volp(p.getName()).teammates.remove(p.getName());
							vol.teammates.add(p.getName());
						}
					}
					SelectTeam(p);
				}
				if(e.getInventory().getName().equalsIgnoreCase(ChatColor.BLUE + "������")||e.getCurrentItem().hasItemMeta()&&e.getCurrentItem().getItemMeta().hasDisplayName()&&e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD+"������ �������")){
					e.setCancelled(true);
				}
				
				if(e.getInventory().getName().equalsIgnoreCase(ChatColor.YELLOW + "������")){
					e.setCancelled(true);
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"�����������")){
						try {main.connection.SetBonus(p.getName(), "bottle");} catch (SQLException e1) {e1.printStackTrace();}
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"��������� ����")){
						achBonus(p, new int[]{0,0,2,0,0}, "bridge");
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"������� ������")){
						achBonus(p, new int[]{0,0,0,0,2}, "star");
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GRAY+"������")){
						achBonus(p, new int[]{0,0,0,0,3}, "ninja");
					}
					bonus(p);
				}
				
				if(e.getInventory().getName().equalsIgnoreCase(ChatColor.YELLOW + "����")){
					e.setCancelled(true);
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"�����")){
						potions(p);
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"����������")){
						magic(p);
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"��������")){
						items(p);
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"���������")){
						team(p);
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"�����������")){
						if(p.hasPermission("unlicker")){ing(p);}
						else{p.sendMessage(ChatColor.YELLOW+"��� ����� ������� ��������� ������ Unlocker ��� ����.");}
					}
				}
				
				
				
				if(e.getInventory().getName().equalsIgnoreCase(ChatColor.YELLOW + "�����")){
					e.setCancelled(true);
					int i=0;
					for(ShopItem si:shop){
						if(si.category.equals("potion")){
							if(i==e.getSlot()){
								buyitem(p,si.recipe.toArray(new ItemStack[si.recipe.size()]),si.item);
								potions(p);
								return;
							}
							i++;
						}
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"������ ������")){
						buyitem(p,recipes.get("diesmoke"),ItemUtil.createPotion(Material.SPLASH_POTION, 1, "������ ������", null, 100,100,100, null));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"����� ���")){
						PotionEffect[] pe = {new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 600, 1)};
						buyitem(p,recipes.get("gods"),ItemUtil.createPotion(Material.POTION, 1, "����� ���", null, 25, 0, 135, pe));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"����� �����")){
						PotionEffect[] pe = {new PotionEffect(PotionEffectType.HARM, 1, 0)};
						buyitem(p,recipes.get("dam1"),ItemUtil.createPotion(Material.SPLASH_POTION, 1, "����� �����", null, 100, 0, 0, pe));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"����� ����� II")){
						PotionEffect[] pe = {new PotionEffect(PotionEffectType.HARM, 1, 1)};
						buyitem(p,recipes.get("dam2"),ItemUtil.createPotion(Material.SPLASH_POTION, 1, "����� ����� II", null, 75, 0, 0, pe));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"����� ����� III")){
						PotionEffect[] pe = {new PotionEffect(PotionEffectType.HARM, 1, 2)};
						buyitem(p,recipes.get("dam3"),ItemUtil.createPotion(Material.SPLASH_POTION, 1, "����� ����� III", null, 50, 0, 0, pe));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"������")){
						PotionEffect[] pe = {new PotionEffect(PotionEffectType.POISON, 200, 0)};
						buyitem(p,recipes.get("poison"),ItemUtil.createPotion(Material.SPLASH_POTION, 1, ChatColor.DARK_GREEN+"������", null, 0, 125, 0, pe));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"������ II")){
						PotionEffect[] pe = {new PotionEffect(PotionEffectType.POISON, 200, 1)};
						buyitem(p,recipes.get("poison2"),ItemUtil.createPotion(Material.SPLASH_POTION, 1, ChatColor.DARK_GREEN+"������ II", null, 0, 100, 0, pe));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"������ III")){
						PotionEffect[] pe = {new PotionEffect(PotionEffectType.POISON, 200, 2)};
						buyitem(p,recipes.get("poison3"),ItemUtil.createPotion(Material.SPLASH_POTION, 1, ChatColor.DARK_GREEN+"������ III", null, 0, 75, 0, pe));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"��������")){
						PotionEffect[] pe = {new PotionEffect(PotionEffectType.SPEED, 600, 0)};
						buyitem(p,recipes.get("speed"),ItemUtil.createPotion(Material.POTION, 1, ChatColor.AQUA+"��������", null, 150, 150, 200, pe));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"�������� II")){
						PotionEffect[] pe = {new PotionEffect(PotionEffectType.SPEED, 600, 1)};
						buyitem(p,recipes.get("speed2"),ItemUtil.createPotion(Material.POTION, 1, ChatColor.AQUA+"�������� II", null, 175, 175, 225, pe));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"�������� III")){
						PotionEffect[] pe = {new PotionEffect(PotionEffectType.SPEED, 600, 2)};
						buyitem(p,recipes.get("speed3"),ItemUtil.createPotion(Material.POTION, 1, ChatColor.AQUA+"�������� III", null, 200, 200, 255, pe));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"�����")){
						PotionEffect[] pe = {new PotionEffect(PotionEffectType.REGENERATION, 600, 0)};
						buyitem(p,recipes.get("reg1"),ItemUtil.createPotion(Material.POTION, 1, ChatColor.LIGHT_PURPLE+"�����", null, 175, 100, 100, pe));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"����� II")){
						PotionEffect[] pe = {new PotionEffect(PotionEffectType.REGENERATION, 600, 1)};
						buyitem(p,recipes.get("reg2"),ItemUtil.createPotion(Material.POTION, 1, ChatColor.LIGHT_PURPLE+"����� II", null, 200, 125, 125, pe));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"����� III")){
						PotionEffect[] pe = {new PotionEffect(PotionEffectType.REGENERATION, 600, 2)};
						buyitem(p,recipes.get("reg3"),ItemUtil.createPotion(Material.POTION, 1, ChatColor.LIGHT_PURPLE+"����� III", null, 225, 150, 150, pe));
					}
				}
				
				
				if(e.getInventory().getName().equalsIgnoreCase(ChatColor.YELLOW + "����������")){
					e.setCancelled(true);
					int i=0;
					for(ShopItem si:shop){
						if(si.category.equals("magic")){
							if(i==e.getSlot()){
								buyitem(p,si.recipe.toArray(new ItemStack[si.recipe.size()]),si.item);
								magic(p);
								return;
							}
							i++;
						}
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"����")){
						buyitem(p,recipes.get("bridge"),ItemUtil.create(Material.CLAY_BRICK, 1, (byte)0, ChatColor.GREEN+"����", null, null, null));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"���-��-��!")){
						buyitem(p,recipes.get("fusroda"),ItemUtil.create(Material.PAPER, 1, (byte)0, ChatColor.AQUA+"���-��-��!", null, null, null));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"�������� ���")){
						buyitem(p,recipes.get("fireball"),ItemUtil.create(Material.FIREBALL, 1, (byte)0, ChatColor.GOLD+"�������� ���", null, null, null));
					}
				}
				
				
				if(e.getInventory().getName().equalsIgnoreCase(ChatColor.YELLOW + "��������")){
					e.setCancelled(true);
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"�����")){
						buyitem(p,recipes.get("grass"),ItemUtil.create(Material.GRASS, 2, (byte)0, ChatColor.GREEN+"�����", null, null, null));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"������")){
						buyitem(p,recipes.get("stone"),ItemUtil.create(Material.STONE, 2, (byte)0, ChatColor.GRAY+"������", null, null, null));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"������")){
						buyitem(p,recipes.get("sts"),ItemUtil.create(Material.STONE_SPADE, 1, (byte)0, ChatColor.GOLD+"������", null, null, null));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"�����")){
						buyitem(p,recipes.get("stp"),ItemUtil.create(Material.STONE_PICKAXE, 1, (byte)0, ChatColor.GOLD+"�����", null, null, null));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"������")){
						buyitem(p,recipes.get("chest"),ItemUtil.create(Material.CHEST, 1, (byte)0, ChatColor.GOLD+"������", null, null, null));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"������� ������")){
						buyitem(p,recipes.get("hand"),ItemUtil.create(Material.GOLD_BARDING, 1, (byte)0, ChatColor.GOLD+"������� ������", null, null, null));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"�����-���������")){
						buyitem(p,recipes.get("stick"),ItemUtil.create(Material.STICK, 1, (byte)0, ChatColor.GOLD + "�����-���������", null, new Enchantment[]{Enchantment.KNOCKBACK}, new int[]{1}));
					}
					int i=7;
					for(ShopItem si:shop){
						if(si.category.equals("item")){
							if(i==e.getSlot()){
								buyitem(p,si.recipe.toArray(new ItemStack[si.recipe.size()]),si.item);
								items(p);
								return;
							}
							i++;
						}
					}
				}
				
				
				if(e.getInventory().getName().equalsIgnoreCase(ChatColor.YELLOW + "���������")){
					e.setCancelled(true);
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.AQUA+"��������� ���������")){
						buyUpgrade(p,"novoid",uprecs.get("novoid"),ChatColor.AQUA+"'��������� ���������'");
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.AQUA+"����� �� ����")){
						buyUpgrade(p,"noclear",uprecs.get("noclear"),ChatColor.AQUA+"'����� �� ����'");
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.AQUA+"�� �� ��������")){
						buyUpgrade(p,"clearproc",uprecs.get("clearproc"),ChatColor.AQUA+"'�� �� ��������'");
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.AQUA+"���")){
						buyUpgrade(p,"voldef",uprecs.get("voldef"),ChatColor.AQUA+"'���'");
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.AQUA+"���������")){
						buyUpgrade(p,"steal",uprecs.get("steal"),ChatColor.AQUA+"'���������'");
					}
				}
				
				if(e.getInventory().getName().equalsIgnoreCase(ChatColor.YELLOW + "�����������")){
					e.setCancelled(true);
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.WHITE+"����� �������")){
						buyitem(p,recipes.get("whitep1"),ItemUtil.create(Material.SUGAR, 1, (byte)0, matToName(Material.SUGAR), null, null, null));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GRAY+"����� �������")){
						buyitem(p,recipes.get("grayp1"),ItemUtil.create(Material.SULPHUR, 1, (byte)0, matToName(Material.SULPHUR), null, null, null));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.YELLOW+"������� �������")){
						buyitem(p,recipes.get("goldp1"),ItemUtil.create(Material.GLOWSTONE_DUST, 1, (byte)0, matToName(Material.GLOWSTONE_DUST), null, null, null));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED+"������� �������")){
						buyitem(p,recipes.get("redp1"),ItemUtil.create(Material.REDSTONE, 1, (byte)0, matToName(Material.REDSTONE), null, null, null));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN+"��������� �����")){
						buyitem(p,recipes.get("seeds1"),ItemUtil.create(Material.SEEDS, 1, (byte)0, matToName(Material.SEEDS), null, null, null));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED+"����")){
						buyitem(p,recipes.get("mus1"),ItemUtil.create(Material.RED_MUSHROOM, 1, (byte)0, matToName(Material.RED_MUSHROOM), null, null, null));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN+"�������� ������������")){
						buyitem(p,recipes.get("em1"),ItemUtil.create(Material.EMERALD, 1, (byte)0, matToName(Material.EMERALD), null, null, null));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.LIGHT_PURPLE+"������ ����� �����")){
						buyitem(p,recipes.get("star1"),ItemUtil.create(Material.NETHER_STAR, 1, (byte)0, matToName(Material.NETHER_STAR), null, null, null));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"���������� ��������")){
						buyitem(p,recipes.get("mes1"),ItemUtil.create(Material.BLAZE_POWDER, 1, (byte)0, matToName(Material.BLAZE_POWDER), null, null, null));
					}
				}
			}
		}
	}
	public static boolean testitem(Player p, Material mat, int count){
		int have=0;
		for(int i=0;i<p.getInventory().getSize();i++){
			if(p.getInventory().getItem(i) != null) {
				if(p.getInventory().getItem(i).getType().equals(mat)){
					have+=p.getInventory().getItem(i).getAmount();
				}
			}
		}
		if(have>=count){
			return true;
		}
		return false;
	}
	static void buyitem(Player p, ItemStack[] st, ItemStack item){
		if(!p.getGameMode().equals(GameMode.CREATIVE))
		for(ItemStack str: st){
			if(testitem(p, str.getType(), str.getAmount())==false){
				noitems(p,st);
				return;
			}
		}
		if(!p.getGameMode().equals(GameMode.CREATIVE))
		for(ItemStack str: st){
			remitem(p, str.getType(), str.getAmount());
		}
		p.getInventory().addItem(item);
	}
	static void remitem(Player p, Material mat, Integer count){
		for(int i=0;i<p.getInventory().getSize();i++){
			if(p.getInventory().getItem(i) != null)
			if(p.getInventory().getItem(i).getType().equals(mat)){
				ItemStack item = p.getInventory().getItem(i);
				int amount = item.getAmount();
				if(amount>=count){
					item.setAmount(amount-count);
					return;
				}
				if(amount<count){
					count-=amount;
					item.setAmount(0);
				}
				if(count<=0){
					return;
				}
			}
		}
	}
	public static String matToName(Material mat){
		if(mat.equals(Material.SUGAR)){return ChatColor.WHITE+"����� �������";}
		if(mat.equals(Material.NETHER_STAR)){return ChatColor.LIGHT_PURPLE+"������ ����� �����";}
		if(mat.equals(Material.GLOWSTONE_DUST)){return ChatColor.YELLOW+"������� �������";}
		if(mat.equals(Material.REDSTONE)){return ChatColor.RED+"������� �������";}
		if(mat.equals(Material.RED_MUSHROOM)){return ChatColor.RED+"����";}
		if(mat.equals(Material.SEEDS)){return ChatColor.GREEN+"��������� �����";}
		if(mat.equals(Material.SULPHUR)){return ChatColor.GRAY+"����� �������";}
		if(mat.equals(Material.EMERALD)){return ChatColor.GREEN+"�������� ������������";}
		if(mat.equals(Material.BLAZE_POWDER)){return ChatColor.GOLD+"���������� ��������";}
		if(mat.equals(Material.GLASS_BOTTLE)){return ChatColor.WHITE+"�������";}
		
		return ChatColor.RED+"��� ��������. "+mat;
	}
	static String[] genlore(ItemStack[] recipe, String[] lore){
		List<String> ret = new ArrayList<>();
		for(String s:lore){
			ret.add(s);
		}
		ret.add(ChatColor.AQUA+"������:");
		for(ItemStack s:recipe){
			ret.add(matToName(s.getType())+ChatColor.GRAY+" x"+ChatColor.GREEN+s.getAmount());
		}
		String[] retu = new String[ret.size()];
		retu = ret.toArray(retu);
		return retu;
	}
	static String[] genloreup(Player p, ItemStack[][] recipes, String[] lore, String upgrade){
		int now = main.getUpgrade(main.volp(p.getName()),upgrade);
		List<String> ret = new ArrayList<>();
		for(String s:lore){
			ret.add(s);
		}
		ret.add(ChatColor.GOLD+"�������: "+ChatColor.GREEN+now+ChatColor.GRAY+"/"+recipes.length);
		if(now==-1){
			ret.add(ChatColor.RED+"����� ������� ��� � ����.");
		}
		else if(now+1<=recipes.length){
			ret.add(ChatColor.AQUA+"������:");
			for(ItemStack s:recipes[now]){
				ret.add(matToName(s.getType())+ChatColor.GRAY+" x"+ChatColor.GREEN+s.getAmount());
			}
		}
		else{
			ret.add(ChatColor.GREEN+"��������!");
		}
		String[] retu = new String[ret.size()];
		retu = ret.toArray(retu);
		return retu;
	}
	public static void buyUpgrade(Player p, String upgrade, ItemStack[][] prices, String message){
		int now = main.getUpgrade(main.volp(p.getName()),upgrade);
		if(now==-1){
			p.sendMessage(ChatColor.RED+"����� ������� ��� � ����. �������� �� ���� �������������.");
			return;
		}
		if(now>=0){
			ItemStack[] price = prices[now];
			if(!p.getGameMode().equals(GameMode.CREATIVE))
			for(ItemStack str: price){
				if(GUI.testitem(p, str.getType(), str.getAmount())==false){
					noitems(p,price);
					return;
				}
			}
			main.volp(p.getName()).upgrades.put(upgrade, now+1);
			if(main.team==true){
				Volcano vol=main.volp(p.getName());
				for(String st:vol.teammates){
					Player pl = Bukkit.getPlayer(st);
					if(pl==null)continue;
					pl.sendMessage(ChatColor.GREEN+"����� "+ChatColor.DARK_GREEN+p.getName()+ChatColor.GREEN+" ������� "+ChatColor.YELLOW+message+ChatColor.GREEN+" �� ������ "+ChatColor.BLUE+(now+1)+ChatColor.GREEN+".");
					pl.playSound(pl.getLocation(), Sound.ENTITY_VILLAGER_YES, 2, 2);
				}
			}
			if(now==2)main.ach(p, "teamer");
			if(!p.getGameMode().equals(GameMode.CREATIVE))
			for(ItemStack str: price){
				remitem(p, str.getType(), str.getAmount());
			}
			team(p);
		}
	}
	static void noitems(Player p, ItemStack[] items){
		String message=ChatColor.RED+"�� ������� ������������: ";
		for(ItemStack item:items){
			int count=0;
			for(int i=0;i<p.getInventory().getSize();i++){
				if(p.getInventory().getItem(i)!=null&&p.getInventory().getItem(i).getType().equals(item.getType())){
					count+=p.getInventory().getItem(i).getAmount();
				}
			}
			if(count<item.getAmount()){
				message=message+matToName(item.getType())+" x"+(item.getAmount()-count)+"; ";
			}
		}
		p.sendMessage(message);
	}
	static ArrayList<Integer> getDifs(Player p){
		ArrayList<Integer> ret = new ArrayList<>();
		int total=0;
		int easy=0;
		int normal=0;
		int hard=0;
		int darksouls=0;
		for(String st:main.stringToArrayList(main.connection.GetStats(p.getName()).get(2))){
			total++;
			boolean found = false;
			for(Ach ach:achs){
				found = true;
				if(ach.id.equals(st)){
					if(ach.dif==1)easy++;
					else if(ach.dif==2)normal++;
					else if(ach.dif==3)hard++;
					else if(ach.dif==4)darksouls++;
				}
			}
			if(!found)p.sendMessage(ChatColor.RED+"������ ����������� ��������� ������ � ������� ��������� "+ChatColor.YELLOW+st+ChatColor.RED+". ������� �������� �� ���� �����.");
		}
		ret.add(total);ret.add(easy);ret.add(normal);ret.add(hard);ret.add(darksouls);
		return ret;
	}
	static void achBonus(Player p, int[] difs, String bonus){
		ArrayList<Integer> dfs = getDifs(p);
		for(int i=0;i<5;i++){
			if(dfs.get(i)<difs[i]){
				p.sendMessage(ChatColor.RED+"� ��� ������������ ������.");
				return;
			}
		}
		try {main.connection.SetBonus(p.getName(), bonus);} catch (SQLException e1) {e1.printStackTrace();}
	}
}
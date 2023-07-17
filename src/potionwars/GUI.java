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
		achs.add(new Ach("irbot", ChatColor.AQUA+"Величайшая неожиданность", 1, new String[]{ChatColor.GRAY+"Вы сами всё поймёте..."}));
		achs.add(new Ach("voidfinalgod", ChatColor.AQUA+"Счасливчик из ниоткуда", 2, new String[]{ChatColor.GRAY+"Спастись от пустотной смерти, когда пустотное восстание прокачено на 1."}));
		achs.add(new Ach("voidfinalgod", ChatColor.AQUA+"Беспроигрышное падение", 2, new String[]{ChatColor.GRAY+"Спастись от пустотной смерти, когда ваш вулкан уничтожен."}));
		achs.add(new Ach("teamer", ChatColor.AQUA+"Самый ценный союзник", 2, new String[]{ChatColor.GRAY+"Прокачать что-либо командное до уровня 3."}));
		//achs.add(new Ach("fusroda", ChatColor.AQUA+"Довакин-дуэлянт НЕРОБИТПОКАЧТО", 3, new String[]{ChatColor.GRAY+"С помощью заклинания 'Фус-Ро-Да' откинуть игрока,",ChatColor.GRAY+"у которого в руке было то же заклинание."}));
		//achs.add(new Ach("nodie", ChatColor.AQUA+"Не трогайте меня! НЕРОБИТПОКАЧТО", 3, new String[]{ChatColor.GRAY+"Победите, не умерев ни разу."}));
		//achs.add(new Ach("lovevoid", ChatColor.AQUA+"Бессмертный одиночка НЕРОБИТПОКАЧТО", 4, new String[]{ChatColor.GRAY+"Спаситесь от пустотной смерти 5 раз подряд."}));
		achs.add(new Ach("mush", ChatColor.AQUA+"Не ешь грибы!", 4, new String[]{ChatColor.GRAY+"Соберите в инвентаре 10 стаков грибов."}));
		achs.add(new Ach("uspeh", ChatColor.AQUA+"Поцан к успеху шёл...", 3, new String[]{ChatColor.GRAY+"Убейте игрока, у которого в инвентаре более",ChatColor.GRAY+"320 инградиентов и материалов."}));
		achs.add(new Ach("fastvol", ChatColor.AQUA+"А где вулкан?", 4, new String[]{ChatColor.GRAY+"Сломайте вулкан менее, чем за 1 минуту."}));
		achs.add(new Ach("norun", ChatColor.AQUA+"Черепаха", 4, new String[]{ChatColor.GRAY+"Выиграть раунд, не используя бег."}));
		achs.add(new Ach("nodievol", ChatColor.AQUA+"Вулканы? Не, не слышали.", 3, new String[]{ChatColor.GRAY+"Продержаться без вулкана 10 минут."}));
	}
	public static void setrecipes(){
		setdif();
		
		shop.add(new ShopItem("potion", ItemUtil.createPotion(Material.SPLASH_POTION, 1, ChatColor.AQUA + "Заморозка", new String[]{ChatColor.YELLOW+"Бррр...",ChatColor.LIGHT_PURPLE+"Создаёт облако, замораживающее врагов."}, 50,50,255, null), new ItemStack[]{new ItemStack(Material.SULPHUR,2),new ItemStack(Material.SUGAR,4)}));
		shop.add(new ShopItem("potion", ItemUtil.createPotion(Material.SPLASH_POTION, 1, ChatColor.GRAY + "Завеса смерти", new String[]{ChatColor.YELLOW+"Смерть придёт за тобой...",ChatColor.LIGHT_PURPLE+"Создаёт завесу, убивающую врагов."}, 100,100,100, null), new ItemStack[]{new ItemStack(Material.NETHER_STAR,3),new ItemStack(Material.RED_MUSHROOM,5),new ItemStack(Material.REDSTONE,5)}));
		shop.add(new ShopItem("potion", ItemUtil.createPotion(Material.SPLASH_POTION, 1, ChatColor.WHITE + "Туман", new String[]{ChatColor.YELLOW+"Вышел ёжик из тумана...",ChatColor.LIGHT_PURPLE+"Создаёт завесу, ослепляющую врагов."}, 255,255,255, null), new ItemStack[]{new ItemStack(Material.NETHER_STAR,5),new ItemStack(Material.SULPHUR,10),new ItemStack(Material.SUGAR,20)}));
		shop.add(new ShopItem("potion", ItemUtil.createPotion(Material.SPLASH_POTION, 1, ChatColor.RED + "Урон I", new String[]{ChatColor.GREEN+"Обычное зелье minecraft."}, 100,0,0, new PotionEffect[]{new PotionEffect(PotionEffectType.HARM, 1, 0)}), new ItemStack[]{new ItemStack(Material.SULPHUR,2),new ItemStack(Material.RED_MUSHROOM,2)}));
		shop.add(new ShopItem("potion", ItemUtil.createPotion(Material.SPLASH_POTION, 1, ChatColor.RED + "Урон II", new String[]{ChatColor.GREEN+"Обычное зелье minecraft."}, 75,0,0, new PotionEffect[]{new PotionEffect(PotionEffectType.HARM, 1, 1)}), new ItemStack[]{new ItemStack(Material.SULPHUR,5),new ItemStack(Material.RED_MUSHROOM,5)}));
		shop.add(new ShopItem("potion", ItemUtil.createPotion(Material.SPLASH_POTION, 1, ChatColor.DARK_RED + "Урон III", new String[]{ChatColor.GREEN+"Почти обычное зелье minecraft."}, 50,0,0, new PotionEffect[]{new PotionEffect(PotionEffectType.HARM, 1, 2)}), new ItemStack[]{new ItemStack(Material.NETHER_STAR,5),new ItemStack(Material.SULPHUR,10),new ItemStack(Material.RED_MUSHROOM,15)}));
		shop.add(new ShopItem("potion", ItemUtil.createPotion(Material.SPLASH_POTION, 1, ChatColor.DARK_GREEN + "Отрава I", new String[]{ChatColor.GREEN+"Обычное зелье minecraft."}, 0,125,0, new PotionEffect[]{new PotionEffect(PotionEffectType.POISON, 300, 0)}), new ItemStack[]{new ItemStack(Material.RED_MUSHROOM,3),new ItemStack(Material.SULPHUR,10)}));
		
		shop.add(new ShopItem("potion", ItemUtil.createPotion(Material.POTION, 1, ChatColor.BLUE + "Зелье маны", new String[]{ChatColor.YELLOW+"Мана небесная...",ChatColor.LIGHT_PURPLE+"+50 маны и макс. запаса маны на минуту."}, 175,150,255, null), new ItemStack[]{new ItemStack(Material.NETHER_STAR,1),new ItemStack(Material.GLOWSTONE_DUST,10),new ItemStack(Material.SUGAR,20)}));
		shop.add(new ShopItem("potion", ItemUtil.createPotion(Material.POTION, 1, ChatColor.AQUA + "Ускоритель маны", new String[]{ChatColor.YELLOW+"Мана-мана, мой нектар...",ChatColor.LIGHT_PURPLE+"Мана восстанавливается в 2 раза быстрее."}, 255,255,175, null), new ItemStack[]{new ItemStack(Material.NETHER_STAR,1),new ItemStack(Material.GLOWSTONE_DUST,15),new ItemStack(Material.SUGAR,50)}));
		shop.add(new ShopItem("potion", ItemUtil.createPotion(Material.POTION, 1, ChatColor.LIGHT_PURPLE + "Реген I", new String[]{ChatColor.GREEN+"Обычное зелье minecraft."}, 175,100,100, new PotionEffect[]{new PotionEffect(PotionEffectType.REGENERATION, 600, 0)}), new ItemStack[]{new ItemStack(Material.SEEDS,2)}));
		shop.add(new ShopItem("potion", ItemUtil.createPotion(Material.POTION, 1, ChatColor.LIGHT_PURPLE + "Реген II", new String[]{ChatColor.GREEN+"Обычное зелье minecraft."}, 200,100,100, new PotionEffect[]{new PotionEffect(PotionEffectType.REGENERATION, 600, 1)}), new ItemStack[]{new ItemStack(Material.SEEDS,7),new ItemStack(Material.SUGAR,3)}));
		shop.add(new ShopItem("potion", ItemUtil.createPotion(Material.POTION, 1, ChatColor.LIGHT_PURPLE + "Реген III", new String[]{ChatColor.GREEN+"Обычное зелье minecraft."}, 225,100,100, new PotionEffect[]{new PotionEffect(PotionEffectType.REGENERATION, 600, 2)}), new ItemStack[]{new ItemStack(Material.SEEDS,15),new ItemStack(Material.SUGAR,5)}));
		shop.add(new ShopItem("potion", ItemUtil.createPotion(Material.POTION, 1, ChatColor.WHITE + "Скорость I", new String[]{ChatColor.GREEN+"Обычное зелье minecraft."}, 150,150,200, new PotionEffect[]{new PotionEffect(PotionEffectType.SPEED, 600, 0)}), new ItemStack[]{new ItemStack(Material.SUGAR,3)}));
		
		shop.add(new ShopItem("magic", ItemUtil.create(Material.CLAY_BRICK, 1, 0, ChatColor.GOLD + "Мост", new String[]{ChatColor.YELLOW+"Кто бригадир?",ChatColor.LIGHT_PURPLE+"Создаёт платформу при клике по блоку.",ChatColor.BLUE+"Расходует 10 маны."}, null, null), new ItemStack[]{new ItemStack(Material.SEEDS,2),new ItemStack(Material.SUGAR,3)}));
		shop.add(new ShopItem("magic", ItemUtil.create(Material.SNOW_BALL, 1, 0, ChatColor.AQUA + "Облако заморозки", new String[]{ChatColor.YELLOW+"Бррр...",ChatColor.LIGHT_PURPLE+"Создаёт небольшое облако, замораживающее врагов.",ChatColor.BLUE+"Расходует 20 маны.",ChatColor.RED+"Блокировка: 0,5 сек."}, null, null), new ItemStack[]{new ItemStack(Material.SUGAR,3),new ItemStack(Material.SULPHUR,1),new ItemStack(Material.GLOWSTONE_DUST,1)}));
		shop.add(new ShopItem("magic", ItemUtil.create(Material.FEATHER, 1, 0, ChatColor.BLUE + "Прыжок", new String[]{ChatColor.YELLOW+"Полетели!",ChatColor.LIGHT_PURPLE+"Подбрасывает вас в направлении взгляда.",ChatColor.BLUE+"Расходует 30 маны.",ChatColor.RED+"Блокировка: 2 сек."}, null, null), new ItemStack[]{new ItemStack(Material.NETHER_STAR,1),new ItemStack(Material.GLOWSTONE_DUST,5)}));
		shop.add(new ShopItem("magic", ItemUtil.create(Material.PAPER, 1, 0, ChatColor.BLUE + "Фус-Ро-Да", new String[]{ChatColor.YELLOW+"Довакин тоже тут был.",ChatColor.LIGHT_PURPLE+"Подбрасывает всех во-круг в направлении взгляда.",ChatColor.BLUE+"Расходует 50 маны.",ChatColor.RED+"Блокировка: 2.5 сек."}, null, null), new ItemStack[]{new ItemStack(Material.NETHER_STAR,1),new ItemStack(Material.REDSTONE,5)}));
		shop.add(new ShopItem("magic", ItemUtil.create(Material.FIREBALL, 1, 0, ChatColor.GOLD + "Фаербол", new String[]{ChatColor.YELLOW+"Magic! Fireball!",ChatColor.LIGHT_PURPLE+"Запускает фаербол в направлении взгляда.",ChatColor.BLUE+"Расходует 50 маны.",ChatColor.RED+"Блокировка: 2 сек."}, null, null), new ItemStack[]{new ItemStack(Material.NETHER_STAR,1),new ItemStack(Material.BLAZE_POWDER,5)}));
		
		shop.add(new ShopItem("item", ItemUtil.createTool(Material.LEATHER_HELMET, ChatColor.GOLD + "Шапка ведьмы", new String[]{ChatColor.YELLOW+"Магия вне-хогвартса!",ChatColor.LIGHT_PURPLE+"-10% времени негативных эффектов."}, null, null), new ItemStack[]{new ItemStack(Material.GLOWSTONE_DUST,2),new ItemStack(Material.RED_MUSHROOM,2),new ItemStack(Material.SEEDS,2)}));
		shop.add(new ShopItem("item", ItemUtil.createTool(Material.LEATHER_LEGGINGS, ChatColor.GOLD + "Штаны за 40 душ", new String[]{ChatColor.YELLOW+"Магия вне-хогвартса!",ChatColor.LIGHT_PURPLE+"-10% моментального урона."}, null, null), new ItemStack[]{new ItemStack(Material.GLOWSTONE_DUST,4),new ItemStack(Material.RED_MUSHROOM,5),new ItemStack(Material.SEEDS,3)}));
		shop.add(new ShopItem("item", ItemUtil.createTool(Material.GOLD_HELMET, ChatColor.YELLOW + "Волшебная каска", new String[]{ChatColor.YELLOW+"Безопасность прежде всего.",ChatColor.LIGHT_PURPLE+"-30% времени негативных эффектов.",ChatColor.LIGHT_PURPLE+"+10% шанс вернуть мост при использовании."}, null, null), new ItemStack[]{new ItemStack(Material.NETHER_STAR,2),new ItemStack(Material.GLOWSTONE_DUST,5),new ItemStack(Material.SEEDS,5)}));
		shop.add(new ShopItem("item", ItemUtil.createTool(Material.GOLD_LEGGINGS, ChatColor.YELLOW + "Золотые штаны", new String[]{ChatColor.YELLOW+"Как оригинально.",ChatColor.LIGHT_PURPLE+"-30% моментального урона.",ChatColor.GOLD+"Магическая эссенция "+ChatColor.LIGHT_PURPLE+"добывается немного быстрее."}, null, null), new ItemStack[]{new ItemStack(Material.NETHER_STAR,4),new ItemStack(Material.BLAZE_POWDER,5),new ItemStack(Material.SEEDS,10)}));
		shop.add(new ShopItem("item", ItemUtil.createTool(Material.DIAMOND_HELMET, ChatColor.AQUA + "Небесный нимб", new String[]{ChatColor.YELLOW+"У ангела украл?",ChatColor.LIGHT_PURPLE+"-50% времени негативных эффектов."}, null, null), new ItemStack[]{new ItemStack(Material.NETHER_STAR,5),new ItemStack(Material.GLOWSTONE_DUST,10),new ItemStack(Material.SEEDS,15)}));
		shop.add(new ShopItem("item", ItemUtil.createTool(Material.DIAMOND_LEGGINGS, ChatColor.AQUA + "Небесный предохранитель", new String[]{ChatColor.YELLOW+"У ангела оторвал?",ChatColor.LIGHT_PURPLE+"-50% моментального урона.",ChatColor.LIGHT_PURPLE+"Постоянная регенерация."}, null, null), new ItemStack[]{new ItemStack(Material.NETHER_STAR,10),new ItemStack(Material.GLOWSTONE_DUST,20),new ItemStack(Material.SEEDS,50)}));
		
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
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.YELLOW + "Магазин");
		String[] lore1 = {
				ChatColor.YELLOW+"Выберите бонус в самом начале игры!",
				ChatColor.GREEN+"Нажмите, чтобы открыть.",
				};
		inv.setItem(10,ItemUtil.create(Material.CHEST, 1, (byte)0, ChatColor.GOLD + "Наборы", lore1, null, null));
		String[] lore2 = {
				ChatColor.YELLOW+"Просмотр всех доступных ачивок.",
				ChatColor.GREEN+"Нажмите, чтобы открыть.",
				};
		inv.setItem(12,ItemUtil.create(Material.EXP_BOTTLE, 1, (byte)0, ChatColor.GOLD + "Ачивки", lore2, null, null));
		p.openInventory(inv);
	}
	public static void SelectTeam(Player p){
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.LIGHT_PURPLE + "Выбор команды");
		int i=0;
		for(Volcano vol:main.vols){
			if(main.volp(p.getName())==null||!main.volp(p.getName()).equals(vol))inv.setItem(i, ItemUtil.create(Material.WOOL, 1, 0, vol.team, new String[]{ChatColor.YELLOW+""+vol.teammates.size()+" игроков"}, null, null));
			if(main.volp(p.getName())!=null&&main.volp(p.getName()).equals(vol))inv.setItem(i, ItemUtil.create(Material.WOOL, 1, 0, vol.team, new String[]{ChatColor.YELLOW+""+vol.teammates.size()+" игроков"}, new Enchantment[]{Enchantment.PROTECTION_ENVIRONMENTAL}, new int[]{10}));
			i++;
		}
		p.openInventory(inv);
	}
	public static void bonus(Player p){
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.YELLOW + "Наборы");
		String selected = "";
		if(main.connection.GetStats(p.getName()).get(0).equals("bottle")){selected = ChatColor.AQUA+"Выбрано!";}
		else{selected = ChatColor.GREEN+"Клик, чтобы выбрать!";}
		String[] lore1 = {
				ChatColor.YELLOW+"Начните игру с 24 бутылками в кармане.",
				ChatColor.LIGHT_PURPLE+"Особенность: "+ChatColor.GREEN+"С бутылок всегда что-то дропается.",
				ChatColor.GRAY+"Обычно с бутылок не дропается ничего с шансом 1/4.",
				selected
				};
		inv.setItem(0,ItemUtil.create(Material.GLASS_BOTTLE, 1, (byte)0, ChatColor.GOLD + "Собутыльник", lore1, null, null));
		
		if(main.connection.GetStats(p.getName()).get(0).equals("bridge")){selected = ChatColor.AQUA+"Выбрано!";}
		else{selected = ChatColor.GREEN+"Клик, чтобы выбрать!";}
		String[] lore2 = {
				ChatColor.YELLOW+"Начните игру с 5 заклинаниями моста!",
				ChatColor.LIGHT_PURPLE+"Особенность: "+ChatColor.GREEN+"25% шанс не потерять мост.",
				ChatColor.RED+"Требования: "+ChatColor.YELLOW+"Выполните 2 задания сложности "+ChatColor.GOLD+"'Неплохое испытание'.",
				selected
				};
		inv.setItem(1,ItemUtil.create(Material.CLAY_BRICK, 1, (byte)0, ChatColor.GOLD + "Создатель пути", lore2, null, null));

		if(main.connection.GetStats(p.getName()).get(0).equals("star")){selected = ChatColor.AQUA+"Выбрано!";}
		else{selected = ChatColor.GREEN+"Клик, чтобы выбрать!";}
		String[] lore3 = {
				ChatColor.YELLOW+"Начните игру с 3 звёздами!",
				ChatColor.LIGHT_PURPLE+"Особенность: "+ChatColor.GREEN+"Вы получаете звёзды на 0,5 сек быстрее!",
				ChatColor.LIGHT_PURPLE+"Ещё одна особенность: "+ChatColor.GREEN+"При утилизации звёзд вам дают больше дропа.",
				ChatColor.RED+"Требования: "+ChatColor.YELLOW+"Выполните 2 задания сложности "+ChatColor.DARK_RED+"'Dark Souls'.",
				selected
				};
		inv.setItem(2,ItemUtil.create(Material.NETHER_STAR, 1, (byte)0, ChatColor.GOLD + "Звёздный магнат", lore3, null, null));
		
		if(main.connection.GetStats(p.getName()).get(0).equals("ninja")){selected = ChatColor.AQUA+"Выбрано!";}
		else{selected = ChatColor.GREEN+"Клик, чтобы выбрать!";}
		String[] lore4 = {
				ChatColor.LIGHT_PURPLE+"Особенность: "+ChatColor.GREEN+"При возрождении вы получаете уникальное зелье дыма.",
				ChatColor.RED+"Требования: "+ChatColor.YELLOW+"Выполните 3 задания сложности "+ChatColor.DARK_RED+"'Dark Souls'.",
				selected
				};
		inv.setItem(3,ItemUtil.create(Material.FLINT, 1, (byte)0, ChatColor.GRAY + "Ниндзя", lore4, null, null));
		
		p.openInventory(inv);
	}
	
	
	public static void cauldron(Player p){
		main.tutor(p, "opca", ChatColor.GREEN+"Это, пожалуй, самое важное место в игре. Здесь ты можешь: "+ChatColor.AQUA+"Варить зелья, создавать заклинания, создавать предметы, и наводить чары на свою команду.");
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.YELLOW + "Котёл");
		String[] lore1 = {
				ChatColor.YELLOW+"Уникальные, и не очень зелья варятся тут!",
				ChatColor.GREEN+"Нажмите, чтобы открыть.",
				};
		inv.setItem(9,ItemUtil.create(Material.POTION, 1, (byte)0, ChatColor.GOLD + "Зелья", lore1, null, null));
		
		String[] lore2 = {
				ChatColor.YELLOW+"Its a call of magic!",
				ChatColor.GREEN+"Нажмите, чтобы открыть.",
				};
		inv.setItem(11,ItemUtil.create(Material.BLAZE_ROD, 1, (byte)0, ChatColor.GOLD + "Заклинания", lore2, null, null));
		
		String[] lore3 = {
				ChatColor.YELLOW+"Хорошие, магические вещи...",
				ChatColor.GREEN+"Нажмите, чтобы открыть.",
				};
		inv.setItem(13,ItemUtil.create(Material.DIAMOND_CHESTPLATE, 1, (byte)0, ChatColor.GOLD + "Предметы", lore3, null, null));
		
		String[] lore4 = {
				ChatColor.YELLOW+"Вся команда благодарна!",
				ChatColor.GREEN+"Нажмите, чтобы открыть.",
				};
		inv.setItem(15,ItemUtil.create(Material.DIAMOND, 1, (byte)0, ChatColor.GOLD + "Командное", lore4, null, null));
		
		String[] lore5 = {
				ChatColor.YELLOW+"Дуратский порошок!",
				ChatColor.BLUE+"Доступен только Unlocker-ам, и выше.",
				};
		inv.setItem(17,ItemUtil.create(Material.SUGAR, 1, (byte)0, ChatColor.GOLD + "Инградиенты", lore5, null, null));
		
		p.openInventory(inv);
	}
	
	
	
	public static void potions(Player p){
		main.tutor(p, "pots", ChatColor.GREEN+"Без зелий нет смысла игры. Зелья вреда, и подобные плохие зелья позволяют атаковать не только игроков, но и их вулканы. "+ChatColor.YELLOW+"Вулканы - некий нексус игроков. Без них игроки не смогут возраждаться. Помнишь бедварс? Так вот: вулкан - это некая кровать. "+ChatColor.RED+"Вы наверняка заметили в рецептах грибы. Их можно собрать, просто сломав гриб, растущий у вас на базе и не только.");
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.YELLOW + "Зелья");
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
				ChatColor.YELLOW+"Обычное зелье minecraft.",
				};
		i++;
		inv.setItem(3,ItemUtil.createPotion(Material.SPLASH_POTION, 1, ChatColor.GOLD + "Зелье урона", genlore(recipes.get("dam1"),lore4), 100, 0, 0, null));
		String[] lore5 = {
				ChatColor.YELLOW+"Обычное зелье minecraft.",
				};
		i++;
		inv.setItem(4,ItemUtil.createPotion(Material.SPLASH_POTION, 1, ChatColor.GOLD + "Зелье урона II", genlore(recipes.get("dam2"),lore5), 75, 0, 0, null));
		String[] lore6 = {
				ChatColor.YELLOW+"Это зелье может ваншотнуть!",
				};
		i++;
		inv.setItem(5,ItemUtil.createPotion(Material.SPLASH_POTION, 1, ChatColor.GOLD + "Зелье урона III", genlore(recipes.get("dam3"),lore6), 50, 0, 0, null));
		
		String[] lore7 = {
				ChatColor.YELLOW+"Обычное зелье minecraft.",
				};
		i++;
		inv.setItem(6,ItemUtil.createPotion(Material.SPLASH_POTION, 1, ChatColor.GOLD + "Отрава", genlore(recipes.get("poison"),lore7), 0, 125, 0, null));
		String[] lore8 = {
				ChatColor.YELLOW+"Обычное зелье minecraft.",
				};
		i++;
		inv.setItem(7,ItemUtil.createPotion(Material.SPLASH_POTION, 1, ChatColor.GOLD + "Отрава II", genlore(recipes.get("poison2"),lore8), 0, 100, 0, null));
		String[] lore9 = {
				ChatColor.YELLOW+"Обычное зелье minecraft.",
				};
		i++;
		inv.setItem(8,ItemUtil.createPotion(Material.SPLASH_POTION, 1, ChatColor.GOLD + "Отрава III", genlore(recipes.get("poison3"),lore9), 0, 75, 0, null));

		String[] lore10 = {
				ChatColor.YELLOW+"Обычное зелье minecraft.",
				};
		i++;
		inv.setItem(9,ItemUtil.createPotion(Material.POTION, 1, ChatColor.GOLD + "Скорость", genlore(recipes.get("speed"),lore10), 150, 150, 200, null));
		String[] lore11 = {
				ChatColor.YELLOW+"Обычное зелье minecraft.",
				};
		i++;
		inv.setItem(10,ItemUtil.createPotion(Material.POTION, 1, ChatColor.GOLD + "Скорость II", genlore(recipes.get("speed2"),lore11), 175, 175, 225, null));
		String[] lore12 = {
				ChatColor.YELLOW+"Уииии!",
				};
		i++;
		inv.setItem(11,ItemUtil.createPotion(Material.POTION, 1, ChatColor.GOLD + "Скорость III", genlore(recipes.get("speed3"),lore12), 200, 200, 255, null));
		String[] lore14 = {
				ChatColor.YELLOW+"Обычное зелье minecraft.",
				};
		i++;
		inv.setItem(13,ItemUtil.createPotion(Material.POTION, 1, ChatColor.GOLD + "Реген II", genlore(recipes.get("reg2"),lore14), 200, 125, 125, null));
		String[] lore15 = {
				ChatColor.YELLOW+"Стань кощеем!",
				};
		i++;
		inv.setItem(14,ItemUtil.createPotion(Material.POTION, 1, ChatColor.GOLD + "Реген III", genlore(recipes.get("reg3"),lore15), 225, 150, 150, null));
		
		p.openInventory(inv);
	}
	
	
	
	public static void magic(Player p){
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.YELLOW + "Заклинания");
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
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.YELLOW + "Предметы");
		String[] lore1 = {ChatColor.YELLOW+"Магические травы очень быстро растут...",};
		inv.setItem(0,ItemUtil.create(Material.GRASS, 2, (byte)0, ChatColor.GOLD + "Трава", genlore(recipes.get("grass"),lore1), null, null));
		
		String[] lore2 = {ChatColor.YELLOW+"Тут рука уже не поможет...",};
		inv.setItem(1,ItemUtil.create(Material.STONE, 2, (byte)0, ChatColor.GOLD + "Камень", genlore(recipes.get("stone"),lore2), null, null));
		
		String[] lore3 = {ChatColor.YELLOW+"Долой траву! Долой мосты!",};
		inv.setItem(2,ItemUtil.create(Material.STONE_SPADE, 1, (byte)0, ChatColor.GOLD + "Лопата", genlore(recipes.get("sts"),lore3), null, null));
		
		String[] lore4 = {ChatColor.YELLOW+"Всё равно пройду!",};
		inv.setItem(3,ItemUtil.create(Material.STONE_PICKAXE, 1, (byte)0, ChatColor.GOLD + "Кирка", genlore(recipes.get("stp"),lore4), null, null));
		
		String[] lore5 = {ChatColor.YELLOW+"Перевёл и озвучил сундук.",};
		inv.setItem(4,ItemUtil.create(Material.CHEST, 1, (byte)0, ChatColor.GOLD + "Сундук", genlore(recipes.get("chest"),lore5), null, null));
		
		String[] lore6 = {ChatColor.YELLOW+"Все грибы теперь мои!",ChatColor.LIGHT_PURPLE+"Позволяет быстро собирать грибы."};
		inv.setItem(5,ItemUtil.create(Material.GOLD_BARDING, 1, (byte)0, ChatColor.GOLD + "Грибной магнит", genlore(recipes.get("hand"),lore6), null, null));
		
		String[] lore7 = {ChatColor.YELLOW+"Ну куда же без неё..."};
		inv.setItem(6,ItemUtil.create(Material.STICK, 1, (byte)0, ChatColor.GOLD + "Палка-посылалка", genlore(recipes.get("stick"),lore7), new Enchantment[]{Enchantment.KNOCKBACK}, new int[]{1}));
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
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.YELLOW + "Командное");
		String[] lore1 = {ChatColor.YELLOW+"Как-бы тебя скинуть...",ChatColor.GREEN+"+5%"+ChatColor.LIGHT_PURPLE+" шанс попасть к себе на базу,",ChatColor.LIGHT_PURPLE+"вместо получения урона от пустоты."};
		inv.setItem(0,ItemUtil.create(Material.FEATHER, 1, (byte)0, ChatColor.AQUA + "Пустотное восстание", genloreup(p,uprecs.get("novoid"),lore1,"novoid"), null, null));
		
		String[] lore2 = {ChatColor.YELLOW+"А вот щас обидно было!",ChatColor.GREEN+"+5%"+ChatColor.LIGHT_PURPLE+" шанс не-очистки инвентаря после смерти."};
		inv.setItem(1,ItemUtil.create(Material.CHEST, 1, (byte)0, ChatColor.AQUA + "Сухим из воды", genloreup(p,uprecs.get("noclear"),lore2,"noclear"), null, null));
		
		String[] lore3 = {ChatColor.YELLOW+"Эй! Верни!",ChatColor.GREEN+"+15%"+ChatColor.LIGHT_PURPLE+" предметов, которые гарантировано сохранятся",ChatColor.LIGHT_PURPLE+"после смерти."};
		inv.setItem(2,ItemUtil.create(Material.SEEDS, 1, (byte)0, ChatColor.AQUA + "Не всё потеряно", genloreup(p,uprecs.get("clearproc"),lore3,"clearproc"), null, null));
		
		String[] lore4 = {ChatColor.YELLOW+"Боже, куда я попал...",ChatColor.GREEN+"+1 урон/сек"+ChatColor.LIGHT_PURPLE+" тем, кто находится"+ChatColor.LIGHT_PURPLE+" рядом с вулканом"};
		inv.setItem(3,ItemUtil.create(Material.FIREBALL, 1, (byte)0, ChatColor.AQUA + "Жар", genloreup(p,uprecs.get("voldef"),lore4,"voldef"), null, null));
		
		String[] lore5 = {ChatColor.YELLOW+"ЭТО ОГРАБЛЕНИЕ!",ChatColor.GREEN+"+15% "+ChatColor.LIGHT_PURPLE+" вещей при убийстве."};
		inv.setItem(4,ItemUtil.create(Material.EMERALD, 1, (byte)0, ChatColor.AQUA + "Воровство", genloreup(p,uprecs.get("steal"),lore5,"steal"), null, null));
		
		p.openInventory(inv);
	}
	
	public static void ing(Player p){
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.YELLOW + "Инградиенты");
		String[] lore1 = {ChatColor.YELLOW+"Немного очистить, и готово...",};
		inv.setItem(0,ItemUtil.create(Material.SUGAR, 1, (byte)0, ChatColor.WHITE + "Белый порошок", genlore(recipes.get("whitep1"),lore1), null, null));
		String[] lore2 = {ChatColor.YELLOW+"Немного подкрасить, и готово...",};
		inv.setItem(1,ItemUtil.create(Material.SULPHUR, 1, (byte)0, ChatColor.GRAY + "Серый порошок", genlore(recipes.get("grayp1"),lore2), null, null));
		String[] lore3 = {ChatColor.YELLOW+"Ну же, редстоун, светись!",};
		inv.setItem(2,ItemUtil.create(Material.GLOWSTONE_DUST, 1, (byte)0, ChatColor.YELLOW + "Золотой порошок", genlore(recipes.get("goldp1"),lore3), null, null));
		String[] lore4 = {ChatColor.YELLOW+"Красна девица...",};
		inv.setItem(3,ItemUtil.create(Material.REDSTONE, 1, (byte)0, ChatColor.RED + "Красный порошок", genlore(recipes.get("redp1"),lore4), null, null));
		String[] lore5 = {ChatColor.YELLOW+"Они ещё немного растут на грибах...",};
		inv.setItem(4,ItemUtil.create(Material.SEEDS, 1, (byte)0, ChatColor.GREEN + "Волшебные травы", genlore(recipes.get("seeds1"),lore5), null, null));
		String[] lore6 = {ChatColor.YELLOW+"MARIO!",};
		inv.setItem(5,ItemUtil.create(Material.RED_MUSHROOM, 1, (byte)0, ChatColor.RED + "Гриб", genlore(recipes.get("mus1"),lore6), null, null));
		String[] lore7 = {ChatColor.YELLOW+"Ну нафиг эти вещи...",};
		inv.setItem(6,ItemUtil.create(Material.EMERALD, 1, (byte)0, ChatColor.GREEN + "Эссенция сплочённости", genlore(recipes.get("em1"),lore7), null, null));
		String[] lore8 = {ChatColor.YELLOW+"Дайте-ка вещичек...",};
		inv.setItem(7,ItemUtil.create(Material.NETHER_STAR, 1, (byte)0, ChatColor.LIGHT_PURPLE + "Звезда тёмной магии", genlore(recipes.get("star1"),lore8), null, null));
		String[] lore9 = {ChatColor.YELLOW+"Пожалуй, слишком дорого...",};
		inv.setItem(8,ItemUtil.create(Material.BLAZE_POWDER, 1, (byte)0, ChatColor.GOLD + "Магическая эссенция", genlore(recipes.get("mes1"),lore9), null, null));
		
		p.openInventory(inv);
	}
	public static void achs(Player p){
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.BLUE + "Ачивки");
		for(int i=0;i<achs.size();i++){
			inv.setItem(i, achs.get(i).genAch(p));
		}
		p.openInventory(inv);
	}
	//TODO ДАЛЬШЕ КЛИКИ ИДУТ
	@EventHandler
	public void select(InventoryClickEvent e){
		if(e.getClickedInventory() != null) {
			Player p = (Player) e.getWhoClicked();
			if(e.getCurrentItem().getItemMeta()!=null){
				
				if(e.getInventory().getName().equalsIgnoreCase(ChatColor.YELLOW + "Магазин")){
					e.setCancelled(true);
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Наборы")){
						bonus(p);
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Ачивки")){
						achs(p);
					}
				}
				if(e.getInventory().getName().equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "Выбор команды")){
					e.setCancelled(true);
					for(Volcano vol:main.vols){
						if(e.getCurrentItem().getItemMeta().getDisplayName().equals(vol.team)){
							if(main.volp(p.getName())!=null)main.volp(p.getName()).teammates.remove(p.getName());
							vol.teammates.add(p.getName());
						}
					}
					SelectTeam(p);
				}
				if(e.getInventory().getName().equalsIgnoreCase(ChatColor.BLUE + "Ачивки")||e.getCurrentItem().hasItemMeta()&&e.getCurrentItem().getItemMeta().hasDisplayName()&&e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD+"Символ команды")){
					e.setCancelled(true);
				}
				
				if(e.getInventory().getName().equalsIgnoreCase(ChatColor.YELLOW + "Наборы")){
					e.setCancelled(true);
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Собутыльник")){
						try {main.connection.SetBonus(p.getName(), "bottle");} catch (SQLException e1) {e1.printStackTrace();}
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Создатель пути")){
						achBonus(p, new int[]{0,0,2,0,0}, "bridge");
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Звёздный магнат")){
						achBonus(p, new int[]{0,0,0,0,2}, "star");
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GRAY+"Ниндзя")){
						achBonus(p, new int[]{0,0,0,0,3}, "ninja");
					}
					bonus(p);
				}
				
				if(e.getInventory().getName().equalsIgnoreCase(ChatColor.YELLOW + "Котёл")){
					e.setCancelled(true);
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Зелья")){
						potions(p);
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Заклинания")){
						magic(p);
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Предметы")){
						items(p);
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Командное")){
						team(p);
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Инградиенты")){
						if(p.hasPermission("unlicker")){ing(p);}
						else{p.sendMessage(ChatColor.YELLOW+"Для этого раздела требуется группа Unlocker или выше.");}
					}
				}
				
				
				
				if(e.getInventory().getName().equalsIgnoreCase(ChatColor.YELLOW + "Зелья")){
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
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Завеса смерти")){
						buyitem(p,recipes.get("diesmoke"),ItemUtil.createPotion(Material.SPLASH_POTION, 1, "Завеса смерти", null, 100,100,100, null));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Божий щит")){
						PotionEffect[] pe = {new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 600, 1)};
						buyitem(p,recipes.get("gods"),ItemUtil.createPotion(Material.POTION, 1, "Божий щит", null, 25, 0, 135, pe));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Зелье урона")){
						PotionEffect[] pe = {new PotionEffect(PotionEffectType.HARM, 1, 0)};
						buyitem(p,recipes.get("dam1"),ItemUtil.createPotion(Material.SPLASH_POTION, 1, "Зелье урона", null, 100, 0, 0, pe));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Зелье урона II")){
						PotionEffect[] pe = {new PotionEffect(PotionEffectType.HARM, 1, 1)};
						buyitem(p,recipes.get("dam2"),ItemUtil.createPotion(Material.SPLASH_POTION, 1, "Зелье урона II", null, 75, 0, 0, pe));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Зелье урона III")){
						PotionEffect[] pe = {new PotionEffect(PotionEffectType.HARM, 1, 2)};
						buyitem(p,recipes.get("dam3"),ItemUtil.createPotion(Material.SPLASH_POTION, 1, "Зелье урона III", null, 50, 0, 0, pe));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Отрава")){
						PotionEffect[] pe = {new PotionEffect(PotionEffectType.POISON, 200, 0)};
						buyitem(p,recipes.get("poison"),ItemUtil.createPotion(Material.SPLASH_POTION, 1, ChatColor.DARK_GREEN+"Отрава", null, 0, 125, 0, pe));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Отрава II")){
						PotionEffect[] pe = {new PotionEffect(PotionEffectType.POISON, 200, 1)};
						buyitem(p,recipes.get("poison2"),ItemUtil.createPotion(Material.SPLASH_POTION, 1, ChatColor.DARK_GREEN+"Отрава II", null, 0, 100, 0, pe));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Отрава III")){
						PotionEffect[] pe = {new PotionEffect(PotionEffectType.POISON, 200, 2)};
						buyitem(p,recipes.get("poison3"),ItemUtil.createPotion(Material.SPLASH_POTION, 1, ChatColor.DARK_GREEN+"Отрава III", null, 0, 75, 0, pe));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Скорость")){
						PotionEffect[] pe = {new PotionEffect(PotionEffectType.SPEED, 600, 0)};
						buyitem(p,recipes.get("speed"),ItemUtil.createPotion(Material.POTION, 1, ChatColor.AQUA+"Скорость", null, 150, 150, 200, pe));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Скорость II")){
						PotionEffect[] pe = {new PotionEffect(PotionEffectType.SPEED, 600, 1)};
						buyitem(p,recipes.get("speed2"),ItemUtil.createPotion(Material.POTION, 1, ChatColor.AQUA+"Скорость II", null, 175, 175, 225, pe));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Скорость III")){
						PotionEffect[] pe = {new PotionEffect(PotionEffectType.SPEED, 600, 2)};
						buyitem(p,recipes.get("speed3"),ItemUtil.createPotion(Material.POTION, 1, ChatColor.AQUA+"Скорость III", null, 200, 200, 255, pe));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Реген")){
						PotionEffect[] pe = {new PotionEffect(PotionEffectType.REGENERATION, 600, 0)};
						buyitem(p,recipes.get("reg1"),ItemUtil.createPotion(Material.POTION, 1, ChatColor.LIGHT_PURPLE+"Реген", null, 175, 100, 100, pe));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Реген II")){
						PotionEffect[] pe = {new PotionEffect(PotionEffectType.REGENERATION, 600, 1)};
						buyitem(p,recipes.get("reg2"),ItemUtil.createPotion(Material.POTION, 1, ChatColor.LIGHT_PURPLE+"Реген II", null, 200, 125, 125, pe));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Реген III")){
						PotionEffect[] pe = {new PotionEffect(PotionEffectType.REGENERATION, 600, 2)};
						buyitem(p,recipes.get("reg3"),ItemUtil.createPotion(Material.POTION, 1, ChatColor.LIGHT_PURPLE+"Реген III", null, 225, 150, 150, pe));
					}
				}
				
				
				if(e.getInventory().getName().equalsIgnoreCase(ChatColor.YELLOW + "Заклинания")){
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
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Мост")){
						buyitem(p,recipes.get("bridge"),ItemUtil.create(Material.CLAY_BRICK, 1, (byte)0, ChatColor.GREEN+"Мост", null, null, null));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Фус-ро-да!")){
						buyitem(p,recipes.get("fusroda"),ItemUtil.create(Material.PAPER, 1, (byte)0, ChatColor.AQUA+"Фус-ро-да!", null, null, null));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Огненный шар")){
						buyitem(p,recipes.get("fireball"),ItemUtil.create(Material.FIREBALL, 1, (byte)0, ChatColor.GOLD+"Огненный шар", null, null, null));
					}
				}
				
				
				if(e.getInventory().getName().equalsIgnoreCase(ChatColor.YELLOW + "Предметы")){
					e.setCancelled(true);
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Трава")){
						buyitem(p,recipes.get("grass"),ItemUtil.create(Material.GRASS, 2, (byte)0, ChatColor.GREEN+"Трава", null, null, null));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Камень")){
						buyitem(p,recipes.get("stone"),ItemUtil.create(Material.STONE, 2, (byte)0, ChatColor.GRAY+"Камень", null, null, null));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Лопата")){
						buyitem(p,recipes.get("sts"),ItemUtil.create(Material.STONE_SPADE, 1, (byte)0, ChatColor.GOLD+"Лопата", null, null, null));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Кирка")){
						buyitem(p,recipes.get("stp"),ItemUtil.create(Material.STONE_PICKAXE, 1, (byte)0, ChatColor.GOLD+"Кирка", null, null, null));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Сундук")){
						buyitem(p,recipes.get("chest"),ItemUtil.create(Material.CHEST, 1, (byte)0, ChatColor.GOLD+"Сундук", null, null, null));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Грибной магнит")){
						buyitem(p,recipes.get("hand"),ItemUtil.create(Material.GOLD_BARDING, 1, (byte)0, ChatColor.GOLD+"Грибной магнит", null, null, null));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Палка-посылалка")){
						buyitem(p,recipes.get("stick"),ItemUtil.create(Material.STICK, 1, (byte)0, ChatColor.GOLD + "Палка-посылалка", null, new Enchantment[]{Enchantment.KNOCKBACK}, new int[]{1}));
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
				
				
				if(e.getInventory().getName().equalsIgnoreCase(ChatColor.YELLOW + "Командное")){
					e.setCancelled(true);
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.AQUA+"Пустотное восстание")){
						buyUpgrade(p,"novoid",uprecs.get("novoid"),ChatColor.AQUA+"'Пустотное восстание'");
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.AQUA+"Сухим из воды")){
						buyUpgrade(p,"noclear",uprecs.get("noclear"),ChatColor.AQUA+"'Сухим из воды'");
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.AQUA+"Не всё потеряно")){
						buyUpgrade(p,"clearproc",uprecs.get("clearproc"),ChatColor.AQUA+"'Не всё потеряно'");
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.AQUA+"Жар")){
						buyUpgrade(p,"voldef",uprecs.get("voldef"),ChatColor.AQUA+"'Жар'");
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.AQUA+"Воровство")){
						buyUpgrade(p,"steal",uprecs.get("steal"),ChatColor.AQUA+"'Воровство'");
					}
				}
				
				if(e.getInventory().getName().equalsIgnoreCase(ChatColor.YELLOW + "Инградиенты")){
					e.setCancelled(true);
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.WHITE+"Белый порошок")){
						buyitem(p,recipes.get("whitep1"),ItemUtil.create(Material.SUGAR, 1, (byte)0, matToName(Material.SUGAR), null, null, null));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GRAY+"Серый порошок")){
						buyitem(p,recipes.get("grayp1"),ItemUtil.create(Material.SULPHUR, 1, (byte)0, matToName(Material.SULPHUR), null, null, null));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.YELLOW+"Золотой порошок")){
						buyitem(p,recipes.get("goldp1"),ItemUtil.create(Material.GLOWSTONE_DUST, 1, (byte)0, matToName(Material.GLOWSTONE_DUST), null, null, null));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED+"Красный порошок")){
						buyitem(p,recipes.get("redp1"),ItemUtil.create(Material.REDSTONE, 1, (byte)0, matToName(Material.REDSTONE), null, null, null));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN+"Волшебные травы")){
						buyitem(p,recipes.get("seeds1"),ItemUtil.create(Material.SEEDS, 1, (byte)0, matToName(Material.SEEDS), null, null, null));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED+"Гриб")){
						buyitem(p,recipes.get("mus1"),ItemUtil.create(Material.RED_MUSHROOM, 1, (byte)0, matToName(Material.RED_MUSHROOM), null, null, null));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN+"Эссенция сплочённости")){
						buyitem(p,recipes.get("em1"),ItemUtil.create(Material.EMERALD, 1, (byte)0, matToName(Material.EMERALD), null, null, null));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.LIGHT_PURPLE+"Звезда тёмной магии")){
						buyitem(p,recipes.get("star1"),ItemUtil.create(Material.NETHER_STAR, 1, (byte)0, matToName(Material.NETHER_STAR), null, null, null));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Магическая эссенция")){
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
		if(mat.equals(Material.SUGAR)){return ChatColor.WHITE+"Белый порошок";}
		if(mat.equals(Material.NETHER_STAR)){return ChatColor.LIGHT_PURPLE+"Звезда тёмной магии";}
		if(mat.equals(Material.GLOWSTONE_DUST)){return ChatColor.YELLOW+"Золотой порошок";}
		if(mat.equals(Material.REDSTONE)){return ChatColor.RED+"Красный порошок";}
		if(mat.equals(Material.RED_MUSHROOM)){return ChatColor.RED+"Гриб";}
		if(mat.equals(Material.SEEDS)){return ChatColor.GREEN+"Волшебные травы";}
		if(mat.equals(Material.SULPHUR)){return ChatColor.GRAY+"Серый порошок";}
		if(mat.equals(Material.EMERALD)){return ChatColor.GREEN+"Эссенция сплочённости";}
		if(mat.equals(Material.BLAZE_POWDER)){return ChatColor.GOLD+"Магическая эссенция";}
		if(mat.equals(Material.GLASS_BOTTLE)){return ChatColor.WHITE+"Бутылка";}
		
		return ChatColor.RED+"Нет названия. "+mat;
	}
	static String[] genlore(ItemStack[] recipe, String[] lore){
		List<String> ret = new ArrayList<>();
		for(String s:lore){
			ret.add(s);
		}
		ret.add(ChatColor.AQUA+"Рецепт:");
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
		ret.add(ChatColor.GOLD+"Уровень: "+ChatColor.GREEN+now+ChatColor.GRAY+"/"+recipes.length);
		if(now==-1){
			ret.add(ChatColor.RED+"Вашей команды нет в базе.");
		}
		else if(now+1<=recipes.length){
			ret.add(ChatColor.AQUA+"Рецепт:");
			for(ItemStack s:recipes[now]){
				ret.add(matToName(s.getType())+ChatColor.GRAY+" x"+ChatColor.GREEN+s.getAmount());
			}
		}
		else{
			ret.add(ChatColor.GREEN+"МАКСИМУМ!");
		}
		String[] retu = new String[ret.size()];
		retu = ret.toArray(retu);
		return retu;
	}
	public static void buyUpgrade(Player p, String upgrade, ItemStack[][] prices, String message){
		int now = main.getUpgrade(main.volp(p.getName()),upgrade);
		if(now==-1){
			p.sendMessage(ChatColor.RED+"Вашей команды нет в базе. Сообщите об этом администрации.");
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
					pl.sendMessage(ChatColor.GREEN+"Игрок "+ChatColor.DARK_GREEN+p.getName()+ChatColor.GREEN+" улучшил "+ChatColor.YELLOW+message+ChatColor.GREEN+" до уровня "+ChatColor.BLUE+(now+1)+ChatColor.GREEN+".");
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
		String message=ChatColor.RED+"Не хватает инградиентов: ";
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
			if(!found)p.sendMessage(ChatColor.RED+"Ошибка определения сложности ачивки с кодовым названием "+ChatColor.YELLOW+st+ChatColor.RED+". Просьба сообщить об этом Геппи.");
		}
		ret.add(total);ret.add(easy);ret.add(normal);ret.add(hard);ret.add(darksouls);
		return ret;
	}
	static void achBonus(Player p, int[] difs, String bonus){
		ArrayList<Integer> dfs = getDifs(p);
		for(int i=0;i<5;i++){
			if(dfs.get(i)<difs[i]){
				p.sendMessage(ChatColor.RED+"У вас недостаточно ачивок.");
				return;
			}
		}
		try {main.connection.SetBonus(p.getName(), bonus);} catch (SQLException e1) {e1.printStackTrace();}
	}
}
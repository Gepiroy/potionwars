package potionwars;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;
import com.sk89q.worldedit.world.DataException;

import ObjectsPW.Ach;
import ObjectsPW.PlayerInfo;
import ObjectsPW.Smoke;
import ObjectsPW.SmokeDie;
import ObjectsPW.Volcano;
import utilsPW.GepUtil;
import utilsPW.ItemUtil;
import utilsPW.SQLConnection;

@SuppressWarnings("deprecation")
public class main extends JavaPlugin{
	public static int startTime = 3200;
	static int upTimeSet = 800;
	public static int upTime = 800;
	public static int upTier = 1;
	//public static List<Location> spawns = new ArrayList<>();
	//public static HashMap<String,List<Location>> Tspawns = new HashMap<>();
	//public static HashMap<String,String> Tplayers = new HashMap<>();
	//public static List<Location> listvolcanos = new ArrayList<>();
	public static List<Volcano> vols = new ArrayList<>();
	//public static HashMap<String,Location> volcanos = new HashMap<>();
	//public static HashMap<Location,Integer> volhp = new HashMap<>();
	public static List<Location> moshs = new ArrayList<>();
	public static HashMap<Location,Integer> moshresp = new HashMap<>();
	public static HashMap<String, List<String>> canAch = new HashMap<>();
	public static HashMap<String, Integer> livenovol = new HashMap<>();
	public static HashMap<String,PlayerInfo> plist = new HashMap<>();
	public static ArrayList<SmokeDie> sdies = new ArrayList<>();
	//public static List<String> players = new ArrayList<>();
	public static List<Smoke> smokes = new ArrayList<>();
	public static List<Location> bannedlocs = new ArrayList<>();
	public static HashMap<String, Integer> dead = new HashMap<>();
	//public static HashMap<String,HashMap<String,Integer>> upgrades = new HashMap<>();
	public static HashMap<String,List<String>> tutorial = new HashMap<>();
	public static Location wait;
	public static Location center;
	public static boolean team = false;
	//public static HashMap<String,Integer> teams = new HashMap<>();
	public static boolean test = false;
	public static SQLConnection connection;
	
	static int pretimer = 0;
	static int timetostart = 60; //60
	public static int timer = timetostart;
	public static int stage = 0;
	int secrate=0;
	public static main instance;
	
	public void onEnable(){
		instance=this;
		connection = new SQLConnection();
	    connection.connect("localhost", "mysql", "mysql", "gepcraft");
		saveDefaultConfig();
		team=getConfig().getBoolean("team");
		Bukkit.getPluginManager().registerEvents(new events(), this);
		Bukkit.getPluginManager().registerEvents(new GUI(), this);
		getCommand("howtoplay").setExecutor(new Help(this));
		GUI.setrecipes();
		for(Player p:Bukkit.getOnlinePlayers()){
			plist.put(p.getName(), new PlayerInfo());
		}
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
			public void run(){
				if(secrate>=4){
					int i=0;
					for(Player pl:Bukkit.getOnlinePlayers()){
						if(armor(pl,1,Material.DIAMOND_LEGGINGS)){
							pl.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 0, true, true));
						}
						String p = pl.getName();
						if(stage==0)events.waitScoreboard(pl);
						if(canAch.containsKey(pl.getName())&&canAch.get(pl.getName()).contains("nodievol")){
							if(livenovol.containsKey(p)){
								livenovol.replace(p,livenovol.get(p)+1);
							}
							else livenovol.put(p,1);
							if(livenovol.get(p)>=600){
								ach(pl,"nodievol");
							}
						}
					}
					for(Volcano vol:vols){
						if(getUpgrade(vol, "voldef")>=1&&i<=0){
							i++;
							for(Entity en:Bukkit.getWorld("world").getNearbyEntities(vol.loc, 10.0, 10.0, 10.0)){
								if(en instanceof Player && !vol.teammates.contains(en.getName()) && ((Player) en).getGameMode().equals(GameMode.SURVIVAL)){
									boolean died = damage((Player) en, getUpgrade(vol, "voldef"), null, true, ChatColor.GOLD+((Player) en).getDisplayName()+ChatColor.YELLOW+" умер от жара вулкана", true);
									for(String st:vol.teammates){
										if(Bukkit.getPlayer(st)!=null&&died)
										events.stealres((Player) en, Bukkit.getPlayer(st), 50);
									}
								}
							}
						}
					}
					secrate=0;
				}
				secrate++;
				if(team==false&&Bukkit.getOnlinePlayers().size()>=2||team==true&&Bukkit.getOnlinePlayers().size()>=3){
					pretimer++;
					if(test==false&&pretimer>=4){
						if(stage==0)if(timer==20||timer==10||timer<=5)globMessage(ChatColor.YELLOW+"До начала игры осталось "+ChatColor.GREEN+timer+ChatColor.YELLOW+" секунд.",Sound.BLOCK_NOTE_HAT);
						if(stage==0)if(timer==300||timer==240||timer==180||timer==120||timer==60){globMessage(ChatColor.YELLOW+"До начала игры осталось "+ChatColor.GREEN+timer/60+ChatColor.YELLOW+" минут.",Sound.BLOCK_NOTE_HAT);}
						if(stage==1)if(timer==20||timer==10||timer<=5)globMessage(ChatColor.YELLOW+"До конца игры осталось "+ChatColor.GREEN+timer+ChatColor.YELLOW+" секунд.",Sound.BLOCK_NOTE_HAT);
						if(stage==1)if(timer==1200||timer==600||timer==300||timer==240||timer==180||timer==120||timer==60){globMessage(ChatColor.YELLOW+"До конца игры осталось "+ChatColor.GREEN+timer/60+ChatColor.YELLOW+" минут.",Sound.BLOCK_NOTE_HAT);}
						timer--;
						if(stage==1){
							upTime--;
							for(Player p:Bukkit.getOnlinePlayers())
							events.updateScoreboard(p);
						}
						pretimer=0;
					}
					if(timer<=0&&pretimer>=3){
						if(stage==0){start();}
						else{end();}
					}
					if(upTime<=0&&pretimer>=3){
						upTier++;
						upTime=upTimeSet;
						globMessage(ChatColor.GREEN+"Теперь вы получаете x"+ChatColor.AQUA+upTier+ChatColor.GREEN+" ресурсов!", Sound.ENTITY_PLAYER_LEVELUP);
					}
				}
				for(Player p:Bukkit.getOnlinePlayers()){
					PlayerInfo pi = plist.get(p.getName());
					if(pi==null){
						GepUtil.debug("pi=null", p.getName(), "error");
						continue;
					}
					pi.changeMana(p, 1);
					if(pi.timers.containsKey("manaspeed"))pi.changeMana(p, 1);
					if(pi.lastHurt!=null){
						if(!pi.timers.containsKey("lastHurtCancel"))pi.lastHurt = null;
					}
					for(String key:new ArrayList<String>(pi.timers.keySet())){
						if(GepUtil.HashMapReplacer(pi.timers, key, -1, true)){
							if(key.equals("manapotion")){pi.maxMana-=50;pi.changeMana(p, -50);}
						}
					}
					if(p.isSprinting()&&canAch.containsKey(p.getName())&&canAch.get(p.getName()).contains("norun")){
						canAch.get(p.getName()).remove("norun");
					}
					HashMap<String, Integer> hm = new HashMap<>(dead);
					if(hm.containsKey(p.getName())){
						p.sendTitle(ChatColor.RED+"Вы умерли!", ChatColor.AQUA+"Возрождение через "+ChatColor.GREEN+dead.get(p.getName()), 0, 5, 5);
						if(secrate==1){
							if(hm.get(p.getName())<=0){
								spawn(p,0);
							}
							dead.replace(p.getName(),hm.get(p.getName())-1);
						}
					}
					if(stage>=1){
						res(p);
					}
				}
				for(SmokeDie sd:new ArrayList<SmokeDie>(sdies))if(!sd.work())sdies.remove(sd);
				for(Smoke s:new ArrayList<Smoke>(smokes)){
					s.particle();
					if(s.type.equals("freeze")){
						for(Player p:s.players(false, true)){
							p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, s.lvl, true, false), true);
						}
					}
					else if(s.type.equals("fog")){
						for(Player p:s.players(false, true)){
							p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 50, 0, true, false), true);
						}
					}
					if(s.time<=0)smokes.remove(s);
				}
				List<Location> moss = new ArrayList<>(moshs);
				HashMap<Location, Integer> mr = new HashMap<>(moshresp);
				for(Location loc:moss){
					if(!loc.getBlock().getType().equals(Material.RED_MUSHROOM) && !moshresp.containsKey(loc)){
						moshresp.put(loc, 20);
					}
					if(mr.containsKey(loc)){
						if(mr.get(loc)<=0){
							loc.getBlock().setType(Material.RED_MUSHROOM);
							moshresp.remove(loc);
						}
						if(mr.get(loc)>=1){
							moshresp.replace(loc,moshresp.get(loc)-1);
						}
					}
				}
			}
		},0,5);
		if(getConfig().contains("wait"))wait = new Location(Bukkit.getWorld(getConfig().getString("wait.World")),getConfig().getInt("wait.x"), getConfig().getInt("wait.y"), getConfig().getInt("wait.z"));
		if(getConfig().contains("center"))center = new Location(Bukkit.getWorld(getConfig().getString("center.World")),getConfig().getInt("center.x"), getConfig().getInt("center.y"), getConfig().getInt("center.z"));
		if(getConfig().contains("volc"))for (String b : getConfig().getConfigurationSection("volc").getKeys(false)) {
			vols.add(new Volcano(new Location(Bukkit.getWorld(getConfig().getString("volc." + b + ".World")),getConfig().getDouble("volc." + b + ".x"), getConfig().getDouble("volc." + b + ".y"), getConfig().getDouble("volc." + b + ".z")),getConfig().getString("volc." + b + ".team"),new Location(Bukkit.getWorld(getConfig().getString("volc." + b + ".spawn.World")),getConfig().getInt("volc." + b + ".spawn.x"), getConfig().getInt("volc." + b + ".spawn.y"), getConfig().getInt("volc." + b + ".spawn.z"))));
			bannedlocs.add(new Location(Bukkit.getWorld(getConfig().getString("volc." + b + ".spawn.World")),getConfig().getInt("vols." + b + ".spawn.x"), getConfig().getInt("vols." + b + ".spawn.y"), getConfig().getInt("vols." + b + ".spawn.z")));
            bannedlocs.add(new Location(Bukkit.getWorld(getConfig().getString("volc." + b + ".spawn.World")),getConfig().getInt("vols." + b + ".spawn.x"), getConfig().getInt("vols." + b + ".spawn.y")+1, getConfig().getInt("vols." + b + ".spawn.z")));
		}
		for (String b : getConfig().getConfigurationSection("moshs").getKeys(false)) {
            moshs.add(new Location(Bukkit.getWorld(getConfig().getString("moshs." + b + ".World")),getConfig().getInt("moshs." + b + ".x"), getConfig().getInt("moshs." + b + ".y"), getConfig().getInt("moshs." + b + ".z")));
		}
		if(test==true){
			start();
		}
	}
	public void onDisable(){
		if(test==false)end();
		getConfig().set("wait.World", wait.getWorld().getName());
        getConfig().set("wait.x", wait.getX());
        getConfig().set("wait.y", wait.getY());
        getConfig().set("wait.z", wait.getZ());
		for (int i=0;i<vols.size();i++) {
            getConfig().set("volc."+i+".World", vols.get(i).loc.getWorld().getName());
            getConfig().set("volc."+i+".x", vols.get(i).loc.getX());
            getConfig().set("volc."+i+".y", vols.get(i).loc.getY());
            getConfig().set("volc."+i+".z", vols.get(i).loc.getZ());
            getConfig().set("volc."+i+".team", vols.get(i).team);
            getConfig().set("volc."+i+".spawn.World", vols.get(i).spawn.getWorld().getName());
            getConfig().set("volc."+i+".spawn.x", vols.get(i).spawn.getX());
            getConfig().set("volc."+i+".spawn.y", vols.get(i).spawn.getY());
            getConfig().set("volc."+i+".spawn.z", vols.get(i).spawn.getZ());
        }
		for (int i=0;i<moshs.size();i++) {
            getConfig().set("moshs."+i+".World", moshs.get(i).getWorld().getName());
            getConfig().set("moshs."+i+".x", moshs.get(i).getX());
            getConfig().set("moshs."+i+".y", moshs.get(i).getY());
            getConfig().set("moshs."+i+".z", moshs.get(i).getZ());
        }
		getConfig().set("team", team);
		saveConfig();
	}
	static void res(Player p){
		PlayerInfo pi = plist.get(p.getName());
		if(p.getLocation().getWorld().getBlockAt(p.getLocation().getBlockX(), p.getLocation().getBlockY()-1, p.getLocation().getBlockZ()).getType().equals(Material.IRON_BLOCK)){
			pi.iron+=5;
		}
		if(p.getLocation().getWorld().getBlockAt(p.getLocation().getBlockX(), p.getLocation().getBlockY()-1, p.getLocation().getBlockZ()).getType().equals(Material.GOLD_BLOCK)){
			pi.gold+=5;
		}
		if(p.getLocation().getWorld().getBlockAt(p.getLocation().getBlockX(), p.getLocation().getBlockY()-1, p.getLocation().getBlockZ()).getType().equals(Material.DIAMOND_BLOCK)){
			pi.star+=5;
		}
		if(p.getLocation().getWorld().getBlockAt(p.getLocation().getBlockX(), p.getLocation().getBlockY()-1, p.getLocation().getBlockZ()).getType().equals(Material.EMERALD_BLOCK)){
			pi.em+=5;
		}
		if(pi.iron>=10){
			tutor(p,"irbot",ChatColor.GREEN+"Что ж, ты нашёл его! Стоп... Бутылки? Где же кирпичи? Где же серебро? Нет, этого здесь нет. Вместо этого тут есть инградиенты. На своей базе ты наверняка заметил печки? Ну так вот: "+ChatColor.AQUA+"бутылки можно переработать в той самой печке."+ChatColor.GREEN+" Кстати, инградиенты нужны, чтобы создавать полезности в "+ChatColor.YELLOW+"котле."+ChatColor.GREEN+" Не забудь посмотреть в котёл после переработки. Удачи!");
			ach(p,"irbot");
			p.getInventory().addItem(ItemUtil.create(Material.GLASS_BOTTLE, upTier, (byte)0, "Бутылка",null,null,null));
			pi.iron=0;
			p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 2);
		}
		if(pi.gold>=20){
			tutor(p,"ess",ChatColor.GREEN+"Помнишь ту печку, в которой ты перерабатывал бутылки? Так вот: "+ChatColor.GOLD+"эссенция - та же бутылка, но она даёт в 3 раза больше, и с неё выпадают инградиенты, которых нет в бутылках.");
			p.getInventory().addItem(ItemUtil.create(Material.BLAZE_POWDER, upTier, (byte)0, ChatColor.GOLD+"Магическая эссенция",null,null,null));
			if(p.getEquipment().getArmorContents()[2]!=null&&p.getEquipment().getArmorContents()[2].getType().equals(Material.GOLD_CHESTPLATE))pi.gold=5;
			else pi.gold=0;
			p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
		}
		if(pi.star>=50){
			tutor(p,"star",ChatColor.GREEN+"Ты уже добрался до центра? Что ж, поздравляю! Теперь у тебя есть звезда! "+ChatColor.LIGHT_PURPLE+"Звезда - самый ценный ресурс в игре. Без неё нельзя варить самые крутые зелья, и не только. Так же эта звезда дорога тем, что в печке распадается на целых 7 предметов!");
			p.getInventory().addItem(ItemUtil.create(Material.NETHER_STAR, upTier, (byte)0, ChatColor.LIGHT_PURPLE+"Звезда тёмной магии",null,null,null));
			if(connection.GetStats(p.getName()).get(0).equals("star"))pi.star=10;
			else pi.star=0;
			p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
		}
		if(pi.em>=50){
			tutor(p,"em",ChatColor.GREEN+"Интересно, что это за изумруды? Что ж, ты их полюбишь, если ты любишь быть дефером в своей команде. "+ChatColor.DARK_GREEN+"Эссэнция сплочённости - особый ресурс, не перерабатывающийся в печке. С помощью этой эссенции можно зачаровать свою команду! Или же себя, если вы играете в соло.");
			p.getInventory().addItem(ItemUtil.create(Material.EMERALD, upTier, (byte)0, ChatColor.GREEN+"Эссенция сплочённости",null,null,null));
			pi.em=0;
			p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_YES, 1, 1);
		}
	}
	static void start(){
		if(center!=null)loadSchematic(center, "map");
		stage = 1;
		sdies.clear();
		int num = 0;
		canAch.clear();
		livenovol.clear();
		plist.clear();
		if(team){
			for(Player p:Bukkit.getOnlinePlayers()){
				if(volp(p.getName())==null){
					int low=100;
					Volcano lov=null;
					for(Volcano vol:vols){
						if(vol.teammates.size()<low&&vol.teammates.size()>0){
							low=vol.teammates.size();
							lov=vol;
						}
					}
					lov.teammates.add(p.getName());
				}
			}
			int low=100;
			for(Volcano vol:vols){
				if(vol.teammates.size()<low&&vol.teammates.size()>0){
					low=vol.teammates.size();
				}
			}
			for(Volcano vol:vols){
				if(vol.teammates.size()>=low+2&&vol.teammates.size()>0){
					globMessage(ChatColor.RED+"Команды слишком имбовые. Постарайтесь уравнять.", Sound.ENTITY_VILLAGER_NO);
					timer=startTime/4;
					return;
				}
			}
		}
		GepUtil.debug("start", null, "info");
		int volid=0;
		for(Player p:Bukkit.getOnlinePlayers()){
			if(!team){
				Volcano vol = vols.get(volid);
				if(vol==null){
					p.kickPlayer(ChatColor.RED+"[Ошибка!] Все команды заняты.");
					continue;
				}
				vol.hp=100;
				vol.teammates.add(p.getName());
				volid++;
			}
			p.getInventory().clear();
			int r=0;
			int g=0;
			int b=0;
			if(volp(p.getName())==null){
				GepUtil.debug("volp is null", p.getName(), "error");
				continue;
			}
			if(volp(p.getName()).team.equals("RED"))r=255;
			if(volp(p.getName()).team.equals("BLUE"))b=255;
			if(volp(p.getName()).team.equals("GREEN"))g=255;
			if(volp(p.getName()).team.equals("YELLOW"))r=255;g=255;
			p.getInventory().setChestplate(ItemUtil.createArmorColored(Material.LEATHER_CHESTPLATE, ChatColor.GOLD+"Символ команды", null, r, g, b));
			plist.put(p.getName(), new PlayerInfo());
			GepUtil.debug("settedarmor", p.getName(), "info");
			List<String> ara = new ArrayList<>();
			if(!stringToArrayList(connection.GetStats(p.getName()).get(3)).contains("norun"))ara.add("norun");
			canAch.put(p.getName(),ara);
			if(connection.GetStats(p.getName()).get(1).equals("1")){
				tutorial.put(p.getName(), new ArrayList<>());
				p.sendMessage(ChatColor.AQUA+"[Обучение] "+ChatColor.GREEN+"Привет! Ты здесь новенький? Если ты уже не новенький, пропиши"+ChatColor.YELLOW+" /howtoplay"+ChatColor.GREEN+", чтобы "+ChatColor.RED+"убить меня ;(");
				p.sendMessage(ChatColor.AQUA+"[Обучение] "+ChatColor.GREEN+"В любом случае, в начале игры тебе нужно найти железный блок, и встать на него. "+ChatColor.YELLOW+"Да, это похоже на bedwars, но на самом деле тут всё гораздо сложнее. Не надо убивать меня раньше времени!");
			}
			String Bonus = connection.GetStats(p.getName()).get(0);
			if(team==true){
				ChatColor color = ChatColor.valueOf(volp(p.getName()).team);
				p.setDisplayName(color+p.getName()+ChatColor.WHITE);
				p.setPlayerListName(color+p.getName());
				p.setCustomName(color+p.getName());
			}
			Scoreboard newScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
			Objective o = newScoreboard.registerNewObjective("stats", "dummy");
			o.setDisplayName(ChatColor.YELLOW+"Potion"+ChatColor.DARK_RED+" Wars");
			o.setDisplaySlot(DisplaySlot.SIDEBAR);
			p.setScoreboard(newScoreboard);
			if(Bonus.equals("bridge")){p.getInventory().addItem(ItemUtil.create(Material.CLAY_BRICK, 5, (byte)0, ChatColor.GREEN+"Мост",null,null,null));}
			if(Bonus.equals("bottle")){p.getInventory().addItem(ItemUtil.create(Material.GLASS_BOTTLE, 24, (byte)0, ChatColor.GREEN+"Бутылка собутыльника",null,null,null));}
			if(Bonus.equals("builder")){p.getInventory().addItem(ItemUtil.create(Material.OBSIDIAN, 1, (byte)0, ChatColor.GREEN+"Обсидиан строителя",null,null,null));}
			if(Bonus.equals("star")){p.getInventory().addItem(ItemUtil.create(Material.NETHER_STAR, 3, (byte)0, ChatColor.LIGHT_PURPLE+"Звезда тёмной магии",null,null,null));}
			if(test==false)spawn(p,num);
			num++;
			if(num>=2)num=0;
		}
		if(test==false)timer = startTime;
	}
	static void end(){
		List<Block> bl = new ArrayList<>(events.placed);
		for(Block b:bl){
			if(b.getType().equals(Material.CHEST)){
				Chest ch = (Chest) b.getState();
				ch.getInventory().clear();
			}
			b.setType(Material.AIR);
			events.placed.remove(b);
		}
		upTier = 1;
		timer = timetostart;
		stage = 0;
		for(Player p:Bukkit.getOnlinePlayers()){
			if(canAch.containsKey(p.getName())&&canAch.get(p.getName()).contains("norun")){
				ach(p, "norun");
			}
			if(tutorial.containsKey(p.getName()))tutorial.remove(p.getName());
			p.setDisplayName(p.getName());
			p.setCustomName(p.getName());
			p.setPlayerListName(p.getName());
			p.setHealth(20);
			p.getInventory().clear();
			events.give(p);
			Scoreboard s = p.getScoreboard();
			for(String e:s.getEntries()){
				s.resetScores(e);
			}
			p.teleport(wait);
			p.setGameMode(GameMode.ADVENTURE);
		}
		for(Volcano vol:vols){
			vol.teammates.clear();
		}
	}
	static void spawn(Player p, int num){
		if(dead.containsKey(p.getName())){
			dead.remove(p.getName());
			if(connection.GetStats(p.getName()).get(0).equals("ninja")){
				p.getInventory().addItem(ItemUtil.createPotion(Material.SPLASH_POTION, 1, ChatColor.GRAY + "Дымовая шашка", new String[]{ChatColor.YELLOW+"Уникальное зелье 'Ниндзя'!",ChatColor.LIGHT_PURPLE+"Создаёт небольшую завесу, ослепляющую врагов."}, 225,225,225, null));
			}
		}
		Location loc = volp(p.getName()).spawn;
		p.teleport(new Location(loc.getWorld(),loc.getX()+0.5,loc.getY(),loc.getZ()+0.5));
		p.setGameMode(GameMode.SURVIVAL);
	}
	public static void globMessage(String message,Sound sound){
		for(Player p:Bukkit.getOnlinePlayers()){
			p.sendMessage(message);
			if(sound!=null){
				p.playSound(p.getLocation(), sound, 1, 1);
			}
		}
	}
	public static boolean damage(Player target, double d, Player damager, boolean noknockback, String customMessage, boolean resetDamage){
		if(target.getGameMode().equals(GameMode.CREATIVE)||target.getGameMode().equals(GameMode.SPECTATOR))return false;
		if(damager==null){target.damage(d);}
		else{target.damage(d, damager);}
		if(noknockback==true){
			target.setVelocity(new org.bukkit.util.Vector());
		}
		EntityDamageEvent damevent = target.getLastDamageCause();
		if(target.getHealth()-damevent.getFinalDamage()<=0){
			target.setGameMode(GameMode.SPECTATOR);
			target.setHealth(20);
			if(damager instanceof Player)events.stealres(target,damager,0);
			deathMessage(target,damager,customMessage);
			regDeath(target,false);
			return true;
		}
		if(resetDamage==true){
			target.setNoDamageTicks(0);
		}
		return false;
	}
	public static boolean locisbanned(Location loc){
		for(Location ban:bannedlocs){
			if(loc.equals(ban)){
				return true;
			}
		}
		return false;
	}
	public static int getUpgrade(Volcano vol,String upgrade){
		if(vol==null){
			return -1;
		}
		if(!vol.upgrades.containsKey(upgrade)){
			vol.upgrades.put(upgrade, 0);
		}
		return vol.upgrades.get(upgrade);
	}
	public static void regDeath(Player p,boolean message){
		String name = p.getName();
        double r = new Random().nextDouble();
		double chance = 0.0;
		if(getUpgrade(volp(name), "noclear")>=1){
			chance=chance+getUpgrade(volp(name), "noclear")/100.0*5.0;
		}
		if(r<=chance){
			p.sendMessage(ChatColor.AQUA+"Ваш инвентарь не был очищен!");
		}
		else if(getUpgrade(volp(name), "clearproc")>=1){
			int cofi=0;
			int cofcl=0;
			for(int i=0;i<p.getInventory().getSize();i++){
				if(p.getInventory().getItem(i)!=null){
					double rr = new Random().nextDouble();
					chance = 0.0;
					cofi++;
					chance=getUpgrade(volp(name), "clearproc")/100.0*15.0;
					if(rr>=chance){
						cofcl++;
						p.getInventory().getItem(i).setAmount(0);
					}
				}
			}
			int send = (int) Math.round(chance*100);
			p.sendMessage(ChatColor.YELLOW+"Из вашего инвентаря было удалено "+ChatColor.RED+cofcl+ChatColor.GRAY+"/"+cofi+ChatColor.YELLOW+" предметов. "+ChatColor.GREEN+"("+send+"% шанс)");
		}
		else{p.getInventory().clear();p.sendMessage(ChatColor.RED+"Ваш инвентарь полностью очищен.");}
		if(volp(name).hp>0){
			main.dead.put(p.getName(), 5);
		}
		else{
			if(message==true)globMessage(ChatColor.YELLOW+"Игрок "+ChatColor.GOLD+p.getName()+ChatColor.YELLOW+" умер. "+ChatColor.BOLD+ChatColor.AQUA+ChatColor.UNDERLINE+"ФИНАЛЬНАЯ СМЕРТЬ!",null);
			events.rempl(p);
			if(!team&&plist.size()==1){
				globMessage(ChatColor.GREEN+"Победил игрок "+ChatColor.GOLD+plist, null);
				end();
			}
			else{
				int CoV=0;
				Volcano v=null;
				for(Volcano vol:vols){
					for(String st:vol.teammates){
						if(plist.get(st).inGame){
							CoV++;
							v=vol;
							break;
						}
					}
				}
				if(CoV<=1){
					main.globMessage(ChatColor.valueOf(v.team)+v.team+" победили!", null);
					end();
				}
			}
		}
	}
	public static void deathMessage(Player target,Entity damager,String customMessage){
		String name = target.getName();
		String message = customMessage;
		if(volp(name).hp<=0){
			message = customMessage+ChatColor.BOLD+ChatColor.AQUA+ChatColor.UNDERLINE+"ФИНАЛЬНОЕ УБИЙСТВО! ";
		}
		if(damager!=null&&target.getName().equals(damager.getName())){
			message = message+ChatColor.BOLD+ChatColor.LIGHT_PURPLE+"САМОУБИЙСТВО! ";
		}
		globMessage(message,null);
	}
	public static String team(String name){
		for(Volcano vol:vols){
			if(vol.teammates.contains(name))return vol.team;
		}
		return null;
	}
	public static Volcano volp(String name){
		for(Volcano vol:vols){
			if(vol.teammates.contains(name))return vol;
		}
		return null;
	}
	public static void tutor(Player p, String tut, String tell){
		if(tutorial.containsKey(p.getName())&&!tutorial.get(p.getName()).contains(tut)){
			p.sendMessage(ChatColor.AQUA+"[Обучение] "+tell);
			List<String> repltut = tutorial.get(p.getName());
			repltut.add(tut);
			tutorial.replace(p.getName(), repltut);
		}
	}
	public static ArrayList<String> stringToArrayList(String st){
		ArrayList<String> ret = new ArrayList<>();
		String toadd = "";
		for(int i=0;i<st.length();i++){
			String c = st.charAt(i)+"";
			if(!c.equals(";")){
				toadd=toadd+c;
			}
			else{
				ret.add(toadd);
				toadd="";
			}
		}
		return ret;
	}
	public static String ArrayListToString(ArrayList<String> ara){
		String ret = "";
		for(String st:ara){
			ret = ret+st+";";
		}
		return ret;
	}
	public static void ach(Player p, String id){
		if(p.getGameMode().equals(GameMode.SURVIVAL))return;
		for(Ach ach:GUI.achs){
			if(ach.id.equals(id)){
				ach.mess(p);
				return;
			}
		}
		p.sendMessage(ChatColor.RED+"Эмм... Тут ошибочка вышла, Геппи напортачил с указанием ачивок... Короче, вам сейчас должны были дать какую-то ачивку с кодовым названием "+ChatColor.GOLD+id+ChatColor.RED+", но почему-то этого названия нет в списке ачивок... Скажите Геппи об этом, а то ачивка не работает походу(");
	}
	public static boolean armor(Player p, int slot, Material mat){
		return (p.getEquipment().getArmorContents()[slot]!=null&&p.getEquipment().getArmorContents()[slot].getType().equals(mat));
	}
	public static void loadSchematic(Location loc, String schName){
		WorldEditPlugin we = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
		File schematic = new File(instance.getDataFolder()+File.separator+"/schematics/"+schName+".schematic");
		if(!schematic.exists()){
			GepUtil.debug("schem with name "+schName+" not exist!", null, "error");
			return;
		}
		EditSession session = we.getWorldEdit().getEditSessionFactory().getEditSession(new BukkitWorld(loc.getWorld()), 1000000);
		try{
			CuboidClipboard clipboard = MCEditSchematicFormat.getFormat(schematic).load(schematic);
			clipboard.paste(session, new Vector(loc.getX(),loc.getY(),loc.getZ()), false);
		}catch(MaxChangedBlocksException|DataException|IOException e){
			e.printStackTrace();
		}
	}
}

package potionwars;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

import ObjectsPW.PlayerInfo;
import ObjectsPW.Smoke;
import ObjectsPW.SmokeDie;
import ObjectsPW.Volcano;
import utilsPW.GepUtil;
import utilsPW.ItemUtil;

public class events implements Listener{
	static List<Block> placed = new ArrayList<>();
	//public static ArrayList<Location> smokediear = new ArrayList<>();
	//public static HashMap<Location,Integer> smokedie = new HashMap<>();
	//public static HashMap<Location,String> sdowners = new HashMap<>();
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Player p=e.getPlayer();
		if(!main.plist.containsKey(p.getName())){
			main.plist.put(p.getName(), new PlayerInfo());
		}
		Scoreboard newScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective o = newScoreboard.registerNewObjective("stats", "dummy");
		o.setDisplayName(ChatColor.YELLOW+"Potion"+ChatColor.DARK_RED+" Wars");
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		p.setScoreboard(newScoreboard);
		p.getInventory().clear();
		if(main.stage==0){
			p.teleport(main.wait);
			p.setGameMode(GameMode.ADVENTURE);
			give(p);
		}
		else{
			if(!main.plist.get(p.getName()).inGame){
				p.teleport(main.wait);
				p.setGameMode(GameMode.SPECTATOR);
			}
			updateScoreboard(p);
		}
		if(main.connection.GetStats(p.getName())==null){
			try {
				main.connection.SetBonus(p.getName(), "bottle");
				main.connection.SetHelp(p.getName(), 1);
				main.connection.SetAchs(p.getName(), "");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	static void give(Player p){
		p.getInventory().setItem(1,ItemUtil.create(Material.EMERALD, ChatColor.GREEN+"Магазин"));
		if(main.team)p.getInventory().setItem(4,ItemUtil.create(Material.WOOL, ChatColor.GREEN+"Выбор команды"));
		p.getInventory().setItem(7,ItemUtil.create(Material.TOTEM, ChatColor.GREEN+"Готов!"));
	}
	@EventHandler
	public void onLeave(PlayerQuitEvent e){
		Player p = e.getPlayer();
		if(main.stage==0)main.plist.remove(p.getName());
		if(p.getGameMode().equals(GameMode.SPECTATOR))return;
		main.regDeath(e.getPlayer(),false);
		main.deathMessage(e.getPlayer(), null, e.getPlayer().getDisplayName()+ChatColor.YELLOW+" был убит богом интернета за то, что он отключился.");
	}
	@EventHandler
	public void explode(EntityExplodeEvent e){
		e.setCancelled(true);
		e.getLocation().getWorld().spawnParticle(Particle.EXPLOSION_HUGE,e.getLocation(), 1, 0.1, 0.1, 0.1, 0.0);
		for(Block b:e.blockList()){
			if(placed.contains(b)){
				b.setType(Material.AIR);
			}
		}
	}
	@EventHandler
	public void proj(ProjectileHitEvent e){
		if(!(e.getEntity().getShooter() instanceof Player))return;
		if(e.getEntity().getType().equals(EntityType.SNOWBALL)){
			main.smokes.add(new Smoke("freeze", 2, 5, e.getEntity().getLocation(), ((Player) e.getEntity().getShooter()).getName(), 1, Particle.CLOUD, 3, 0.3, 0.3, 0));
		}
	}
	@EventHandler
	public void interact(PlayerInteractEvent e){
		if (!e.getHand().equals(EquipmentSlot.HAND)){return;}
        Player p = e.getPlayer();
        PlayerInfo pi = main.plist.get(p.getName());
        if(p.getInventory().getItemInMainHand().hasItemMeta() && p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.GREEN+"Магазин")){
        	GUI.shop(p);
        }
        if(p.getInventory().getItemInMainHand().hasItemMeta() && p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.GREEN+"Выбор команды")){
        	GUI.SelectTeam(p);
        }
        //debug
        //p.sendMessage(""+p.getInventory().getItemInMainHand().getType());
        if(e.getAction().equals(Action.RIGHT_CLICK_AIR)||e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
        	if(p.getInventory().getItemInMainHand().getType().equals(Material.FIREBALL)){
        		e.setCancelled(true);
        		if(pi.timers.containsKey("fireball")||!pi.changeMana(p, -50)){
        			return;
        		}
        		Fireball f = p.launchProjectile(Fireball.class);
        		f.setDirection(p.getLocation().getDirection().multiply(2.5));
        		f.setYield((float) 2.5);
        		p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-1);
        		GepUtil.HashMapReplacer(pi.timers, "fireball", 8, false);
        	}
        	if(p.getInventory().getItemInMainHand().getType().equals(Material.SNOW_BALL)){
        		if(pi.timers.containsKey("snowball")||!pi.changeMana(p, -20)){
        			e.setCancelled(true);
        			return;
        		}
        		GepUtil.HashMapReplacer(pi.timers, "snowball", 2, false);
        	}
        	if(p.getInventory().getItemInMainHand().getType().equals(Material.PAPER)){
        		if(pi.timers.containsKey("fusroda")){
        			return;
        		}
        		GepUtil.HashMapReplacer(pi.timers, "fusroda", 5, false);
        		p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-1);
        		p.playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 1, 1);
        		for(Entity en:p.getNearbyEntities(5, 5, 5)){
        			Vector vec = p.getLocation().getDirection();
        			if(vec.getY()<=0)vec.setY(vec.getY()-vec.getY()*2);
            		en.setVelocity(new Vector(vec.getX()*1.5,vec.getY()*1.5,vec.getZ()*1.5));
        			if(en instanceof Player && main.team(en.getName()).equals(main.team(p.getName()))){
        				((Player) en).playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 1, 1);
        			}
        		}
        	}
        	if(p.getInventory().getItemInMainHand().getType().equals(Material.FEATHER)){
        		if(pi.timers.containsKey("jump")||!pi.changeMana(p, -30)){
        			return;
        		}
        		GepUtil.HashMapReplacer(pi.timers, "jump", 8, false);
        		p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-1);
        		p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1, 1);
        		Vector vec = p.getLocation().getDirection();
        		if(vec.getY()<=0)vec.setY(vec.getY()-vec.getY()*2);
            	p.setVelocity(new Vector(vec.getX()*2.5,vec.getY()*1.5+0.25,vec.getZ()*2.5));
        	}
        	if(p.getInventory().getItemInMainHand().getType().equals(Material.TOTEM)){
        		if(pi.timers.containsKey("ready")){
        			pi.timers.remove("ready");
        			p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 2, 1);
        		}
        		else{
        			pi.timers.put("ready", 13370);
        			p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_YES, 2, 1);
        			if(!main.team&&Bukkit.getOnlinePlayers().size()>1||main.team&&Bukkit.getOnlinePlayers().size()>2){
        				boolean allready=true;
        				for(PlayerInfo pli:main.plist.values()){
        					if(!pli.timers.containsKey("ready")){
        						allready=false;
        						break;
        					}
        				}
        				if(allready&&main.stage==0){
        					main.timer=3;
        					for(PlayerInfo pli:main.plist.values()){
            					if(pli.timers.containsKey("ready")){
            						pli.timers.remove("ready");
            					}
            				}
        				}
        			}
        		}
        	}
        }
		if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			Block b = e.getClickedBlock();
			if(b.getType().equals(Material.CAULDRON)){
				e.setCancelled(true);
				GUI.cauldron(p);
				return;
			}
			if(b.getType().equals(Material.FURNACE)){
				e.setCancelled(true);
				if(!p.isSneaking()){
					utilization(p);
				}
				else{
					int am=p.getInventory().getItemInMainHand().getAmount();
					while(am>0){
						utilization(p);
						am--;
					}
				}
				return;
			}
			if(p.getInventory().getItemInMainHand().hasItemMeta())
			if(!e.isCancelled()&&p.getInventory().getItemInMainHand().getType().equals(Material.CLAY_BRICK)&&pi.changeMana(p, -10)){
				if(getDirection(p).equals("N")){
					fill(b.getX()-3,b.getY(),b.getZ()-1,3,1,3,Material.GRASS_PATH, null);
				}
				if(getDirection(p).equals("NE")){
					fill(b.getX()-2,b.getY(),b.getZ()-2,3,1,3,Material.GRASS_PATH, null);
				}
				if(getDirection(p).equals("E")){
					fill(b.getX()-1,b.getY(),b.getZ()-3,3,1,3,Material.GRASS_PATH, null);
				}
				if(getDirection(p).equals("SE")){
					fill(b.getX(),b.getY(),b.getZ()-2,3,1,3,Material.GRASS_PATH, null);
				}
				if(getDirection(p).equals("S")){
					fill(b.getX()+1,b.getY(),b.getZ()-1,3,1,3,Material.GRASS_PATH, null);
				}
				if(getDirection(p).equals("SW")){
					fill(b.getX(),b.getY(),b.getZ(),3,1,3,Material.GRASS_PATH, null);
				}
				if(getDirection(p).equals("W")){
					fill(b.getX()-1,b.getY(),b.getZ()+1,3,1,3,Material.GRASS_PATH, null);
				}
				if(getDirection(p).equals("NW")){
					fill(b.getX()-2,b.getY(),b.getZ(),3,1,3,Material.GRASS_PATH, null);
				}
				double ch=0;
				if(main.connection.GetStats(p.getName()).get(0).equals("bridge"))ch+=0.25;
				if(p.getEquipment().getArmorContents()[3]!=null&&p.getEquipment().getArmorContents()[3].getType().equals(Material.GOLD_HELMET))ch+=0.1;
				if(ch<=0)p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-1);
				else{
					double r = new Random().nextDouble();
					if(r>=ch){
						p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-1);
					}
					else{
						p.sendMessage(ChatColor.GREEN+"Вы не потеряли свой мост!");
						p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_YES, 1, (float) 1.5);
					}
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void breakb(BlockBreakEvent e){
		Player p = e.getPlayer();
		PlayerInfo pi = main.plist.get(p.getName());
		Block b = e.getBlock();
		if(b.getType().equals(Material.RED_MUSHROOM) && !p.getGameMode().equals(GameMode.CREATIVE)){
			if(pi.timers.containsKey("mus")){
				e.setCancelled(true);
				if(!p.isSneaking() && !p.hasPermission("vip"))p.sendMessage(ChatColor.RED+"Вы можете собрать не больше 1 гриба за 1 сек.");
				if(!p.isSneaking() && p.hasPermission("vip"))p.sendMessage(ChatColor.RED+"Вы можете собрать не больше 1 гриба за 0.5 сек.");
			}
			else{
				main.tutor(p, "mus", ChatColor.GREEN+"А вот и грибы! Самый нужный для атаки ресурс! "+ChatColor.RED+"Вы не можете быстро собирать грибы. Они нужны всем, не только вам. Вы это поймёте, если будете играть в team режиме. Если вам надоело, что грибы нельзя быстро собирать - сделайте грибной магнит, либо вы можете купить VIP.");
				if(GUI.testitem(p, Material.RED_MUSHROOM, 640)==true)main.ach(p, "mush");
				p.getInventory().addItem(ItemUtil.create(Material.RED_MUSHROOM, main.upTier, (byte) 0, ChatColor.RED+"Гриб", null, null, null));
				if(!p.getInventory().getItemInMainHand().getType().equals(Material.GOLD_BARDING)){
					if(p.hasPermission("vip"))GepUtil.HashMapReplacer(pi.timers, "mus", 2, false);
					else GepUtil.HashMapReplacer(pi.timers, "mus", 4, false);
				}
				return;
			}
		}
		if(p.getGameMode().equals(GameMode.CREATIVE) && p.getInventory().getItemInMainHand().getType().equals(Material.FLINT)){
			if(b.getType().equals(Material.EMERALD_BLOCK)){
				main.wait=b.getLocation();
				p.sendMessage(ChatColor.YELLOW+"Установлена точка комнаты ожидания.");
			}
			if(b.getType().equals(Material.DIAMOND_BLOCK)){
				main.center=b.getLocation();
				p.sendMessage(ChatColor.YELLOW+"Установлен центр карты.");
			}
			if(b.getType().equals(Material.WOOL)){
				String color = DyeColor.getByWoolData(b.getData()).toString();
				for(Volcano vol:main.vols){
					if(vol.team.equals(color)){
						vol.spawn=b.getLocation();
						p.sendMessage(ChatColor.YELLOW+"Установлена точка респы вулкану "+ChatColor.valueOf(color)+color);
						return;
					}
				}
				main.vols.add(new Volcano(new Location(b.getWorld(),b.getX()+0.5,b.getY(),b.getZ()+0.5),color,b.getLocation()));
				p.sendMessage(ChatColor.YELLOW+"Добавлен новый вулкан - "+ChatColor.valueOf(color)+color);
			}
		}
		else if(placed.contains(e.getBlock())){
			placed.remove(e.getBlock());
		}
		else if(!p.getGameMode().equals(GameMode.CREATIVE)){
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void place(BlockPlaceEvent e){
		Player p = e.getPlayer();
		Block b = e.getBlock();
		if(!p.getGameMode().equals(GameMode.CREATIVE)){
			placed.add(e.getBlock());
		}
		if(b.getType().equals(Material.RED_MUSHROOM)){
			if(p.getGameMode().equals(GameMode.CREATIVE)){
				main.moshs.add(b.getLocation());
				p.sendMessage(ChatColor.GREEN+"Этот гриб будет респаться.");
			}
			else{
				e.setCancelled(true);
				p.sendMessage(ChatColor.RED+"Нееет, посадить этот гриб не получится.");
			}
		}
		if(b.getType().equals(Material.REDSTONE_WIRE)){
			e.setCancelled(true);
		}
		if(main.locisbanned(b.getLocation())==true){
			p.sendMessage(ChatColor.RED+"Тут нельзя ставить блоки.");
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void drink(PlayerItemConsumeEvent e){
		if(e.getItem().getType().equals(Material.POTION)){
			Player p = e.getPlayer();
			PlayerInfo pi=main.plist.get(p.getName());
			String name = e.getItem().getItemMeta().getDisplayName();
			if(name.equalsIgnoreCase(ChatColor.BLUE+"Зелье маны")){
				if(pi.timers.containsKey("manapotion")){
					p.sendMessage(ChatColor.RED+"Слишком много маны! Не надо так!");
					e.setCancelled(true);
					return;
				}
				p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 2, 1);
				p.getWorld().spawnParticle(Particle.CRIT_MAGIC, p.getEyeLocation(), 25, 0.3, 0.3, 0.3, 1);
				pi.maxMana+=50;pi.changeMana(p, 50);pi.timers.put("manapotion", 240);
			}
			if(name.equalsIgnoreCase(ChatColor.AQUA+"Ускоритель маны")){
				if(pi.timers.containsKey("manaspeed")){
					p.sendMessage(ChatColor.RED+"Слишком быстрая мана! Не надо так!");
					e.setCancelled(true);
					return;
				}
				p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 2, 1);
				p.getWorld().spawnParticle(Particle.CRIT_MAGIC, p.getEyeLocation(), 25, 0.3, 0.3, 0.3, 1);
				pi.timers.put("manaspeed", 240);
			}
		}
	}
	@EventHandler
	public void onSplash(PotionSplashEvent e){
	    Player shooter = (Player) e.getPotion().getShooter();
	    usepotion(shooter, e.getPotion());
	    for(PotionEffect pef:e.getPotion().getEffects()){
	        if(!pef.getType().equals(PotionEffectType.POISON)) continue;
	        e.setCancelled(true);
	        int duration=pef.getDuration();
	        for(LivingEntity entity:e.getAffectedEntities()){
	            if(!(entity instanceof Player)) continue;
	            Player pl = (Player) entity;
	            int time=(int)(duration*e.getIntensity(entity));
	            if(pl.getEquipment().getArmorContents()!=null&&pl.getEquipment().getArmorContents()[3]!=null){
	                Material helmet=pl.getEquipment().getArmorContents()[3].getType();
	                int coef = 0;
	                if(helmet==Material.LEATHER_HELMET) coef=10;
	                else if(helmet==Material.GOLD_HELMET) coef=30;
	                else if(helmet==Material.DIAMOND_HELMET) coef=50;
	                time=(int)(time-=time/100*coef);
	            }
	            pl.addPotionEffect(new PotionEffect(PotionEffectType.POISON, time, pef.getAmplifier(), true, true),true);
	        }
	    }
	}
	@EventHandler
	public void onCraft(CraftItemEvent e){
		e.setCancelled(true);
		e.getWhoClicked().sendMessage(ChatColor.RED+"Что это ты задумал? Неее, не прокатит.");
	}
	public static HashMap<String,Integer> respawn = new HashMap<>();
	@EventHandler
	public void hurt(EntityDamageEvent e){
		if(e.getEntity() instanceof Player){
			Player p = (Player) e.getEntity();
			PlayerInfo pi = main.plist.get(p.getName());
			if(main.stage==0||main.timer>=(main.startTime-5)||p.getGameMode().equals(GameMode.SPECTATOR)){
				e.setCancelled(true);
				return;
			}
			DamageCause cause = e.getCause();
			if(cause.equals(DamageCause.VOID)){
				if(pi.lastHurt!=null)GepUtil.HashMapReplacer(pi.timers, "lastHurtCancel", 5, false);
				if(main.getUpgrade(main.volp(p.getName()), "novoid")>=1){
					double r = new Random().nextDouble();
					double bonus = 0;
					int games = Integer.parseInt(main.connection.GetStats(p.getName()).get(3));
					if(games<=3)bonus+=0.05;
					if(games<=5)bonus+=0.05;
					double chance = main.getUpgrade(main.volp(p.getName()), "novoid")/100.0*5.0+bonus;
					if(r<=chance){
						Location loc = main.volp(p.getName()).spawn;
						p.teleport(new Location(loc.getWorld(),loc.getX()+0.5,loc.getY(),loc.getZ()+0.5));
						p.sendMessage(ChatColor.AQUA+"Вы были спасены от пустотной смерти благодаря командной прокачке "+ChatColor.YELLOW+"'Пустотное восстание'"+ChatColor.AQUA+"!");
						if(main.getUpgrade(main.volp(p.getName()), "novoid")==1)main.ach(p, "voidgod");
						if(main.volp(p.getName()).hp<=0)main.ach(p, "voidfinalgod");
						e.setCancelled(true);
						p.setFallDistance(0);
					}
				}
			}
			if(cause.equals(DamageCause.ENTITY_EXPLOSION)){
				e.setDamage(e.getDamage()/2);
			}
		}
		if(e.isCancelled()&&main.test)e.setCancelled(false);
	}
	@EventHandler
	public void entdam(EntityDamageByEntityEvent e){
		if(e.getEntity() instanceof Player){
			Player p = (Player) e.getEntity();
			PlayerInfo pi = main.plist.get(p.getName());
			if(e.getDamager() instanceof Player){
				if(main.team(e.getEntity().getName()).equals(main.team(e.getDamager().getName())) && !e.getEntity().getName().equals(e.getDamager().getName())){
					e.setCancelled(true);
				}
				else if(e.getDamager() instanceof Player){
					pi.lastHurt=e.getDamager().getName();
					GepUtil.HashMapReplacer(pi.timers, "lastHurtCancel", 20, false);
				}
			}
			else if(e.getDamager() instanceof ThrownPotion){
				Entity damager = (Entity) ((Projectile) e.getDamager()).getShooter();
				if(!main.test&&main.volp(p.getName()).equals(main.volp(damager.getName())) && !e.getEntity().getName().equals(damager.getName())){
					e.setCancelled(true);
				}
				else{
					double damToSet=e.getDamage();
					pi.lastHurt=damager.getName();
					GepUtil.HashMapReplacer(pi.timers, "lastHurtCancel", 20, false);
					if(e.getEntity().getName().equals(damager.getName())){
						damToSet/=2;
					}
					double coef = 1;
					if(p.getEquipment().getArmorContents()[1].getType().equals(Material.LEATHER_LEGGINGS))coef=0.9;
					if(p.getEquipment().getArmorContents()[1].getType().equals(Material.GOLD_LEGGINGS))coef=0.7;
					if(p.getEquipment().getArmorContents()[1].getType().equals(Material.DIAMOND_LEGGINGS))coef=0.5;
					damToSet/=coef;
					p.sendMessage("real="+e.getDamage()+";set="+damToSet);
					e.setDamage(damToSet);
				}
			}
			else if(e.getDamager() instanceof Fireball){
				Entity damager = (Entity) ((Projectile) e.getDamager()).getShooter();
				if(main.volp(e.getEntity().getName()).equals(main.volp(damager.getName())) && !e.getEntity().getName().equals(damager.getName())){
					e.setCancelled(true);
				}
				else{
					pi.lastHurt=damager.getName();
					GepUtil.HashMapReplacer(pi.timers, "lastHurtCancel", 20, false);
				}
			}
		}
		if(e.isCancelled()&&main.test)e.setCancelled(false);
	}
	@EventHandler
	public void death(PlayerDeathEvent e){
		Player p=e.getEntity();
		PlayerInfo pi = main.plist.get(p.getName());
		e.setDeathMessage("");
		p.setGameMode(GameMode.SPECTATOR);
		p.setHealth(20);
		EntityDamageEvent damevent = p.getLastDamageCause();
		DamageCause cause = damevent.getCause();
		Entity damager = null;
		if(damevent instanceof EntityDamageByEntityEvent){
			EntityDamageByEntityEvent ene = (EntityDamageByEntityEvent) p.getLastDamageCause();
			damager = ene.getDamager();
		}
		if(cause.equals(DamageCause.VOID)){
			if(main.team==true){
				Location loc = main.volp(p.getName()).spawn;
				p.teleport(new Location(loc.getWorld(),loc.getX()+0.5,loc.getY(),loc.getZ()+0.5));
			}
			String mes = ChatColor.GOLD+p.getDisplayName()+ChatColor.YELLOW+" познал, что такое пустотная смерть";
			if(pi.lastHurt!=null){
				mes+=" благодаря "+ChatColor.GOLD+pi.lastHurt+ChatColor.YELLOW+".";
				if(Bukkit.getPlayer(pi.lastHurt)!=null)stealres(p,Bukkit.getPlayer(pi.lastHurt),50);
			}
			else mes+=".";
			main.deathMessage(p,null,mes);
		}
		else if(cause.equals(DamageCause.ENTITY_ATTACK)){main.deathMessage(p,damager,ChatColor.GOLD+p.getDisplayName()+ChatColor.YELLOW+" был забит до смерти игроком "+ChatColor.GOLD+((Player) damager).getDisplayName()+ChatColor.YELLOW+". ");stealres(p,(Player) damager,0);}
		else if(cause.equals(DamageCause.MAGIC)){
			ThrownPotion potion = (ThrownPotion) damager;
			main.deathMessage(p,(Entity) potion.getShooter(),ChatColor.GOLD+p.getDisplayName()+ChatColor.YELLOW+" был убит зельем игрока "+ChatColor.GOLD+((Player) potion.getShooter()).getDisplayName()+ChatColor.YELLOW+". ");
			stealres(p,(Player) potion.getShooter(),0);
		}
		else if(cause.equals(DamageCause.PROJECTILE)&&damager instanceof Fireball){
			Fireball potion = (Fireball) damager;
			main.deathMessage(p,(Entity) potion.getShooter(),ChatColor.GOLD+p.getDisplayName()+ChatColor.YELLOW+" умер, когда узнал, что в него попал огненный шар игрока "+ChatColor.GOLD+((Player) potion.getShooter()).getDisplayName()+ChatColor.YELLOW+". ");
			stealres(p,(Player) potion.getShooter(),25);
		}
		else if(cause.equals(DamageCause.ENTITY_EXPLOSION)){
			Fireball potion = (Fireball) damager;
			main.deathMessage(p,(Entity) potion.getShooter(),ChatColor.GOLD+p.getDisplayName()+ChatColor.YELLOW+" погиб от осколков взрыва огненного шара игрока "+ChatColor.GOLD+((Player) potion.getShooter()).getDisplayName()+ChatColor.YELLOW+". ");
			stealres(p,(Player) potion.getShooter(),25);
		}
		main.regDeath(p, false);
	}
	@EventHandler
	public void chat(AsyncPlayerChatEvent e){
		String mes = e.getMessage();
		Player p = e.getPlayer();
		if(p.isOp()){
			if(mes.equals("start")){
				e.setCancelled(true);
				if(main.stage==0)main.timer=3;
			}
			if(mes.equals("test")){
				e.setCancelled(true);
				if(main.test)main.globMessage(ChatColor.BLUE+"Режим теста выключен",Sound.BLOCK_STONE_BUTTON_CLICK_OFF);
				else main.globMessage(ChatColor.AQUA+"Режим теста включен",Sound.BLOCK_STONE_BUTTON_CLICK_ON);
				main.test=!main.test;
			}
		}
	}
	//TODO EVENTSUP
	static void utilization(Player p){
		if(p.getInventory().getItemInMainHand().getType().equals(Material.GLASS_BOTTLE)){
			List<String[]> todrop = new ArrayList<>();
			if(!main.connection.GetStats(p.getName()).get(0).equals("bottle")){todrop.add(new String[]{"nothing","white","magicGrass","gray"});}
			else {todrop.add(new String[]{"white","magicGrass","gray"});}
			String[] drops = todrop.get(0);
			String drop = drops[new Random().nextInt(drops.length)];
			if(drop.equalsIgnoreCase("white")){
				p.getInventory().addItem(ItemUtil.create(Material.SUGAR, ChatColor.WHITE+"Белый порошок"));
			}
			if(drop.equalsIgnoreCase("magicGrass")){
				p.getInventory().addItem(ItemUtil.create(Material.SEEDS, ChatColor.GREEN+"Волшебные травы"));
			}
			if(drop.equalsIgnoreCase("gray")){
				p.getInventory().addItem(ItemUtil.create(Material.SULPHUR, ChatColor.GRAY+"Серый порошок"));
			}
			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-1);
			p.playSound(p.getLocation(), Sound.BLOCK_GLASS_BREAK, 1, 2);
			return;
		}
		if(p.getInventory().getItemInMainHand().getType().equals(Material.BLAZE_POWDER)){
			for(int i=0;i<3;i++){
			String[] drops = {"white","red","gold","gray","magicGrass"};
			String drop = drops[new Random().nextInt(drops.length)];
			if(drop.equalsIgnoreCase("red")){
				p.getInventory().addItem(ItemUtil.create(Material.REDSTONE, ChatColor.RED+"Красный порошок"));
			}
			if(drop.equalsIgnoreCase("white")){
				p.getInventory().addItem(ItemUtil.create(Material.SUGAR, ChatColor.WHITE+"Белый порошок"));
			}
			if(drop.equalsIgnoreCase("gold")){
				p.getInventory().addItem(ItemUtil.create(Material.GLOWSTONE_DUST, ChatColor.YELLOW+"Золотой порошок"));
			}
			if(drop.equalsIgnoreCase("gray")){
				p.getInventory().addItem(ItemUtil.create(Material.SULPHUR, ChatColor.GRAY+"Серый порошок"));
			}
			if(drop.equalsIgnoreCase("magicGrass")){
				p.getInventory().addItem(ItemUtil.create(Material.SEEDS, ChatColor.GREEN+"Волшебные травы"));
			}
			}
			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-1);
			p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1, 2);
			return;
		}
		if(p.getInventory().getItemInMainHand().getType().equals(Material.NETHER_STAR)){
			int c = 7;
			if(main.connection.GetStats(p.getName()).get(0).equals("star"))c=9;
			for(int i=0;i<c;i++){
				List<String[]> todrop = new ArrayList<>();
				if(!main.connection.GetStats(p.getName()).get(0).equals("star")){todrop.add(new String[]{"red","gold","mushroom"});}
				else {todrop.add(new String[]{"white","magicGrass","gray","red","gold","mushroom"});}
				String[] drops = todrop.get(0);
				String drop = drops[new Random().nextInt(drops.length)];
			if(drop.equalsIgnoreCase("red")){
				p.getInventory().addItem(ItemUtil.create(Material.REDSTONE, ChatColor.RED+"Красный порошок"));
			}
			if(drop.equalsIgnoreCase("gold")){
				p.getInventory().addItem(ItemUtil.create(Material.GLOWSTONE_DUST, ChatColor.YELLOW+"Золотой порошок"));
			}
			if(drop.equalsIgnoreCase("mushroom")){
				p.getInventory().addItem(ItemUtil.create(Material.RED_MUSHROOM, ChatColor.RED+"Гриб"));
			}
			if(drop.equalsIgnoreCase("white")){
				p.getInventory().addItem(ItemUtil.create(Material.SUGAR, ChatColor.WHITE+"Белый порошок"));
			}
			if(drop.equalsIgnoreCase("magicGrass")){
				p.getInventory().addItem(ItemUtil.create(Material.SEEDS, ChatColor.GREEN+"Волшебные травы"));
			}
			if(drop.equalsIgnoreCase("gray")){
				p.getInventory().addItem(ItemUtil.create(Material.SULPHUR, ChatColor.GRAY+"Серый порошок"));
			}
			}
			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-1);
			p.playSound(p.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 2);
			return;
		}
		if(p.getInventory().getItemInMainHand().getType().equals(Material.AIR)){
			if(!p.isSneaking())p.sendMessage(ChatColor.RED+"Вы что, "+ChatColor.GOLD+"РУКУ "+ChatColor.RED+"собрались утилизировать?");
			return;
		}
		if(!p.isSneaking())p.sendMessage(ChatColor.RED+"Этот предмет нельзя утилизировать.");
	}
	static void usepotion(Player p, ThrownPotion potion){
		if(potion.getItem().getItemMeta().getDisplayName().equals(ChatColor.GRAY+"Завеса смерти")){
			main.sdies.add(new SmokeDie(potion.getLocation(),p));
		}
		else if(potion.getItem().getItemMeta().getDisplayName().equals(ChatColor.RED+"Урон I")){
			for(Volcano vol:main.vols){
				vol.damage(potion.getLocation(), 1.5, 10, (Player) potion.getShooter());
			}
		}
		else if(potion.getItem().getItemMeta().getDisplayName().equals(ChatColor.RED+"Урон II")){
			for(Volcano vol:main.vols){
				vol.damage(potion.getLocation(), 1.5, 20, (Player) potion.getShooter());
			}
		}
		else if(potion.getItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_RED+"Урон III")){
			for(Volcano vol:main.vols){
				vol.damage(potion.getLocation(), 1.5, 35, (Player) potion.getShooter());
			}
		}
		else if(potion.getItem().getItemMeta().getDisplayName().equals(ChatColor.AQUA+"Заморозка")){
			main.smokes.add(new Smoke("freeze", 2, 20, potion.getLocation(), ((Player) potion.getShooter()).getName(), 1.5, Particle.CLOUD, 5, 0.55, 0.45, 0));
			//new Smoke(Type, lvl, Time, Loc, Pname, Radius, Particle, Pc, Pxz, Py, Pextra)
		}
		else if(potion.getItem().getItemMeta().getDisplayName().equals(ChatColor.WHITE+"Туман")){
			main.smokes.add(new Smoke("fog", 1, 50, potion.getLocation(), ((Player) potion.getShooter()).getName(), 8, Particle.CLOUD, 100, 3, 1, 0.1));
			//new Smoke(Type, lvl, Time, Loc, Pname, Radius, Particle, Pc, Pxz, Py, Pextra)
		}
		else if(potion.getItem().getItemMeta().getDisplayName().equals(ChatColor.GRAY+"Дымовая шашка")){
			main.smokes.add(new Smoke("fog", 1, 25, potion.getLocation(), ((Player) potion.getShooter()).getName(), 4, Particle.CLOUD, 50, 1.5, 0.5, 0.05));
			//new Smoke(Type, lvl, Time, Loc, Pname, Radius, Particle, Pc, Pxz, Py, Pextra)
		}
	}
	static void fill(int x, int y, int z, int dx, int dy, int dz, Material mat, Material[] replcust){
		for(int setx=0;setx<dx;setx++){
			for(int sety=0;sety<dy;sety++){
				for(int setz=0;setz<dz;setz++){
					if(replcust==null&&Bukkit.getWorld("world").getBlockAt(x+setx,y+sety,z+setz).getType().equals(Material.AIR)&&main.locisbanned(Bukkit.getWorld("world").getBlockAt(x+setx,y+sety,z+setz).getLocation())==false){
						Bukkit.getWorld("world").getBlockAt(x+setx,y+sety,z+setz).setType(mat);
						placed.add(Bukkit.getWorld("world").getBlockAt(x+setx,y+sety,z+setz));
					}
					if(replcust!=null&&placed.contains(Bukkit.getWorld("world").getBlockAt(x+setx,y+sety,z+setz))){
						for(Material mats:replcust){
							if(Bukkit.getWorld("world").getBlockAt(x+setx,y+sety,z+setz).getType().equals(mats)){
								Bukkit.getWorld("world").getBlockAt(x+setx,y+sety,z+setz).setType(mat);
								placed.add(Bukkit.getWorld("world").getBlockAt(x+setx,y+sety,z+setz));
							}
						}
					}
				}
			}
		}
	}
	public static String getDirection(Player player) {
        double rotation = (player.getLocation().getYaw() - 90) % 360;
        if (rotation < 0) {
            rotation += 360.0;
        }
         if (0 <= rotation && rotation < 22.5) {
            return "N";
        } else if (22.5 <= rotation && rotation < 67.5) {
            return "NE";
        } else if (67.5 <= rotation && rotation < 112.5) {
            return "E";
        } else if (112.5 <= rotation && rotation < 157.5) {
            return "SE";
        } else if (157.5 <= rotation && rotation < 202.5) {
            return "S";
        } else if (202.5 <= rotation && rotation < 247.5) {
            return "SW";
        } else if (247.5 <= rotation && rotation < 292.5) {
            return "W";
        } else if (292.5 <= rotation && rotation < 337.5) {
            return "NW";
        } else if (337.5 <= rotation && rotation < 360.0) {
            return "N";
        } else {
            return null;
        }
    }
	public static void updateScoreboard(Player p){
		PlayerInfo pi = main.plist.get(p.getName());
		Scoreboard s = p.getScoreboard();
		for(String e:s.getEntries()){
			s.resetScores(e);
		}
		Objective o = s.getObjective("stats");
		o.getScore(ChatColor.BLUE+"").setScore(-1);
		o.getScore(ChatColor.RED+"").setScore(1);
		o.getScore(ChatColor.BLUE+"Время: "+ChatColor.YELLOW+main.timer+ChatColor.BLUE+" секунд.").setScore(-2);
		o.getScore(ChatColor.GREEN+"x"+ChatColor.AQUA+(main.upTier+1)+ChatColor.GREEN+" через "+ChatColor.YELLOW+main.upTime+ChatColor.BLUE+" секунд.").setScore(-3);
		if(main.volp(p.getName())==null)return;
		o.getScore(ChatColor.GREEN+"Ваш вулкан: "+ChatColor.AQUA+main.volp(p.getName()).hp+ChatColor.GREEN+"%").setScore(0);
		if(main.team==false){
			int i=2;
			for(Volcano vol:main.vols){
				if(vol.teammates.size()==0)continue;
				Player pl=Bukkit.getPlayer(vol.teammates.get(0));
				if(pl==null)continue;
				if(!pl.getName().equals(p.getName())){
					o.getScore(ChatColor.YELLOW+"Вулкан "+ChatColor.RED+pl.getName()+ChatColor.YELLOW+": "+ChatColor.DARK_RED+main.volp(pl.getName()).hp+ChatColor.YELLOW+"%").setScore(i);
					i++;
				}
			}
		}
		if(main.team==true){
			String team = pi.team;
			int i=2;
			for(Volcano to:main.vols){
				String ott = to.team;
				if(!ott.equals(team)){
					o.getScore(ChatColor.YELLOW+"Вулкан "+ChatColor.valueOf(ott)+ott+ChatColor.YELLOW+": "+ChatColor.DARK_RED+main.volp(p.getName()).hp+ChatColor.YELLOW+"%").setScore(i);
				}
				i++;
			}
		}
	}
	public static void waitScoreboard(Player p){
		Scoreboard s = p.getScoreboard();
		for(String e:s.getEntries()){
			s.resetScores(e);
		}
		Objective o = s.getObjective("stats");
		o.getScore(ChatColor.GREEN+"Сыграно игр: "+ChatColor.AQUA+main.connection.GetStats(p.getName()).get(3)).setScore(3);
		o.getScore(ChatColor.GREEN+"Побед: "+ChatColor.AQUA+main.connection.GetStats(p.getName()).get(4)).setScore(2);
		o.getScore(ChatColor.RED+"").setScore(1);
		o.getScore(ChatColor.BLUE+"До старта: "+ChatColor.YELLOW+main.timer+ChatColor.BLUE+" секунд.").setScore(0);
	}
	public static void rempl(Player p){
		main.plist.get(p.getName()).inGame=false;
		if(main.tutorial.containsKey(p.getName()))main.tutorial.remove(p.getName());
	}
	static void stealres(Player p, Player stealer, int desteal){
		int coef = main.getUpgrade(main.volp(p.getName()), "steal")*15+10;
		Material[] mats = {Material.GLASS_BOTTLE,Material.BLAZE_POWDER,Material.NETHER_STAR,Material.SUGAR,Material.SULPHUR,Material.GLOWSTONE_DUST,Material.REDSTONE,Material.RED_MUSHROOM,Material.EMERALD};
		int totalhaved=0;
		for(Material mat:mats){
			double count=0;
			for(int i=0;i<p.getInventory().getSize();i++){
				if(p.getInventory().getItem(i)!=null) {
					if(p.getInventory().getItem(i).getType().equals(mat)){
						count+=p.getInventory().getItem(i).getAmount();
						totalhaved+=p.getInventory().getItem(i).getAmount();
					}
				}
			}
			int secretbonus = 0;
			int games = Integer.parseInt(main.connection.GetStats(p.getName()).get(3));
			if(games<=3)secretbonus+=5;
			if(games<=5)secretbonus+=5;
			if(desteal>=1)coef=(int) (coef/100.00*(100-desteal));
			count-=(count-count/100*(coef+secretbonus));
			int give = (int) count;
			stealer.getInventory().addItem(ItemUtil.create(mat, give, (byte)0, GUI.matToName(mat), null, null, null));
		}
		if(totalhaved>=320)main.ach(stealer, "uspeh");
		stealer.sendMessage(ChatColor.GREEN+"Вы украли "+coef+"% вещей врага!");
	}
}

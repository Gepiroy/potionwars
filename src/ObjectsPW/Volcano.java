package ObjectsPW;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import potionwars.main;

public class Volcano {
	public Location loc;
	public Location spawn;
	public int hp = 100;
	public String team;
	public ArrayList<String> teammates = new ArrayList<>();
	public HashMap<String,Integer> upgrades = new HashMap<>();
	public Volcano(Location Loc, String Team, Location Spawn){
		loc = Loc;
		spawn = Spawn;
		team = Team;
	}
	public boolean damage(Location damfrom, double range, int damage, Player damager){
		if(damfrom.distance(loc)>range)return false;
		if(teammates.contains(damager.getName())){
			damager.sendMessage(ChatColor.RED+"Это ВАШ вулкан!");
			return false;
		}
		if(hp==0){
			damager.sendMessage(ChatColor.YELLOW+"Этот вулкан уже уничтожен.");
			return false;
		}
		for(String pla:teammates){
			if(Bukkit.getPlayer(pla)!=null){
				Bukkit.getPlayer(pla).sendMessage(""+ChatColor.BOLD+ChatColor.DARK_RED+ChatColor.UNDERLINE+"ВАШ ВУЛКАН АТАКУЮТ!");
				Bukkit.getPlayer(pla).playSound(Bukkit.getPlayer(pla).getLocation(), Sound.ENTITY_ENDERDRAGON_HURT, 1, 1);
			}
		}
		damager.playSound(damager.getLocation(), Sound.ENTITY_ENDERDRAGON_HURT, 1, 1);
		hp-=damage;
		if(hp<=0){
			hp=0;
			for(String pla:teammates){
				main.canAch.get(pla).add("nodievol");
			}
			main.globMessage(ChatColor.RED+"Вулкан "+ChatColor.YELLOW+team+ChatColor.RED+" был разрушен игроком "+ChatColor.DARK_RED+damager.getDisplayName()+ChatColor.RED+".",Sound.ENTITY_ENDERDRAGON_DEATH);
			if(main.timer>=(main.startTime-60))main.ach(damager, "fastvol");
		}
		return true;
	}
}

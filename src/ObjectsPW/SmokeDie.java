package ObjectsPW;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import potionwars.main;

public class SmokeDie {
	public Location loc;
	public int time = 20;
	public String owner;
	public SmokeDie(Location Loc, Player p){
		loc = Loc;
		owner = p.getName();
	}
	public boolean work(){
		if(Bukkit.getPlayer(owner)==null)return false;
		loc.getWorld().spawnParticle(Particle.SMOKE_LARGE, loc, 10, 1,1,1,0);
		for(Entity en:loc.getWorld().getNearbyEntities(loc, 1.5, 1.5, 1.5)){
			if(en instanceof Player){
				Player p = (Player) en;
				if(p.getName().equals(owner)){main.damage(p, 1, Bukkit.getPlayer(owner), true, ChatColor.GOLD+p.getDisplayName()+ChatColor.YELLOW+" погиб в своём собственном смертельном облаке. ", true);}
				else{main.damage(p, 3, Bukkit.getPlayer(owner), true, ChatColor.GOLD+p.getDisplayName()+ChatColor.YELLOW+" погиб в смертельном облаке игрока "+ChatColor.GOLD+owner+ChatColor.YELLOW+". ", true);}
			}
		}
		time--;
		if(time<=0)return false;
		else return true;
	}
}

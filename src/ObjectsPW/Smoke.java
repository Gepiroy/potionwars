package ObjectsPW;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import potionwars.main;

public class Smoke {
	public String type;
	public Location loc;
	public int time = 20;
	public int lvl = 1;
	public String owner;
	Particle particle = Particle.SMOKE_LARGE;
	int pc = 1;
	double py = 1;
	double pxz = 1;
	double pextra = 1;
	double radius = 1;
	public Smoke(String Type, int lvl, int Time, Location Loc, String Pname, double Radius, Particle Particle, int Pc, double Pxz, double Py, double Pextra){
		type=Type;
		loc = Loc;
		owner = Pname;
		particle = Particle;
		pc=Pc;
		pxz=Pxz;
		py=Py;
		pextra=Pextra;
		radius=Radius;
		time = Time;
	}
	public void particle(){
		loc.getWorld().spawnParticle(particle, loc, pc, pxz, py, pxz, pextra, null);
		time--;
	}
	public ArrayList<Entity> entities(boolean cyl){
		ArrayList<Entity> ret = new ArrayList<>();
		for(Entity en:loc.getWorld().getNearbyEntities(loc, radius, radius, radius)){
			if(cyl){
				if(loc.distance(en.getLocation())<=radius)ret.add(en);
			}
			else ret.add(en);
		}
		return ret;
	}
	public ArrayList<Player> players(boolean cyl, boolean ignoreTeam){
		ArrayList<Player> ret = new ArrayList<>();
		for(Entity en:loc.getWorld().getNearbyEntities(loc, radius, radius, radius)){
			if(en instanceof Player){
				Player p = (Player) en;
				if(cyl){
					if(loc.distance(en.getLocation())<=radius)ret.add(p);
				}
				else ret.add(p);
				if(ignoreTeam){
					if(main.volp(owner).teammates.contains(p.getName())){
						ret.remove(p);
					}
				}
			}
		}
		return ret;
	}
}

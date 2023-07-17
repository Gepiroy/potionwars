package ObjectsPW;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class PlayerInfo {
	public String lastHurt = null;
	public String team = null;
	public int iron = 0;
	public int gold = 0;
	public int star = 0;
	public int em = 0;
	public int mana=0;
	public int maxMana=100;
	public boolean inGame = false;
	public HashMap<String,Integer> timers = new HashMap<>();
	public boolean changeMana(Player p, int i){
		if(mana+i<0)return false;
		else if(mana+i>maxMana)return false;
		else{
			mana+=i;
			p.setExp((float) (mana/(maxMana+0.00)));
			p.setLevel(mana);
			return true;
		}
	}
}
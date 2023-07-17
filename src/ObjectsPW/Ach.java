package ObjectsPW;

import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import potionwars.main;
import utilsPW.ItemUtil;

public class Ach {
	public String id;
	String name;
	public int dif = 1;
	String[] lore;
	public Ach(String Id, String Name, int Dif, String[] Lore){
		id = Id;
		name = Name;
		dif = Dif;
		lore = Lore;
	}
	public void mess(Player p){
		if(!main.stringToArrayList(main.connection.GetStats(p.getName()).get(2)).contains(id)){
			p.sendMessage(ChatColor.GREEN+"[Ачивка!] "+name);
			String message = "";
			if(id.equals("irbot"))message=ChatColor.GREEN+"Ты нашёл бутылку! Это же так удивительно! Это же так оригинально! Ура! Слава тебе!";
			else message = lore+"";
			p.sendMessage(message);
			p.sendMessage(ChatColor.GREEN+"{[Поздравляем!]}");
			p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, 1);
			p.playSound(p.getLocation(), Sound.BLOCK_ENDERCHEST_OPEN, 2, 0);
			ArrayList<String> replachs = main.stringToArrayList(main.connection.GetStats(p.getName()).get(2));
			replachs.add(id);
			try {
				main.connection.SetAchs(p.getName(), main.ArrayListToString(replachs));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public ItemStack genAch(Player p){
		int r=0;
		int g=0;
		String diff = ChatColor.YELLOW+"Сложность: ";
		if(dif==1)diff=diff+ChatColor.GREEN+"Легко.";
		if(dif==2)diff=diff+ChatColor.GOLD+"Неплохое испытание.";
		if(dif==3)diff=diff+ChatColor.RED+"Сложно!";
		if(dif==4)diff=diff+ChatColor.DARK_RED+"Dark Souls";
		if(main.stringToArrayList(main.connection.GetStats(p.getName()).get(2)).contains(id))g=255;else r=255;
		return ItemUtil.createPotion(Material.POTION, 1, name, ItemUtil.addlore(lore, diff), r, g, 0, null);
	}
}

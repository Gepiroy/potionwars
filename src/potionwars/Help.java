package potionwars;

import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Help implements CommandExecutor {
	@SuppressWarnings("unused")
	private main plugin;
	public Help(main plugin) {
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender send, Command cmd, String label, String[] args) {
		String help = main.connection.GetStats(send.getName()).get(1);
		if(help.equalsIgnoreCase("1")){
			send.sendMessage(ChatColor.AQUA+"[��������] "+ChatColor.RED+"���? ���! ��� �� ���! ������! � ������� �������! ���� ����� ����� ������ - ���� ��� ������� ��� ���! � �� �����! ..........."+ChatColor.GRAY+".........");
			main.tutorial.remove(send.getName());
			try {
				main.connection.SetHelp(send.getName(), 0);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else{
			send.sendMessage(ChatColor.AQUA+"[��������] "+ChatColor.GREEN+"���! ����... ��, � ���� ����� ����� �� ������. �������, ��� ��-���� ������ ����) ������ � ����� ���� ���� ��������!");
			main.tutorial.put(send.getName(),new ArrayList<>());
			try {
				main.connection.SetHelp(send.getName(), 1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

}

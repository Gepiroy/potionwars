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
			send.sendMessage(ChatColor.AQUA+"[Обучение] "+ChatColor.RED+"Что? Нет! Как ты мог! Неееет! Я задержу дыхание! Если нужна будет помощь - пиши эту команду ещё раз! Я всё прощу! ..........."+ChatColor.GRAY+".........");
			main.tutorial.remove(send.getName());
			try {
				main.connection.SetHelp(send.getName(), 0);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else{
			send.sendMessage(ChatColor.AQUA+"[Обучение] "+ChatColor.GREEN+"Фух! Боже... Да, я могу очень долго не дышать. Спасибо, что всё-таки вернул меня) Теперь я снова буду тебе помогать!");
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

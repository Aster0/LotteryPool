package me.astero.lotterypool.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.astero.lotterypool.LotteryPool;
import me.astero.lotterypool.gui.LotteryDetailsGUI;
import me.astero.lotterypool.gui.MainMenu;
import me.astero.lotterypool.gui.PurchaseTicketMenu;
import me.astero.lotterypool.gui.ViewParticipantsGUI;


public class LotteryPoolCommand implements CommandExecutor {
	
	private LotteryPool main;
	
	public LotteryPoolCommand(LotteryPool main)
	{
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player)
		{
			Player player = (Player) sender;
			
			if(args.length < 1)
			{
				new MainMenu(main, player);
				
			}
			else if(args[0].equalsIgnoreCase("version"))
			{
				if(player.hasPermission("lotterypool.admin"))
				{
					player.sendMessage("");
					player.sendMessage(ChatColor.BLUE + "                      ↣ ------------- ↢" + ChatColor.GRAY 
							+  "\nServer is running " + ChatColor.BLUE + "Companions version " + main.getDescription().getVersion() + ChatColor.BLUE + 
							"\n WIKI: " + ChatColor.GRAY +  "https://gitlab.com/Aster0/lotterypool-reborn/wikis/home" + ChatColor.BLUE + "\n                      ↣ ------------- ↢" );
					player.sendMessage("");
				}
			}
			else if(args[0].equalsIgnoreCase("purchase"))
			{
				new PurchaseTicketMenu(main, player);
			}
			else if(args[0].equalsIgnoreCase("pool"))
			{
				new LotteryDetailsGUI(main, player);
			}
			else if(args[0].equals("help"))
			{
				for(String message : main.getFileHandler().getHelpMessage())
				{
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
				}
			}
			else if(args[0].equalsIgnoreCase("players"))
			{
				new ViewParticipantsGUI(main, player);
			}
			else if(args[0].equals("test"))
			{
				player.sendMessage(main.getLottery().isLotteryStart() + "");
			}
			else
			{
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', 
						main.getFileHandler().getInvalidUsageMessage()));
			}
		}
		else
		{
			System.out.println(ChatColor.GRAY + "You must be a " + ChatColor.BLUE + "player" + ChatColor.GRAY + " to use this command!");
		}
		
		return false;
	}

}

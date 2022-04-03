package me.astero.lotterypool.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.astero.lotterypool.LotteryPool;
import me.astero.lotterypool.gui.MainMenu;

public class LotteryPoolAdminCommand implements CommandExecutor {
	
	private LotteryPool main;
	
	public LotteryPoolAdminCommand(LotteryPool main)
	{
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player)
		{
			Player player = (Player) sender;
			
			if(player.hasPermission("lotterypool.admin"))
			{
				if(args.length > 1)
				{
					if(args[0].equalsIgnoreCase("addpool"))
					{
						try
						{
	
							
							main.getFileManager().getLotteryData().set("current-session.totalpool", 
									main.getFileManager().getLotteryData().getInt("current-session.totalpool") + Integer.parseInt(args[1]));
							
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getFileHandler().getPrefix() +
									"&aSucess! &9$" + args[1] + "&a has been added to the total pool."));
						}
						catch(NumberFormatException notANumber)
						{
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getFileHandler().getPrefix() + "&cYou must input a number!"));
						}
					}
					else if(args[0].equalsIgnoreCase("removepool"))
					{
						try
						{
							
							main.getFileManager().getLotteryData().set("current-session.totalpool", 
									main.getFileManager().getLotteryData().getInt("current-session.totalpool") - Integer.parseInt(args[1]));
							
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getFileHandler().getPrefix() +
									"&aSucess! &9$" + args[1] + "&a has been removed from the total pool."));
						}
						catch(NumberFormatException notANumber)
						{
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getFileHandler().getPrefix() + "&cYou must input a number!"));
						}
					}
					else if(args[0].equalsIgnoreCase("setpool"))
					{
						try
						{
							
							main.getFileManager().getLotteryData().set("current-session.totalpool", 
									Integer.parseInt(args[1]));
							
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getFileHandler().getPrefix() +
									"&aSucess! &9$" + args[1] + "&a has been settled to the total pool."));
						}
						catch(NumberFormatException notANumber)
						{
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getFileHandler().getPrefix() + "&cYou must input a number!"));
						}
					}
				}
				else if(args[0].equalsIgnoreCase("reload"))
				{
					main.getFileManager().reloadConfigs();
						
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getFileHandler().getPrefix() + "&aConfiguration has been successfully reloaded."));
				}
				else
				{
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', 
							main.getFileHandler().getInvalidUsageMessage()));
				}
			}
			else
			{
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', 
						main.getFileHandler().getNoPermissionMessage()));
			}
			
					
				
			
		}
		else
		{
			System.out.println(ChatColor.GRAY + "You must be a " + ChatColor.BLUE + "player" + ChatColor.GRAY + " to use this command!");
		}
		return false;
	}

}

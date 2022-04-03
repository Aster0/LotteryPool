package me.astero.lotterypool.lottery;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.astero.lotterypool.LotteryPool;

public class LotteryTicket {
	
	private LotteryPool main;
	
	public LotteryTicket(LotteryPool main)
	{
		this.main = main;
	}
	
	/*
	 *  Returns the price for 1
	 *  lottery ticket.
	 */	
	public int getPrice()
	{
		return main.getFileHandler().getDefaultTicketPrice() + main.getFileHandler().getTaxAmount();
	}
	
	public void purchase(Player player)
	{
		
		if(main.getEconomyHandler().econ.has(player, getPrice()))
		{
			PlayerData.instanceOf(player).buyTicket();
			
			main.getFileManager().getLotteryData().set("current-session.players." + player.getUniqueId().toString() + ".tickets", 
					PlayerData.instanceOf(player).getTotalTickets());
			
			main.getFileManager().getLotteryData().set("current-session.totalpool", 
					main.getFileManager().getLotteryData().getInt("current-session.totalpool") + main.getFileHandler().getDefaultTicketPrice());
			
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', main.getFileHandler().getPrefix() +
					main.getFileHandler().getParticipatedBroadcast())
					.replace("%player%", player.getName().toString()));
			
			main.getEconomyHandler().econ.withdrawPlayer(player, getPrice());
			
			main.getLottery().removeBossBar();
			
			main.getLottery().bossBarUpdate();
			
		}

	}
	


}

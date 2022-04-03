package me.astero.lotterypool.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.astero.lotterypool.LotteryPool;
import me.astero.lotterypool.lottery.PlayerData;

public class ViewParticipantMenuListener implements Listener {
	
	private LotteryPool main;
	
	public ViewParticipantMenuListener(LotteryPool main)
	{
		this.main = main;
	}
	
	@EventHandler
	public void onClick (InventoryClickEvent e)
	{
		
		Player player = (Player) e.getWhoClicked();
		
		try
		{
			
			boolean lotteryDetailsGUI = ChatColor.translateAlternateColorCodes('&', 
					e.getClickedInventory().getTitle()).equals(ChatColor.translateAlternateColorCodes('&', 
							main.getFileHandler().getViewPlayerName()));
			
			
			if(lotteryDetailsGUI)
			{
				if(e.getCurrentItem() != null)
				{
					e.setCancelled(true);				
					
					String getCurrent = e.getCurrentItem().getItemMeta().getDisplayName();
					
					if(getCurrent.equals(ChatColor.translateAlternateColorCodes('&', main.getFileHandler().getPurchaseTicketName())))
					{
						Bukkit.dispatchCommand(player, "lotterypool purchase");
					}
					else if(getCurrent.equals(ChatColor.translateAlternateColorCodes('&', main.getFileHandler().getGoBackName())))
					{
						if(PlayerData.instanceOf(player).getPageNumber() == 1)
						{
							Bukkit.dispatchCommand(player, "lotterypool");
						}
						else
						{
							
							PlayerData.instanceOf(player).setPageNumber(PlayerData.instanceOf(player).getPageNumber() - 1);
							Bukkit.dispatchCommand(player, "lotterypool players");
						}
					}
					else if(getCurrent.equals(ChatColor.translateAlternateColorCodes('&', main.getFileHandler().getNextPageName())))
					{
						PlayerData.instanceOf(player).setPageNumber(PlayerData.instanceOf(player).getPageNumber() + 1);
						Bukkit.dispatchCommand(player, "lotterypool players");
					}
				}
			}
				
		}
		catch(NullPointerException e1)
		{
			
		}
	}

}

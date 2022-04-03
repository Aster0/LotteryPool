package me.astero.lotterypool.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.astero.lotterypool.LotteryPool;

public class MainMenuListener implements Listener {
	
	private LotteryPool main;
	
	public MainMenuListener(LotteryPool main)
	{
		this.main = main;
	}

    
	@EventHandler
	public void onClick (InventoryClickEvent e)
	{
		Player player = (Player) e.getWhoClicked();
		
		

		
		try
		{
			
			boolean mainGUI = ChatColor.translateAlternateColorCodes('&', 
					e.getClickedInventory().getTitle()).equals(ChatColor.translateAlternateColorCodes('&', 
							main.getFileHandler().getMainMenuTitle()));
			
			
			if(mainGUI)
			{
				if(e.getCurrentItem() != null)
				{
					e.setCancelled(true);				
					

					String getCurrent = e.getCurrentItem().getItemMeta().getDisplayName();
					
					if(getCurrent.equals(ChatColor.translateAlternateColorCodes('&', main.getFileHandler().getPurchaseTicketName())))
					{
						Bukkit.dispatchCommand(player, "lotterypool purchase");
					}
					else if(getCurrent.equals(ChatColor.translateAlternateColorCodes('&', main.getFileHandler().getPoolCheckName())))
					{
						Bukkit.dispatchCommand(player, "lotterypool pool");
					}
					else if(getCurrent.equals(ChatColor.translateAlternateColorCodes('&', main.getFileHandler().getViewPlayerName())))
					{
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

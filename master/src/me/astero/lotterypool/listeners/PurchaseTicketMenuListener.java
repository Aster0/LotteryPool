package me.astero.lotterypool.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.astero.lotterypool.LotteryPool;
import me.astero.lotterypool.gui.PurchaseTicketMenu;
import me.astero.lotterypool.lottery.LotteryTicket;
import me.astero.lotterypool.lottery.PlayerData;

public class PurchaseTicketMenuListener implements Listener {
	
	private LotteryPool main;
	private LotteryTicket ticket;
	
	public PurchaseTicketMenuListener(LotteryPool main)
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
							main.getFileHandler().getPurchaseTicketMenuTitle()));
			
			
			if(mainGUI)
			{
				if(e.getCurrentItem() != null)
				{
					e.setCancelled(true);				
					

					String getCurrent = e.getCurrentItem().getItemMeta().getDisplayName();
					
					if(getCurrent.equals(ChatColor.translateAlternateColorCodes('&', main.getFileHandler().getEnoughMoneyName())))
					{
						if(main.getEconomyHandler().econ.has(player, main.getFileHandler().getLotteryPrice()))
						{
							ticket = new LotteryTicket(main);
							
							ticket.purchase(player);
							
							new PurchaseTicketMenu(main, player);
						}
					}
					else if(getCurrent.equals(ChatColor.translateAlternateColorCodes('&', main.getFileHandler().getGoBackName())))
					{
						PlayerData.instanceOf(player).goBack();
					}
				}
			}
				
		}
		catch(NullPointerException e1)
		{
			
		}
	}

}

package me.astero.lotterypool.gui;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;

import me.astero.lotterypool.LotteryPool;
import me.astero.lotterypool.util.InventoryBuilder;

public class MainMenu {
	
    private LotteryPool main;
    
    public MainMenu(LotteryPool main, Player player)
    {
    	this.main = main;
    	openGUI(player);
    }

	public void openGUI(Player player) 
	{
		
		ArrayList<String> setLore = new ArrayList<>();
		
		for(String getLore : main.getFileHandler().getNextPoolLore())
		{
			setLore.add(ChatColor.translateAlternateColorCodes('&', getLore
					.replace("%nextpooltime%", String.valueOf(main.getFileHandler().getNextPoolTime()))));
		}
		ItemMeta itemMeta;
		itemMeta = main.getFileHandler().getNextPool().getItemMeta();
		itemMeta.setLore(setLore);
		main.getFileHandler().getNextPool().setItemMeta(itemMeta);
		
		setLore.clear();
		for(String getLore : main.getFileHandler().getViewPlayerLore())
		{
			setLore.add(ChatColor.translateAlternateColorCodes('&', getLore
					.replace("%players%", String.valueOf(main.getLottery().getTotalParticipants()))));
		}
		
		itemMeta = main.getFileHandler().getViewPlayer().getItemMeta();
		itemMeta.setLore(setLore);
		main.getFileHandler().getViewPlayer().setItemMeta(itemMeta);
		
		
		Inventory mainMenuGUI = new InventoryBuilder(main.getFileHandler().getMainMenuSize(), main.getFileHandler().getMainMenuTitle())
				.setItem(main.getFileHandler().getPurchaseTicketSlot(), main.getFileHandler().getPurchaseTicket())
				.setItem(main.getFileHandler().getPoolCheckSlot(), main.getFileHandler().getPoolCheck())
				.setItem(main.getFileHandler().getNextPoolSlot(), main.getFileHandler().getNextPool())
				.setItem(main.getFileHandler().getViewPlayerSlot(), main.getFileHandler().getViewPlayer()).build();
		
		player.openInventory(mainMenuGUI);
		
		
		
	}
	
    

}

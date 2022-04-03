package me.astero.lotterypool.gui;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;

import me.astero.lotterypool.LotteryPool;
import me.astero.lotterypool.util.InventoryBuilder;

public class LotteryDetailsGUI {
	
    private LotteryPool main;
    
    public LotteryDetailsGUI(LotteryPool main, Player player)
    {
    	this.main = main;
    	openGUI(player);
    }

	public void openGUI(Player player) 
	{
		
		
		ArrayList<String> setLore = new ArrayList<>();
		
		for(String getLore : main.getFileHandler().getPoolAmountLore())
		{
			setLore.add(ChatColor.translateAlternateColorCodes('&', getLore
					.replace("%time%", String.valueOf(main.getFileHandler().getLotteryDuration())))
					.replace("%amount%", String.valueOf(main.getLottery().getLotteryPool())));
		}
		
		ItemMeta poolAmountMeta = main.getFileHandler().getPoolAmount().getItemMeta();
		poolAmountMeta.setLore(setLore);
		
		main.getFileHandler().getPoolAmount().setItemMeta(poolAmountMeta);
		
		Inventory lotteryDetailsGUI = new InventoryBuilder(main.getFileHandler().getLotteryDetailsSize(), main.getFileHandler().getLotteryDetailsTitle())
				.setItem(main.getFileHandler().getPoolAmountSlot(), main.getFileHandler().getPoolAmount())
				.setItem(main.getFileHandler().lotteryDetailsGoBackSlot, main.getFileHandler().goBack).build();
		
		player.openInventory(lotteryDetailsGUI);
		
		
		
	}

}

package me.astero.lotterypool.gui;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.astero.lotterypool.LotteryPool;
import me.astero.lotterypool.lottery.PlayerData;
import me.astero.lotterypool.util.InventoryBuilder;

public class PurchaseTicketMenu {
	
	private LotteryPool main;
	private ItemStack participate;
	private ItemMeta participateMeta = null;
	
    public PurchaseTicketMenu(LotteryPool main, Player player)
    {
    	this.main = main;
    	openGUI(player);
    }

	public void openGUI(Player player) 
	{
		
		ArrayList<String> setLore = new ArrayList<>();
		
		for(String getLore : main.getFileHandler().getEnoughMoneyLore())
		{
			setLore.add(ChatColor.translateAlternateColorCodes('&', getLore
					.replace("%price%", String.valueOf(main.getFileHandler().getDefaultTicketPrice())))
					.replace("%taxamount%", String.valueOf(main.getFileHandler().getTaxAmount()))
					.replace("%maxtickets%", String.valueOf(main.getFileHandler().getMaxTicketPurchasable()))
					.replace("%tickets%", String.valueOf(PlayerData.instanceOf(player).getTotalTickets())));
		}
		
		
		if(main.getLottery().isLotteryStart() == true)
		{
			if(PlayerData.instanceOf(player).isMaxTicketPurchasable())
			{
				participate = main.getFileHandler().getAlreadyParticipated();
			}
			else if(main.getEconomyHandler().econ.has(player, main.getFileHandler().getLotteryPrice() + main.getFileHandler().getTaxAmount()))
			{
				participate = main.getFileHandler().getEnoughMoney();
				updateLore(setLore);
			}	
			else if(!main.getEconomyHandler().econ.has(player, main.getFileHandler().getLotteryPrice() + main.getFileHandler().getTaxAmount()))
			{
				participate = main.getFileHandler().getNotEnoughMoney(); 
				updateLore(setLore);
			}
		}
		else
		{
			participate = main.getFileHandler().getNoLotteries();
		}

		if(participateMeta != null) participate.setItemMeta(participateMeta);



		
		
		

		
		Inventory purchaseTicketGUI = new InventoryBuilder(main.getFileHandler().getPurchaseTicketMenuSize(), 
				main.getFileHandler().getPurchaseTicketMenuTitle())
				.setItem(main.getFileHandler().getParticipateSlot(), participate)
				.setItem(main.getFileHandler().purchaseTicketGoBackSlot, main.getFileHandler().goBack).build();
		
		player.openInventory(purchaseTicketGUI);
		
		
		
	}
	
	public void updateLore(ArrayList<String> list)
	{
		participateMeta = participate.getItemMeta();
		participateMeta.setLore(list);		
	}

}

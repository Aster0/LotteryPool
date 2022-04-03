/*
 *  I used this from an old page system Util, 
 *  the code will be further optimized in the future.
 */

package me.astero.lotterypool.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import me.astero.lotterypool.LotteryPool;
import me.astero.lotterypool.lottery.PlayerData;
import me.astero.lotterypool.util.InventoryBuilder;

public class ViewParticipantsGUI {
	
	private LotteryPool main;
	private ItemStack participantSkull;
	private SkullMeta participantSkullMeta;
	
    public ViewParticipantsGUI(LotteryPool main, Player player)
    {
    	this.main = main;
    	openGUI(player);
    }

	public void openGUI(Player player) 
	{
		
		Inventory viewParticipantsGUI = new InventoryBuilder(54, 
				main.getFileHandler().getViewPlayerName())
				.setItem(45, main.getFileHandler().goBack)
				.setItem(53, main.getFileHandler().nextPage).build();
		
		try
		{
			participantSkull = new ItemStack(Material.PLAYER_HEAD);
		}
		catch(NoSuchFieldError | NoSuchMethodError versionsBelow1_13)
		{
			participantSkull = new ItemStack(Material.valueOf("SKULL_ITEM"), 1, (short) 3);
		}
		
		participantSkullMeta = (SkullMeta) participantSkull.getItemMeta();
		

		

		try
		{
			for(String participant : main.getFileManager().getLotteryData().getConfigurationSection("current-session.players").getKeys(false))
			{			
				
				Player participatedPlayer = Bukkit.getPlayer(UUID.fromString(participant));
				try
				{
					participantSkullMeta.setOwningPlayer(participatedPlayer.getPlayer());
				}
				catch(NoSuchMethodError versionsBelow1_13)
				{
					participantSkullMeta.setOwner(participatedPlayer.getPlayer().getName());
				}
				
				participantSkullMeta.setDisplayName(ChatColor.BLUE + participatedPlayer.getName().toString());
				participantSkullMeta.setLore(Arrays.asList("", ChatColor.GRAY + "Tickets: ", 
						ChatColor.BLUE + String.valueOf(PlayerData.instanceOf(participatedPlayer).getTotalTickets())));
				
				participantSkull.setItemMeta(participantSkullMeta);
				
				
				int playerSkullPerPage = 54 - 2; // 2 because of goBack and nextPage itemstacks.
				
				int startValue = (PlayerData.instanceOf(player).getPageNumber() - 1) * playerSkullPerPage; // if page one... = 0... 
	
				ArrayList<ItemStack> participantSkulls = new ArrayList<>();
				participantSkulls.add(participantSkull);
	
				
				int endValue = PlayerData.instanceOf(player).getPageNumber()  * playerSkullPerPage;
				
				if(endValue > participantSkulls.size())
				{
					endValue = participantSkulls.size();
				}
				
				try
				{
					for(ItemStack participantItemStack : participantSkulls.subList(startValue, endValue))
					{
	
						    	 
						    	 if(!viewParticipantsGUI.contains(participantItemStack))
						    	 {
						    		 viewParticipantsGUI.addItem(participantItemStack);
						    	 }
		
					     
					}
					
				}
			    catch(IllegalArgumentException error)
			    {
			    	viewParticipantsGUI.addItem(new ItemStack(Material.AIR));
			    }  
			}
		}
		catch(NullPointerException noParticipants) {}
		
		player.openInventory(viewParticipantsGUI);
	}

}

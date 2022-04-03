package me.astero.lotterypool.lottery;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.astero.lotterypool.LotteryPool;

public class PlayerData {
	
	private static LotteryPool main = LotteryPool.getPlugin(LotteryPool.class);
	
	private Player player;
	
	private int totalTickets;
	private int pageNumber = 1;
	
	private final static Map<UUID, PlayerData> players = new HashMap<>(); // One static to control the class.
	
	public PlayerData(Player player)
	{
		this.player = player;
	}
	

	public void updatePlayer(Player player)
	{
		this.player = player;
	}
	
	
    public Player getPlayer() {
        return player;
    }

    public static PlayerData instanceOf(Player player) 
    {	
        players.putIfAbsent(player.getUniqueId(), new PlayerData(player));
        
        if (players.containsKey(player.getUniqueId()))
        {
        	players.get(player.getUniqueId()).updatePlayer(player);
        }
        return players.get(player.getUniqueId());
    }
    
    public void buyTicket()
    {
    	
    	main.getFileManager().getLotteryData().set("current-session.players." + player.getUniqueId().toString() + ".tickets", 
    			getTotalTickets() + 1);
    	this.totalTickets++;
    }
    
    public void setTotalTickets(int newTotal)
    {
    	this.totalTickets = newTotal;
    }
    
    public int getTotalTickets()
    {
    	totalTickets = main.getFileManager().getLotteryData().getInt("current-session.players." + player.getUniqueId().toString() + ".tickets");
    	return totalTickets;
    }
    
    public boolean isMaxTicketPurchasable()
    {
    	return getTotalTickets() == main.getFileHandler().getMaxTicketPurchasable();
    }
    
	public int getPageNumber()
	{
		return pageNumber;
	}
	
	public void setPageNumber(int number)
	{
		this.pageNumber = number;
	}
	
	public void goBack()
	{
		Bukkit.dispatchCommand(player, "lotterypool");
	}
    

}

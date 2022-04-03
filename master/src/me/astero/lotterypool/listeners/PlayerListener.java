package me.astero.lotterypool.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.astero.lotterypool.LotteryPool;

public class PlayerListener implements Listener {
	
	private LotteryPool main;
	
	public PlayerListener(LotteryPool main)
	{
		this.main = main;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e)
	{
		Player player = e.getPlayer();
		
		if(main.getLottery().isLotteryStart())
		{
			main.getLottery().addPlayerBossBar(player);
		}
	}

}

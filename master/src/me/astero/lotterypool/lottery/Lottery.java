package me.astero.lotterypool.lottery;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import me.astero.lotterypool.LotteryPool;

public class Lottery {
	
	private List<UUID> participants = new ArrayList<>();
	private int endID;
	
	private LotteryPool main;
	private long startTime;
	
	private boolean lotteryStart;
	private BossBar bossBar;
	
	public Lottery(LotteryPool main)
	{
		this.main = main;
		
		main.getFileHandler().setLotteryDuration(0); // Set the initial value to 0, so it resets when a session starts.
		
		if(main.getFileHandler().isStartOnServer() == true)
		{
			startTime = main.getFileHandler().getDelayStartTime();
		}
		else
		{
			startTime = main.getFileHandler().getLotteryInterval();
		}
		
		start();
		
		updateCurrentPool();
		updateNextPool();
	}
	
	public void start()
	{
		
	
	 
	 Bukkit.getScheduler().runTaskTimer(main, new Runnable() {
		 
		 
		 
			 public void run()
			 {
				 
				 setLotteryStart(true);
				 main.getFileHandler().setLotteryDuration(main.getFileHandler().getInitialLotteryDuration());
				 
				 Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', 
						 main.getFileHandler().getPrefix() + main.getFileHandler().getLotteryStartBroadcast())
						 .replace("%time%", String.valueOf(main.getFileHandler().getInitialLotteryDuration())));
				 
				 if(main.getFileHandler().isSoundOnStart())
				 {
					 for(Player players : Bukkit.getOnlinePlayers())
					 {
						 try
						 {
							 players.playSound(players.getLocation(), Sound.valueOf(main.getFileHandler().getLotteryStartSound()), 1.0F, 1.0F);
						 }
						 catch(IllegalArgumentException soundNotFound)
						 {
							 System.out.println(ChatColor.RED + "Lottery start sound - " + ChatColor.AQUA + 
									 main.getFileHandler().getLotteryStartSound() + ChatColor.RED +" is not found.");
						 }
					 }
				 }
				 
				 bossBarCreation();
				 
				 end();
	
			 }
		 }, startTime, main.getFileHandler().getLotteryInterval());
	 
	}
	
	
	public void end()
	{
		endID = Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
			 
			@Override
			public void run() 
			{ 
				if(isLotteryStart())
				{
					setLotteryStart(false);
					main.getFileHandler().setLotteryDuration(0);
					
					Random rand = new Random();
					try
					{
						for(String participant : main.getFileManager().getLotteryData().getConfigurationSection("current-session.players").getKeys(false))
						{					
							int getPlayerTickets = PlayerData.instanceOf(Bukkit.getPlayer(UUID.fromString(participant))).getTotalTickets();

								
							for(int loop = 0; loop < getPlayerTickets; loop++)
							{
								participants.add(UUID.fromString(participant));
							}

						}
						
						OfflinePlayer winner = Bukkit.getOfflinePlayer(participants.get(rand.nextInt(participants.size())));
						
						if(main.getFileHandler().isWinnerSound())
						{
							if(winner.isOnline())
							{
								Player onlineWinner = (Player) winner;
								
								try
								{
									onlineWinner.playSound(onlineWinner.getLocation(), 
											Sound.valueOf(main.getFileHandler().getLotteryWinnerSound()), 1.0F, 1.0F);
								}
								 catch(IllegalArgumentException soundNotFound)
								 {
									 System.out.println(ChatColor.RED + "Lottery start sound - " + ChatColor.AQUA + 
											 main.getFileHandler().getLotteryWinnerSound() + ChatColor.RED +" is not found.");
								 }
							}
						}
						
						int prize = main.getFileManager().getLotteryData().getInt("current-session.totalpool");
						
						 Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', 
								 main.getFileHandler().getPrefix() + main.getFileHandler().getLotteryEndBroadcast()
								 .replace("%player%", winner.getName())
								 .replace("%amount%", String.valueOf(prize))));
						 
						 main.getEconomyHandler().econ.depositPlayer(winner, 
								 prize);
						 
							participants.clear();
							
							main.getFileManager().getLotteryData().set("current-session.players", null);
							main.getFileManager().getLotteryData().set("current-session.totalpool", 0);
							main.getFileManager().saveFile();
					}
					
					catch(NullPointerException noWinner) 
					{ 
						Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', main.getFileHandler().getPrefix() 
								+ main.getFileHandler().getNoWinnerBroadcast()));
					}
					finally
					{
						removeBossBar();
						Bukkit.getScheduler().cancelTask(endID); // Makes sure the task doesn't get called twice - more threads.
					}
					
					
					
					
				}				
			}
		}, main.getFileHandler().getInitialLotteryDuration() * 1200);
	}
	
	
	public void bossBarCreation()
	{
		if(main.getFileHandler().isBossBarEnabled())
		{
			bossBar = Bukkit.createBossBar(ChatColor.translateAlternateColorCodes('&', main.getFileHandler().getBossBarStartMessage()), 
					BarColor.valueOf(main.getFileHandler().getBossBarColor()),
					BarStyle.SEGMENTED_6);
		}
		
		addBossBar();
	}
	
	public void removeBossBar()
	{
		if(main.getFileHandler().isBossBarEnabled())
		{
			bossBar.removeAll();
		}
	}
	
	public void addBossBar()
	{
		if(main.getFileHandler().isBossBarEnabled())
		{
			for(Player players : Bukkit.getOnlinePlayers())
			{
				bossBar.addPlayer(players);
			}
		}
	}
	
	public void addPlayerBossBar(Player player)
	{
		if(main.getFileHandler().isBossBarEnabled())
		{
			bossBar.addPlayer(player);
		}
	}
	
	public void bossBarUpdate()
	{
		if(main.getFileHandler().isBossBarEnabled())
		{
			bossBar.removeAll();
			
			bossBar.setTitle(ChatColor.translateAlternateColorCodes('&', main.getFileHandler().getBossBarMessage())
					.replace("%time%", String.valueOf(main.getFileHandler().getLotteryDuration()))
					.replace("%players%", String.valueOf(getTotalParticipants())));
			
			if(main.getFileHandler().getLotteryDuration() <= main.getFileHandler().getBossBarAlert())
			{
				bossBar.setColor(BarColor.RED);
			}
		}
		
		addBossBar();
	}
	
	public void updateCurrentPool()
	{
		Bukkit.getScheduler().runTaskTimerAsynchronously(main, new Runnable() {
			 
			@Override
			public void run() 
			{ 
				if(isLotteryStart())
				{
					
					if(!(main.getFileHandler().getLotteryDuration() < 2))
					{
						main.getFileHandler().setLotteryDuration(main.getFileHandler().getLotteryDuration() - 1);
						
						bossBarUpdate();
					}
				
				}
			}

		}, 1200L, 1200L);
	}
	
	public void updateNextPool()
	{
		Bukkit.getScheduler().runTaskTimerAsynchronously(main, new Runnable() {
			 
			@Override
			public void run() 
			{ 
				
					if(main.getFileHandler().getNextPoolTime() < 2)
					{
						main.getFileHandler().setNextPoolTime(main.getFileHandler().getILotteryInterval());
					}
					else
					{
						main.getFileHandler().setNextPoolTime(main.getFileHandler().getNextPoolTime() - 1);
					}
		
			}

		}, 1200L, 1200L);
	}
	
	public boolean isLotteryStart()
	{
		return lotteryStart;
	}
	
	public void setLotteryStart(boolean started)
	{
		this.lotteryStart = started;
	}
	
	public List<UUID> getParticipants()
	{
		return participants;
	}
	
	public int getTotalParticipants()
	{
		
		int participants;
		
		try
		{
			List<String> totalParticipants = new ArrayList<>();
			for(String participant : main.getFileManager().getLotteryData().getConfigurationSection("current-session.players").getKeys(false))
			{			
				totalParticipants.add(participant);
			}
			
			participants = totalParticipants.size();
		}
		catch(NullPointerException noParticipants)
		{
			participants = 0;
		}
		
		return participants;
	}
	
	public BossBar getBossBar()
	{
		return bossBar;
	}
	
	public int getLotteryPool()
	{
		
		return main.getFileManager().getLotteryData().getInt("current-session.totalpool");
	}

}

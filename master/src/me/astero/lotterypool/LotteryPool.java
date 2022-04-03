package me.astero.lotterypool;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import me.astero.lotterypool.commands.LotteryPoolAdminCommand;
import me.astero.lotterypool.commands.LotteryPoolCommand;
import me.astero.lotterypool.economy.EconomyHandler;
import me.astero.lotterypool.filemanager.FileHandler;
import me.astero.lotterypool.filemanager.FileManager;
import me.astero.lotterypool.listeners.LotteryDetailsMenuListener;
import me.astero.lotterypool.listeners.MainMenuListener;
import me.astero.lotterypool.listeners.PlayerListener;
import me.astero.lotterypool.listeners.PurchaseTicketMenuListener;
import me.astero.lotterypool.listeners.ViewParticipantMenuListener;
import me.astero.lotterypool.lottery.Lottery;

public class LotteryPool extends JavaPlugin{

	private FileManager fileManager;
	private FileHandler fileHandler;
	private Lottery lottery;
    private EconomyHandler economyHandler;
	
	@Override
	public void onEnable() {
		
		/*
		 *  Register economy using the 
		 *  EconomyHandler class.
		 */
		
		System.out.println("\n" + ChatColor.BLUE + "LotteryPool" + ChatColor.GRAY + " by Astero" + ChatColor.BLUE + " is loading up...\n");
		
		economyHandler = new EconomyHandler(this);
		System.out.println(ChatColor.BLUE + "→" + ChatColor.GRAY + " Economy is loaded up!");
		
		getConfig().options().copyDefaults();
		saveDefaultConfig();
		
		getCommand("lotterypool").setExecutor(new LotteryPoolCommand(this));
		getCommand("lotterypoola").setExecutor(new LotteryPoolAdminCommand(this));
		System.out.println(ChatColor.BLUE + "→" + ChatColor.GRAY + " Commands are loaded up!");
		
		Bukkit.getPluginManager().registerEvents(new MainMenuListener(this), this);
		Bukkit.getPluginManager().registerEvents(new PurchaseTicketMenuListener(this), this);
		Bukkit.getPluginManager().registerEvents(new LotteryDetailsMenuListener(this), this);
		Bukkit.getPluginManager().registerEvents(new ViewParticipantMenuListener(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
		
		System.out.println(ChatColor.BLUE + "→" + ChatColor.GRAY + " Event Listeners are loaded up!");
		
		fileManager = new FileManager(this);
		System.out.println(ChatColor.BLUE + "→" + ChatColor.GRAY + " YAML files are loaded up!");
		
		fileHandler = new FileHandler(this);
		System.out.println(ChatColor.BLUE + "→" + ChatColor.GRAY + " Caching has been done!\n");
	
		lottery = new Lottery(this);
		System.out.println(ChatColor.BLUE + "LotteryPool" + ChatColor.GRAY + " by Astero" + ChatColor.BLUE + " has been sucessfully loaded up!\n");

		
	}
	
	@Override
	public void onDisable()
	{
		System.out.println(ChatColor.BLUE + "LotteryPool" + ChatColor.GRAY + " is disabling and saving necessary files..");
		getFileManager().saveFile();
		System.out.println(ChatColor.BLUE + "LotteryPool" + ChatColor.GRAY + " by Astero" + ChatColor.BLUE + " has been sucessfully disabled!\n");
	}
	
	public Lottery getLottery()
	{
		return lottery;
	}
	
	public EconomyHandler getEconomyHandler()
	{
		return economyHandler;
	}
	
	public FileHandler getFileHandler()
	{
		return fileHandler;
	}
	
	public FileManager getFileManager()
	{
		return fileManager;
	}
	
}

package me.astero.lotterypool.filemanager;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import me.astero.lotterypool.LotteryPool;

public class FileManager {
	
	public File lotteryData, messagesData;
	public YamlConfiguration modifyLotteryData, modifyMessagesData;
	private LotteryPool main;
	

	public FileManager(LotteryPool main)
	{
		this.main = main;
		initiateFiles();
	}
	
	// CUSTOM YML FILES
	
	public void initiateFiles()
	{
		try {
			initiateYAMLFiles();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	public YamlConfiguration getLotteryData() 
	{
		return modifyLotteryData;
	}
	

	public File getLotteryFile()
	{
		return lotteryData;
	}
	
	public YamlConfiguration getMessagesData() 
	{
		return modifyMessagesData;
	}
	

	public File getMessagesFile()
	{
		return messagesData;
	}
	
	
	

	public void initiateYAMLFiles() throws IOException
	{
		lotteryData = new File(Bukkit.getServer().getPluginManager()
				.getPlugin("LotteryPool").getDataFolder(), "lotterydata.yml");
		
		messagesData = new File(Bukkit.getServer().getPluginManager()
				.getPlugin("LotteryPool").getDataFolder(), "lang.yml");


		
		if(!lotteryData.exists()) // If lang.yml is not found.
		{
			main.saveResource("lotterydata.yml", false);
		} 
		
		if(!messagesData.exists()) // If lang.yml is not found.
		{
			main.saveResource("lang.yml", false);
		} 
		
		
		modifyLotteryData = YamlConfiguration.loadConfiguration(lotteryData);		
		modifyMessagesData = YamlConfiguration.loadConfiguration(messagesData);		

	}
	
	public void saveFile()
	{
		try {
			modifyLotteryData.save(getLotteryFile());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
    public void reloadConfigs()
    {
    	main.reloadConfig();
		modifyLotteryData = YamlConfiguration.loadConfiguration(lotteryData);
		modifyMessagesData = YamlConfiguration.loadConfiguration(messagesData);		
		
		main.getFileHandler().cache();
    }
	


}

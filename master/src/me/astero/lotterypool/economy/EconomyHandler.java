package me.astero.lotterypool.economy;

import org.bukkit.plugin.RegisteredServiceProvider;

import me.astero.lotterypool.LotteryPool;
import net.milkbowl.vault.economy.Economy;

public class EconomyHandler {
	
	private LotteryPool main;
    public static Economy econ = null;
	
	public EconomyHandler(LotteryPool main)
	{
		this.main = main;
		
        if (!setupEconomy() )
        {
            System.out.println(String.format("[%s] - Disabled due to no Vault dependency found!", main.getDescription().getName()));
            main.getServer().getPluginManager().disablePlugin(main);
            return;
        }
	}
	
    public boolean setupEconomy() {
        if (main.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = main.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
        
    }

}

package me.astero.lotterypool.filemanager;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import lombok.Setter;
import me.astero.lotterypool.LotteryPool;
import me.astero.lotterypool.util.ItemBuilderUtil;
import net.md_5.bungee.api.ChatColor;

public class FileHandler {
	
	@Getter public String mainMenuTitle, purchaseTicketName, poolCheckName, poolAmountName, nextPoolName,
	viewPlayerName, purchaseTicketMenuTitle, enoughMoneyName, notEnoughMoneyName, alreadyParticipatedName,
	bossBarColor, bossBarMessage, lotteryStartBroadcast, prefix, lotteryEndBroadcast, lotteryStartSound, noLotteriesName,
	lotteryDetailsTitle, participatedBroadcast, noWinnerBroadcast, goBackName, nextPageName, invalidUsageMessage, noPermissionMessage,
	lotteryWinnerSound, bossBarStartMessage;
	
	@Getter public int mainMenuSize, purchaseTicketSlot, purchaseTicketAmount, poolCheckSlot, poolCheckAmount, 
	poolAmountAmount, poolAmountSlot, nextPoolSlot, nextPoolAmount, viewPlayerSlot, viewPlayerAmount, participateSlot,
	participateAmount, purchaseTicketMenuSize, enoughMoneyAmount, notEnoughMoneyAmount, alreadyParticipatedAmount,
	defaultTicketPrice, initialLotteryDuration, bossBarAlert, lotteryPrice, maxTicketPurchasable, taxAmount, lotteryDetailsSize,
	lotteryDetailsGoBackSlot, purchaseTicketGoBackSlot;
	
	@Getter @Setter public int lotteryDuration, nextPoolTime, iLotteryInterval;
	
	
	@Getter public long lotteryInterval, delayStartTime;
	
	@Getter public boolean startOnServer, soundOnStart, bossBarEnabled, winnerSound;
	
	@Getter public List<String> enoughMoneyLore, poolAmountLore, nextPoolLore, viewPlayerLore, helpMessage;
	
	@Getter public ItemStack purchaseTicket, poolCheck, poolAmount, nextPool, viewPlayer, notEnoughMoney, enoughMoney,
	alreadyParticipated, noLotteries, goBack, nextPage;
	
	
    private final LotteryPool main;
    
    public FileHandler(LotteryPool lp) {
    	this.main = lp;
    	
    	cache();
    }
    
    
	public void cache() 
	{
		
		invalidUsageMessage = main.getFileManager().getMessagesData().getString("messages-en.invalidusage");
		noPermissionMessage = main.getFileManager().getMessagesData().getString("messages-en.nopermission");
		defaultTicketPrice = main.getConfig().getInt("settings.lotteryprice");
		lotteryInterval = main.getConfig().getLong("settings.lotteryevery") * 1200;
		nextPoolTime = main.getConfig().getInt("settings.lotteryevery");
		iLotteryInterval = main.getConfig().getInt("settings.lotteryevery");
		startOnServer = main.getConfig().getBoolean("settings.onserverstart");
		bossBarColor = main.getConfig().getString("settings.bossbarcolor");
		bossBarMessage = main.getConfig().getString("settings.bossbarmessage");
		lotteryStartBroadcast = main.getFileManager().getMessagesData().getString("messages-en.lotterystartbroadcast");
		participatedBroadcast = main.getFileManager().getMessagesData().getString("messages-en.participatebroadcast");
		noWinnerBroadcast = main.getFileManager().getMessagesData().getString("messages-en.nowinnersbroadcast");
		prefix = main.getFileManager().getMessagesData().getString("messages-en.prefix");
		lotteryDuration = main.getConfig().getInt("settings.lotteryduration");
		initialLotteryDuration = main.getConfig().getInt("settings.lotteryduration");
		bossBarAlert = main.getConfig().getInt("settings.bossbaralert");
		lotteryEndBroadcast = main.getFileManager().getMessagesData().getString("messages-en.lotteryendbroadcast");
		lotteryStartSound = main.getConfig().getString("sounds.startsound");
		soundOnStart = main.getConfig().getBoolean("sounds.playstartsound");
		lotteryWinnerSound = main.getConfig().getString("sounds.winnersound");
		winnerSound = main.getConfig().getBoolean("sounds.playsoundtowinner");
		lotteryPrice = main.getConfig().getInt("settings.lotteryprice");
		maxTicketPurchasable = main.getConfig().getInt("settings.maxtickets");
		taxAmount = main.getConfig().getInt("settings.taxamount");		
		bossBarEnabled = main.getConfig().getBoolean("settings.bossbar");
		purchaseTicketGoBackSlot = main.getConfig().getInt("gui.purchaseTicketGUI.goBackSlot");
		lotteryDetailsGoBackSlot = main.getConfig().getInt("gui.lotteryDetailsGUI.goBackSlot");
		helpMessage = main.getFileManager().getMessagesData().getStringList("messages-en.helpMessage");
		bossBarStartMessage = main.getConfig().getString("settings.bossbarstartmessage");
		delayStartTime = main.getConfig().getLong("settings.delaystart") * 20;
	
			
		mainMenuTitle = main.getConfig().getString("gui.mainMenuGUI.title");
		mainMenuSize = main.getConfig().getInt("gui.mainMenuGUI.size");
		
		purchaseTicketName = main.getConfig().getString("gui.mainMenuGUI.items.purchase-ticket.name");
		purchaseTicketAmount = main.getConfig().getInt("gui.mainMenuGUI.items.purchase-ticket.amount");
		
		
		purchaseTicketSlot = main.getConfig().getInt("gui.mainMenuGUI.items.purchase-ticket.slot");
		
		try
		{
			purchaseTicket = new ItemBuilderUtil(
					Material.valueOf(main.getConfig().getString("gui.mainMenuGUI.items.purchase-ticket.item")),
					ChatColor.translateAlternateColorCodes('&', purchaseTicketName), 
							purchaseTicketAmount)
					.setLore(main.getConfig().getStringList("gui.mainMenuGUI.items.purchase-ticket.description")).build();
		}
		catch(IllegalArgumentException itemNotFound)
		{
			purchaseTicket = new ItemBuilderUtil(
					Material.STONE,
					ChatColor.translateAlternateColorCodes('&', purchaseTicketName), 
							purchaseTicketAmount)
					.setLore(main.getConfig().getStringList("gui.mainMenuGUI.items.purchase-ticket.description")).build();
		}
		
		
		poolCheckName = main.getConfig().getString("gui.mainMenuGUI.items.pool-check.name");
		poolCheckAmount = main.getConfig().getInt("gui.mainMenuGUI.items.pool-check.amount");
		
		poolCheckSlot = main.getConfig().getInt("gui.mainMenuGUI.items.pool-check.slot");
		try
		{
			poolCheck = new ItemBuilderUtil(
					Material.valueOf(main.getConfig().getString("gui.mainMenuGUI.items.pool-check.item")),
					ChatColor.translateAlternateColorCodes('&', poolCheckName), 
							purchaseTicketAmount)
					.setLore(main.getConfig().getStringList("gui.mainMenuGUI.items.pool-check.description")).build();
		}
		catch(IllegalArgumentException itemNotFound)
		{
			poolCheck = new ItemBuilderUtil(
					Material.STONE,
					ChatColor.translateAlternateColorCodes('&', poolCheckName), 
							purchaseTicketAmount)
					.setLore(main.getConfig().getStringList("gui.mainMenuGUI.items.pool-check.description")).build();
		}
		
		
		
		nextPoolName = main.getConfig().getString("gui.mainMenuGUI.items.next-pool.name");
		nextPoolAmount = main.getConfig().getInt("gui.mainMenuGUI.items.next-pool.amount");
		
		nextPoolSlot = main.getConfig().getInt("gui.mainMenuGUI.items.next-pool.slot");
		
		nextPoolLore = main.getConfig().getStringList("gui.mainMenuGUI.items.next-pool.description");
		try
		{
			nextPool = new ItemBuilderUtil(
					Material.valueOf(main.getConfig().getString("gui.mainMenuGUI.items.next-pool.item")),
					ChatColor.translateAlternateColorCodes('&', nextPoolName), 
							purchaseTicketAmount).build();
		}
		catch(IllegalArgumentException itemNotFound)
		{
			nextPool = new ItemBuilderUtil(
					Material.STONE,
					ChatColor.translateAlternateColorCodes('&', nextPoolName), 
							purchaseTicketAmount).build();
		}
		
		
		viewPlayerName = main.getConfig().getString("gui.mainMenuGUI.items.view-players.name");
		viewPlayerAmount = main.getConfig().getInt("gui.mainMenuGUI.items.view-players.amount");
		
		viewPlayerSlot = main.getConfig().getInt("gui.mainMenuGUI.items.view-players.slot");
		
		viewPlayerLore = main.getConfig().getStringList("gui.mainMenuGUI.items.view-players.description");
		try
		{
			viewPlayer = new ItemBuilderUtil(
					Material.valueOf(main.getConfig().getString("gui.mainMenuGUI.items.view-players.item")),
					ChatColor.translateAlternateColorCodes('&', viewPlayerName), 
							purchaseTicketAmount).build();
		}
		catch(IllegalArgumentException itemNotFound)
		{
			viewPlayer = new ItemBuilderUtil(
					Material.STONE,
							ChatColor.translateAlternateColorCodes('&', viewPlayerName), 
							purchaseTicketAmount).build();
		}
		
		
		purchaseTicketMenuTitle = main.getConfig().getString("gui.purchaseTicketGUI.title");
		purchaseTicketMenuSize = main.getConfig().getInt("gui.purchaseTicketGUI.size");
		
		participateSlot = main.getConfig().getInt("gui.purchaseTicketGUI.items.participate.slot");
		participateAmount = main.getConfig().getInt("gui.purchaseTicketGUI.items.participate.amount");
		
		enoughMoneyName = main.getConfig().getString("gui.purchaseTicketGUI.items.participate.enoughMoney.name");
		
		enoughMoneyLore = main.getConfig().getStringList("gui.purchaseTicketGUI.items.participate.enoughMoney.description");
		try
		{
			enoughMoney = new ItemBuilderUtil(
					Material.valueOf(main.getConfig().getString("gui.purchaseTicketGUI.items.participate.enoughMoney.item")),
					ChatColor.translateAlternateColorCodes('&', enoughMoneyName), 
						participateAmount).build();
		}
		catch(IllegalArgumentException itemNotFound)
		{
			enoughMoney = new ItemBuilderUtil(
					Material.STONE,
					ChatColor.translateAlternateColorCodes('&', enoughMoneyName), 
						participateAmount)
					.setLore(main.getConfig().getStringList("gui.purchaseTicketGUI.items.participate.enoughMoney.description")).build();
		}
		
		notEnoughMoneyName = main.getConfig().getString("gui.purchaseTicketGUI.items.participate.notEnoughMoney.name");
		
		try
		{
			notEnoughMoney = new ItemBuilderUtil(
					Material.valueOf(main.getConfig().getString("gui.purchaseTicketGUI.items.participate.notEnoughMoney.item")),
					ChatColor.translateAlternateColorCodes('&', notEnoughMoneyName), 
						participateAmount).build();
		}
		catch(IllegalArgumentException itemNotFound)
		{
			notEnoughMoney = new ItemBuilderUtil(
					Material.STONE,
					ChatColor.translateAlternateColorCodes('&', notEnoughMoneyName), 
						participateAmount).build();
		}
		
		alreadyParticipatedName = main.getConfig().getString("gui.purchaseTicketGUI.items.participate.alreadyParticipated.name");
		
		try
		{
			alreadyParticipated = new ItemBuilderUtil(
					Material.valueOf(main.getConfig().getString("gui.purchaseTicketGUI.items.participate.alreadyParticipated.item")),
					ChatColor.translateAlternateColorCodes('&', alreadyParticipatedName), 
						participateAmount)
					.setLore(main.getConfig().getStringList("gui.purchaseTicketGUI.items.participate.alreadyParticipated.description")).build();
		}
		catch(IllegalArgumentException itemNotFound)
		{
			alreadyParticipated = new ItemBuilderUtil(
					Material.STONE,
					ChatColor.translateAlternateColorCodes('&', alreadyParticipatedName), 
						participateAmount)
					.setLore(main.getConfig().getStringList("gui.purchaseTicketGUI.items.participate.alreadyParticipated.description")).build();
		}
		
		noLotteriesName = main.getConfig().getString("gui.purchaseTicketGUI.items.participate.noLotteries.name");
		
		try
		{
			noLotteries = new ItemBuilderUtil(
					Material.valueOf(main.getConfig().getString("gui.purchaseTicketGUI.items.participate.noLotteries.item")),
					ChatColor.translateAlternateColorCodes('&', noLotteriesName), 
						participateAmount)
					.setLore(main.getConfig().getStringList("gui.purchaseTicketGUI.items.participate.noLotteries.description")).build();
		}
		catch(IllegalArgumentException itemNotFound)
		{
			noLotteries = new ItemBuilderUtil(
					Material.STONE,
					ChatColor.translateAlternateColorCodes('&', noLotteriesName), 
						participateAmount)
					.setLore(main.getConfig().getStringList("gui.purchaseTicketGUI.items.participate.noLotteries.description")).build();
		}
		
		lotteryDetailsTitle = main.getConfig().getString("gui.lotteryDetailsGUI.title");
		lotteryDetailsSize = main.getConfig().getInt("gui.lotteryDetailsGUI.size");
		
		poolAmountName = main.getConfig().getString("gui.lotteryDetailsGUI.items.pool-amount.name");
		poolAmountAmount = main.getConfig().getInt("gui.lotteryDetailsGUI.items.pool-amount.amount");
		
		poolAmountSlot = main.getConfig().getInt("gui.lotteryDetailsGUI.items.pool-amount.slot");
		
		poolAmountLore = main.getConfig().getStringList("gui.lotteryDetailsGUI.items.pool-amount.description");
		try
		{
			poolAmount = new ItemBuilderUtil(
					Material.valueOf(main.getConfig().getString("gui.lotteryDetailsGUI.items.pool-amount.item")),
					ChatColor.translateAlternateColorCodes('&', poolAmountName), 
							purchaseTicketAmount).build();
		}
		catch(IllegalArgumentException itemNotFound)
		{
			poolAmount = new ItemBuilderUtil(
					Material.STONE,
					ChatColor.translateAlternateColorCodes('&', poolAmountName), 
							purchaseTicketAmount).build();
		}
		
		goBackName = main.getConfig().getString("gui.goBack.name");
		
		try
		{
			goBack = new ItemBuilderUtil(
					Material.valueOf(main.getConfig().getString("gui.goBack.item")),
					ChatColor.translateAlternateColorCodes('&', goBackName), 
							1)
					.setLore(main.getConfig().getStringList("gui.goBack.description")).build();
		}
		catch(IllegalArgumentException itemNotFound)
		{
			goBack = new ItemBuilderUtil(
					Material.STONE,
					ChatColor.translateAlternateColorCodes('&', goBackName), 
							1)
					.setLore(main.getConfig().getStringList("gui.goBack.description")).build();
		}
		
		nextPageName = main.getConfig().getString("gui.nextPage.name");
		
		try
		{
			nextPage = new ItemBuilderUtil(
					Material.valueOf(main.getConfig().getString("gui.nextPage.item")),
					ChatColor.translateAlternateColorCodes('&', nextPageName), 
							1)
					.setLore(main.getConfig().getStringList("gui.nextPage.description")).build();
		}
		catch(IllegalArgumentException itemNotFound)
		{
			nextPage = new ItemBuilderUtil(
					Material.STONE,
					ChatColor.translateAlternateColorCodes('&', nextPageName), 
							1)
					.setLore(main.getConfig().getStringList("gui.nextPage.description")).build();
		}
	


	}
	

	

	
}

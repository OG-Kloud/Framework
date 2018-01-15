package net.kloud.framework;

import java.io.File;
import java.util.Map;

import com.google.common.collect.Maps;

import net.kloud.framework.interfaces.IEconomy;
import net.kloud.framework.utils.KloudLogger;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = Framework.MODID, name = Framework.NAME, version = Framework.VERSION)
public class Framework {
	
	public static final String MODID = "kloudframework", NAME = "Kloud Framework", VERSION = "@Version@";
	
	public Map<ResourceLocation, IEconomy> economys = Maps.newHashMap();
	private IEconomy economy;
	private String economyToUse = "kloudeconomy";
	public double startingBalance = 0.00F;
	public Configuration config;
	
	@Instance
	public static Framework instance;
	
	public void registerEconomy(IEconomy econ, String modid) {
		economys.put(new ResourceLocation(modid, "economy"), econ);
	}
	
	public IEconomy getEconomy() {
		return economy;
	}
	
	public void initEconomy() {
		economy = economys.get(new ResourceLocation(economyToUse, "economy"));
		syncConfig();
	}
	
	public void syncConfig() {
		KloudLogger.log("syncConfig()");
		try {
			config.load();
			Property isEconomyID = config.get("Economy Variables", "economyId", "kloudeconomy");
			isEconomyID.setComment("The modid of the economy plugin to use");
			Property balance = config.get("Economy Variables", "startingBalace", 0.00);
			balance.setComment("If the current Economy plugin uses a default balance");
			Property debugMode = config.get("Economy Variables", "debugMode", false);
			debugMode.setComment("Sets if console output should be enabled");
			economyToUse = isEconomyID.getString();
			startingBalance = balance.getDouble();
			KloudLogger.setDebug(debugMode.getBoolean());
		} catch(Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if(config.hasChanged())config.save();
		}
	}
	
	@EventHandler
	public void onEvent(FMLPreInitializationEvent e) {
		KloudLogger.log("PreEvent");
		File configDir = new File(e.getModConfigurationDirectory(), "Framework");
		configDir.mkdirs();
		File con = new File(configDir,"economy.klouddata");
		config = new Configuration(new File(con.getPath()), "1");
		syncConfig();
	}
	
	@EventHandler
	public void onEvent(FMLServerStartingEvent e) {
		
	}

}

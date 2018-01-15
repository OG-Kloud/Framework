package net.kloud.framework.interfaces;

import net.kloud.framework.Framework;
import net.minecraft.entity.player.EntityPlayer;

public interface IEconomy {
	
	default void registerEconomyPlugin(IEconomy economy) {
		Framework.instance.registerEconomy(this, "temp");
	}
	
	public void addBalance(EntityPlayer player, double amount);
	public void subtrBalance(EntityPlayer player, double amount);
	public double getBalance(EntityPlayer player);
	public boolean isBalanceSuff(EntityPlayer player, double amount);
	public void registerWithEconomy(EntityPlayer player);
	

}

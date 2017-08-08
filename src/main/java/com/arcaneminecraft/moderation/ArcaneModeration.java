package com.arcaneminecraft.moderation;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.arcaneminecraft.api.ArcaneCommons;

public final class ArcaneModeration extends JavaPlugin {
	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		Alert alert = new Alert(this);
		getCommand("alert").setExecutor(alert);
		getServer().getPluginManager().registerEvents(alert, this);
	}
	
	@Override
	public void onDisable() {
		this.saveConfig();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// Changes gamemode. This is pretty awesome.
		// g0, g1, g2, g3
		if (cmd.getName().equalsIgnoreCase("g0")) {
			if (sender.hasPermission("arcane.command.g0") || sender.hasPermission("minecraft.command.gamemode")) {
				if (args.length == 0 && !(sender instanceof Player)) {
					sender.sendMessage(ArcaneCommons.noConsoleMsg());
					return true;
				}
				String c = "gamemode " + label.charAt(1) + (args.length == 0 ? "" : " " + args[0]);
				if (sender instanceof Player) 
					return ((Player)sender).performCommand(c);
				else
					return getServer().dispatchCommand(getServer().getConsoleSender(), c);
			} else {
				sender.sendMessage(ArcaneCommons.noPermissionMsg(label));
				return true;
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("opme")) {
			if (sender.hasPermission("arcane.command.opme")) {
				if (sender.isOp()) {
					sender.sendMessage(ArcaneCommons.tag("OP","You are already opped. Use /deop to remove op."));
				} else {
					sender.setOp(true);
					getLogger().warning(sender.getName() + " opped self.");
					sender.sendMessage(ArcaneCommons.tag("OP","You have been opped. Use it with care."));
				}
			} else {
				sender.sendMessage(ArcaneCommons.noPermissionMsg(label));
				return true;
			}
		}
		
		return false;
	}


}

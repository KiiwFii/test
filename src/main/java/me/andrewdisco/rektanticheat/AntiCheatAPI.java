package me.andrewdisco.rektanticheat;

import java.util.List;

import me.andrewdisco.rektanticheat.checks.Check;
import org.bukkit.plugin.Plugin;

public class AntiCheatAPI {

	private static AntiCheat AntiCheat;

	@SuppressWarnings("unused")
	private final Plugin plugin;

	public AntiCheatAPI(Plugin plugin) {
		this.plugin = plugin;
		AntiCheat = (AntiCheat) plugin;
	}

	public static List<Check> getChecks() {
		return AntiCheat.getChecks();
	}
}
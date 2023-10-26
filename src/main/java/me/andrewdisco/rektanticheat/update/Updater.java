package me.andrewdisco.rektanticheat.update;

import me.andrewdisco.rektanticheat.AntiCheat;
import org.bukkit.Bukkit;

public class Updater implements Runnable {
	private final me.andrewdisco.rektanticheat.AntiCheat AntiCheat;
	private final int updater;

	public Updater(AntiCheat AntiCheat) {
		this.AntiCheat = AntiCheat;
		this.updater = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.AntiCheat, this, 0, 1);
	}

	public void Disable() {
		Bukkit.getScheduler().cancelTask(this.updater);
	}
	@Override
	public void run() {
		final UpdateType[] arrupdateType = UpdateType.values();
		final int n = arrupdateType.length;
		int n2 = 0;
		while (n2 < n) {
			final UpdateType updateType = arrupdateType[n2];
			if (updateType != null && updateType.Elapsed()) {
				try {
					final UpdateEvent event = new UpdateEvent(updateType);
					Bukkit.getPluginManager().callEvent(event);
				} catch (final Exception ex) {
					ex.printStackTrace();
				}
			}
			++n2;
		}
	}
}
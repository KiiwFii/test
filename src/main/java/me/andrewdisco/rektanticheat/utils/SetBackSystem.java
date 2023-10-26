package me.andrewdisco.rektanticheat.utils;

import me.andrewdisco.rektanticheat.AntiCheat;
import me.andrewdisco.rektanticheat.data.DataPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class SetBackSystem implements Listener {
	public static void setBack(Player p) {
		final DataPlayer data = AntiCheat.getInstance().getDataManager().getData(p);
		if (data != null) {
			if (!data.isShouldSetBack()) {
				data.setShouldSetBack(true);
			}
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onMove(PlayerMoveEvent e) {
		final Player p = e.getPlayer();
		final DataPlayer data = AntiCheat.getInstance().getDataManager().getData(p);
		if (data != null) {
			if (data.isShouldSetBack()) {
				if (data.getSetBackTicks() >= 5) {
					final Location setback = data.getSetbackLocation() != null ? data.getSetbackLocation() : e.getFrom();
					e.setTo(setback);
					data.setShouldSetBack(false);
				} else {
					final Location setback = data.getSetbackLocation() != null ? data.getSetbackLocation() : e.getFrom();
					e.setTo(setback);
					data.setSetBackTicks(data.getSetBackTicks() + 1);
				}
			} else if(PlayerUtil.isOnGround(e, p)) {
				data.setSetbackLocation(e.getFrom());
			}
		}
	}
}
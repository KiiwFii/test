package me.andrewdisco.rektanticheat.checks.movement.teleport;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import me.andrewdisco.rektanticheat.checks.movement.vclip.VClipA;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import me.andrewdisco.rektanticheat.AntiCheat;
import me.andrewdisco.rektanticheat.checks.Check;
import me.andrewdisco.rektanticheat.checks.CheckType;
import me.andrewdisco.rektanticheat.events.SharedEvents;

public class TeleportA extends Check{
	public TeleportA (AntiCheat AntiCheat) {
		super("TeleportA", "Teleport", CheckType.Movement, true, false, false, false, false, 10, 1, 600000L, AntiCheat);
	}


	public static Map<UUID, Long> lastTPCancel = new HashMap<>();
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	private void onTeleport(PlayerTeleportEvent e) {
		final Player p = e.getPlayer();
		if (SharedEvents.getLastJoin().containsKey(p)) {
			if (System.currentTimeMillis() - SharedEvents.getLastJoin().get(p) < 5000 | p == null) {
				return;
			}
		}
		if (e.getCause() == TeleportCause.UNKNOWN) {
			final World w = p.getWorld();
			final Location from = p.getLocation();
			Bukkit.getScheduler().runTaskLater(AntiCheat.Instance, () -> {
				final Location to = e.getTo();
				final Location loc = p.getLocation();
				final int x = (int) loc.getX();
				final int y = (int) loc.getY();
				final int z = (int) loc.getZ();
				final Block b = w.getBlockAt(x, y, z);
				if (from.getWorld() != to.getWorld()) {
					return;
				}
				if (from != to || from != p.getLocation()) {
					this.getAntiCheat().logCheat(this, p, "[1] Unknown teleport cause", "(Type: A)");
					if (VClipA.lastLocation.containsKey(p)) {
						p.teleport(VClipA.lastLocation.get(p));
					}
				}
			}
			, 3);
		}
	}
}

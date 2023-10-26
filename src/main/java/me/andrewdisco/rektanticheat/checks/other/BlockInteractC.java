package me.andrewdisco.rektanticheat.checks.other;

import java.util.HashSet;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;

import me.andrewdisco.rektanticheat.AntiCheat;
import me.andrewdisco.rektanticheat.checks.Check;
import me.andrewdisco.rektanticheat.checks.CheckType;
import me.andrewdisco.rektanticheat.utils.PlayerUtil;
import me.andrewdisco.rektanticheat.utils.ServerUtil;
import me.andrewdisco.rektanticheat.utils.VelocityUtil;

public class BlockInteractC extends Check {
	public BlockInteractC(AntiCheat AntiCheat) {
		super("BlockInteractC", "BI", CheckType.Other, true, false, false, false, true, 20, 1, 600000L, AntiCheat);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onPlaceBlock(BlockPlaceEvent e) {
		if (ServerUtil.isBukkitVerison("1_7")) {
			return;
		}
		final Player p = e.getPlayer();
		final Block t = p.getTargetBlock((HashSet<Byte>) null, 5);
		if (p.getAllowFlight()
				|| e.getPlayer().getVehicle() != null
				|| !getAntiCheat().isEnabled()
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()
				|| p.getGameMode().equals(GameMode.CREATIVE)
				|| VelocityUtil.didTakeVelocity(p)) {
			return;
		}

		final double x = PlayerUtil.getEff(p);
		if (e.getBlock().getWorld().getBlockAt(e.getBlock().getLocation().subtract(0.0, 1.0, 0.0)).getType() == Material.AIR) {
			if (!e.getBlock().getLocation().equals(t.getLocation()) && !e.isCancelled() && t.getType().isSolid() && !t.getType().name().toLowerCase().contains("sign") && !t.getType().toString().toLowerCase().contains("fence") && p.getLocation().getY() > e.getBlock().getLocation().getY()) {
				if (x != 0) {

					getAntiCheat().logCheat(this, p, "[1] BlockInteract", "(Type: C)");
				}
			}

			if (e.getBlockAgainst().isLiquid() && e.getBlock().getType() != Material.WATER_LILY) {
				getAntiCheat().logCheat(this, p, "[2] BlockInteract", "(Type: C)");
			}
		}
	}
}
package me.andrewdisco.rektanticheat.checks.movement.noslowdown;

import java.util.Map;
import java.util.UUID;

import me.andrewdisco.rektanticheat.checks.Check;
import me.andrewdisco.rektanticheat.checks.CheckType;
import me.andrewdisco.rektanticheat.utils.BlockUtil;
import me.andrewdisco.rektanticheat.utils.MathUtil;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

import me.andrewdisco.rektanticheat.utils.PlayerUtil;

public class NoSlowdownB extends Check {

	public static Map<UUID, Map.Entry<Integer, Long>> speedTicks;

	public NoSlowdownB(me.andrewdisco.rektanticheat.AntiCheat AntiCheat) {
		super("NoSlowdownB", "NoSlowdown", CheckType.Movement, true, false, false, false, true, 10, 1, 600000L, AntiCheat);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onMove(PlayerMoveEvent e) {
		final Location from = e.getFrom();
		final Location to = e.getTo();
		if (to.getX() == e.getFrom().getX() && from.getY() == to.getY()
				&& from.getZ() == from.getZ()) {
			return;
		}
		final Player p = e.getPlayer();
		final double OffsetY = MathUtil.offset(MathUtil.getVerticalVector(from.toVector()), MathUtil.getVerticalVector(to.toVector()));
		final double OffsetXZ = MathUtil.offset(MathUtil.getHorizontalVector(from.toVector()), MathUtil.getHorizontalVector(to.toVector()));
		if (!BlockUtil.isNearLiquid(p)
				|| p.getAllowFlight()
				|| p.getGameMode().equals(GameMode.CREATIVE)
				|| PlayerUtil.isNearSlab(p)
				|| BlockUtil.isNearFence(p)
				|| BlockUtil.isNearStair(p)
				|| PlayerUtil.isNearSign(p)
				|| OffsetY > 0.55
				|| OffsetXZ > 0.3
				|| to.getY() < from.getY()
				|| PlayerUtil.isNearAir(p)
				|| OffsetY < 0.131 ) {
			return;
		}
		if (PlayerUtil.isInLiquid(p)) {
			getAntiCheat().logCheat(this, p, "OffsetY: " + OffsetY + " OffsetXZ: " + OffsetXZ, "(Type: B)");
		}
	}
}
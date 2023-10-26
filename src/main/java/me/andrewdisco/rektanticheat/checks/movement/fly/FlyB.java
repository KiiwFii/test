package me.andrewdisco.rektanticheat.checks.movement.fly;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import me.andrewdisco.rektanticheat.checks.Check;
import me.andrewdisco.rektanticheat.checks.CheckType;
import me.andrewdisco.rektanticheat.data.DataPlayer;
import me.andrewdisco.rektanticheat.other.Latency;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

import me.andrewdisco.rektanticheat.utils.BlockUtil;
import me.andrewdisco.rektanticheat.utils.CheatUtil;
import me.andrewdisco.rektanticheat.utils.PlayerUtil;
import me.andrewdisco.rektanticheat.utils.ServerUtil;

public class FlyB extends Check {

	public static Map<UUID, Long> flyTicks;

	public FlyB(me.andrewdisco.rektanticheat.AntiCheat AntiCheat) {
		super("FlyB", "Fly", CheckType.Movement, true, true, false, true, false, 5, 1, 600000L, AntiCheat);
		flyTicks = new HashMap<>();
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onMove(PlayerMoveEvent e) {
		if (!getAntiCheat().isEnabled()
				|| ServerUtil.isBukkitVerison("1_13")) {
			return;
		}
		final Player p = e.getPlayer();

		if (e.isCancelled()
				|| (e.getTo().getX() == e.getFrom().getX()) && (e.getTo().getZ() == e.getFrom().getZ())
				|| p.getAllowFlight()
				|| DataPlayer.getWasFlying() > 0
				|| p.getVehicle() != null
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| PlayerUtil.isInWater(p)
				|| CheatUtil.isInWeb(p)
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()
				|| Latency.getLag(p) > 92) {
			return;
		}
		if (!ServerUtil.isBukkitVerison("1_8")
				&&!ServerUtil.isBukkitVerison("1_7")) {
			if (p.hasPotionEffect(PotionEffectType.getByName("LEVITATION"))) {
				return;
			}
		}

		if (ServerUtil.isBukkitVerison("1_13")) {
			if (BlockUtil.isNearStair(p)) {
				return;
			}
		}
		if (CheatUtil.blocksNear(p.getLocation())) {
			if (flyTicks.containsKey(p.getUniqueId())) {
				flyTicks.remove(p.getUniqueId());
			}
			return;
		}
		if (Math.abs(e.getTo().getY() - e.getFrom().getY()) > 0.06) {
			if (flyTicks.containsKey(p.getUniqueId())) {
				flyTicks.remove(p.getUniqueId());
			}
			return;
		}

		long Time = System.currentTimeMillis();
		if (flyTicks.containsKey(p.getUniqueId())) {
			Time = flyTicks.get(p.getUniqueId()).longValue();
		}
		final long MS = System.currentTimeMillis() - Time;
		if (MS > 200L) {
			dumplog(p, "Logged for Fly Type B;  MS: " + MS);
			getAntiCheat().logCheat(this, p, "Hovering for " + MS + " MS", "(Type: B)"
					);
			flyTicks.remove(p.getUniqueId());
			return;
		}
		flyTicks.put(p.getUniqueId(), Time);
	}
}
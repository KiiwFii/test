package me.andrewdisco.rektanticheat.checks.other;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

import me.andrewdisco.rektanticheat.AntiCheat;
import me.andrewdisco.rektanticheat.checks.Check;
import me.andrewdisco.rektanticheat.checks.CheckType;
import me.andrewdisco.rektanticheat.other.Latency;
import me.andrewdisco.rektanticheat.utils.TimeUtil;

public class TimerB extends Check {

	public static Map<UUID, Map.Entry<Integer, Long>> timerTicks;

	public TimerB(AntiCheat AntiCheat) {
		super("TimerB", "Timer", CheckType.Other, true, false, false, false, true, 14, 1, 6000L, AntiCheat);
		timerTicks = new HashMap<>();
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onMove(PlayerMoveEvent e) {
		final Player p = e.getPlayer();
		final UUID u = p.getUniqueId();
		if (!getAntiCheat().isEnabled()
				|| (Latency.getLag(p) > 500)
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()
				|| (e.getFrom().getX() == e.getTo().getX() && e.getFrom().getZ() == e.getTo().getZ()
				&& e.getFrom().getY() == e.getTo().getY())) {
			return;
		}

		int Count = 0;
		long Time = System.currentTimeMillis();
		if (timerTicks.containsKey(u)) {
			Count = timerTicks.get(u).getKey().intValue();
			Time = timerTicks.get(u).getValue().longValue();
		}

		Count++;

		if ((timerTicks.containsKey(p.getUniqueId())) && (TimeUtil.elapsed(Time, 1000L))) {
			if (Count > 40) {
				this.getAntiCheat().logCheat(this, p, "Count: " + Count + ", Time: " + Time, "(Type: B)");
			}
			Count = 0;
			Time = TimeUtil.nowlong();
		}
		timerTicks.put(u, new AbstractMap.SimpleEntry<>(Count, Time));
	}
}
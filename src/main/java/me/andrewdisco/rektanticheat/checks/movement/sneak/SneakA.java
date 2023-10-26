package me.andrewdisco.rektanticheat.checks.movement.sneak;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import me.andrewdisco.rektanticheat.checks.Check;
import me.andrewdisco.rektanticheat.checks.CheckType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import me.andrewdisco.rektanticheat.packets.events.PacketEntityActionEvent;
import me.andrewdisco.rektanticheat.utils.TimeUtil;

public class SneakA extends Check {
	public static Map<UUID, Map.Entry<Integer, Long>> sneakTicks;

	public SneakA(me.andrewdisco.rektanticheat.AntiCheat AntiCheat) {
		super("SneakA", "Sneak", CheckType.Movement, true, true, false, true, false, 5, 1, 600000L, AntiCheat);
		sneakTicks = new HashMap<>();
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void EntityAction(PacketEntityActionEvent e) {
		final Player p = e.getPlayer();
		final UUID u = p.getUniqueId();
		if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()
				|| !p.isSneaking()) {
			return;
		}
		int Count = 0;
		long Time = -1L;
		if (sneakTicks.containsKey(u)) {
			Count = sneakTicks.get(u).getKey().intValue();
			Time = sneakTicks.get(u).getValue().longValue();
		}
		Count++;
		if (sneakTicks.containsKey(u)) {
			if (TimeUtil.elapsed(Time, 100L)) {
				Count = 0;
				Time = System.currentTimeMillis();
			} else {
				Time = System.currentTimeMillis();
			}
		}
		if (Count > 50) {
			Count = 0;
			getAntiCheat().logCheat(this, p, null, "(Type: A)");
		}
		sneakTicks.put(u, new AbstractMap.SimpleEntry<>(Count, Time));
	}
}
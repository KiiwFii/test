package me.andrewdisco.rektanticheat.checks.combat.autoclicker;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import me.andrewdisco.rektanticheat.checks.Check;
import me.andrewdisco.rektanticheat.checks.CheckType;
import me.andrewdisco.rektanticheat.packets.events.PacketSwingArmEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import me.andrewdisco.rektanticheat.utils.TimeUtil;

public class AutoClickerD extends Check {
	public static Map<UUID, Integer> clicks;
	public static Map<UUID, Long> recording;
	public AutoClickerD(me.andrewdisco.rektanticheat.AntiCheat AntiCheat) {
		super("AutoClickerD", "AutoClicker",  CheckType.Combat, true, true, false, true, false, 5, 1, 600000L, AntiCheat);

		clicks = new HashMap<>();
		recording = new HashMap<>();
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onSwing(PacketSwingArmEvent e) {
		final Player p = e.getPlayer();
		if (p == null) {
			return;
		}
		if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
			return;
		}
		int clicks = AutoClickerD.clicks.getOrDefault(this, 0);
		final long time = recording.getOrDefault(p.getUniqueId(), System.currentTimeMillis());
		if(TimeUtil.elapsed(time, 1000L)) {
			if(clicks > 18) {
				getAntiCheat().logCheat(this, p, clicks + " Clicks/Second", "(Type: D)");
			}
			clicks = 0;
			recording.remove(p.getUniqueId());
		} else {
			clicks++;
		}
		AutoClickerD.clicks.put(p.getUniqueId(), clicks);
		recording.put(p.getUniqueId(), time);
	}
}
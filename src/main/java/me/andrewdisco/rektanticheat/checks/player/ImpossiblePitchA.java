package me.andrewdisco.rektanticheat.checks.player;

import me.andrewdisco.rektanticheat.checks.Check;
import me.andrewdisco.rektanticheat.checks.CheckType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

public class ImpossiblePitchA extends Check {
	public ImpossiblePitchA(me.andrewdisco.rektanticheat.AntiCheat AntiCheat) {
		super("ImpossiblePitchA", "ImpossiblePitch", CheckType.Player, true, true, false, true, false, 10, 1, 600000L, AntiCheat);
	}
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onMove(PlayerMoveEvent e) {
		final Player p = e.getPlayer();
		final double x = p.getLocation().getPitch();
		if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
			return;
		}
		if (x > 90 || x < -90) {
			getAntiCheat().logCheat(this, p, "Head went back too far. Pitch: " + x, "(Type: A)");
		}
	}
}
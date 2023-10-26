package me.andrewdisco.rektanticheat.checks.movement.sneak;

import me.andrewdisco.rektanticheat.checks.Check;
import me.andrewdisco.rektanticheat.checks.CheckType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

public class SneakB extends Check {

	public SneakB(me.andrewdisco.rektanticheat.AntiCheat AntiCheat) {
		super("SneakB", "Sneak", CheckType.Movement, true, true, false, true, false, 5, 1, 600000L, AntiCheat);
	}

	@SuppressWarnings("unused")
	private void onMove(PlayerMoveEvent e) {
		final Player p = e.getPlayer();
		if (p.isSneaking()) {
			if (p.isSprinting()) {
				if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
						|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
					return;
				}
				getAntiCheat().logCheat(this, p, null, "(Type: B)");
			}
		}
	}
}
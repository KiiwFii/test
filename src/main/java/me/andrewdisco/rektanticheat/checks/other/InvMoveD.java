package me.andrewdisco.rektanticheat.checks.other;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.andrewdisco.rektanticheat.AntiCheat;
import me.andrewdisco.rektanticheat.checks.Check;
import me.andrewdisco.rektanticheat.checks.CheckType;

public class InvMoveD extends Check {
	public InvMoveD(AntiCheat AntiCheat) {
		super("InvMoveD", "InvMove", CheckType.Other, true, false, false, false, true, 15, 1, 600000L, AntiCheat);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void InventoryClickEvent(InventoryClickEvent e) {
		final Player p = (Player) e.getWhoClicked();
		if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()
				|| p.getAllowFlight()
				|| p.getGameMode().equals(GameMode.CREATIVE)) {
			return;
		}
		if (p.isSneaking()) {
			getAntiCheat().logCheat(this, p, "Sneaking while having a gui open!", "(Type: D)");
		}
	}
}
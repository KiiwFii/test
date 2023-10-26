package me.andrewdisco.rektanticheat.checks.other;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import me.andrewdisco.rektanticheat.AntiCheat;
import me.andrewdisco.rektanticheat.checks.Check;
import me.andrewdisco.rektanticheat.checks.CheckType;

public class InvMoveA extends Check {
	public InvMoveA(AntiCheat AntiCheat) {
		super("InvMoveA", "InvMove", CheckType.Other, true, false, false, false, true, 60, 20, 3000L, AntiCheat);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onMove(PlayerMoveEvent e) {
		final Player p = e.getPlayer();
		final InventoryView view = p.getOpenInventory();
		final Inventory top = view.getTopInventory();
		if (view !=null) {
			if (top.toString().contains("CraftInventoryCrafting")
					|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
					|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()
					|| p.getAllowFlight()
					|| p.getGameMode().equals(GameMode.CREATIVE)) {
				return;
			}
			getAntiCheat().logCheat(this, p, "Moving while having a gui open!", "(Type: A)");
		}
	}
}
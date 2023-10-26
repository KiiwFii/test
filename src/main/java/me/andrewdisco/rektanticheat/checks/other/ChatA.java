package me.andrewdisco.rektanticheat.checks.other;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import me.andrewdisco.rektanticheat.AntiCheat;
import me.andrewdisco.rektanticheat.checks.Check;
import me.andrewdisco.rektanticheat.checks.CheckType;

public class ChatA extends Check{

	public ChatA(AntiCheat AntiCheat) {
		super("ChatA", "Chat", CheckType.Other, true, false, false, false, true, 10, 1, 600000L, AntiCheat);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onChat(AsyncPlayerChatEvent e) {
		final Player p = e.getPlayer();
		final InventoryView view = p.getOpenInventory();
		final Inventory top = view.getTopInventory();
		if (view !=null) {
			if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
					|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
				return;
			} if (!top.toString().contains("CraftInventoryCrafting")) {
				getAntiCheat().logCheat(this, p, "Chatting while a gui is open!", "(Type: A)");
			} if (p.isSneaking()) {
				getAntiCheat().logCheat(this, p, "Sprinting while chatting!", "(Type: A)");
			} if (p.isSprinting()) {
				getAntiCheat().logCheat(this, p, "Sneaking while chatting!", "(Type: A)");
			}if (p.isDead()) {
				getAntiCheat().logCheat(this, p, "Chatting while being dead!", "(Type: A)");
			}
		}

	}
}
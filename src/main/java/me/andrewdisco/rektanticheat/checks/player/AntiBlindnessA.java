package me.andrewdisco.rektanticheat.checks.player;

import me.andrewdisco.rektanticheat.checks.Check;
import me.andrewdisco.rektanticheat.checks.CheckType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

public class AntiBlindnessA extends Check {
	public AntiBlindnessA(me.andrewdisco.rektanticheat.AntiCheat AntiCheat) {
		super("AntiBlindnessA", "AntiBlindness", CheckType.Player, true, false, false, false, true, 5, 1, 600000L, AntiCheat);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onMove(PlayerMoveEvent e) {
		final Player p = e.getPlayer();
		if (p.isSprinting() && p.hasPotionEffect(PotionEffectType.BLINDNESS)) {
			getAntiCheat().logCheat(this, p, "Sprnting while having blindness potion effect!", "(Type: A)");
		}
	}
}

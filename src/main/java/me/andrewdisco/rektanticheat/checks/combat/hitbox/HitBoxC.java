package me.andrewdisco.rektanticheat.checks.combat.hitbox;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import me.andrewdisco.rektanticheat.checks.Check;
import me.andrewdisco.rektanticheat.checks.CheckType;
import me.andrewdisco.rektanticheat.packets.events.PacketUseEntityEvent;
import me.andrewdisco.rektanticheat.utils.CheatUtil;
import me.andrewdisco.rektanticheat.utils.MathUtil;
import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class HitBoxC extends Check {

	public HitBoxC(me.andrewdisco.rektanticheat.AntiCheat AntiCheat) {
		super("HitBoxC", "HitBox", CheckType.Combat, true, false, false, false, true, 10, 1, 600000L, AntiCheat);
	}

	public static Map<UUID, Integer> count = new HashMap<>();
	public static Map<UUID, Player> lastHit = new HashMap<>();
	public static Map<UUID, Double> yawDif = new HashMap<>();

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onUse(PacketUseEntityEvent e) {

		if (!(e.getAttacker() instanceof Player) || !(e.getAttacked() instanceof Player) || e.getAttacker().getGameMode().equals(GameMode.CREATIVE)) {
			return;
		}
		final Player p = e.getAttacker();
		if (p == null) {
			return;
		}

		final LivingEntity attacked = (Player) e.getAttacked();

		int verbose = count.getOrDefault(p.getUniqueId(), 0);

		final double offset = CheatUtil.getOffsetOffCursor(p, attacked);

		if(offset > 30) {
			if((verbose+= 2) > 25) {
				getAntiCheat().logCheat(this, p, "Offset: " + MathUtil.round(offset, 4) + "> 30", "(Type: A)");
			}
		} else if(verbose > 0) {
			verbose--;
		}
		count.put(p.getUniqueId(), verbose);
	}
}
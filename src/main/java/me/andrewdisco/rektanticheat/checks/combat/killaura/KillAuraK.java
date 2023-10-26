package me.andrewdisco.rektanticheat.checks.combat.killaura;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import me.andrewdisco.rektanticheat.checks.Check;
import me.andrewdisco.rektanticheat.checks.CheckType;
import me.andrewdisco.rektanticheat.other.Latency;
import me.andrewdisco.rektanticheat.packets.PacketPlayerType;
import me.andrewdisco.rektanticheat.packets.events.PacketKillauraEvent;
import me.andrewdisco.rektanticheat.packets.events.PacketUseEntityEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import me.andrewdisco.rektanticheat.utils.ServerUtil;

public class KillAuraK extends Check {

	public static Map<UUID, Integer> verbose;
	public static Map<UUID, Long> lastArmSwing;

	public KillAuraK(me.andrewdisco.rektanticheat.AntiCheat AntiCheat) {
		super("KillauraK", "Killaura", CheckType.Combat, true, false, false, false, true, 10, 1, 600000L, AntiCheat);
		verbose = new HashMap<>();
		lastArmSwing = new HashMap<>();
	}

	@EventHandler
	public void onHit(PacketUseEntityEvent e) {

		if(!getAntiCheat().isEnabled()
				|| getAntiCheat().getLag().getTPS() < 19) {
			return;
		}

		final Player player = e.getAttacker();
		if (player == null) {
			return;
		}

		int verbose = KillAuraK.verbose.getOrDefault(player.getUniqueId(), 0);

		if(player.isDead()) {
			verbose++;
		} else if(KillAuraK.verbose.containsKey(player.getUniqueId())) {
			KillAuraK.verbose.remove(player.getUniqueId());
			return;
		}

		if(verbose > 4) {
			verbose = 0;
			getAntiCheat().logCheat(this, player, "Attacking while dead.", "(Type: K)");
		}

		KillAuraK.verbose.put(player.getUniqueId(), verbose);
	}

	@EventHandler
	public void onSwing(PacketKillauraEvent e) {
		if (getAntiCheat().getServer().getPluginManager().isPluginEnabled("ViaVersion")) {
			return;
		}

		if(!getAntiCheat().isEnabled()
				|| getAntiCheat().getLag().getTPS() < 19
				|| ServerUtil.isBukkitVerison("1_13")) {
			return;
		}

		final Player player = e.getPlayer();
		if(e.getType() == PacketPlayerType.ARM_SWING) {
			lastArmSwing.put(player.getUniqueId(), System.currentTimeMillis());
		}

		if(e.getType() == PacketPlayerType.USE) {
			final long lastArmSwing = KillAuraK.lastArmSwing.getOrDefault(player.getUniqueId(), System.currentTimeMillis());

			if((System.currentTimeMillis() - lastArmSwing) > 100 && Latency.getLag(player) < 50) {
				getAntiCheat().logCheat(this, player, "Missed while looking at victim.", "(Type: K)");
			}
		}
	}



}
package me.andrewdisco.rektanticheat.checks.movement.ascension;

import java.util.Map;
import java.util.WeakHashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

import me.andrewdisco.rektanticheat.AntiCheat;
import me.andrewdisco.rektanticheat.checks.Check;
import me.andrewdisco.rektanticheat.checks.CheckType;
import me.andrewdisco.rektanticheat.utils.ServerUtil;

public class AscensionB extends Check {
	private final Map<Player, Integer> verbose = new WeakHashMap<>();
	private final Map<Player, Float> lastYMovement = new WeakHashMap<>();
	public AscensionB(me.andrewdisco.rektanticheat.AntiCheat AntiCheat) {
		super("AscensionB", "Ascension",  CheckType.Movement, true, true, false, true, false, 5, 1, 600000L, AntiCheat);
	}
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onMove(PlayerMoveEvent e) {
		final Player p = e.getPlayer();
		int verbose = this.verbose.getOrDefault(p, 0);
		final float yDelta = (float) (e.getTo().getY() - e.getFrom().getY());
		if (p.getAllowFlight()
				|| !lastYMovement.containsKey(p)
				|| Math.abs(yDelta - lastYMovement.get(p)) > 0.002
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
			return;
		}
		if (!ServerUtil.isBukkitVerison("1_8")
				&&!ServerUtil.isBukkitVerison("1_7")) {
			if (p.hasPotionEffect(PotionEffectType.getByName("LEVITATION"))) {
				return;
			}
		}
		if (verbose++ > 5) {
			AntiCheat.Instance.logCheat(this, p, Math.abs(yDelta - lastYMovement.get(p)) + "<-" + 0.002, "(Type B)");
		}
		lastYMovement.put(p, yDelta);
		this.verbose.put(p, verbose);
	}
}
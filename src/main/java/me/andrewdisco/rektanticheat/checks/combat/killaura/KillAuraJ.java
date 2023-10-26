package me.andrewdisco.rektanticheat.checks.combat.killaura;

import me.andrewdisco.rektanticheat.checks.Check;
import me.andrewdisco.rektanticheat.checks.CheckType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class KillAuraJ extends Check {
	private float lastYaw;
	private float lastBad;
	public KillAuraJ(me.andrewdisco.rektanticheat.AntiCheat AntiCheat) {
		super("KillAuraJ", "KillAura",  CheckType.Combat, true, false, false, false, true, 10, 1, 600000L, AntiCheat);
	}
	@SuppressWarnings("unused")
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onHit(EntityDamageByEntityEvent e) {
		float yaw;
		if (!(e.getDamager() instanceof Player)
				|| !(e.getEntity() instanceof Player)) {
			return;
		}
		final Player p = (Player)e.getDamager();
		if (p == null) {
			return;
		}
		this.lastYaw = yaw = p.getLocation().getYaw();
		final float f2 = Math.abs(yaw - this.lastYaw) % 180.0f;
	}
	public boolean onAim(Player p, float yaw) {
		final float badYaw = Math.abs(yaw - this.lastYaw) % 180.0f;
		this.lastYaw = yaw;
		this.lastBad = Math.round(badYaw * 10.0f) * 0.1f;
		if (yaw < 0.1) {
			return true;
		}
		if (badYaw > 1.0f && Math.round(badYaw * 10.0f) * 0.1f == badYaw && Math.round(badYaw) != badYaw) {
			if (badYaw == this.lastBad) {
				if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
						|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
					return true;
				}
				getAntiCheat().logCheat(this, p, "Huzuni Aura", "(Type: J)");
				return true;
			}
		} else {
			return true;
		}
		return false;
	}
}
package me.andrewdisco.rektanticheat.checks.combat.criticals;

import me.andrewdisco.rektanticheat.AntiCheat;
import me.andrewdisco.rektanticheat.checks.Check;
import me.andrewdisco.rektanticheat.checks.CheckType;
import me.andrewdisco.rektanticheat.data.DataPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.andrewdisco.rektanticheat.utils.BlockUtil;
import me.andrewdisco.rektanticheat.utils.PlayerUtil;

public class CriticalsA extends Check {

	public CriticalsA(me.andrewdisco.rektanticheat.AntiCheat AntiCheat) {
		super("CriticalsA", "Criticals",  CheckType.Combat, true, false, false, false, true, 20, 1, 600000L, AntiCheat);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onAttack(EntityDamageByEntityEvent e) {
		try {
			if(!(e.getDamager() instanceof Player)) {
				return;
			}
			final Player p = (Player) e.getDamager();
			if (p == null) {
				return;
			}
			if(getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
					|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
				return;
			}

			final Entity entity = e.getEntity();
			final DataPlayer data = AntiCheat.getInstance().getDataManager().getData(p);
			if (entity instanceof Player) {
				final Player p1 = (Player) entity;
			}
			if(data.getAboveBlockTicks() > 0
					|| PlayerUtil.isInWeb(p)
					|| p.getAllowFlight()
					|| p.isFlying()
					|| BlockUtil.isNearPistion(p)
					|| data.getWaterTicks() > 0
					|| PlayerUtil.hasSlabsNear(p.getLocation())) {
				return;
			}
			if (BlockUtil.isNearLiquid(p) && BlockUtil.isNearFence(p)) {
				return;
			}
			int verbose = data.getCriticalsVerbose();

			if(p.getFallDistance() > 0 && data.getFallDistance() == 0) {
				if(++verbose > 3) {
					getAntiCheat().logCheat(this, p, "Packet", "(Type: A)");
					verbose = 0;
				}
			} else {
				verbose = 0;
			}
			data.setCriticalsVerbose(verbose);
		} catch (final Exception ex) {

		}
	}
}

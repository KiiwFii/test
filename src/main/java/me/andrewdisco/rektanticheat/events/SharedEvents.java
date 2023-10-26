package me.andrewdisco.rektanticheat.events;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import me.andrewdisco.rektanticheat.AntiCheat;
import me.andrewdisco.rektanticheat.checks.combat.autoclicker.AutoClickerA;
import me.andrewdisco.rektanticheat.checks.combat.autoclicker.AutoClickerB;
import me.andrewdisco.rektanticheat.checks.combat.autoclicker.AutoClickerC;
import me.andrewdisco.rektanticheat.checks.combat.autoclicker.AutoClickerD;
import me.andrewdisco.rektanticheat.checks.combat.reach.ReachD;
import me.andrewdisco.rektanticheat.checks.movement.ascension.AscensionA;
import me.andrewdisco.rektanticheat.checks.movement.ascension.AscensionC;
import me.andrewdisco.rektanticheat.checks.movement.fly.FlyB;
import me.andrewdisco.rektanticheat.checks.movement.glide.GlideA;
import me.andrewdisco.rektanticheat.checks.movement.jesus.JesusA;
import me.andrewdisco.rektanticheat.checks.movement.nofall.NoFallA;
import me.andrewdisco.rektanticheat.checks.movement.sneak.SneakA;
import me.andrewdisco.rektanticheat.checks.movement.speed.SpeedB;
import me.andrewdisco.rektanticheat.checks.movement.speed.SpeedC;
import me.andrewdisco.rektanticheat.checks.other.ChangeA;
import me.andrewdisco.rektanticheat.checks.other.RegenA;
import me.andrewdisco.rektanticheat.checks.other.TimerA;
import me.andrewdisco.rektanticheat.checks.player.PacketsA;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.player.PlayerVelocityEvent;

import me.andrewdisco.rektanticheat.checks.combat.antikb.AntiKBA;
import me.andrewdisco.rektanticheat.checks.combat.criticals.CriticalsB;
import me.andrewdisco.rektanticheat.checks.combat.fastbow.FastBowA;
import me.andrewdisco.rektanticheat.checks.combat.hitbox.HitBoxA;
import me.andrewdisco.rektanticheat.checks.combat.hitbox.HitBoxC;
import me.andrewdisco.rektanticheat.checks.combat.killaura.KillAuraA;
import me.andrewdisco.rektanticheat.checks.combat.killaura.KillAuraD;
import me.andrewdisco.rektanticheat.checks.combat.killaura.KillAuraF;
import me.andrewdisco.rektanticheat.checks.combat.killaura.KillAuraG;
import me.andrewdisco.rektanticheat.checks.combat.killaura.KillAuraH;
import me.andrewdisco.rektanticheat.checks.combat.killaura.KillAuraK;
import me.andrewdisco.rektanticheat.checks.movement.noslowdown.NoSlowdownA;

@SuppressWarnings("static-access")
public class SharedEvents implements Listener {
	private static Map<Player, Long> lastSprintStart = new HashMap<>();
	private static Map<Player, Long> lastJoin = new HashMap<>();
	private static Map<Player, Long> lastSprintStop = new HashMap<>();
	public static Set<UUID> teleported = new HashSet<>();
	public static Map<Player, Long> placedBlock = new HashMap<>();

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
	public void onPlace(BlockPlaceEvent e) {
		final Player p = e.getPlayer();
		placedBlock.put(p, System.currentTimeMillis());
	}
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
	public void onInteract(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if (e.isCancelled()) {
			if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
				return;
			}
			placedBlock.put(p, System.currentTimeMillis());
		}
	}
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onJoin(PlayerJoinEvent e) {
		final Player p = e.getPlayer();
		final UUID u = p.getUniqueId();
		PacketsA.blacklist.add(u);
		this.lastJoin.put(e.getPlayer(), System.currentTimeMillis());
		teleported.add(u);
		this.lastSprintStart.remove(p);
		this.lastSprintStop.remove(p);
		if (p.hasPermission("anticheat.staff")) {
			AntiCheat.AlertsOn.add(p);
		}
		if(AntiCheat.isInPhaseTimer(p)) {
			AntiCheat.timerLeft.put(p.getName().toString(), 3);
		}else {
			AntiCheat.getInstance().startTimerPhaseCheck(p);
		}


		AntiCheat.getInstance().getDataManager().addPlayerData(p);
		AntiCheat.getInstance().getDataManager().add(p);
	}

	@EventHandler(ignoreCancelled = true)
	private void teleport(PlayerTeleportEvent e) {
		if (e.getCause() != TeleportCause.UNKNOWN) {
			teleported.add(e.getPlayer().getUniqueId());
		}
	}
	@EventHandler(priority = EventPriority.HIGH)
	private void onDeath(PlayerDeathEvent e) {
		teleported.add(e.getEntity().getUniqueId());
		final Player p = e.getEntity();
		JesusA.onWater.remove(p);
		NoFallA.cancel.add(p);
		JesusA.placedBlockOnWater.remove(p);
		JesusA.count.remove(p);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onTeleport(PlayerTeleportEvent e) {
		final Player p = e.getPlayer();
		if (e.getCause() == TeleportCause.ENDER_PEARL) {
			NoFallA.cancel.add(p);
		}
	}
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onVelocity(PlayerVelocityEvent e) {
		JesusA.velocity.put(e.getPlayer(), System.currentTimeMillis());
	}


	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void OnPlace(BlockPlaceEvent e) {
		if (e.getBlockReplacedState().getBlock().getType() == Material.WATER) {
			JesusA.placedBlockOnWater.add(e.getPlayer());
		}
	}
	@EventHandler
	public void PlayerChangedWorld(PlayerChangedWorldEvent e) {
		final UUID u = e.getPlayer().getUniqueId();
		PacketsA.blacklist.add(u);
	}

	@EventHandler
	private void PlayerRespawn(PlayerRespawnEvent e) {
		final UUID u = e.getPlayer().getUniqueId();
		PacketsA.blacklist.add(u);
		teleported.add(u);
	}
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onQuit(PlayerQuitEvent e) {
		final Player p = e.getPlayer();
		final UUID uuid = p.getUniqueId();
		AntiCheat.getInstance().Violations.remove(uuid);
		teleported.remove(uuid);
		PacketsA.packetTicks.remove(uuid);
		PacketsA.lastPacket.remove(uuid);
		PacketsA.blacklist.remove(uuid);
		TimerA.packets.remove(uuid);
		TimerA.verbose.remove(uuid);
		TimerA.lastPacket.remove(uuid);
		SpeedC.lastHit.remove(uuid);
		SpeedC.tooFastTicks.remove(uuid);
		SpeedC.speedTicks.remove(uuid);
		SpeedC.velocity.remove(uuid);
		ChangeA.falling.remove(uuid);
		SneakA.sneakTicks.remove(uuid);
		SpeedB.speedTicks.remove(uuid);
		SpeedB.tooFastTicks.remove(uuid);
		SpeedB.lastHit.remove(uuid);
		NoSlowdownA.speedTicks.remove(uuid);
		NoFallA.FallDistance.remove(uuid);
		NoFallA.FallDistance.containsKey(uuid);
		NoFallA.cancel.remove(p);
		AntiCheat.getInstance().getDataManager().removePlayerData(p);
		AscensionA.AscensionTicks.remove(uuid);
		AscensionA.velocity.remove(uuid);
		FlyB.flyTicks.remove(uuid);
		GlideA.flyTicks.remove(uuid);
		AutoClickerA.LastMS.remove(uuid);
		AutoClickerA.Clicks.remove(uuid);
		AutoClickerA.ClickTicks.remove(uuid);
		AutoClickerB.LastMS.remove(uuid);
		AutoClickerB.Clicks.remove(uuid);
		AutoClickerB.Clicks.remove(uuid);
		AutoClickerD.clicks.remove(uuid);
		AutoClickerD.recording.remove(uuid);
		HitBoxC.count.remove(uuid);
		HitBoxC.yawDif.remove(uuid);
		HitBoxC.lastHit.remove(uuid);
		AutoClickerC.packetHitsSinceLastCheck.remove(uuid);
		AutoClickerC.lastCheckedTick.remove(uuid);
		AutoClickerC.lastPacketTick.remove(uuid);
		AutoClickerC.lastTickWithPacketSent.remove(uuid);
		AutoClickerC.hitsSinceLastCheck.remove(uuid);
		AutoClickerC.cps.remove(uuid);
		AutoClickerC.cpsMS.remove(uuid);
		CriticalsB.CritTicks.remove(uuid);
		CriticalsB.CritTicks.remove(uuid);
		HitBoxA.count.remove(uuid);
		HitBoxA.yawDif.remove(uuid);
		HitBoxA.lastHit.remove(uuid);
		KillAuraD.packetTicks.remove(uuid);
		KillAuraF.AuraTicks.remove(uuid);
		KillAuraG.AimbotTicks.remove(uuid);
		KillAuraG.Differences.remove(uuid);
		KillAuraG.LastLocation.remove(uuid);
		KillAuraK.verbose.remove(uuid);
		KillAuraK.lastArmSwing.containsKey(uuid);
		RegenA.LastHeal.remove(uuid);
		RegenA.FastHealTicks.remove(uuid);
		AscensionC.flyTicks.remove(uuid);
		placedBlock.remove(p);
		AntiCheat.AlertsOn.remove(p);
		TimerA.toCancel.remove(p);
		JesusA.placedBlockOnWater.remove(p);
		AntiKBA.lastVelocity.remove(p);
		AntiKBA.awaitingVelocity.remove(p);
		AntiKBA.totalMoved.remove(p);
		KillAuraA.counts.remove(p);
		KillAuraA.blockGlitched.remove(p);
		KillAuraH.lastAttack.remove(p);
		ReachD.offsets.remove(p);
		ReachD.reachTicks.remove(p);
		ReachD.projectileHit.remove(p);
		FastBowA.bowPull.remove(p);
		FastBowA.count.remove(p);
		AntiCheat.getInstance().getDataManager().remove(p);

	}
	public static Map<Player, Long> getLastJoin() {
		return lastJoin;
	}
	public void setLastJoin(Map<Player, Long> lastJoin) {
		this.lastJoin = lastJoin;
	}
	public static Map<Player, Long> getLastSprintStart() {
		return lastSprintStart;
	}
	public void setLastSprintStart(Map<Player, Long> lastSprintStart) {
		this.lastSprintStart = lastSprintStart;
	}
	public static Map<Player, Long> getLastSprintStop() {
		return lastSprintStop;
	}
	public void setLastSprintStop(Map<Player, Long> lastSprintStop) {
		this.lastSprintStop = lastSprintStop;
	}
}
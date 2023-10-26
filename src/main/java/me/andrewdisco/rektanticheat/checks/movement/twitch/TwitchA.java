package me.andrewdisco.rektanticheat.checks.movement.twitch;

import me.andrewdisco.rektanticheat.checks.Check;
import me.andrewdisco.rektanticheat.checks.CheckType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import me.andrewdisco.rektanticheat.AntiCheat;
import me.andrewdisco.rektanticheat.packets.PacketPlayerType;
import me.andrewdisco.rektanticheat.packets.events.PacketPlayerEvent;

public class TwitchA extends Check {
	public TwitchA(AntiCheat AntiCheat) {
		super("TwitchA", "Twitch",  CheckType.Combat, true, true, false, true, false, 5, 1, 600000L, AntiCheat);
	}

	@EventHandler(priority=EventPriority.HIGH)
	private void Player(PacketPlayerEvent e) {
		final Player p = e.getPlayer();
		if (e.getType() != PacketPlayerType.LOOK) {
			return;
		}
		if ((e.getPitch() > 90.1F) || (e.getPitch() < -90.1F)) {
			if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
					|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
				return;
			}
			getAntiCheat().logCheat(this, p, null, "(Type: A)");
		}
	}
}

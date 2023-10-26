package me.andrewdisco.rektanticheat.events;

import me.andrewdisco.rektanticheat.AntiCheat;
import me.andrewdisco.rektanticheat.data.DataPlayer;
import me.andrewdisco.rektanticheat.packets.events.PacketPlayerEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class PacketListener implements Listener {
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onPacketPlayerEvent(PacketPlayerEvent e) {
		final Player p = e.getPlayer();
		final DataPlayer data = AntiCheat.getInstance().getDataManager().getData(p);
		if (data != null) {
			if (data.getLastPlayerPacketDiff() > 200) {
				data.setLastDelayedPacket(System.currentTimeMillis());
			}
			data.setLastPlayerPacket(System.currentTimeMillis());
		}
	}
}
/*
 * This file is part of NyanClans Bukkit plug-in.
 *
 * NyanClans is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NyanClans is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NyanClans. If not, see <https://www.gnu.org/licenses/>.
 */
package nyanclans.core.events;

import java.util.Date;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import nyanclans.core.player.ClanPlayer;
import nyanclans.utils.PluginUtils;

/** @author NyanGuyMF */
public final class PlayerJoinHandler implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(final PlayerJoinEvent event) {
        if (!event.isAsynchronous()) {
            PluginUtils.runTaskAsync(() -> handleJoinEvent(event));
            return;
        }

        handleJoinEvent(event);
    }

    private void handleJoinEvent(final PlayerJoinEvent event) {
        if (ClanPlayer.exists(event.getPlayer().getName())) {
            updatePlayer(event.getPlayer());
            return;
        }

        ClanPlayer player = new ClanPlayer(event.getPlayer().getName());
        player.setFirstServerJoin(new Date(System.currentTimeMillis()));
        player.setLastServerJoin(player.getFirstServerJoin());
        player.create();
    }

    private void updatePlayer(final Player bukkitPlayer) {
        ClanPlayer player = new ClanPlayer(bukkitPlayer);
        player.setLastServerJoin(new Date(System.currentTimeMillis()));
        player.save();
    }
}

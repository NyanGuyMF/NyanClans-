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

import static nyanclans.storage.yaml.messages.MessagesManager.colored;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import nyanclans.core.player.ClanPlayer;
import nyanclans.storage.yaml.clan.ClanConfig;

/** @author NyanGuyMF - Vasiliy Bely */
public final class ChatMessageHandler implements Listener {
    private ClanConfig config;

    public ChatMessageHandler(final ClanConfig config) {
        this.config = config;
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void insertClanInMessage(final AsyncPlayerChatEvent event) {
        String format = event.getFormat();

        // De Morgan's law «not (A or B) = not A and not B»
        if (!(format.contains("{clan-colored}") || format.contains("clan")))
            return;

        ClanPlayer player = ClanPlayer.playerByName(event.getPlayer().getName());

        // it shouldn't happen because Console cannot send messages,
        // but what if it will?
        if (player == null)
            return;

        if (!player.isClanMember()) {
            event.setFormat(format.replace("{clan-colored}", "").replace("{clan}", ""));
            return;
        }

        String clanNameFormat = colored(config.getClanPlaceholderFormat());
        String clanName = player.getClan().getName();

        format = format.replace(
            "{clan}", clanNameFormat.replace("{clan-name}", clanName)
        );
        format = format.replace(
            "{clan-colored}", clanNameFormat.replace("{clan-name}", colored(clanName))
        );

        event.setFormat(format);
    }

    public void register(final JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}

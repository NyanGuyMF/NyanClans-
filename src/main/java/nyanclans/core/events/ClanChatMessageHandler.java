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

import nyanclans.core.player.ClanPlayer;
import nyanclans.storage.yaml.clan.ClanChatConfig;

/** @author NyanGuyMF - Vasiliy Bely */
final class ClanChatMessageHandler implements DefaultHandler<AsyncClanChatMessageEvent> {
    private ClanChatConfig config;

    protected ClanChatMessageHandler(final ClanChatConfig config) {
        this.config = config;
    }

    @Override public void handle(final AsyncClanChatMessageEvent event) {
        ClanPlayer player = event.getPlayer();
        String formatedMessage = getFormatFor(player).replace("{message}", event.getMessage());

        player.getClan().getOnlineMembers().parallelStream().forEach(member -> {
            member.sendMessage(formatedMessage);
        });
    }

    /**
     * Gets clan chat format for given player.
     * <p>
     * Inserts clan and rank placeholders, only
     * message placeholder is still empty after this method.
     *
     * @param   player  Player to take his clan and rank.
     * @return Clan chat format for given player or <tt>null</tt>
     *      if {@link ClanPlayer} isn't clan member (should never happen).
     *
     * @see ClanChatConfig#getClanChatFormat()
     */
    private String getFormatFor(final ClanPlayer player) {
        if (!player.isClanMember())
            return null;

        return colored(
            config.getClanChatFormat()
                .replace("{clan}", player.getClan().getName())
                .replace("{rank}", player.getRank().getName())
                .replace("{player}", player.getName())
        );
    }
}

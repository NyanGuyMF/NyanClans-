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
package nyanclans.core.commands.clan.sub;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import nyanclans.core.clan.Clan;
import nyanclans.core.player.ClanPlayer;
import nyanclans.core.rank.RankPermission;
import nyanclans.storage.yaml.messages.MessagesManager;

/** @author NyanGuyMF - Vasiliy Bely */
public final class DeleteCommand extends ClanSubCommand {
    private final MessagesManager messages;

    public DeleteCommand(final MessagesManager messages) {
        super("delete", RankPermission.delete, messages.usage("clan", "delete"));

        this.messages = messages;
    }

    @Override
    public boolean execute(final CommandSender performer, final String[] args) {
        ClanPlayer player = ClanPlayer.playerByName(performer.getName());

        // Usually its ConsoleCommandSender
        if (player == null) {
            performer.sendMessage(messages.error(
                    "only-player", "/clan " + super.getName()
            ));
            return true;
        }

        if (!player.isClanMember()) {
            performer.sendMessage(messages.error("not-clan-member"));
            return true;
        }

        if (!player.hasPermission(super.getPermission())) {
            performer.sendMessage(messages.error(
                    "no-permission", "/clan " + super.getName()
            ));
            return true;
        }

        // here starts deleting logic
        // first of all we update player's data,
        // then deleting ranks and only after clan itself
        Clan clan = player.getClan();

        // delete clan data from players and send them message
        // about clan deleting.
        // Maybe I should create another one method
        clan.getMembers().parallelStream().forEach(member -> {
            String clanDeleted = messages.info(
                    "clan-deleted", clan.getName(), player.getName()
            );
            // access to bukkit method in parallel mode may cause problems
            // but I've never got them yet
            @SuppressWarnings("deprecation")
            Player bMember = Bukkit.getPlayer(member.getName());

            if ((bMember != null) && (bMember.isOnline())) {
                bMember.sendMessage(clanDeleted);
            }

            // now player isn't clan member anymore
            member.setClan(null);
            member.setRank(null);
            member.setClanJoin(null);
        });

        // clear clan's ranks
        clan.getRanks().parallelStream().forEach(rank -> {
            rank.delete();
        });

        clan.delete();

        return true;
    }
}

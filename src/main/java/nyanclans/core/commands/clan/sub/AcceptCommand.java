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

import org.bukkit.command.CommandSender;

import nyanclans.core.clan.Clan;
import nyanclans.core.player.ClanPlayer;
import nyanclans.storage.cache.Invite;
import nyanclans.storage.cache.InviteCache;
import nyanclans.storage.yaml.messages.MessagesManager;

/** @author NyanGuyMF - Vasiliy Bely */
public final class AcceptCommand extends ClanSubCommand {
    private final MessagesManager messages;
    private InviteCache inviteCache;

    public AcceptCommand(final MessagesManager messages) {
        // plug-in doesn't requires any permission to accept invite, so just null
        super("accept", null, messages.usage("clan", "accept"));

        this.messages = messages;
    }

    @Override
    public boolean execute(final CommandSender performer, final String[] args) {
        ClanPlayer player = ClanPlayer.playerByName(performer.getName());

        if (player == null) {
            performer.sendMessage(messages.error("no-permission", super.getFullName()));
            return true;
        }

        if (player.isClanMember()) {
            performer.sendMessage(messages.error("you-already-in-clan"));
            return true;
        }

        if (!inviteCache.isPlayerCached(player.getName())) {
            performer.sendMessage(messages.error("no-invites"));
            return true;
        }

        Invite invite;
        if (args.length == 0) {
            invite = inviteCache.getLastCachedInvite(player.getName());
        } else {
            invite = inviteCache.getCachedInvite(player.getName(), args[0]);

            if (invite == null) {
                performer.sendMessage(messages.error("no-such-invite", args[0]));
                return true;
            }
        }

        ClanPlayer inviter = invite.getInviter();
        Clan clan = invite.getClan();
        inviteCache.removeCachedInvite(player.getName(), clan.getName());

        clan.addMember(player);
        clan.save();
        player.setClan(clan);
        player.setRank(clan.getRankByAlias("player"));
        player.save();

        String playerJoined = messages.info(
            "player-join-clan", clan.getName(),
            player.getName(), inviter.getName()
        );
        clan.getOnlineMembers().parallelStream()
            .forEach(member -> member.sendMessage(playerJoined));

        return true;
    }
}

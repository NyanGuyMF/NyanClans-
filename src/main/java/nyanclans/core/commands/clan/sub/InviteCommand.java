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

import static nyanclans.core.player.ClanPlayer.playerByName;

import org.bukkit.command.CommandSender;

import nyanclans.core.player.ClanPlayer;
import nyanclans.core.rank.RankPermission;
import nyanclans.storage.cache.Invite;
import nyanclans.storage.cache.InviteCache;
import nyanclans.storage.yaml.messages.MessagesManager;

/** @author NyanGuyMF - Vasiliy Bely */
public final class InviteCommand extends ClanSubCommand {
    private final MessagesManager messages;
    private final InviteCache inviteCache;

    public InviteCommand(final MessagesManager messages) {
        super(
            "invite", RankPermission.invite, messages.usage("clan", "invite")
        );

        this.messages = messages;
        inviteCache   = new InviteCache();
    }

    @Override
    public boolean execute(final CommandSender performer, final String[] args) {
        ClanPlayer player = playerByName(performer.getName());

        if (player == null) {
            performer.sendMessage(messages.error("only-player", super.getFullName()));
            return true;
        }

        if (args.length == 0) {
            performer.sendMessage(super.getUsage());
            return true;
        }

        if (!player.isClanMember()) {
            performer.sendMessage(messages.error("not-clan-member"));
            return true;
        }

        if (!super.hasPermission(player)) {
            performer.sendMessage(messages.error("no-permission", super.getFullName()));
            return true;
        }

        // first argument should be player's name
        ClanPlayer invitedPlayer = playerByName(args[0]);

        // it should happen only if player wasn't on server yet
        if (invitedPlayer == null) {
            performer.sendMessage(messages.error("player-not-found", args[0]));
            return true;
        }

        // we can invite only player, who isn't clan member
        // TODO: think about luring players away from their clans
        if (invitedPlayer.isClanMember()) {
            String error;

            if (invitedPlayer.getClan().equals(player.getClan())) {
                error = messages.error("invites-is-your-member", invitedPlayer.getName());
            } else {
                error = messages.error("invites-is-other-member", invitedPlayer.getName());
            }

            performer.sendMessage(error);
            return true;
        }

        Invite invite = new Invite(invitedPlayer, player, player.getClan());

        // add invite to cache
        inviteCache.cacheInvite(invite);

        // TODO: implement JSON message about invite
        // TODO: implement message, that player was invited

        return true;
    }
}

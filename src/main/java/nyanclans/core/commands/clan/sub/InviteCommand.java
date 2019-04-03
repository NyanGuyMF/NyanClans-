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

import static com.comphenix.protocol.ProtocolLibrary.getProtocolManager;
import static com.comphenix.protocol.wrappers.WrappedChatComponent.fromJson;
import static nyanclans.core.player.ClanPlayer.playerByName;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.nio.file.Files;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.ChatType;

import nyanclans.core.NyanClansPlugin;
import nyanclans.core.events.ReloadEvent;
import nyanclans.core.player.ClanPlayer;
import nyanclans.core.rank.RankPermission;
import nyanclans.storage.cache.Invite;
import nyanclans.storage.cache.InviteCache;
import nyanclans.storage.yaml.clan.ClanConfig;
import nyanclans.storage.yaml.messages.MessagesManager;
import nyanclans.utils.Observer;
import nyanclans.utils.PluginUtils;

/** @author NyanGuyMF - Vasiliy Bely */
public final class InviteCommand extends ClanSubCommand implements Observer<ReloadEvent> {
    private final MessagesManager messages;
    private final ClanConfig clanConfig;
    private final InviteCache inviteCache;
    private String inviteMessageFormat;

    public InviteCommand(final NyanClansPlugin plugin) {
        super(
            "invite", RankPermission.invite, plugin.getMessagesConfig().usage("clan", "invite")
        );

        messages      = plugin.getMessagesConfig();
        clanConfig    = plugin.getConfiguration().getClans();
        inviteCache   = new InviteCache();

        new ReloadEvent().addObserver(this);
        update(new File(plugin.getDataFolder(), "invite-message.json"));
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
//        if (invitedPlayer.isClanMember()) {
//            String error;
//
//            if (invitedPlayer.getClan().equals(player.getClan())) {
//                error = messages.error("invites-is-your-member", invitedPlayer.getName());
//            } else {
//                error = messages.error("invites-is-other-member", invitedPlayer.getName());
//            }
//
//            performer.sendMessage(error);
//            return true;
//        }

        Invite invite = new Invite(invitedPlayer, player, player.getClan());

        // add invite to cache and schedule remove function for it.
        inviteCache.cacheInvite(invite);
        PluginUtils.runTaskLater(() -> {
            inviteCache.removeCachedInvite(player.getName(), player.getClan().getName());
        }, clanConfig.getInviteExpiresAfter());

        final String clanRating = String.format("%1.2f", player.getClan().getRating());
        final String members    = String.valueOf(player.getClan().getMembers().size());
        final String message    = inviteMessageFormat
                .replace("{clan-name}", player.getClan().getName())
                .replace("{clan-name-colored}", player.getClan().getColoredName())
                .replace("{clan-rating}", clanRating)
                .replace("{clan-members}", members)
                .replace("{inviter}", player.getName())
                .replace("{inviter-rank}", player.getRank().getColoredName())
                .replace("{invited}", invitedPlayer.getName());

        sendMessage((Player) performer, message);

        return true;
    }

    @Override public void update(final ReloadEvent obs) {
        update(new File(obs.getPlugin().getDataFolder(), "invite-message.json"));
    }

    /**
     * Default ProtocolLib message sending method.
     * <p>
     * Sends JSON message to player via ProtocolLib.
     *
     * @param   receiver        Player, that will receive the message.
     * @param   jsonMessage     JSON message to send.
     * @return <tt>true</tt> if message sent successfully.
     */
    private boolean sendMessage(final Player receiver, final String jsonMessage) {
        PacketType chat = PacketType.Play.Server.CHAT;
        PacketContainer packetMessage = new PacketContainer(chat);
        packetMessage.getChatTypes().write(0, ChatType.CHAT);
        packetMessage.getChatComponents().write(0, fromJson(jsonMessage));

        try {
            getProtocolManager().sendServerPacket(receiver, packetMessage);
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    private void update(final File inviteMessageFile) {
        StringBuffer buffer = new StringBuffer();

        try {
            Files.lines(inviteMessageFile.toPath(), Charset.forName("UTF-8"))
                .map(str -> str.trim())
                .forEach(buffer::append);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        inviteMessageFormat = buffer.toString();
    }
}

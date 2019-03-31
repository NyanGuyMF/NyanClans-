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
package nyanclans.core.commands.clan;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;
import static nyanclans.storage.yaml.clan.ClanChatConfig.parseAllowedColors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import nyanclans.core.events.AsyncClanChatMessageEvent;
import nyanclans.core.player.ClanPlayer;
import nyanclans.storage.yaml.clan.ClanChatConfig;
import nyanclans.storage.yaml.clan.ClanConfig;
import nyanclans.storage.yaml.messages.MessagesManager;

/** @author NyanGuyMF - Vasiliy Bely */
public final class ClanChatCommand implements CommandExecutor {
    private final MessagesManager messages;
    private final ClanChatConfig config;

    public ClanChatCommand(final MessagesManager messages, final ClanConfig config) {
        this.messages = messages;
        this.config   = config.getChat();
    }

    @Override
    public boolean onCommand(
        final CommandSender sender, final Command command,
        final String label, final String[] args
    ) {
        // nothing to send, player should enter message
        if (args.length == 0) {
            sender.sendMessage(messages.usage(
                    "clanchat", "clanchat", config.getClanChatSymbol()
            ));
            return true;
        }

        ClanPlayer player = ClanPlayer.playerByName(sender.getName());

        // usually its ConsoleCommandSender
        if (player == null) {
            sender.sendMessage(messages.error("only-player", "/cc"));
            return true;
        }

        // there are no clan to send message, so good bye player :D
        if (!player.isClanMember()) {
            sender.sendMessage(messages.error("not-clan-member"));
            return true;
        }

        // make one string message form all arguments with allowed colors
        final String message = parseAllowedColors(stream(args).collect(joining(" ")));

        new AsyncClanChatMessageEvent(player, message).notifyObservers();

        return true;
    }

    public void register(final JavaPlugin plugin) {
        plugin.getCommand("clanchat").setExecutor(this);
    }
}

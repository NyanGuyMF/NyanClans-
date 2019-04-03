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

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import nyanclans.core.player.ClanPlayer;

/** @author NyanGuyMF - Vasiliy Bely */
public final class ClanCompleter implements TabCompleter {
    private List<String> subCommands;

    public ClanCompleter() {
        subCommands = asList(
            "create",   "delete",
            "invite",   "accept",
            "deny"
        );
    }

    @Override
    public List<String> onTabComplete(
            final CommandSender sender, final Command command,
            final String alias, final String[] args
    ) {
        if (args.length == 1)
            return subCommands.parallelStream()
                    .filter(subCommand -> subCommand.startsWith(args[0]))
                    .collect(Collectors.toList());

        if (args.length == 2)
            return completeSecond(sender, args[0]);

        return null;
    }

    /**
     * Completes second argument for each known command.
     * <p>
     * Known commands:
     * <ol>
     *   <li><b>/clan delete {clan-name}</b> - completes with player's clan name {@link #completeDelete(CommandSender)}</li>
     * </ol>
     *
     * @param   sender  Command sender to complete his command.
     * @param   arg     First sub command (create, delete etc.).
     * @return
     */
    private List<String> completeSecond(final CommandSender sender, final String arg) {
        if (arg.equalsIgnoreCase("delete"))
            return completeDelete(sender);
        else if (arg.equalsIgnoreCase("completeInvite"))
            return completeInvite(sender);

        // return empty list, not players names
        return asList("");
    }

    /**
     * Completes /clan delete {gang-name} with sender's clan name.
     * <p>
     * Returns empty list if given {@link CommandSender} isn't
     * clan member.
     *
     * @param   sender  Command sender to complete his command.
     * @return Sender's clan name or empty list.
     */
    private List<String> completeDelete(final CommandSender sender) {
        ClanPlayer player = ClanPlayer.playerByName(sender.getName());

        // Usually its ConsoleCommandSender
        if (player == null)
            return asList("");

        // TODO: add delete command for developers (delete any clan)

        if (!player.isClanMember())
            // return empty list, not players names
            return asList("");

        return asList(player.getClan().getName());
    }

    private List<String> completeInvite(final CommandSender sender) {
        ClanPlayer player = ClanPlayer.playerByName(sender.getName());

        // Usually its ConsoleCommandSender
        if (player == null)
            return asList("");

        // player without clan cannot invite others
        if (!player.isClanMember())
            // return empty list, not players names
            return asList("");

        List<String> members = player.getClan().getMembers().parallelStream()
                .map(member -> member.getName())
                .collect(toList());

        return Bukkit.getOnlinePlayers().parallelStream()
                .filter(onlinePlayer -> {
                    return !members.contains(onlinePlayer.getName());
                })
                .map(onlinePlayer -> onlinePlayer.getName())
                .collect(toList());
    }
}

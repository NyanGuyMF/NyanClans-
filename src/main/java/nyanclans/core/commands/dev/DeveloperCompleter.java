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
package nyanclans.core.commands.dev;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

/** @author NyanGuyMF */
public final class DeveloperCompleter implements TabCompleter {
    private List<String> subCommands;

    public DeveloperCompleter(final Set<String> subCommands) {
        this.subCommands = subCommands.parallelStream().collect(Collectors.toList());
    }

    public DeveloperCompleter(final List<String> subCommands) {
        this.subCommands = subCommands;
    }

    @Override
    public List<String> onTabComplete(
            final CommandSender sender, final Command command,
            final String alias, final String[] args
    ) {
        if (args.length == 0)
            return subCommands;
        else if (args.length == 1)
            return completeSubCommands(args[0]);
        else
            return null;
    }

    private List<String> completeSubCommands(final String arg) {
        return subCommands.parallelStream()
                .filter(cmd -> cmd.startsWith(arg))
                .collect(toList());
    }
}

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

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import nyanclans.core.commands.BaseCommandManager;
import nyanclans.core.commands.dev.sub.Fresh;
import nyanclans.core.commands.dev.sub.Help;
import nyanclans.core.commands.dev.sub.PlayerInfo;
import nyanclans.core.commands.dev.sub.Reload;
import nyanclans.storage.db.DatabaseConnector;
import nyanclans.storage.yaml.messages.MessageBuilder;
import nyanclans.storage.yaml.messages.MessagesConfig;

/** @author NyanGuyMF */
public final class DeveloperCommand
        extends BaseCommandManager<CommandSender, String>
        implements CommandExecutor {
    private final DatabaseConnector databaseConnector;
    private MessagesConfig messages;

    public DeveloperCommand(
            final MessagesConfig messages, final DatabaseConnector databaseConnector
    ) {
        this.messages          = messages;
        this.databaseConnector = databaseConnector;

        setupSubCommands(messages);
    }

    @Override
    public boolean onCommand(
        final CommandSender sender, final Command command,
        final String label, final String[] args
    ) {
        if (args.length == 0) {
            new MessageBuilder(messages.usage().getDev().getDevCommand()).send(sender);
            return true;
        }

        final String subCommand = args[0].toLowerCase();
        final String[] subCommandArgs = Arrays.copyOfRange(args, 1, args.length);

        if (super.hasSubCommand(subCommand))
            return super.getSubCommand(subCommand).execute(sender, subCommand, subCommandArgs);

        new MessageBuilder(messages.usage().getDev().getDevCommand()).send(sender);
        return true;
    }

    /** Registers this command for given plug-in. */
    public void register(final JavaPlugin plugin) {
        PluginCommand command = plugin.getCommand("clandev");
        command.setExecutor(this);
        command.setTabCompleter(new DeveloperCompleter(super.getSubCommands().keySet()));
    }

    private void setupSubCommands(final MessagesConfig messages) {
        addSubCommand(new PlayerInfo(messages));
        addSubCommand(new Reload(messages));
        addSubCommand(new Help(messages));
        addSubCommand(new Fresh(messages, databaseConnector));
    }
}

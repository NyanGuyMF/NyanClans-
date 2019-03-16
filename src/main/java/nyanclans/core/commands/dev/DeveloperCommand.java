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
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import nyanclans.core.commands.BaseCommandManager;
import nyanclans.core.commands.SubCommand;
import nyanclans.core.commands.dev.sub.Help;
import nyanclans.core.commands.dev.sub.PlayerInfo;
import nyanclans.core.commands.dev.sub.Reload;
import nyanclans.storage.yaml.messages.MessageBuilder;
import nyanclans.storage.yaml.messages.MessagesConfig;
import nyanclans.utils.Observer;

/** @author NyanGuyMF */
public final class DeveloperCommand
        extends BaseCommandManager<CommandSender, String>
        implements CommandExecutor, Observer<MessagesConfig> {
    private final Map<String, SubCommand<CommandSender, String>> subCommands;
    private String usageMessage;

    public DeveloperCommand(final MessagesConfig messages) {
        subCommands = new HashMap<>();
        setupSubCommands(messages);
        messages.addObserver(this);

        usageMessage = new MessageBuilder()
                .message(messages.usage().getDev().getDevCommand())
                .build();
    }

    @Override
    public boolean onCommand(
        final CommandSender sender, final Command command,
        final String label, final String[] args
    ) {
        if (args.length == 0) {
            sender.sendMessage(usageMessage);
            return true;
        }

        final String subCommand = args[0].toLowerCase();
        final String[] subCommandArgs = Arrays.copyOfRange(args, 1, args.length);

        if (super.hasSubCommand(subCommand))
            return super.getSubCommand(subCommand).execute(sender, subCommand, subCommandArgs);
        else {
            usage(sender);
            return true;
        }
    }

    @Override
    public void update(final MessagesConfig obs) {
        usageMessage = new MessageBuilder()
                .message(obs.usage().getDev().getDevCommand())
                .build();
    }

    /** Registers this command for given plug-in. */
    public void register(final JavaPlugin plugin) {
        PluginCommand command = plugin.getCommand("clandev");
        command.setExecutor(this);
        command.setTabCompleter(new DeveloperCompleter(subCommands.keySet()));
    }

    private void usage(final CommandSender receiver) {
        receiver.sendMessage(usageMessage);
    }

    private void setupSubCommands(final MessagesConfig messages) {
        addSubCommand(new PlayerInfo(messages));
        addSubCommand(new Reload(messages));
        addSubCommand(new Help(messages));
    }
}

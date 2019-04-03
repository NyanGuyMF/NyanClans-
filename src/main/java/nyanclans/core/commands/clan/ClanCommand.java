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

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import nyanclans.core.NyanClansPlugin;
import nyanclans.core.commands.BaseCommandManager;
import nyanclans.core.commands.clan.sub.CreateCommand;
import nyanclans.core.commands.clan.sub.DeleteCommand;
import nyanclans.core.commands.clan.sub.InviteCommand;
import nyanclans.core.rank.RankPermission;
import nyanclans.storage.yaml.messages.MessagesManager;

/** @author NyanGuyMF - Vasiliy Bely */
public final class ClanCommand
    extends BaseCommandManager<RankPermission>
    implements CommandExecutor {
    private final MessagesManager messages;

    public ClanCommand(final NyanClansPlugin plugin) {
        messages = plugin.getMessagesConfig();

        super.addSubCommand(new CreateCommand(messages, plugin.getConfiguration()));
        super.addSubCommand(new DeleteCommand(messages));
        super.addSubCommand(new InviteCommand(messages, plugin));
    }

    @Override
    public boolean onCommand(
            final CommandSender sender, final Command command,
            final String label, final String[] args
    ) {
        if (args.length == 0) {
            sender.sendMessage(messages.usage("clan", "clan"));
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(messages.error("only-players", "/clan"));
            return true;
        }

        final String   subCommand     = args[0].toLowerCase();
        final String[] subCommandArgs = Arrays.copyOfRange(args, 1, args.length);

        if (super.hasSubCommand(subCommand))
            return super.getSubCommand(subCommand).execute(sender, subCommandArgs);

        sender.sendMessage(messages.usage("clan", "clan"));
        return true;
    }

    public void register(final JavaPlugin plugin) {
        plugin.getCommand("clan").setExecutor(this);
        plugin.getCommand("clan").setTabCompleter(new ClanCompleter());
    }
}

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
package nyanclans.core.commands.dev.sub;

import org.bukkit.command.CommandSender;

import nyanclans.core.commands.SubCommand;
import nyanclans.storage.yaml.messages.MessagesManager;

/** @author nyanguymf */
public final class Help extends SubCommand<String> {
    private MessagesManager messages;

    public Help(final MessagesManager messages) {
        super(
            "help", "nyanclans.dev.help",
            ""
        );

        this.messages = messages;
    }

    @Override
    public boolean execute(final CommandSender sender, final String[] args) {
        if (!hasPermission(sender)) {
            sender.sendMessage(messages.error("no-permission", super.getName()));
            return true;
        }

        messages.allHelpFor("dev", false).forEach(msg -> {
            sender.sendMessage(MessagesManager.colored(msg));
        });

        return true;
    }
}

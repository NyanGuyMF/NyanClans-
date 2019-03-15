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
package nyanclans.commands.dev.sub;

import org.bukkit.command.CommandSender;

import nyanclans.commands.SubCommand;
import nyanclans.storage.yaml.messages.MessageBuilder;
import nyanclans.storage.yaml.messages.MessagesConfig;

/** @author NyanGuyMF */
public final class Reload extends SubCommand<String> {
    private MessagesConfig messagesConfig;

    public Reload(final MessagesConfig messages) {
        super(
            "reload", "nyanclans.dev.reload",
            messages.usage().getDev().getReload()
        );

        messagesConfig = messages;
    }

    @Override
    public boolean execute(final CommandSender sender, final String command, final String[] args) {
        if (!hasPermission(sender)) {
            new MessageBuilder()
                .message(messagesConfig.error().getNoPermission())
                .args(super.getName())
                .send(sender);
            return true;
        }

        messagesConfig.loadAndSave(); // it will reload messages
        new MessageBuilder()
            .message(messagesConfig.info().getReloadSuccess())
            .send(sender);

        return true;
    }

    @Override
    public boolean hasPermission(final CommandSender sender) {
        return sender.hasPermission(super.getPermission());
    }
}

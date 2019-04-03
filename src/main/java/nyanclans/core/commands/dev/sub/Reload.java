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

import nyanclans.core.NyanClansPlugin;
import nyanclans.core.commands.SubCommand;
import nyanclans.core.events.ReloadEvent;

/** @author NyanGuyMF */
public final class Reload extends SubCommand<String> {
    private NyanClansPlugin plugin;

    public Reload(final NyanClansPlugin plugin) {
        super(
            "reload", "nyanclans.dev.reload",
            ""
        );

        this.plugin = plugin;
    }

    @Override
    public boolean execute(final CommandSender sender, final String[] args) {
        if (!hasPermission(sender)) {
            sender.sendMessage(plugin.getMessagesConfig().error("no-permission"));
            return true;
        }

        plugin.getMessagesConfig().loadAndSave(); // it will reload messages
        plugin.getMessagesConfig().notifyObservers();
        sender.sendMessage(plugin.getMessagesConfig().info("reload-success"));

        ReloadEvent event = new ReloadEvent();
        event.update(plugin);
        event.notifyObservers();

        return true;
    }
}

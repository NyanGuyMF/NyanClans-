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
import nyanclans.storage.yaml.messages.MessagesConfig;
import nyanclans.utils.Observer;

/** @author nyanguymf */
public final class PlayerInfo extends SubCommand<String> implements Observer<MessagesConfig> {

    public PlayerInfo(final MessagesConfig messages) {
        super(
            "player", "nyanclans.dev.playerinfo",
            messages.usage().getDev().getPlayer()
        );
        messages.addObserver(this);
    }

    @Override
    public boolean execute(final CommandSender sender, final String command, final String[] args) {
        sender.sendMessage("Not implemented yet");
        return true;
    }

    @Override
    public boolean hasPermission(final CommandSender sender) {
        return sender.hasPermission(super.getPermission());
    }

    @Override
    public void update(final MessagesConfig obs) {
        super.setUsage(obs.usage().getDev().getPlayer());
    }
}

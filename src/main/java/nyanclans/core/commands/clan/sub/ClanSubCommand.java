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
package nyanclans.core.commands.clan.sub;

import org.bukkit.command.CommandSender;

import nyanclans.core.commands.SubCommand;
import nyanclans.core.player.ClanPlayer;
import nyanclans.core.rank.RankPermission;

/**
 * Represents implementation of sub command for clan commands
 * with overrided permissions methods.
 *
 * @author NyanGuyMF - Vasiliy Bely
 */
public abstract class ClanSubCommand extends SubCommand<RankPermission> {

    public ClanSubCommand(final String name, final RankPermission permission, final String usage) {
        super(name, permission, usage);
    }

    @Override public final boolean hasPermission(final CommandSender sender) {
        ClanPlayer player = ClanPlayer.playerByName(sender.getName());

        if (player == null)
            return false;

        return player.hasPermission(super.getPermission());
    }

    public final boolean hasPermission(final ClanPlayer player) {
        return player.hasPermission(super.getPermission());
    }
}

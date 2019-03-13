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
package nyanclans.commands;

import org.bukkit.command.CommandSender;

/** @author NyanGuyMF */
public abstract class SubCommand<K> {
    /** Command name, basically its /command «name» [args]. */
    private final String name;

    /**
     * Command aliases, basically it
     * /command «alias[0]|alias[1]|alias[n]» [args].
     */
    private final String[] aliases;

    /**
     * Player's permission to use this command,
     * basically it looks like «nyanclans.dev.recreate».
     */
    private final K permission;

    /**
     * Creates new sub command, which is /command «sub command» [args].
     *
     * @param   name        Command name, {@link #name}.
     * @param   aliases     Command aliases, {@link #aliases}.
     * @param   permission  Permission to use command, {@link #permission}.
     */
    public SubCommand(final String name, final String[] aliases, final K permission) {
        this.name       = name;
        this.aliases    = aliases;
        this.permission = permission;
    }

    public abstract boolean execute(CommandSender sender, String command, String[] args);

    /**
     * Check if {@link CommandSender} has permission
     * to perform this command.
     *
     * @param   sender  Command sender instance to check.
     * @return <tt>true</tt> if has.
     */
    public abstract boolean hasPermission(final CommandSender sender);

    /** Gets sub command's name, {@link #name} */
    public final String getName() {
        return name;
    }

    /** Gets array of sub command aliases, {@link #aliases} */
    public final String[] getAliases() {
        return aliases;
    }

    /** Gets permission to use command, {@link #permission} */
    public final K getPermission() {
        return permission;
    }
}

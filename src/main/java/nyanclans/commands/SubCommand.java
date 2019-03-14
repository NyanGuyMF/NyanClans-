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
     * Player's permission to use this command,
     * basically it looks like «nyanclans.dev.recreate».
     */
    private final K permission;

    /** Should be shown if command sender didn't gave arguments. */
    private String usage;

    /**
     * Creates new sub command, which is /command «sub command» [args].
     *
     * @param   name        Command name, {@link #name}.
     * @param   permission  Permission to use command, {@link #permission}.
     */
    public SubCommand(
            final String name, final K permission,
            final String usage
    ) {
        this.name       = name;
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

    @Override
    public String toString() {
        return "SubCommand [name=" + name + ", permission=" + permission + "]";
    }

    /** Gets sub command's name, {@link #name} */
    public final String getName() {
        return name;
    }

    /** Gets permission to use command, {@link #permission} */
    public final K getPermission() {
        return permission;
    }

    /** Gets {@link #usage} */
    public final String getUsage() {
        return usage;
    }

    /** Sets {@link #usage} */
    public final void setUsage(final String usage) {
        this.usage = usage;
    }
}

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
package nyanclans.core.commands;

import java.util.Map;

/**
 * Provides methods to manage sub commands.
 *
 * @author NyanGuyMF
 */
public interface CommandManager<PermissionType> {
    /**
     * Adds given sub command to private {@link Map}.
     *
     * @param   subCommand  {@link SubCommand} which you want to add.
     */
    void addSubCommand(final SubCommand<PermissionType> subCommand);

    /**
     * Check does it have given sub command name.
     *
     * @param   subCommandName  Name of sub command which you want to check.
     * @return <tt>true</tt> if it contains.
     */
    boolean hasSubCommand(final String subCommandName);

    /**
     * Gets {@link SubCommand} for given name.
     * <p>
     * If sub commands list doesn't contains given sub command
     * name it will return null, so you should check it at first:
     * {@link #hasSubCommand(String)}
     *
     * @param   subCommandName  Name of sub command which you want to get.
     * @return {@link SubCommand} or <tt>null</tt>.
     */
    SubCommand<PermissionType> getSubCommand(final String subCommandName);
}

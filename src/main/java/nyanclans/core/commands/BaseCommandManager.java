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

import java.util.HashMap;
import java.util.Map;

/**
 * Basic implementation of {@link CommandManager} interface.
 *
 * @author NyanGuyMF
 */
public class BaseCommandManager<PermissionType>
        implements CommandManager<PermissionType> {
    private Map<String, SubCommand<PermissionType>> subCommands;

    public BaseCommandManager() {
        subCommands = new HashMap<>();
    }

    @Override
    public final void addSubCommand(final SubCommand<PermissionType> subCommand) {
        subCommands.put(subCommand.getName(), subCommand);
    }

    @Override
    public final boolean hasSubCommand(final String subCommandName) {
        return subCommands.containsKey(subCommandName);
    }

    @Override
    public final SubCommand<PermissionType> getSubCommand(final String subCommandName) {
        return subCommands.get(subCommandName);
    }

    /** Gets subCommands */
    public Map<String, SubCommand<PermissionType>> getSubCommands() {
        return subCommands;
    }
}

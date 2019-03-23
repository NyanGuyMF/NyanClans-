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

import java.util.Objects;

import org.bukkit.command.CommandSender;

/** @author NyanGuyMF */
public abstract class SubCommand<CommandPerformer, PermissionType> {
    /** Command name, basically its /command «name» [args]. */
    private final String name;

    /**
     * Player's permission to use this command,
     * basically it looks like «nyanclans.dev.recreate».
     */
    private final PermissionType permission;

    /** Should be shown if command sender didn't gave arguments. */
    private String usage;

    /**
     * Creates new sub command, which is /command «sub command» [args].
     *
     * @param   name        Command name, {@link #name}.
     * @param   permission  Permission to use command, {@link #permission}.
     */
    public SubCommand(
            final String name, final PermissionType permission,
            final String usage
    ) {
        this.name       = name;
        this.permission = permission;
        this.usage      = usage;
    }

    public abstract boolean execute(CommandPerformer performer, String command, String[] args);

    /**
     * Check if {@link CommandPerformer} has permission
     * to perform this command.
     * <p>
     * By default if performer is instance of {@link CommandSender}
     * it checks {@link CommandSender#hasPermission(String)} with
     * {@link #permission}.
     * <p>
     * Override it if you use not {@link CommandSender} or other
     * permission class.
     *
     * @param   performer  Command sender instance to check.
     * @return <tt>true</tt> if has.
     */
    public boolean hasPermission(final CommandPerformer performer) {
        try {
            if (performer instanceof CommandSender)
                return ((CommandSender) performer).hasPermission(getPermission().toString());
        } catch (ClassCastException ignore) {}

        return false;
    }

    /**
     * Sends usage message to command performer if it's
     * instance of {@link CommandSender} class.
     *
     * @param   performer  The one who performed command.
     * @return Always <tt>true</tt>.
     */
    public boolean sendUsage(final CommandPerformer performer) {
        if (performer instanceof CommandSender) {
            ((CommandSender) performer).sendMessage(getUsage());
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, permission);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (!(obj instanceof SubCommand))
            return false;

        SubCommand<CommandPerformer, PermissionType> other;
        try {
            other = (SubCommand<CommandPerformer, PermissionType>) obj;
        } catch (ClassCastException ex) {
            return false;
        }

        return Objects.equals(name, other.name) && Objects.equals(permission, other.permission);
    }

    @Override
    public String toString() {
        return "SubCommand [name=" + name + ", permission=" + permission + "]";
    }

    /** Gets sub command's name, {@link #name} */
    public final String getName() {
        return name;
    }

    /** Gets permission to use command, {@link #permission} */
    public final PermissionType getPermission() {
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

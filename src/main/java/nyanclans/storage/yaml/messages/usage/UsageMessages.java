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
package nyanclans.storage.yaml.messages.usage;

import de.exlll.configlib.annotation.ConfigurationElement;

/** @author NyanGuyMF */
@ConfigurationElement
public final class UsageMessages {
    private DevCommandsUsage dev = new DevCommandsUsage();

    /** Gets dev */
    public DevCommandsUsage getDev() {
        return dev;
    }

    /** Sets dev */
    public void setDev(final DevCommandsUsage devCommands) {
        this.dev = devCommands;
    }
}

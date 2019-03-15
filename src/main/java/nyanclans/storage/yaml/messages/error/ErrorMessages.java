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
package nyanclans.storage.yaml.messages.error;

import de.exlll.configlib.annotation.ConfigurationElement;

/** @author NyanGuyMF */
@ConfigurationElement
public final class ErrorMessages {
    private String noPermission = "&cYou have no permission for &6{0} &ccommand.";

    /** Gets noPermission */
    public String getNoPermission() {
        return noPermission;
    }

    /** Sets noPermission */
    public void setNoPermission(String noPermission) {
        this.noPermission = noPermission;
    }
}

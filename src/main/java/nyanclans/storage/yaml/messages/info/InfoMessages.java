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
package nyanclans.storage.yaml.messages.info;

import de.exlll.configlib.annotation.ConfigurationElement;

/** @author NyanGuyMF */
@ConfigurationElement
public final class InfoMessages {
    private String reloadSuccess = "&3NyanClans &8» &aMessage configuration has been reloaded.";
    private String freshSuccess = "&3NyanClans &8» &6«&b{0}&6» &afreshed successfully.";

    /** Gets message for successful message configuration reload. */
    public String getReloadSuccess() {
        return reloadSuccess;
    }

    /** Sets message for successful message configuration reload. */
    public void setReloadSuccess(final String reloadSuccess) {
        this.reloadSuccess = reloadSuccess;
    }

    /** Gets freshSuccess */
    public String getFreshSuccess() {
        return freshSuccess;
    }

    /** Sets freshSuccess */
    public void setFreshSuccess(final String freshSuccess) {
        this.freshSuccess = freshSuccess;
    }
}

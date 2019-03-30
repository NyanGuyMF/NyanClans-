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
package nyanclans.storage.yaml;

import de.exlll.configlib.annotation.Comment;
import de.exlll.configlib.annotation.ConfigurationElement;

/** @author NyanGuyMF - Vasiliy Bely */
@ConfigurationElement
public final class ClanConfig {
    private int minClanNameLength = 3;

    private int maxClanNameLength = 24;

    @Comment("Regular expression for clan name. Set 'none' if it doesn't need.")
    private String clanNameRegex = "none";

    /** @return the minClanNameLength */
    public int getMinClanNameLength() {
        return minClanNameLength;
    }

    /** Sets minClanNameLength */
    public void setMinClanNameLength(final int minClanNameLength) {
        this.minClanNameLength = minClanNameLength;
    }

    /** @return the maxClanNameLength */
    public int getMaxClanNameLength() {
        return maxClanNameLength;
    }

    /** Sets maxClanNameLength */
    public void setMaxClanNameLength(final int maxClanNameLength) {
        this.maxClanNameLength = maxClanNameLength;
    }

    /** @return the clanNameRegex */
    public String getClanNameRegex() {
        return clanNameRegex;
    }

    /** Sets clanNameRegex */
    public void setClanNameRegex(final String clanNameRegex) {
        this.clanNameRegex = clanNameRegex;
    }
}

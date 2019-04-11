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
package nyanclans.storage.yaml.clan;

import de.exlll.configlib.annotation.Comment;
import de.exlll.configlib.annotation.ConfigurationElement;

/** @author NyanGuyMF - Vasiliy Bely */
@ConfigurationElement
public final class RatingConfig {
    @Comment("How much clan will get rating for joined player")
    private double clanJoin = 5.0D;

    @Comment({
        "Time in minutes - how long will joined players stay in cache",
        "to ensure rating abuse"
    })
    private int clanJoinCachedTime = 30;

    /** @return the clanJoin */
    public double getClanJoin() {
        return clanJoin;
    }

    /** Sets clanJoin */
    public void setClanJoin(double clanJoin) {
        this.clanJoin = clanJoin;
    }

    /** @return the clanJoinCachedTime */
    public int getClanJoinCachedTime() {
        return clanJoinCachedTime;
    }

    /** Sets clanJoinCachedTime */
    public void setClanJoinCachedTime(int clanJoinCachedTime) {
        this.clanJoinCachedTime = clanJoinCachedTime;
    }
}

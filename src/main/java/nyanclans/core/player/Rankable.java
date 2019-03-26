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
package nyanclans.core.player;

/**
 * Describes relationship with rankable players.
 *
 * @author NyanGuyMF - Vasiliy Bely
 */
public interface Rankable<RankType, PermissionType> {
    /** Gets player's rank. */
    RankType getRank();

    /** Sets player's rank. */
    void setRank(RankType rank);

    /**
     * Check is player has given permission.
     *
     * @param   permission  Permission which you want to check.
     * @return <tt>true</tt> if player has.
     */
    boolean hasPermission(PermissionType permission);
}

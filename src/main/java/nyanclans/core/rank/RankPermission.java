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
package nyanclans.core.rank;

import nyanclans.core.player.Rankable;

/** @author NyanGuyMF - Vasiliy Bely */
public enum RankPermission {
    /* --------
      Because of ConfigLib I have to set all constants in lower case.
       -------- */
    all,

    /** Allows to delete rank. */
    delete,

    /** Allows to kick players from clan. */
    kick,

    /**
     * Only leader and player with {@link #KICK_BYPASS}
     * can kick player with this permission.
     */
    kick_exempt,

    /** Allows to kick players with {@link #KICK_EXEMPT} permission. */
    kick_bypass,

    /**
     * {@link Rankable} with this permission will can invite
     * players which are not in clan yet.
     */
    invite,

    /** Allows to deposit money to clan balance. */
    deposit,

    /** Allows to take money from clan balance. */
    take,

    /** Allows to teleport to clan home point. */
    home_tp,

    /** Allows to send messages to clan chat. */
    chat,

    /** Allows to make an announcement to clan chat. */
    broadcast,

    /**
     * {@link Rankable} with this permission can set rank for
     * other clan players.
     * <p>
     * Clan leader's rank cannot be changed.
     */
    rank_set,

    rank_create;

    RankPermission() {}
}

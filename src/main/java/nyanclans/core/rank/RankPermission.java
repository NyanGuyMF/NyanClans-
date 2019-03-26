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
    ALL("all"),

    /** Allows to kick players from clan. */
    KICK("kick"),

    /**
     * Only leader and player with {@link #KICK_BYPASS}
     * can kick player with this permission.
     */
    KICK_EXEMPT("kick_exempt"),

    /** Allows to kick players with {@link #KICK_EXEMPT} permission. */
    KICK_BYPASS("kick_bypass"),

    /**
     * {@link Rankable} with this permission will can invite
     * players which are not in clan yet.
     */
    INVITE("invite"),

    /** Allows to deposit money to clan balance. */
    DEPOSIT("deposit"),

    /** Allows to take money from clan balance. */
    TAKE("take"),

    /** Allows to teleport to clan home point. */
    HOME_TP("home_tp"),

    /** Allows to send messages to clan chat. */
    CHAT("chat"),

    /** Allows to make an announcement to clan chat. */
    BROADCAST("broadcast"),

    /**
     * {@link Rankable} with this permission can set rank for
     * other clan players.
     * <p>
     * Clan leader's rank cannot be changed.
     */
    RANK_SET("rank_set"),

    RANK_CREATE("rank_create");

    private final String permission;

    private RankPermission(final String permission) {
        this.permission = permission;
    }

    @Override public String toString() {
        return permission;
    }
}

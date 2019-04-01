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
package nyanclans.storage.cache;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/** @author NyanGuyMF - Vasiliy Bely */
public final class InviteCache {
    private static volatile Map<String, LinkedHashMap<String, Invite>> cache;

    public InviteCache() {
        // because of we have a static cache for all instances
        // we shouldn't create new map for each instance
        if (InviteCache.cache == null) {
            InviteCache.cache = new HashMap<>();
        }
    }

    /**
     * Adds invite to cache.
     *
     * @param   invite  Invite to add.
     * @return Old invite of player from clan or
     *      <tt>null</tt> if it's first invite.
     */
    public Invite cacheInvite(final Invite invite) {
        cachePlayer(invite.getPlayer().getName());

        return getCachedPlayer(invite.getPlayer().getName())
                .put(invite.getClan().getName(), invite);
    }

    /**
     * Gets cached invite for given player with clan.
     * <p>
     * Returns <tt>null</tt> if there are no invites for player
     * or invites from of given clan.
     *
     * @param   player  Invited player.
     * @param   clan    Specific clan, that have invited player.
     * @return Cached value or <tt>null</tt>.
     */
    public synchronized Invite getCachedInvite(final String player, final String clan) {
        Map<String, Invite> playerInvites = InviteCache.cache.get(player);

        if (playerInvites == null)
            return null;

        return playerInvites.get(clan);
    }

    /**
     * Gets invites of cached player.
     * <p>
     * If player wasn't already cached it will return
     * <tt>null</tt> value.
     *
     * @param   player  Player name.
     * @return Map of invites from different clans of given player
     *      or <tt>null</tt> if doesn't exist.
     */
    public synchronized LinkedHashMap<String, Invite> getCachedPlayer(final String player) {
        return InviteCache.cache.get(player);
    }

    /**
     * Removes all invites of given player.
     *
     * @param   player  Player, whose invites you want to remove.
     * @return Map with invites of player.
     */
    public synchronized LinkedHashMap<String, Invite> removeCachedPlayer(final String player) {
        return InviteCache.cache.remove(player);
    }

    /**
     * Removes cached clan invite and returns it.
     * <p>
     * Returns <tt>null</tt> if there was no invite of
     * given player and clan.
     *
     * @param   player  Invited player.
     * @param   clan    Specific clan, that have invited player.
     * @return Cached value or <tt>null</tt>.
     *
     * @throws NullPointerException if player wasn't cached.
     * @see #isInviteCached(String, String)
     */
    public synchronized Invite removeCachedInvite(final String player, final String clan) {
        if (!isPlayerCached(player))
            return null;

        return InviteCache.cache.get(player).remove(clan);
    }

    /**
     * Checks whether the player has been cached.
     *
     * @param   player  Player name.
     * @return <tt>true</tt> if value is cached.
     *
     * @see #isInviteCached(String, String)
     */
    public synchronized boolean isPlayerCached(final String player) {
        // see also comment to #isClanCached(String, String)
        return getCachedPlayer(player) != null;
    }

    /**
     * Checks whether the clan invite has been cached
     * for the given player.
     *
     * @param   player  Invited player.
     * @param   clan    Specific clan, that have invited player.
     * @return <tt>true</tt> if value is cached.
     */
    public synchronized boolean isInviteCached(final String player, final String clan) {
        /*--------
           The same as HashMap#contains(), bit people may use
           different Map implementations, so I decide use this
           instead of possibility of standard AbstractMap#contains()
           method usage - it have O(n) running time in worse case.
           So this method depends only on hash computing algorithm complexity.
          --------*/
        return getCachedInvite(player, clan) != null;
    }

    private void cachePlayer(final String player) {
        if (isPlayerCached(player))
            return;

        InviteCache.cache.put(player, new LinkedHashMap<String, Invite>());
    }
}

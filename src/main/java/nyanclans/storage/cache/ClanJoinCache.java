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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nyanclans.core.events.AsyncClanJoinEvent;

/** @author NyanGuyMF - Vasiliy Bely */
public final class ClanJoinCache {
    private static volatile Map<String, Set<String>> cache;

    public ClanJoinCache() {
        ClanJoinCache.cache = new HashMap<>();
    }

    /**
     * Adds event's player and clan into cache.
     * <p>
     * If event was already cached it will leave cache unchanged
     * and return <tt>false</tt>.
     *
     * @param   event   The event, that happened.
     * @return <tt>true</tt> if event wasn't cached yet.
     */
    public synchronized boolean addToCache(final AsyncClanJoinEvent event) {
        String playerName = event.getPlayer().getName();
        String clanName   = event.getClan().getName();

        if (!ClanJoinCache.cache.containsKey(playerName)) {
            ClanJoinCache.cache.put(playerName, new HashSet<>());
        }

        return ClanJoinCache.cache.get(playerName).add(clanName);
    }

    /**
     * Removes event's player and clan from cache.
     *
     * @param   event   The event, that happened.
     */
    public synchronized void remove(final AsyncClanJoinEvent event) {
        String playerName = event.getPlayer().getName();

        // nothing to remove
        if (!ClanJoinCache.cache.containsKey(playerName))
            return;

        String clanName = event.getClan().getName();
        ClanJoinCache.cache.get(playerName).remove(clanName);
    }
}

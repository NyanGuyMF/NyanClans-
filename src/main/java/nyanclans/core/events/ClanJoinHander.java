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
package nyanclans.core.events;

import static nyanclans.utils.PluginUtils.runTaskLater;

import nyanclans.storage.cache.ClanJoinCache;
import nyanclans.storage.yaml.clan.RatingConfig;

/** @author NyanGuyMF - Vasiliy Bely */
final class ClanJoinHander implements DefaultHandler<AsyncClanJoinEvent> {
    private static RatingConfig config;
    private ClanJoinCache cache;

    public ClanJoinHander(final RatingConfig config) {
        ClanJoinHander.config = config;
        cache = new ClanJoinCache();
    }

    @Override public void handle(final AsyncClanJoinEvent event) {
        // ensure player doesn't abuse rating
        if (!cache.addToCache(event))
            return;

        event.getClan().increaseRating(ClanJoinHander.config.getClanJoin());
        event.getClan().save();

        runTaskLater(
            () -> cache.remove(event),
            ClanJoinHander.config.getClanJoinCachedTime()
        );
    }
}

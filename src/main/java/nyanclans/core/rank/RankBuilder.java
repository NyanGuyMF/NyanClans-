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

import nyanclans.core.clan.Clan;

/** @author NyanGuyMF - Vasiliy Bely */
public interface RankBuilder {
    /**
     * Sets alias to building {@link Rank}.
     *
     * @param   alias   Alias to set to rank.
     * @return Instance of current builder.
     */
    RankBuilder alias(final String alias);

    /**
     * Sets permissions array to bulding {@link Rank}.
     *
     * @param   permissions     Array of permissions to set to rank.
     * @return Instance of current builder.
     */
    RankBuilder permissions(final RankPermission... permissions);

    /**
     * Sets name to building {@link Rank}.
     *
     * @param   name    Name of rank to set.
     * @return Instance of current builder.
     */
    RankBuilder name(final String name);

    /**
     * Sets {@link Clan} instance to builder {@link Rank}.
     *
     * @param   clan    {@link Clan} instance to set.
     * @return Instance of current builder.
     */
    RankBuilder clan(final Clan clan);

    /** Returns constructed {@link Rank} instance. */
    Rank build();
}

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

import java.util.Arrays;

import nyanclans.core.clan.Clan;

/**
 * Default Builder pattern.
 *
 * @author NyanGuyMF - Vasiliy Bely
 */
class BaseRankBuilder implements RankBuilder {
    private Rank rank;

    @Override public BaseRankBuilder permissions(final RankPermission... permissions) {
        // I wanted to set it parallel, but Java API here isn't synchronized/violate
        Arrays.stream(permissions)
            .forEach(rank.getPermissions()::add);
        return this;
    }

    @Override public BaseRankBuilder alias(final String alias) {
        rank.setAlias(alias);
        return this;
    }

    @Override public BaseRankBuilder name(final String name) {
        rank.setName(name);
        return this;
    }

    @Override public BaseRankBuilder clan(final Clan clan) {
        rank.setClan(clan);
        return this;
    }

    @Override public Rank build() {
        return rank;
    }

    protected void setRank(final Rank rank) {
        this.rank = rank;
    }
}

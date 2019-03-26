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

import nyanclans.storage.yaml.RankConfig;

/** @author NyanGuyMF - Vasiliy Bely */
public final class RankBuildDirector {
    public enum RankTemplate {
        MODER,      LEADER,
        PLAYER,     CUSTOM,
    }

    private static RankConfig config;

    public static void setConfig(final RankConfig config) {
        if (RankBuildDirector.config == null) {
            RankBuildDirector.config = config;
        }
    }

    /**
     * Gets builder instance for given template.
     * <p>
     * If given template builder doesn't exists it
     * will return <tt>null</tt> value.
     *
     * @param   template    Builder template that you wish to get.
     * @return Implementation of {@link RankBuilder} or <tt>null</tt>.
     */
    public static RankBuilder getBuilder(final RankTemplate template) {
        switch (template) {
        case CUSTOM:
            return new BaseRankBuilder();
        case LEADER:
            return new LeaderRankBuilder(RankBuildDirector.config);
        case MODER:
            return new ModerRankBuilder(RankBuildDirector.config);
        case PLAYER:
            return new PlayerRankBuilder(RankBuildDirector.config);

        default:
            return null;
        }
    }
}

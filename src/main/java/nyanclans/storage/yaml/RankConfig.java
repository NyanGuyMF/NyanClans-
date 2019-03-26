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
package nyanclans.storage.yaml;

import static nyanclans.core.rank.RankPermission.ALL;
import static nyanclans.core.rank.RankPermission.BROADCAST;
import static nyanclans.core.rank.RankPermission.CHAT;
import static nyanclans.core.rank.RankPermission.DEPOSIT;
import static nyanclans.core.rank.RankPermission.HOME_TP;
import static nyanclans.core.rank.RankPermission.INVITE;
import static nyanclans.core.rank.RankPermission.KICK;
import static nyanclans.core.rank.RankPermission.KICK_BYPASS;
import static nyanclans.core.rank.RankPermission.KICK_EXEMPT;

import de.exlll.configlib.annotation.ConfigurationElement;
import de.exlll.configlib.annotation.ElementType;
import nyanclans.core.rank.Rank;
import nyanclans.core.rank.RankBuildDirector;
import nyanclans.core.rank.RankBuildDirector.RankTemplate;

/** @author NyanGuyMF - Vasiliy Bely */
@ConfigurationElement
public final class RankConfig {
    private int rankNameMaxLength = 16;
    private int rankNameMinLength = 3;

    @ElementType(Rank.class)
    private Rank leader;

    @ElementType(Rank.class)
    private Rank player;

    @ElementType(Rank.class)
    private Rank moder;

    public RankConfig() {
        leader = RankBuildDirector.getBuilder(RankTemplate.CUSTOM)
                .alias("leader")
                .name("&6Leader")
                .permissions(ALL)
                .build();
        moder = RankBuildDirector.getBuilder(RankTemplate.CUSTOM)
                .alias("leader")
                .name("&6Leader")
                .permissions(
                    KICK,           INVITE,
                    KICK_EXEMPT,    KICK_BYPASS,
                    DEPOSIT,        HOME_TP,
                    BROADCAST,      CHAT
                )
                .build();
        player = RankBuildDirector.getBuilder(RankTemplate.CUSTOM)
                .alias("leader")
                .name("&6Leader")
                .permissions(
                    DEPOSIT,    HOME_TP,    CHAT
                )
                .build();
    }

    /** @return the rankNameMaxLength */
    public int getRankNameMaxLength() {
        return rankNameMaxLength;
    }

    /** Sets rankNameMaxLength */
    public void setRankNameMaxLength(final int rankNameMaxLength) {
        this.rankNameMaxLength = rankNameMaxLength;
    }

    /** @return the rankNameMinLength */
    public int getRankNameMinLength() {
        return rankNameMinLength;
    }

    /** Sets rankNameMinLength */
    public void setRankNameMinLength(final int rankNameMinLength) {
        this.rankNameMinLength = rankNameMinLength;
    }

    /** @return the leader */
    public Rank getLeader() {
        return leader;
    }

    /** Sets leader */
    public void setLeader(final Rank leader) {
        this.leader = leader;
    }

    /** @return the player */
    public Rank getPlayer() {
        return player;
    }

    /** Sets player */
    public void setPlayer(final Rank player) {
        this.player = player;
    }

    /** @return the moder */
    public Rank getModer() {
        return moder;
    }

    /** Sets moder */
    public void setModer(final Rank moder) {
        this.moder = moder;
    }
}

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

import java.sql.SQLException;
import java.util.Date;

import org.bukkit.entity.Player;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import nyanclans.core.clan.Clan;
import nyanclans.core.clan.Rank;
import nyanclans.storage.Storagable;

/** @author NyanGuyMF */
public class ClanPlayer implements Storagable {
    private static Dao<ClanPlayer, String> dao;

    @DatabaseField(id=true, canBeNull=false, columnName="player_name", unique=true)
    private String name = "";

    @DatabaseField(foreign=true, foreignAutoRefresh=true)
    private Clan clan;

    @DatabaseField(foreign=true, foreignAutoRefresh=true)
    private Rank rank;

    @DatabaseField(dataType=DataType.DATE_STRING)
    private Date clanJoin;

    @DatabaseField(dataType=DataType.DATE_STRING)
    private Date firstServerJoin;

    @DatabaseField(dataType=DataType.DATE_STRING)
    private Date lastServerJoin;

    protected ClanPlayer() {}

    public ClanPlayer(final Player bukkitPlayer) {
        setName(bukkitPlayer.getName());
    }

    public ClanPlayer(final String playerName) {
        setName(playerName);
    }

    public static void initDao(final Dao<ClanPlayer, String> dao) {
        if (ClanPlayer.dao != null) {
            ClanPlayer.dao = dao;
        }
    }

    public static boolean exists(final String player) {
        try {
            return ClanPlayer.dao.idExists(player);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean create() {
        try {
            ClanPlayer.dao.create(this);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean save() {
        try {
            ClanPlayer.dao.update(this);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean reload() {
        try {
            ClanPlayer.dao.refresh(this);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean delete() {
        try {
            ClanPlayer.dao.delete(this);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    /** Gets name */
    public String getName() {
        return name;
    }

    /** Sets name */
    public void setName(final String name) {
        this.name = name;
    }

    /** Gets clan */
    public Clan getClan() {
        return clan;
    }

    /** Sets clan */
    public void setClan(final Clan clan) {
        this.clan = clan;
    }

    /** Gets rank */
    public Rank getRank() {
        return rank;
    }

    /** Sets rank */
    public void setRank(final Rank rank) {
        this.rank = rank;
    }

    /** Gets clanJoin */
    public Date getClanJoin() {
        return clanJoin;
    }

    /** Sets clanJoin */
    public void setClanJoin(final Date clanJoin) {
        this.clanJoin = clanJoin;
    }

    /** Gets firstServerJoin */
    public Date getFirstServerJoin() {
        return firstServerJoin;
    }

    /** Sets firstServerJoin */
    public void setFirstServerJoin(final Date firstServerJoin) {
        this.firstServerJoin = firstServerJoin;
    }

    /** Gets lastServerJoin */
    public Date getLastServerJoin() {
        return lastServerJoin;
    }

    /** Sets lastServerJoin */
    public void setLastServerJoin(final Date lastServerJoin) {
        this.lastServerJoin = lastServerJoin;
    }
}

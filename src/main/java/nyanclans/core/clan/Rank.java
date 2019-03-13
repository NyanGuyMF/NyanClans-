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
package nyanclans.core.clan;

import java.sql.SQLException;

import javax.persistence.Id;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import nyanclans.core.Storagable;

/*
 * TODO: RankBuilder abstract class, ModerRankBuilder,
 *    PlayerRankBuilder, LeaderRankBuilder and CustomRankBuilder
 *    classes with Builder pattern and RankDirector class.
 */

/** @author NyanGuyMF */
@DatabaseTable(tableName="ranks")
public final class Rank implements Storagable {
    private static Dao<Rank, Integer> dao;

    @Id private long id;

    @DatabaseField(foreign=true, foreignAutoRefresh=true)
    private Clan clan;

    @DatabaseField(canBeNull=false)
    private String name;

    @DatabaseField(canBeNull=false)
    private String alias;

    public static void initDao(final Dao<Rank, Integer> dao) {
        if (Rank.dao != null) {
            Rank.dao = dao;
        }
    }

    @Override
    public boolean save() {
        try {
            Rank.dao.update(this);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean reload() {
        try {
            Rank.dao.refresh(this);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean delete() {
        try {
            Rank.dao.delete(this);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    /** Gets id */
    public long getId() {
        return id;
    }

    /** Sets id */
    public void setId(final long id) {
        this.id = id;
    }

    /** Gets clan */
    public Clan getClan() {
        return clan;
    }

    /** Sets clan */
    public void setClan(final Clan clan) {
        this.clan = clan;
    }

    /** Gets name */
    public String getName() {
        return name;
    }

    /** Sets name */
    public void setName(final String name) {
        this.name = name;
    }

    /** Gets alias */
    public String getAlias() {
        return alias;
    }

    /** Sets alias */
    public void setAlias(final String alias) {
        this.alias = alias;
    }
}

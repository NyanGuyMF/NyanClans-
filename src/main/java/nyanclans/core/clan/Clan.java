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
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import nyanclans.core.Storagable;
import nyanclans.core.player.ClanPlayer;

/** @author NyanGuyMF */
@DatabaseTable(tableName = "clans")
public final class Clan implements Storagable {
    private static Dao<Clan, Integer> dao;
    @Id protected long id;

    @Column(unique=true)
    protected String name = "";

    @DatabaseField(foreign=true, foreignAutoRefresh=true)
    protected ClanPlayer leader;

    @Column private double balance = 0.0;

    @ForeignCollectionField
    protected List<ClanPlayer> members;

    public static void initDao(final Dao<Clan, Integer> dao) {
        if (Clan.dao != null) {
            Clan.dao = dao;
        }
    }

    @Override
    public boolean save() {
        try {
            Clan.dao.update(this);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean reload() {
        try {
            Clan.dao.refresh(this);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean delete() {
        try {
            Clan.dao.delete(this);
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

    /** Gets name */
    public String getName() {
        return name;
    }

    /** Sets name */
    public void setName(final String name) {
        this.name = name;
    }

    /** Gets leader */
    public ClanPlayer getLeader() {
        return leader;
    }

    /** Sets leader */
    public void setLeader(final ClanPlayer leader) {
        this.leader = leader;
    }

    /** Gets balance */
    public double getBalance() {
        return balance;
    }

    /** Sets balance */
    public void setBalance(final double balance) {
        this.balance = balance;
    }
}

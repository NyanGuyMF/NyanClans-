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

import static java.util.Objects.hash;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;

import javax.persistence.Column;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import nyanclans.core.player.ClanPlayer;
import nyanclans.storage.Storagable;

/** @author NyanGuyMF */
@DatabaseTable(tableName = "clans")
public final class Clan implements Storagable {
    private static Dao<Clan, String> dao;

    @DatabaseField(id=true, unique=true)
    protected String name = "";

    @DatabaseField(foreign=true, foreignAutoRefresh=true)
    protected ClanPlayer leader;

    @Column private double balance = 0.0;

    @ForeignCollectionField
    private Collection<ClanPlayer> members = new HashSet<>();

    @DatabaseField(columnName="created_at", dataType=DataType.DATE_STRING)
    private Date createdAt;

    public static void initDao(final Dao<Clan, String> dao) {
        if (Clan.dao != null) {
            Clan.dao = dao;
        }
    }

    public static boolean exists(final String clan) {
        try {
            return Clan.dao.idExists(clan);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override public boolean create() {
        try {
            Clan.dao.create(this);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    @Override public boolean save() {
        try {
            Clan.dao.update(this);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override public boolean reload() {
        try {
            Clan.dao.refresh(this);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    @Override public boolean delete() {
        try {
            Clan.dao.delete(this);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    @Override public int hashCode() {
        return hash(name);
    }

    @Override public boolean equals(final Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (!(obj instanceof Clan))
            return false;

        Clan other = (Clan) obj;
        return Objects.equals(name, other.name);
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

    /** Gets members */
    public Collection<ClanPlayer> getMembers() {
        return members;
    }

    /** Sets members */
    public void setMembers(final Collection<ClanPlayer> members) {
        this.members = members;
    }

    /** @return the createdAt */
    public Date getCreatedAt() {
        return createdAt;
    }

    /** Sets createdAt */
    public void setCreatedAt(final Date createdAt) {
        this.createdAt = createdAt;
    }
}

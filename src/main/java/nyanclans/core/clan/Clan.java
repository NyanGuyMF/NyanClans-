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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import nyanclans.core.player.ClanPlayer;
import nyanclans.core.rank.Rank;
import nyanclans.storage.Storagable;
import nyanclans.storage.yaml.messages.MessagesManager;

/** @author NyanGuyMF */
@DatabaseTable(tableName = "clans")
public final class Clan implements Storagable {
    private static Dao<Clan, String> dao;

    @DatabaseField(id=true, unique=true)
    protected String name = "";

    @DatabaseField(foreign=true, foreignAutoRefresh=true)
    protected ClanPlayer leader;

    @Column private double balance = 0.0;

    @Column private double rating = 0.0;

    @ForeignCollectionField
    private Collection<ClanPlayer> members = new HashSet<>();

    @ForeignCollectionField
    private Collection<Rank> ranks = new HashSet<>();

    @DatabaseField(columnName="created_at", dataType=DataType.DATE_STRING)
    private Date createdAt;

    public Clan() {}

    public Clan(final String name, final ClanPlayer leader) {
        this.leader = leader;
        this.name   = name;
        createdAt   = new Date();
    }

    public static void initDao(final Dao<Clan, String> dao) {
        if (Clan.dao == null) {
            Clan.dao = dao;
        }
    }

    public static boolean isClanExists(final String clan) {
        try {
            return Clan.dao.idExists(clan);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Converts members list into online players.
     * <p>
     * Returns empty list if there are no online member
     * in clan.
     *
     * @return List of clan online members.
     */
    public List<Player> getOnlineMembers() {
        List<Player> online = new ArrayList<>();

        if (Bukkit.getOnlinePlayers().size() == 0)
            return online;

        for (ClanPlayer member : getMembers()) {
            @SuppressWarnings("deprecation")
            Player player = Bukkit.getPlayer(member.getName());

            if ((player != null) && (player.isOnline())) {
                online.add(player);
            }
        }

        return online;
    }

    /**
     * Gets clan's rank by given alias.
     * <p>
     * If rank with given alias doesn't exists it
     * will return <tt>null</tt>.
     *
     * @param   alias   Rank's alias.
     * @return {@link Rank} instance or <tt>null</tt>.
     */
    public Rank getRankByAlias(final String alias) {
        for (Rank rank : ranks)
            if (rank.getAlias().equals(alias))
                return rank;

        return null;
    }

    /**
     * Adds given {@link Rank} instance to clan.
     *
     * @param   rank    {@link Rank} instance to add.
     * @return <tt>true</tt> if rank added successfully.
     */
    public boolean addRank(final Rank rank) {
        return getRanks().add(rank);
    }

    /**
     * Adds new member into clan.
     *
     * @param   newMember   New member to add.
     * @return <tt>true</tt> if player added successfully.
     */
    public boolean addMember(final ClanPlayer newMember) {
        return getMembers().add(newMember);
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

    /** Gets name and translates colors. */
    public String getColoredName() {
        return MessagesManager.colored(getName());
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

    /** Increases current rating for given amount. */
    public void increaseRating(final double amount) {
        rating += amount;
    }

    /** @return the rating */
    public double getRating() {
        return rating;
    }

    /** Sets rating */
    public void setRating(final double rating) {
        this.rating = rating;
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

    /** @return the ranks */
    public Collection<Rank> getRanks() {
        return ranks;
    }

    /** Sets ranks */
    public void setRanks(final Collection<Rank> ranks) {
        this.ranks = ranks;
    }
}

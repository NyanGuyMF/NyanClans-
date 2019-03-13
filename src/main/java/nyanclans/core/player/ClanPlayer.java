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

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;

import nyanclans.core.Storagable;
import nyanclans.core.clan.Clan;
import nyanclans.core.clan.Rank;

/** @author NyanGuyMF */
public class ClanPlayer implements Storagable {
    private static Dao<ClanPlayer, String> dao;

    @DatabaseField(id=true, canBeNull=false, columnName="player_name", unique=true)
    protected final String name = "";

    @DatabaseField(foreign=true, foreignAutoRefresh=true)
    protected Clan clan;

    @DatabaseField(foreign=true, foreignAutoRefresh=true)
    protected Rank rank;

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
}

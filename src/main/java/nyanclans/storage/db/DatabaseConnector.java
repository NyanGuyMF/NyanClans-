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
package nyanclans.storage.db;

import static com.j256.ormlite.dao.DaoManager.createDao;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import nyanclans.core.clan.Clan;
import nyanclans.core.clan.Rank;
import nyanclans.core.player.ClanPlayer;
import nyanclans.storage.yaml.DatabaseConfig;

/** @author NyanGuyMF */
public final class DatabaseConnector {
    public enum ConnectionStatus { SUCCESS, INVALID_DRIVER, CONNECTION_ERROR }
    private final DatabaseConfig config;
    private final File pluginFolder;
    private ConnectionStatus state;
    private ConnectionSource conn;

    /**
     * Creates new {@link DatabaseConnector} and initializes
     * configuration file in given plug-in folder.
     *
     * @param   pluginFolder    Plugin's folder provided by
     * Bukkit, default is {serverDir}/plugins/{pluginName}
     * @throws IOException if couldn't create configuration file.
     */
    public DatabaseConnector(final File pluginFolder) throws IOException {
        this.pluginFolder     = pluginFolder;
        final File configFile = new File(pluginFolder, "database.yml");

        if (!configFile.exists()) {
            configFile.createNewFile();
        }

        config = new DatabaseConfig(configFile);
        config.loadAndSave();
    }

    /**
     * Initializes {@link Dao}s for {@link ClanPlayer},
     * {@link Clan} and {@link Rank} classes.
     *
     * @return <tt>true</tt> if all {@link Dao}s was
     * initialized successfully.
     */
    public boolean initDaos() {
        boolean isAllConnected = true;

        try {
            ClanPlayer.initDao(createDao(conn, ClanPlayer.class));
        } catch (SQLException ex) {
            ex.printStackTrace();
            if (isAllConnected) {
                isAllConnected = false;
            }
        }

        try {
            Clan.initDao(createDao(conn, Clan.class));
        } catch (SQLException ex) {
            ex.printStackTrace();
            if (isAllConnected) {
                isAllConnected = false;
            }
        }

        try {
            Rank.initDao(createDao(conn, Rank.class));
        } catch (SQLException ex) {
            ex.printStackTrace();
            if (isAllConnected) {
                isAllConnected = false;
            }
        }

        return isAllConnected;
    }

    /**
     * Connects to database with driver from configuration
     * ({@link #config}).
     *
     * @return {@link ConnectionStatus#SUCCESS} if connected
     * successfully, {@link ConnectionStatus#INVALID_DRIVER}
     * if driver from configuration not supported and
     * the {@link ConnectionStatus#CONNECTION_ERROR} if
     * some {@link SQLException} has been thrown.
     *
     * @see {@link #connectH2()} - connector for H2 drivers.
     */
    public ConnectionStatus connect() {
        // TODO: add other database drivers and test it all
        switch (config.getDatabaseDriver().toLowerCase()) {
        case "h2":
            return connectH2();

        default:
            setState(ConnectionStatus.INVALID_DRIVER);
            return getState();
        }
    }

    // TODO: test tables

    /**
     * Connects to local database. Will create new db
     * file if it wasn't exists yet.
     *
     * @return <tt>{@link ConnectionStatus#SUCCESS}</tt>
     * if connected successfully and {@link ConnectionStatus#CONNECTION_ERROR}
     * if some {@link SQLException} has been thrown.
     */
    private ConnectionStatus connectH2() {
        try {
            conn = new JdbcConnectionSource(
                "jdbc:h2:"
                + pluginFolder.getAbsolutePath()
                + File.pathSeparatorChar
            );
        } catch (SQLException ex) {
            ex.printStackTrace();
            setState(ConnectionStatus.CONNECTION_ERROR);
            return getState();
        }

        setState(ConnectionStatus.SUCCESS);
        return getState();
    }

    /**
     * Disconnecting from database connection.
     * <p>
     * Connection may throw {@link IOException}, in
     * this case method will return <tt>false</tt>.
     *
     * @return <tt>true</tt> if closed successfully or
     * was already closed.
     */
    public boolean disconnect() {
        try {
            conn.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    /** Gets state */
    public ConnectionStatus getState() {
        return state;
    }

    /** Sets state */
    private void setState(final ConnectionStatus state) {
        this.state = state;
    }
}
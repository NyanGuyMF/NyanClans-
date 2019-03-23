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
package nyanclans.storage.yaml.db;

import static com.j256.ormlite.dao.DaoManager.createDao;
import static com.j256.ormlite.table.TableUtils.createTable;

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
import nyanclans.utils.dependency.DatabaseDriver;
import nyanclans.utils.dependency.DependencyManager;

/** @author NyanGuyMF */
public final class DatabaseConnector {
    public enum ConnectionStatus { SUCCESS, INVALID_DRIVER, CONNECTION_ERROR }
    private final DatabaseConfig config;
    private final File pluginFolder;
    private final DependencyManager dependencyManager;
    private Dao<ClanPlayer, String> playerDao;
    private Dao<Clan, String> clanDao;
    private Dao<Rank, Integer> rankDao;
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
    public DatabaseConnector(
        final File pluginFolder, final DependencyManager dependencyManager
    ) throws IOException {
        this.pluginFolder      = pluginFolder;
        this.dependencyManager = dependencyManager;
        final File configFile  = new File(pluginFolder, "database.yml");

        if (!configFile.exists()) {
            configFile.createNewFile();
            config = new DatabaseConfig(pluginFolder);
            config.save();
        } else {
            config = new DatabaseConfig(pluginFolder);
        }

        config.load();
    }

    /**
     * Loads database driver as dependency from configuration.
     *
     * @return <tt>true</tt> if loaded successfully.
     */
    public boolean loadDriver() {
        DatabaseDriver secureDriver = dependencyManager.getSecureDriver(config.getDatabaseDriver());
        boolean isLoaded = false;

        if (!dependencyManager.isDriverFileExists(config.getDatabaseDriver())) {
            if (!downloadDriver(secureDriver))
                return false;
        }

        System.out.println("Loading database driver.");

        if (secureDriver != null) {
            if (dependencyManager.loadDriver(secureDriver)) {
                System.out.println("Database driver loaded.");
                isLoaded = true;
            } else {
                System.err.println("Couldn't load database driver.");
            }
        } else {
            System.out.println("Loading unsecure database driver: " + config.getDatabaseDriver());

            if (dependencyManager.loadCustomDriver(config.getDatabaseDriver())) {
                System.out.println("Database driver loaded.");
                isLoaded = true;
            } else {
                System.err.println("Couldn't load database driver.");
            }
        }

        return isLoaded;
    }

    private boolean downloadDriver(final DatabaseDriver driver) {
        if (driver == null)
            return false;

        System.out.println("Downloading database driver...");
        boolean isDownloaded = false;

        switch (dependencyManager.downloadDriver(driver)) {
        case SUCCESS:
            System.out.println("Database driver downloaded successfully.");
            isDownloaded = true;
            break;
        case HASH_ERROR:
            System.out.println("Downloaded database driver has invalid hash code.");
            break;
        case CONNECTION_ERROR:
            System.out.println("Couldn't connect to server with driver.");
            break;
        case DOWNLOAD_ERROR:
            System.out.println("Couldn't download database driver.");
            break;

        default:
            System.out.println("Unhandled error while downloading database driver.");
            break;
        }

        return isDownloaded;
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
            setPlayerDao(createDao(conn, ClanPlayer.class));
            ClanPlayer.initDao(getPlayerDao());
        } catch (SQLException ex) {
            ex.printStackTrace();
            if (isAllConnected) {
                isAllConnected = false;
            }
        }

        try {
            setClanDao(createDao(conn, Clan.class));
            Clan.initDao(getClanDao());
        } catch (SQLException ex) {
            ex.printStackTrace();
            if (isAllConnected) {
                isAllConnected = false;
            }
        }

        try {
            setRankDao(createDao(conn, Rank.class));
            Rank.initDao(getRankDao());
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
                + File.separatorChar
                + config.getDatabase()
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
     * Creating database tables for {@link ClanPlayer},
     * {@link Clan} and {@link Rank} classes.
     * <p>
     * If tables are already exists it will not
     * drop and create, just ignore (create if not exists query).
     *
     * @return <tt>true</tt> if all tables was created successfully.
     */
    public boolean createTables() {
        boolean isAllConnected = true;

        try {
            if (!getPlayerDao().isTableExists()) {
                createTable(conn, ClanPlayer.class);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            if (isAllConnected) {
                isAllConnected = false;
            }
        }

        try {
            if (!getClanDao().isTableExists()) {
                createTable(conn, Clan.class);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            if (isAllConnected) {
                isAllConnected = false;
            }
        }

        try {
            if (!getRankDao().isTableExists()) {
                createTable(conn, Rank.class);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            if (isAllConnected) {
                isAllConnected = false;
            }
        }

        return isAllConnected;
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
            if (conn != null) {
                conn.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    /** @return the config */
    public DatabaseConfig getConfig() {
        return config;
    }

    public boolean isConnected() {
        return conn != null;
    }

    /** Gets current connection source. */
    public ConnectionSource getConnection() {
        return conn;
    }

    /** Gets state */
    public ConnectionStatus getState() {
        return state;
    }

    /** Sets state */
    private void setState(final ConnectionStatus state) {
        this.state = state;
    }

    /** Gets playerDao */
    public Dao<ClanPlayer, String> getPlayerDao() {
        return playerDao;
    }

    /** Sets playerDao */
    public void setPlayerDao(final Dao<ClanPlayer, String> playerDao) {
        this.playerDao = playerDao;
    }

    /** Gets clanDao */
    public Dao<Clan, String> getClanDao() {
        return clanDao;
    }

    /** Sets clanDao */
    public void setClanDao(final Dao<Clan, String> clanDao) {
        this.clanDao = clanDao;
    }

    /** Gets rankDao */
    public Dao<Rank, Integer> getRankDao() {
        return rankDao;
    }

    /** Sets rankDao */
    public void setRankDao(final Dao<Rank, Integer> rankDao) {
        this.rankDao = rankDao;
    }
}

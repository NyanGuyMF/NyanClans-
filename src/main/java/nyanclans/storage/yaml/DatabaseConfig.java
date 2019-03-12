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

import java.io.File;
import java.io.FileNotFoundException;

import org.bukkit.plugin.java.JavaPlugin;

import de.exlll.configlib.annotation.Comment;
import de.exlll.configlib.configs.yaml.BukkitYamlConfiguration;

/** @author nyanguymf */
public final class DatabaseConfig extends BukkitYamlConfiguration {
    @Comment("Not tested on other drivers, it's only local db driver")
    private String databaseDriver = "h2";

    @Comment("Your database with tables (or without)")
    private String database = "nyanclans";

    @Comment("Database user, never use 'root' user:")
    private String user = "root";

    @Comment("Database user's password, yep - 'root' is very strong password:")
    private String password = "root";

    /**
     * Initialize database configuration.
     * <p>
     * Make sure you created database.yml configuration file
     * before calling this constructor, {@link FileNotFoundException}
     * will be thrown if database.yml file doesn't exists in given folder.
     *
     * @param   pluginFolder            {@link JavaPlugin#getDataFolder()}
     * @throws  FileNotFoundException   if there is no database.yml file.
     */
    public DatabaseConfig(final File pluginFolder) {
        super(new File(pluginFolder, "database.yml").toPath());
    }

    /** Gets databaseDriver */
    public String getDatabaseDriver() {
        return databaseDriver;
    }

    /** Sets databaseDriver */
    public void setDatabaseDriver(final String databaseDriver) {
        this.databaseDriver = databaseDriver;
    }

    /** Gets database */
    public String getDatabase() {
        return database;
    }

    /** Sets database */
    public void setDatabase(final String database) {
        this.database = database;
    }

    /** Gets user */
    public String getUser() {
        return user;
    }

    /** Sets user */
    public void setUser(final String user) {
        this.user = user;
    }

    /** Gets password */
    public String getPassword() {
        return password;
    }

    /** Sets password */
    public void setPassword(final String password) {
        this.password = password;
    }
}

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
package nyanclans.core;

import java.io.File;
import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin;

import nyanclans.storage.db.DatabaseConnector;

/** @author NyanGuyMF */
public final class NyanClansPlugin extends JavaPlugin {
    private DatabaseConnector databaseConnector;

    public NyanClansPlugin() {    }

    @Override public void onLoad() {
        /*
         * TODO:
         *   init & load yaml configurations;
         *   connect to database and init daos;
         */
        if (!super.getDataFolder().exists()) {
            super.getDataFolder().mkdir();
        }

        databaseConnector = databaseConnect(super.getDataFolder());
        databaseConnector.createTables();
        databaseConnector.initDaos();
    }

    @Override public void onEnable() {
        /*
         * TODO:
         *   connect to Vault;
         *   connect to ProtocolLib;
         */
    }

    @Override public void onDisable() {
        /*
         * TODO:
         *   save configurations;
         *   clear caches;
         */
        if (databaseConnector.disconnect()) {
            super.getLogger().info("Disconnected from database.");
        } else {
            super.getLogger().info("Couldn't disconnect from database.");
        }
    }

    /** @see {@link DatabaseConnector#DatabaseConnector(File)} */
    private DatabaseConnector databaseConnect(final File pluginFolder) {
        try {
            DatabaseConnector databaseConnector = new DatabaseConnector(pluginFolder);

            switch (databaseConnector.connect()) {
            case SUCCESS:
                super.getLogger().info("Connected to database.");
                return databaseConnector;

            case CONNECTION_ERROR:
            case INVALID_DRIVER:
            default:
                System.out.println("Error while connecting to database");
                return null;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}

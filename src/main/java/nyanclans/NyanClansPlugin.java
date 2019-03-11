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
package nyanclans;

import org.bukkit.plugin.java.JavaPlugin;

/** @author nyanguymf */
public final class NyanClansPlugin extends JavaPlugin {
    @Override public void onLoad() {
        /*
         * TODO:
         *   init & load yaml configurations;
         *   connect to database;
         */
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
         *   break connection with database;
         *   save configurations;
         *   clear caches;
         */
    }
}

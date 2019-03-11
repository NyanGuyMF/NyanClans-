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

/**
 * Represents the object which can be saved, loaded and deleted.
 *
 * @author nyanguymf
 */
public interface Storagable {
    /**
     * Saves object to storage.
     *
     * @return <tt>true</tt> if saved successfully and
     * <tt>false</tt> if not.
     */
    boolean save();

    /**
     * Loads object from storage.
     * <p>
     * Should be used after creating new instance of object.
     *
     * @return <tt>true</tt> if loaded successfully and
     * <tt>false</tt> if not.
     */
    boolean load();

    /**
     * Reloading object from storage.
     * <p>
     * Should be used if you want get latest version of object.
     *
     * @return <tt>true</tt> if reloaded successfully and
     * <tt>false</tt> if not.
     */
    boolean reload();

    /**
     * Deletes object from storage.
     * <p>
     * Be careful while using it, usually it's undone.
     *
     * @return <tt>true</tt> if deleted successfully and
     * <tt>false</tt> if not.
     */
    boolean delete();
}

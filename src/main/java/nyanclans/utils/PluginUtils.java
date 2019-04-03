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
package nyanclans.utils;

import static org.bukkit.Bukkit.getScheduler;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

/**
 * Provides utilities for methods of {@link JavaPlugin}
 * without having Singleton main class and private instance
 * of it in any classes.
 * <p>
 * Something like Proxy or Delegate pattern.
 *
 * @author NyanGuyMF
 */
public final class PluginUtils {
    private static JavaPlugin plugin;
    private static boolean isInitialized = false;

    public static void init(final JavaPlugin plugin) {
        PluginUtils.plugin = plugin;
        setInitialized(true);
    }

    /** @see BukkitScheduler#runTask(org.bukkit.plugin.Plugin, Runnable). */
    public static BukkitTask runTask(final Runnable task) {
        return getScheduler().runTask(PluginUtils.plugin, task);
    }

    /** @see BukkitScheduler#runTaskAsynchronously(org.bukkit.plugin.Plugin, Runnable). */
    public static BukkitTask runTaskAsync(final Runnable task) {
        return getScheduler().runTaskAsynchronously(PluginUtils.plugin, task);
    }

    /**
     * Runs given runnable after given delay in seconds.
     *
     * @param   task    The task to schedule.
     * @param   delay   Delay before running in seconds.
     * @return Scheduled bukkit task.
     */
    public static BukkitTask runTaskLater(final Runnable task, final int delay) {
        return getScheduler().runTaskLater(PluginUtils.plugin, task, delay * 20);
    }

    /** Gets isInitialized */
    public static boolean isInitialized() {
        return PluginUtils.isInitialized;
    }

    /** Sets isInitialized */
    private static void setInitialized(final boolean isInitialized) {
        PluginUtils.isInitialized = isInitialized;
    }
}

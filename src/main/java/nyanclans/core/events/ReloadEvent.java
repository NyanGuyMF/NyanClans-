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
package nyanclans.core.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import nyanclans.core.NyanClansPlugin;
import nyanclans.storage.yaml.PluginConfiguration;
import nyanclans.storage.yaml.messages.MessagesManager;
import nyanclans.utils.Observable;
import nyanclans.utils.Observer;

/** @author NyanGuyMF - Vasiliy Bely */
public final class ReloadEvent implements Observable<ReloadEvent> {
    private static List<Observer<ReloadEvent>> observers;
    private static boolean isUpdated = false;
    private static NyanClansPlugin plugin;

    protected static void init(final NyanClansPlugin plugin) {
        ReloadEvent.observers = new ArrayList<>();

        if (plugin != null) {
            ReloadEvent.plugin = plugin;
        }
    }

    @Override public void addObserver(final Observer<ReloadEvent> obs) {
        ReloadEvent.observers.add(obs);
    }

    @Override public void removeObserver(final Observer<ReloadEvent> obs) {
        ReloadEvent.observers.remove(obs);
    }

    @Override public void notifyObservers() {
        if (ReloadEvent.isUpdated) {
            ReloadEvent.observers.parallelStream().forEach(obs -> obs.update(this));
            ReloadEvent.isUpdated = false;
        }
    }

    /**
     * Updates event data.
     * <p>
     * Will not update data if given plugin is <tt>null</tt>
     * or it's already updated.
     *
     * @param   plugin  The main class of plugin.
     * @return <tt>true</tt> if plugin wasn't updated yet.
     */
    public boolean update(final NyanClansPlugin plugin) {
        if (ReloadEvent.isUpdated)
            return false;

        if (plugin == null)
            return false;

        ReloadEvent.plugin = plugin;
        ReloadEvent.isUpdated = true;

        return ReloadEvent.isUpdated;
    }

    public JavaPlugin getPlugin() {
        return ReloadEvent.plugin;
    }

    public PluginConfiguration getConfig() {
        return ReloadEvent.plugin.getConfiguration();
    }

    public MessagesManager getMessages() {
        return ReloadEvent.plugin.getMessagesConfig();
    }
}

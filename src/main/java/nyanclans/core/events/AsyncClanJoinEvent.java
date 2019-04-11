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

import nyanclans.core.clan.Clan;
import nyanclans.core.player.ClanPlayer;
import nyanclans.storage.yaml.clan.RatingConfig;
import nyanclans.utils.Observable;
import nyanclans.utils.Observer;
import nyanclans.utils.PluginUtils;

/** @author NyanGuyMF - Vasiliy Bely */
public final class AsyncClanJoinEvent
    extends ClanEvent
    implements Observable<AsyncClanJoinEvent> {

    private static List<Observer<AsyncClanJoinEvent>> observers;
    private static DefaultHandler<AsyncClanJoinEvent> defaultHandler;

    private volatile String message;
    private volatile ClanPlayer player;
    private volatile boolean isHandled;

    /**
     * Sets default handler if it wasn't set yet.
     * <p>
     * Returns <tt>false</tt> if handler was already set.
     *
     * @return <tt>true</tt> only if default handler wasn't set yet.
     */
    protected static boolean init(final RatingConfig ratingConfig) {
        if (AsyncClanJoinEvent.defaultHandler != null)
            return false;

        AsyncClanJoinEvent.observers = new ArrayList<>();
//        AsyncClanJoinEvent.defaultHandler = new ClanChatMessageHandler(clanChatConfig);
        return true;
    }

    public AsyncClanJoinEvent(final Clan clan) {
        super(clan, true);
    }

    @Override public void addObserver(final Observer<AsyncClanJoinEvent> obs) {
        AsyncClanJoinEvent.observers.add(obs);
    }

    @Override public void removeObserver(final Observer<AsyncClanJoinEvent> obs) {
        AsyncClanJoinEvent.observers.remove(obs);
    }

    @Override public void notifyObservers() {
        if (isHandled())
            return;

        // maybe I should run every handlers in separated tasks
        // but I think it would be quite problematic to handle
        PluginUtils.runTaskAsync(() -> AsyncClanJoinEvent.observers.forEach(obs -> {
            obs.update(this);
        }));
        AsyncClanJoinEvent.defaultHandler.handle(this);

        setHandled(true);
    }

    /** @return the message */
    public synchronized String getMessage() {
        return message;
    }

    /** @return the player */
    public synchronized ClanPlayer getPlayer() {
        return player;
    }

    /** @return the isHandled */
    public synchronized boolean isHandled() {
        return isHandled;
    }

    /** Sets isHandled */
    public synchronized void setHandled(final boolean isHandled) {
        this.isHandled = isHandled;
    }
}

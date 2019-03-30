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
import nyanclans.utils.Observable;
import nyanclans.utils.Observer;
import nyanclans.utils.PluginUtils;

/** @author NyanGuyMF - Vasiliy Bely */
public final class AsyncClanChatMessage
    extends ClanEvent
    implements Observable<AsyncClanChatMessage> {

    private List<Observer<AsyncClanChatMessage>> observers;
    private DefaultHandler<AsyncClanChatMessage> defaultHandler;
    private String message;
    private ClanPlayer player;

    public AsyncClanChatMessage(
        final Clan clan, final String message,
        final ClanPlayer sender
    ) {
        super(clan, true);

        observers = new ArrayList<>();
    }

    @Override public void addObserver(final Observer<AsyncClanChatMessage> obs) {
        observers.add(obs);
    }

    @Override public void removeObserver(final Observer<AsyncClanChatMessage> obs) {
        observers.remove(obs);
    }

    @Override public void notifyObservers() {
        // maybe I should run every handlers in separated tasks
        // but I think it would be quite problematic to handle
        PluginUtils.runTaskAsync(() -> observers.forEach(obs -> {
            obs.update(this);
            defaultHandler.handle(this);
        }));
    }

    /**
     * Sets default handler if it wasn't set yet.
     * <p>
     * Returns <tt>false</tt> if handler was already set.
     *
     * @param   handler     {@link DefaultHandler} implementation
     *      to handle this event.
     * @return <tt>true</tt> only if default handler wasn't set yet.
     */
    public boolean setDefaultHandler(final DefaultHandler<AsyncClanChatMessage> handler) {
        if (defaultHandler != null)
            return false;

        defaultHandler = handler;
        return true;
    }

    /** @return the message */
    public String getMessage() {
        return message;
    }

    /** Sets message */
    public void setMessage(final String message) {
        this.message = message;
    }

    /** @return the player */
    public ClanPlayer getPlayer() {
        return player;
    }

    /** Sets player */
    public void setPlayer(final ClanPlayer player) {
        this.player = player;
    }
}

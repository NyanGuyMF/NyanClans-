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

import nyanclans.core.NyanClansPlugin;
import nyanclans.core.clan.Clan;

/**
 * Represents clan related event.
 *
 * @author NyanGuyMF - Vasiliy Bely
 */
public abstract class ClanEvent {
    private Clan clan;
    private boolean isAsync;

    public static void initEvents(final NyanClansPlugin plugin) {
        if (plugin == null)
            return;

        AsyncClanChatMessageEvent.init(plugin.getConfiguration().getClans().getChat());
        AsyncClanJoinEvent.init(plugin.getConfiguration().getClans().getRating());
        ReloadEvent.init(plugin);
    }

    public ClanEvent(final Clan clan) {
        setClan(clan);
    }

    /**
     * @param isAsync true indicates the event will fire asynchronously, false
     *     by default from default constructor
     */
    public ClanEvent(final Clan clan, final boolean isAsync) {
        setClan(clan);
        setAsync(isAsync);
    }

    /**
     * Returns {@link Clan} that was involved in this event.
     *
     * @return {@link Clan} which is involved in this event.
     */
    public Clan getClan() {
        return clan;
    }

    /** Sets clan. */
    protected void setClan(final Clan clan) {
        this.clan = clan;
    }

    /**
     * @return <tt>false</tt> by default and <tt>true</tt> if
     *      event fires asynchronously (in new thread).
     * @see {@link org.bukkit.event.Event#isAsynchronous()}
     */
    public boolean isAsynchronous() {
        return isAsync;
    }

    /** Sets isAsync. */
    protected void setAsync(final boolean isAsync) {
        this.isAsync = isAsync;
    }
}

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
package nyanclans.storage.cache;

import nyanclans.core.clan.Clan;
import nyanclans.core.player.ClanPlayer;

/**
 * Represents invite to clan from one player to another.
 *
 * @author NyanGuyMF - Vasiliy Bely
 */
public final class Invite {
    private ClanPlayer invitedPlayer;

    private ClanPlayer inviter;

    private Clan clan;

    public Invite(final ClanPlayer invitedPlayer, final ClanPlayer inviter, final Clan clan) {
        this.invitedPlayer = invitedPlayer;
        this.inviter = inviter;
        this.clan = clan;
    }

    /**
     * Gets the clan, to which player was invited
     * by other player.
     *
     * @return Clan, to which player was invited.
     */
    public synchronized Clan getClan() {
        return clan;
    }

    /**
     * Gets the player, who has invited other player
     * to his clan.
     *
     * @return Inviter.
     */
    public synchronized ClanPlayer getInviter() {
        return inviter;
    }

    /**
     * Gets the player, who was invited.
     *
     * @return Invited player.
     */
    public synchronized ClanPlayer getInvited() {
        return invitedPlayer;
    }

    /** Sets clan */
    protected void setClan(final Clan clan) {
        this.clan = clan;
    }

    /** Sets inviter */
    protected void setInviter(final ClanPlayer inviter) {
        this.inviter = inviter;
    }

    /** Sets player */
    protected void setPlayer(final ClanPlayer player) {
        invitedPlayer = player;
    }
}

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
package nyanclans.storage.yaml.messages;

import de.exlll.configlib.annotation.ConfigurationElement;

/** @author nyanguymf */
@ConfigurationElement
public final class DevCommandsUsage {
    private String devCommand = "&eEnter &c/clandev help &efor help";
    private String player = "&e/clandev player &6«&cplayer name&6»";
    private String fresh  = "&e/clandev fresh";

    public DevCommandsUsage() {}

    /** Gets player */
    public String getPlayer() {
        return player;
    }

    /** Gets devCommand */
    public String getDevCommand() {
        return devCommand;
    }

    /** Sets devCommand */
    public void setDevCommand(final String devCommand) {
        this.devCommand = devCommand;
    }

    /** Sets player */
    public void setPlayer(final String player) {
        this.player = player;
    }

    /** Gets fresh */
    public String getFresh() {
        return fresh;
    }

    /** Sets fresh */
    public void setFresh(final String fresh) {
        this.fresh = fresh;
    }
}

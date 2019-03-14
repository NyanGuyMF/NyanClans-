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

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import de.exlll.configlib.annotation.ConfigurationElement;

/** @author NyanGuyMF */
@ConfigurationElement
public final class HelpMessages {
    private Map<String, String> devCommands = ImmutableMap.<String, String>builder()
            .put("player", "&e/clandev player &6«&cplayer name&6» &f- show info about player")
            .build();

    /** Gets devCommands */
    public Map<String, String> getDevCommands() {
        return devCommands;
    }

    /** Sets devCommands */
    public void setDevCommands(Map<String, String> devCommands) {
        this.devCommands = devCommands;
    }
}

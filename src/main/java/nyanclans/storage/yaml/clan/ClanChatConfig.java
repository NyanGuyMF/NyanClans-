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
package nyanclans.storage.yaml.clan;

import static nyanclans.storage.yaml.messages.MessagesManager.colored;

import java.util.ArrayList;
import java.util.List;

import de.exlll.configlib.annotation.Comment;
import de.exlll.configlib.annotation.ConfigurationElement;
import nyanclans.storage.yaml.messages.MessagesManager;

/** @author NyanGuyMF - Vasiliy Bely */
@ConfigurationElement
public final class ClanChatConfig {
    @Comment("Player can type {symbol} + message to send message to clan chat")
    private String clanChatSymbol = "%";

    @Comment("Represents format of clan chat messages")
    private String clanChatFormat = "&6«&e{clan}&6» {rank} {player}: {message}";

    @Comment({
        "Color codes here will be translated in clan chat message",
        "Leave it empty without changig if you want to allow all colors"
    })
    private static List<String> allowedColors;

    public ClanChatConfig() {
        setAllowedColors(new ArrayList<>());
    }

    /**
     * Translates only allowed colors in given {@link String}
     * and returns it.
     * <p>
     * If allowed colors list is empty it will translate
     * all colors.
     *
     * @param   str     String to parse.
     * @return String with translated colors.
     *
     * @see {@link MessagesManager#colored(String)}
     */
    public static String parseAllowedColors(String str) {
        if (ClanChatConfig.allowedColors.isEmpty())
            return colored(str);

        for (final String allowed : ClanChatConfig.allowedColors) {
            str = str.replace(allowed, allowed.replace('&', '\u00a7'));
        }

        return str;
    }

    /** @return the clanChatSymbol */
    public String getClanChatSymbol() {
        return clanChatSymbol;
    }

    /** Sets clanChatSymbol */
    public void setClanChatSymbol(final String clanChatSymbol) {
        this.clanChatSymbol = clanChatSymbol;
    }

    /** @return the clanChatFormat */
    public String getClanChatFormat() {
        return clanChatFormat;
    }

    /** Sets clanChatFormat */
    public void setClanChatFormat(final String clanChatFormat) {
        this.clanChatFormat = clanChatFormat;
    }

    /** @return the allowedColors */
    public List<String> getAllowedColors() {
        return ClanChatConfig.allowedColors;
    }

    /** Sets allowedColors */
    public void setAllowedColors(final List<String> allowedColors) {
        ClanChatConfig.allowedColors = allowedColors;
    }
}

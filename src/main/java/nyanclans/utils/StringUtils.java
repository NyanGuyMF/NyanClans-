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

/**
 * Contains useful utilities to handle {@link String} values.
 *
 * @see #translateColors(String)
 * @see #insertArgs(String, String...)
 *
 * @author NyanGuyMF
 */
public final class StringUtils {
    /**
     * Translates user-friendly colors to default Bukkit colors.
     * <p>
     * Simply replaces all '&' characters in given string with
     * '§' (ua7 in Unicode (u00a8)) character.
     *
     * @param   str     String with colors to translate.
     * @return {@link String} with translated colors.
     */
    public static String translateColors(final String str) {
        return str.replace('&', '\u00a7');
    }

    /**
     * Replaces "{0}", "{1}", "{n}" chunks in given String
     * with appropriate argument (args[0], args[1], args[n]).
     * <p>
     * Example: String <tt>"Hello, {0}! I'm {1} c:"</tt>
     * with arguments {@code {"Notch", "NyanGuyMF"}} will
     * be <tt>"Hello, Notch! I'm NyanGuyMF c:"</tt>.
     *
     * @param   str     String value to insert arguments.
     * @param   args    Values to insert into String.
     * @return String with argument in it.
     */
    public static String insertArgs(String str, final String... args) {
        if (args.length == 0)
            return str;

        for (int c = 0; c < args.length; c++) {
            str = str.replace("{" + c + "}", args[c]);
        }

        return str;
    }
}

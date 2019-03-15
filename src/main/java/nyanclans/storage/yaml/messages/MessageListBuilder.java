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

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.CommandSender;

/** @author nyanguymf */
public final class MessageListBuilder {
    private List<String> message;

    /** Sets new message. */
    public MessageListBuilder message(final Collection<String> message) {
        this.message = message.parallelStream().collect(Collectors.toList());
        return this;
    }

    /**
     * Replaces "{0}", "{1}", "{n}" chunks in given String
     * with appropriate argument (args[0], args[1], args[n]).
     * <p>
     * Example: String <tt>"Hello, {0}! I'm {1} c:"</tt>
     * with arguments {@code {"Notch", "NyanGuyMF"}} will
     * be <tt>"Hello, Notch! I'm NyanGuyMF c:"</tt>.
     *
     * @param   args    Values to insert into String.
     */
    public MessageListBuilder args(final String... args) {
        if (args.length == 0)
            return this;

        return this;
    }

    /** Sends colored message to given {@link CommandSender}. */
    public void send(final CommandSender receiver) {
        build().forEach(receiver::sendMessage);
    }

    public List<String> build() {
        return colored().message;
    }

    /**
     * Translates user-friendly colors to default Bukkit colors.
     * <p>
     * Simply replaces all '&' characters in given string with
     * '§' (ua7 in Unicode (u00a8)) character.
     */
    private MessageListBuilder colored() {
        message = message.parallelStream()
                .map(msg -> msg.replace('&', '\u00a7'))
                .collect(Collectors.toList());
        return this;
    }
}
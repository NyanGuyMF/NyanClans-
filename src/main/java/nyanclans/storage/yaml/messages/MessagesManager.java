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

import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import de.exlll.configlib.configs.yaml.BukkitYamlConfiguration;
import nyanclans.storage.yaml.YamlFieldNameFormater;
import nyanclans.utils.Observable;
import nyanclans.utils.Observer;
import nyanclans.utils.PluginUtils;

/** @author NyanGuyMF */
public class MessagesManager extends BukkitYamlConfiguration implements Observable<MessagesManager> {
    private final List<Observer<MessagesManager>> ignoreObservers = new ArrayList<>();

    /**
     * Provides usage message for each plug-in command
     * in format of<pre>
     *   command-name : {
     *     sub-command : usage-message,
     *     ...
     *   },
     *   ...</pre>
     * <p>
     * Example: {@code help.get("dev").get("player")} will
     * get usage for "player" sub command of "dev".
     */
    private Map<String, Map<String, String>> usage = new HashMap<>();

    /**
     * Provides help message for each plug-in command
     * in format of <pre>
     *   command-name : {
     *     sub-command : usage-message,
     *     ...
     *   },
     *   ...</pre>
     * <p>
     * Example: {@code help.get("dev").get("player")} will
     * get help for "player" sub command of "dev".
     */
    private Map<String, Map<String, String>> help = new HashMap<>();

    private Map<String, String> info = ImmutableMap.<String, String>builder()
            .put("reload-success", "&3NyanClans &8» &aMessage configuration has been reloaded.")
            .put("fresh-success", "&3NyanClans &8» &6«&b{0}&6» &afreshed successfully.")
            .put("clan-created", "&6«&e{0}&6» &eClan created.")
            .put("clan-deleted", "&6«&e{0}&6» &c{1} &ehas deleted clan.")
            .put("player-join-clan", "&6«&e{0}&6» &c{1} has joined to clan by invition of {2}.")
            .build();

    private Map<String, String> error = ImmutableMap.<String,String>builder()
            .put("no-permission", "&cYou have no permission for &6{0} &ccommand.")
            .put("not-clan-member", "&cYou're not clan member.")
            .put("only-player", "&cOnly players can use «&6{0}&c» command.")
            .put("fresh-table", "&cCouldn't fresh table «&6{0}&c».")
            .put("invalid-clan-name", "&cYou've entered invalid clan name - «&6{0}&c».")
            .put("clan-already-exists", "&cClan with name «&6{0}&c» already exists.")
            .put("you-already-in-clan", "&cYou're already in clan.")
            .put("clan-create-error", "&6«&e{0}&6» &eCouldn't create clan.")
            .put("rank-create-error", "&6«&e{0}&6» &eCouldn't create rank &6«&c{1}&6»&e.")
            .put("invited-is-other-member", "&cThe player «&6{0}&c» is already member of other clan!")
            .put("invited-is-your-member", "&cThe player «&6{0}&c» is already member of your clan!")
            .put("no-invites", "&cUnfotrunately, there are no invites for you.")
            .put("no-such-invite", "&cUnfotrunately, there are no invite from «&6{0}&c» clan for you.")
            .put("player-offline", "&cPlayer &c«&6{0}&c» isn't online.")
            .put("player-not-found", "&cPlayer &c«&6{0}&c» not found.")
            .build();

    /**
     * Creates new {@link MessagesManager} instance.
     * <p>
     * Make sure that you already created <tt>messages.yml</tt>
     * file in your plug-in folder before calling it.
     * <p>
     * <b>Important</b>: may cause {@link FileSystemException}
     * if you use {@link #load()} method too often on Unix systems.
     *
     * @param   messagesFile    Files with all plug-in messages.
     * @throws FileNotFoundException if messages file doesn't exists.
     * file in given folder.
     */
    public MessagesManager(final File messagesFile)  {
        super(
            messagesFile.toPath(),
            YamlFieldNameFormater.getProps()
        );

        usage.put(
            "dev",
            ImmutableMap.<String, String>builder()
                .put("clandev", "&eEnter &c/clandev help &efor help")
                .put("player", "&e/clandev player &6«&cplayer name&6»")
                .put("fresh", "&e/clandev fresh &6«&cplayer&6| &ctable&6| &call&6» &6[&cplayer&6| &ctable&6]")
                .build()
        );
        usage.put(
            "clan",
            ImmutableMap.<String, String>builder()
                .put("clan", "&eEnter &c/clan help &efor help")
                .put("create", "&e/clan create &6«&cclan name&6»")
                .put("delete", "&e/clan delete")
                .put("invite", "&e/clan invite &6«&cplayer&c»")
                .put("accept", "&e/clan accept &6[&cclan&6]")
                .put("deny", "&e/clan deny &6[&cclan&6]")
                .build()
        );
        usage.put(
            "clanchat",
            ImmutableMap.<String, String>builder()
                .put("clanchat", "&eEnter &6{0} &6«&cmessage&6» &e or &6/cc &6«&cmessage&6»")
                .build()
        );

        help.put(
            "dev",
            ImmutableMap.<String, String>builder()
                .put("player", "&e/clandev player &6«&cplayer name&6» &f- show info about player")
                .put("reload", "&e/clandev reload &f- reload message configuration")
                .put("help", "&e/clandev help &f- this menu")
                .build()
        );
        help.put(
            "clan",
            ImmutableMap.<String, String>builder()
                .put("create", "&e/clan create &6«&cclan name&6» &f- create new clan")
                .put("delete", "&e/clan delete &f- delete your clan")
                .put("invite", "&e/clan invite &6«&cplayer&c» &f- invite player to your clan")
                .put("accept", "&e/clan accept &6[&cclan&6] &f- accept last or specific clan invite")
                .put("deny", "&e/clan deny &6[&cclan&6] &f- deny last or specific clan invite")
                .build()
        );
    }

    public void saveAndLoad() {
        super.save();
        super.load();
    }

    @Override
    public void addObserver(final Observer<MessagesManager> obs) {
        ignoreObservers.add(obs);
    }

    @Override
    public void removeObserver(final Observer<MessagesManager> obs) {
        ignoreObservers.remove(obs);
    }

    @Override
    public void notifyObservers() {
        ignoreObservers.parallelStream().forEach(obs ->
            PluginUtils.runTaskAsync(() -> obs.update(this))
        );
    }

    /**
     * Gets all help messages for given command.
     * <p>
     * Translates colors in every message by default.
     * <p>
     * Returns <tt>null</tt> if given command doesn't
     * exists.
     *
     * @param   command     Command for which you want get
     * help messages.
     * @return List of help message or <tt>null</tt>.
     */
    public Collection<String> allHelpFor(final String command) {
        return allHelpFor(command, true);
    }

    /**
     * Gets all help messages for given command.
     * <p>
     * Returns <tt>null</tt> if given command doesn't
     * exists.
     *
     * @param   command     Command for which you want get
     * help messages.
     * @param   isColored   If <tt>true</tt> will translate colors
     * in {@link String}.
     * @return List of help message or <tt>null</tt>.
     */
    public Collection<String> allHelpFor(final String command, final boolean isColored) {
        if (!help.containsKey(command))
            return null;

        if (isColored)
            return help.get(command).values().parallelStream()
                    .map(str -> colored(str))
                    .collect(toList());
        else
            return help.get(command).values();
    }

    /**
     * Gets error message from configuration.
     * <p>
     * It translates colors by default.
     * <p>
     * Returns <tt>null</tt> if there aren't message for
     * given key.
     *
     * @param   key     The key of message you want to get.
     * @param   args    Unnecessary array of {@link String} values
     * to insert it into message with {@link #args(String, String...)}.
     * @return Colored message with argument in it or <tt>null</tt>.
     *
     * @see #args(String...)
     * @see #colored(String)
     */
    public String error(final String key, final String...args) {
        if (args.length == 0)
            return error(key, true);
        else
            return colored(args(error(key, false), args));
    }

    /**
     * Gets error message from configuration.
     * <p>
     * Returns <tt>null</tt> if there aren't message for
     * given key.
     *
     * @param   key         The key of message you want to get.
     * @param   isColored   If <tt>true</tt> will translate colors
     * in {@link String}.
     * @return Colored message with argument in it or <tt>null</tt>.
     *
     * @see #colored(String)
     */
    public String error(final String key, final boolean isColored) {
        if (isColored)
            return colored(error.get(key));
        else
            return error.get(key);
    }

    /**
     * Gets information message from configuration.
     * <p>
     * It translates colors by default.
     * <p>
     * Returns <tt>null</tt> if there aren't message for
     * given key.
     *
     * @param   key     The key of message you want to get.
     * @param   args    Unnecessary array of {@link String} values
     * to insert it into message with {@link #args(String, String...)}.
     * @return Colored message with argument in it or <tt>null</tt>.
     *
     * @see #args(String...)
     * @see #colored(String)
     */
    public String info(final String key, final String...args) {
        if (args.length == 0)
            return info(key, true);
        else
            return colored(args(info(key, false), args));
    }

    /**
     * Gets information message from configuration.
     * <p>
     * Returns <tt>null</tt> if there aren't message for
     * given key.
     *
     * @param   key         The key of message you want to get.
     * @param   isColored   If <tt>true</tt> will translate colors
     * in {@link String}.
     * @return Colored message with argument in it or <tt>null</tt>.
     *
     * @see #colored(String)
     */
    public String info(final String key, final boolean isColored) {
        if (isColored)
            return colored(info.get(key));
        else
            return info.get(key);
    }

    /**
     * Gets help message for given command.
     * <p>
     * If usage messages doesn't contains given command
     * or sub command as key it will return <tt>null</tt>.
     * <p>
     * It translates colors by default.
     * <p>
     * Returns <tt>null</tt> if there aren't message for
     * given key.
     *
     * @param   command     Name of command that owns sub command.
     * @param   subCommand  Name of sub command, for which you want
     * to get usage message.
     * @param   args        Array of values to insert into {@link String}
     * with {@link #args(String, String...)} method.
     * @return <tt>null</tt> or message.
     *
     * @see #args(String...)
     * @see #colored(String)
     */
    public String help(final String command, final String subCommand, final String...args) {
        if (args.length == 0)
            return help(command, subCommand, true);
        else
            return colored(args(help(command, subCommand, false), args));
    }

    /**
     * Gets help message for given command.
     * <p>
     * If usage messages doesn't contains given command
     * or sub command as key it will return <tt>null</tt>.
     * <p>
     * Returns <tt>null</tt> if there aren't message for
     * given key.
     *
     * @param   command     Name of command that owns sub command.
     * @param   subCommand  Name of sub command, for which you want
     * to get usage message.
     * @param   isColored   If <tt>true</tt> will translate colors
     * in {@link String}.
     * @return <tt>null</tt> or message.
     */
    public String help(final String command, final String subCommand, final boolean isColored) {
        if (!help.containsKey(command))
            return null;

        if (isColored)
            return colored(help.get(command).get(subCommand));
        else
            return help.get(command).get(subCommand);
    }

    /**
     * Gets usage message for given command.
     * <p>
     * If usage messages doesn't contains given command
     * or sub command as key it will return <tt>null</tt>.
     * <p>
     * It translates colors by default.
     * <p>
     * Returns <tt>null</tt> if there aren't message for
     * given key.
     *
     * @param   command     Name of command that owns sub command.
     * @param   subCommand  Name of sub command, for which you want
     * to get usage message.
     * @param   args        Array of values to insert into {@link String}
     * with {@link #args(String, String...)} method.
     * @return <tt>null</tt> or message.
     *
     * @see #args(String...)
     * @see #colored(String)
     */
    public String usage(final String command, final String subCommand, final String...args) {
        if (args.length == 0)
            return usage(command, subCommand, true);
        else
            return colored(args(usage(command, subCommand, false), args));
    }

    /**
     * Gets usage message for given command.
     * <p>
     * If usage messages doesn't contains given command
     * or sub command as key it will return <tt>null</tt>.
     * <p>
     * Returns <tt>null</tt> if there aren't message for
     * given key.
     *
     * @param   command     Name of command that owns sub command.
     * @param   subCommand  Name of sub command, for which you want
     * to get usage message.
     * @param   isColored   If <tt>true</tt> will translate colors
     * in {@link String}.
     * @return <tt>null</tt> or message.
     */
    public String usage(final String command, final String subCommand, final boolean isColored) {
        if (!usage.containsKey(command))
            return null;

        if (isColored)
            return colored(usage.get(command).get(subCommand));
        else
            return usage.get(command).get(subCommand);
    }

    /**
     * Replaces "{0}", "{1}", "{n}" chunks in given String
     * with appropriate argument (args[0], args[1], args[n]).
     * <p>
     * Example: String <tt>"Hello, {0}! I'm {1} c:"</tt>
     * with arguments {@code {"Notch", "NyanGuyMF"}} will
     * be <tt>"Hello, Notch! I'm NyanGuyMF c:"</tt>.
     * <p>
     * Returns <tt>null</tt> if given {@link String} message
     * is <tt>null</tt>.
     *
     * @param   message     {@link String} to insert values into it.
     * @param   args        Values to insert into String.
     */
    public static String args(String message, final String... args) {
        if (message == null)
            return message;

        if (args.length == 0)
            return message;

        for (int c = 0; c < args.length; c++) {
            message = message.replace("{" + c + "}", args[c]);
        }

        return message;
    }

    /**
     * Translates user-friendly colors to default Bukkit colors.
     * <p>
     * Simply replaces all '&' characters in given string with
     * '§' (ua7 in Unicode (u00a7)) character.
     * <p>
     * Returns <tt>null</tt> if given {@link String} message
     * is <tt>null</tt>.
     *
     * @param   message     {@link String} to translate colors.
     */
    public static String colored(final String message) {
        if (message == null)
            return null;

        return message.replace('&', '\u00a7');
    }
}

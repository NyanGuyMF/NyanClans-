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

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;
import java.util.ArrayList;
import java.util.List;

import de.exlll.configlib.configs.yaml.BukkitYamlConfiguration;
import nyanclans.storage.yaml.YamlFieldNameFormater;
import nyanclans.storage.yaml.messages.error.ErrorMessages;
import nyanclans.storage.yaml.messages.help.HelpMessages;
import nyanclans.storage.yaml.messages.info.InfoMessages;
import nyanclans.storage.yaml.messages.usage.UsageMessages;
import nyanclans.utils.Observable;
import nyanclans.utils.Observer;
import nyanclans.utils.PluginUtils;

/** @author NyanGuyMF */
public class MessagesConfig extends BukkitYamlConfiguration implements Observable<MessagesConfig> {
    private final List<Observer<MessagesConfig>> ignoreObservers;

    private HelpMessages help;

    private UsageMessages usage;

    private ErrorMessages error;

    private InfoMessages info;

    /**
     * Creates new {@link MessagesConfig} instance.
     * <p>
     * Make sure that you already created <tt>messages.yml</tt>
     * file in your plug-in folder before calling it.
     * <p>
     * <b>Important</b>: may cause {@link FileSystemException}
     * if you use {@link #load()} method too often on Unix systems.
     *
     * @param   pluginFolder    Plug-in's folder provided by Bukkit.
     * @throws FileNotFoundException if there is no <tt>messages.yml</tt>
     * file in given folder.
     */
    public MessagesConfig(final File pluginFolder) {
        super(
            new File(pluginFolder, "messages.yml").toPath(),
            BukkitYamlProperties.builder()
                .addFilter(fn -> !fn.getName().startsWith("ignore"))
                .setFormatter(YamlFieldNameFormater.getInstance())
                .build()
        );

        ignoreObservers = new ArrayList<>();

        setHelp(new HelpMessages());
        setUsage(new UsageMessages());
        setError(new ErrorMessages());
        setInfo(new InfoMessages());
    }

    public void saveAndLoad() {
        super.save();
        super.load();
    }

    /*----------------------------------*
     *    Form Observable interface     *
     *----------------------------------*/

    @Override
    public void addObserver(final Observer<MessagesConfig> obs) {
        ignoreObservers.add(obs);
    }

    @Override
    public void removeObserver(final Observer<MessagesConfig> obs) {
        ignoreObservers.remove(obs);
    }

    @Override
    public void notifyObservers() {
        ignoreObservers.parallelStream().forEach(obs ->
            PluginUtils.runTaskAsync(() -> obs.update(this))
        );
    }

    /*---------------------------------------*
     *     Getters and setters goes here     *
     *---------------------------------------*/

    /** Gets help */
    public HelpMessages help() {
        return help;
    }

    /** Sets help */
    public void setHelp(final HelpMessages helpMessages) {
        help = helpMessages;
    }

    /** Gets usage */
    public UsageMessages usage() {
        return usage;
    }

    /** Sets usage */
    public void setUsage(final UsageMessages usageMessages) {
        usage = usageMessages;
    }

    /** Gets error */
    public ErrorMessages error() {
        return error;
    }

    /** Sets error */
    public void setError(final ErrorMessages error) {
        this.error = error;
    }

    /** Gets info */
    public InfoMessages info() {
        return info;
    }

    /** Sets info */
    public void setInfo(final InfoMessages info) {
        this.info = info;
    }
}

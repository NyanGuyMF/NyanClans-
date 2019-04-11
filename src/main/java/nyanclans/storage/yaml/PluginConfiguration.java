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
package nyanclans.storage.yaml;

import java.io.File;

import de.exlll.configlib.configs.yaml.BukkitYamlConfiguration;
import nyanclans.storage.yaml.clan.ClanConfig;

/** @author NyanGuyMF - Vasiliy Bely */
public final class PluginConfiguration extends BukkitYamlConfiguration {
    private String language = "en";
    private ClanConfig clans;
    private RankConfig ranks;

    public PluginConfiguration(final File pluginFolder) {
        super(
            new File(pluginFolder, "config.yml").toPath(),
            YamlFieldNameFormater.getProps()
        );
        setClans(new ClanConfig());
        ranks = new RankConfig();
    }

    /** @return the language */
    public String getLanguage() {
        return language;
    }

    /** Sets language */
    public void setLanguage(final String language) {
        this.language = language;
    }

    /** @return the clans */
    public ClanConfig getClans() {
        return clans;
    }

    /** Sets clans */
    public void setClans(final ClanConfig clans) {
        this.clans = clans;
    }

    /** @return the ranks */
    public RankConfig getRanks() {
        return ranks;
    }

    /** Sets ranks */
    public void setRanks(final RankConfig ranks) {
        this.ranks = ranks;
    }
}

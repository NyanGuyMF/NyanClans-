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
package nyanclans.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import nyanclans.core.commands.clan.ClanChatCommand;
import nyanclans.core.commands.clan.ClanCommand;
import nyanclans.core.commands.dev.DeveloperCommand;
import nyanclans.core.events.AsyncClanChatMessageEvent;
import nyanclans.core.events.ChatMessageHandler;
import nyanclans.core.events.PlayerJoinHandler;
import nyanclans.core.events.ReloadEvent;
import nyanclans.core.player.ClanPlayer;
import nyanclans.core.rank.RankBuildDirector;
import nyanclans.storage.yaml.PluginConfiguration;
import nyanclans.storage.yaml.db.DatabaseConnector;
import nyanclans.storage.yaml.messages.MessagesManager;
import nyanclans.utils.PluginUtils;
import nyanclans.utils.dependency.DependencyManager;

/** @author NyanGuyMF */
public final class NyanClansPlugin extends JavaPlugin {
    private DatabaseConnector databaseConnector;
    private DependencyManager dependencyManager;
    private MessagesManager messagesConfig;
    private PluginConfiguration config;

    public NyanClansPlugin() {}

    @Override public void onLoad() {
        dependencyManager = new DependencyManager(super.getDataFolder(), this);
        loadConfigs(super.getDataFolder());
    }

    @Override public void onEnable() {
        /*
         * TODO:
         *   connect to Vault;
         *   connect to ProtocolLib;
         */
        // Add online players to database if they are not in it yet
        Bukkit.getOnlinePlayers().parallelStream()
            .filter(player -> !ClanPlayer.isPlayerExists(player.getName()))
            .forEach(bukkitPlayer -> {
                ClanPlayer player = new ClanPlayer(bukkitPlayer.getName());
                player.setFirstServerJoin(new Date(System.currentTimeMillis()));
                player.setLastServerJoin(player.getFirstServerJoin());
                player.create();
            });

        if (!PluginUtils.isInitialized()) {
            PluginUtils.init(this);
        }
        AsyncClanChatMessageEvent.init(config.getClans().getChat());
        ReloadEvent.init(this);

        registerCommands();
        registerListeners();
    }

    @Override public void onDisable() {
        /*
         * TODO:
         *   save configurations;
         *   clear caches;
         */
        if (databaseConnector.disconnect()) {
            super.getLogger().info("Disconnected from database.");
        } else {
            super.getLogger().info("Couldn't disconnect from database.");
        }
    }

    /** Registers all plug-in commands. */
    private void registerCommands() {
        new DeveloperCommand(this).register(this);
        new ClanCommand(this).register(this);
        new ClanChatCommand(messagesConfig, config.getClans()).register(this);
    }

    private void registerListeners() {
        new PlayerJoinHandler().register(this);
        new ChatMessageHandler(config.getClans()).register(this);
    }

    /**
     * Loads all configurations.
     * <p>
     * Will create standard plug-in's folder if it wasn't
     * exist yet.
     *
     * @param   pluginFolder    Plug-in's folder provided by Bukkit.
     */
    private void loadConfigs(final File pluginFolder) {
        if (!pluginFolder.exists()) {
            pluginFolder.mkdir();
        }

        File inviteMessage = new File(pluginFolder, "invite-message.json");
        if (!inviteMessage.exists()) {
            try {
                Files.copy(getResource(inviteMessage.getName()), inviteMessage.toPath());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        try {
            databaseConnector = databaseConnect(pluginFolder);
        } catch (IOException ex) {
            ex.printStackTrace();
            databaseConnector = null;
        }

        if (databaseConnector != null) {
            databaseConnector.initDaos();
            databaseConnector.createTables();
        }

        messagesConfig = loadMessagesYaml(pluginFolder);
        config         = loadPluginConfig(pluginFolder);

        RankBuildDirector.setConfig(config.getRanks());
    }

    /**
     * Initializes {@link MessagesManager} class instance.
     * <p>
     * Will create new <tt>messages.yml</tt> file if it wasn't
     * exist yet. If cannot create it will disable plug-in
     * and return <tt>null</tt> value.
     *
     * @param   pluginFolder    Plug-in's folder provided by Bukkit.
     * @return {@link MessagesManager} or <tt>null</tt>.
     */
    private MessagesManager loadMessagesYaml(final File pluginFolder) {
        File messages = new File(pluginFolder, "messages.yml");
        MessagesManager config;

        if (!messages.exists()) {
            try {
                messages.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                super.getPluginLoader().disablePlugin(this);
                return null;
            }
            config = new MessagesManager(pluginFolder);
            config.saveAndLoad();
        } else {
            config = new MessagesManager(pluginFolder);
            config.loadAndSave();
        }

        return config;
    }

    /** The same as {@link #loadMessagesYaml(File)}. */
    private PluginConfiguration loadPluginConfig(final File pluginFolder) {
        File configFile = new File(pluginFolder, "config.yml");
        PluginConfiguration config;

        if (!configFile.exists()) {
            config = new PluginConfiguration(pluginFolder);
            config.saveAndLoad();
        } else {
            config = new PluginConfiguration(pluginFolder);
            config.loadAndSave();
        }

        return config;
    }

    /** @see {@link DatabaseConnector#DatabaseConnector(File)} */
    private DatabaseConnector databaseConnect(final File pluginFolder) throws IOException {
        DatabaseConnector databaseConnector = new DatabaseConnector(pluginFolder, dependencyManager);

        // We cannot connect without database driver.
        if (!databaseConnector.loadDriver())
            return databaseConnector;

        switch (databaseConnector.connect()) {
        case SUCCESS:
            super.getLogger().info("Connected to database.");
            break;

        default:
            super.getLogger().info("Error while connecting to database");
            break;
        }

        return databaseConnector;
    }

    /** @return the messagesConfig */
    public MessagesManager getMessagesConfig() {
        return messagesConfig;
    }

    /** @return the config */
    public PluginConfiguration getConfiguration() {
        return config;
    }

    /** @return the databaseConnector */
    public DatabaseConnector getDatabaseConnector() {
        return databaseConnector;
    }
}

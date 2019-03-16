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
package nyanclans.core.commands.dev.sub;

import static com.j256.ormlite.table.TableUtils.createTable;
import static com.j256.ormlite.table.TableUtils.dropTable;

import java.sql.SQLException;
import java.util.Arrays;

import org.bukkit.command.CommandSender;

import com.j256.ormlite.dao.Dao;

import nyanclans.core.commands.SubCommand;
import nyanclans.core.commands.SubCommandManager;
import nyanclans.storage.db.DatabaseConnector;
import nyanclans.storage.yaml.messages.MessageBuilder;
import nyanclans.storage.yaml.messages.MessagesConfig;

/** @author NyanGuyMF */
public final class Fresh extends SubCommandManager<CommandSender, String> {
    private final DatabaseConnector databaseConnector;
    private MessagesConfig messages;

    // &e/clandef fresh &6«&cplayer&6, &ctable&6, &call&6» &6[&cplayer&6, &ctable&6]
    public Fresh(final MessagesConfig messages, final DatabaseConnector databaseConnector) {
        super(
            "fresh", "naynclans.dev.fresh",
            messages.usage().getDev().getFresh()
        );

        this.databaseConnector = databaseConnector;
        this.messages = messages;

        AllCommand all = new AllCommand(super.getUsage());
        super.addSubCommand(all);
    }

    @Override
    public boolean execute(
        final CommandSender performer, final String command,
        final String[] args
    ) {
        if (args.length == 0)
            return super.sendUsage(performer);

        final String subCommand = args[0].toLowerCase();
        final String[] subCommandArgs = Arrays.copyOfRange(args, 1, args.length);

        if (!super.hasSubCommand(subCommand))
            return super.sendUsage(performer);

        return super.getSubCommand(subCommand).execute(performer, subCommand, subCommandArgs);
    }

    private final class AllCommand extends SubCommand<CommandSender, String> {
        public AllCommand(final String usage) {
            super("all", "nyanclans.dev.fresh.all", usage);
        }

        @Override
        public boolean execute(
            final CommandSender performer, final String command,
            final String[] args
        ) {
            boolean isAllFreshed = true;

            isAllFreshed = isAllFreshed
                        && freshTable("Clan", databaseConnector.getClanDao(), performer)
                        && freshTable("Player", databaseConnector.getPlayerDao(), performer)
                        && freshTable("Rank", databaseConnector.getRankDao(), performer);

            if (isAllFreshed) {
                new MessageBuilder()
                    .message(messages.info().getFreshSuccess())
                    .args("All")
                    .send(performer);
            }

            return true;
        }

        @Override
        public boolean hasPermission(final CommandSender performer) {
            return true; // already checked
        }

        private boolean freshTable(
            final String tableName, final Dao<?, ?> dao,
            final CommandSender performer
        ) {
            try {
                if (dao.isTableExists()) {
                    dropTable(dao, false);
                }

                createTable(dao);
                return true;
            } catch (SQLException ex) {
                ex.printStackTrace();
                new MessageBuilder()
                    .message(messages.error().getFreshTable())
                    .args(tableName)
                    .send(performer);
                return false;
            }
        }
    }
}

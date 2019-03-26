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
import nyanclans.storage.yaml.db.DatabaseConnector;
import nyanclans.storage.yaml.messages.MessagesManager;

/** @author NyanGuyMF */
public final class Fresh extends SubCommand<String> {
    private final DatabaseConnector databaseConnector;
    private MessagesManager messages;

    public Fresh(final MessagesManager messages, final DatabaseConnector databaseConnector) {
        super(
            "fresh", "naynclans.dev.fresh",
            messages.usage("dev", "fresh")
        );

        this.databaseConnector = databaseConnector;
        this.messages = messages;

    }

    @Override
    public boolean execute(
        final CommandSender performer, final String[] args
    ) {
        if (args.length == 0)
            return super.sendUsage(performer);

        final String subCommand = args[0].toLowerCase();
        final String[] subCommandArgs = Arrays.copyOfRange(args, 1, args.length);

        switch (subCommand) {
        case "all":
            return freshAll(performer);

        default:
            return super.sendUsage(performer);
        }
    }

    private boolean freshAll(final CommandSender performer) {
        freshTable("Clan", databaseConnector.getClanDao(), performer);
        freshTable("Player", databaseConnector.getPlayerDao(), performer);
        freshTable("Rank", databaseConnector.getRankDao(), performer);

        performer.sendMessage(messages.info("fresh-success", "All"));

        return true;
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
            performer.sendMessage(messages.error("fresh-table", tableName));
            return false;
        }
    }
}

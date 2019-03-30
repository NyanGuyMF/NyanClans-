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
package nyanclans.core.commands.clan.sub;

import static nyanclans.core.player.ClanPlayer.playerByName;
import static nyanclans.core.rank.RankBuildDirector.RankTemplate.LEADER;
import static nyanclans.core.rank.RankBuildDirector.RankTemplate.MODER;
import static nyanclans.core.rank.RankBuildDirector.RankTemplate.PLAYER;

import java.util.regex.Pattern;

import org.bukkit.command.CommandSender;

import nyanclans.core.clan.Clan;
import nyanclans.core.commands.SubCommand;
import nyanclans.core.player.ClanPlayer;
import nyanclans.core.rank.Rank;
import nyanclans.core.rank.RankBuildDirector;
import nyanclans.core.rank.RankPermission;
import nyanclans.storage.yaml.ClanConfig;
import nyanclans.storage.yaml.PluginConfiguration;
import nyanclans.storage.yaml.messages.MessagesManager;

/** @author NyanGuyMF - Vasiliy Bely */
public final class CreateCommand extends SubCommand<RankPermission> {
    private final MessagesManager messages;
    private final ClanConfig config;

    public CreateCommand(final MessagesManager messages, final PluginConfiguration config) {
        super("create", null, messages.usage("clan", "create"));

        this.messages = messages;
        this.config   = config.getClans();
    }

    @Override
    public boolean execute(final CommandSender performer, final String[] args) {
        /* --------
          Player doesn't need any permission to create clan,
          so we just validating arguments and creating it.
           -------- */
        if (args.length == 0) {
            performer.sendMessage(messages.usage("clan", "create"));
            return true;
        }

        final String clanName = args[0];

        if (!isClanNameValid(config.getClanNameRegex(), clanName)) {
            performer.sendMessage(messages.error("invalid-clan-name", clanName));
            return true;
        }

        if (Clan.isClanExists(clanName)) {
            performer.sendMessage(messages.error("clan-already-exists", clanName));
            return true;
        }

        ClanPlayer leader = playerByName(performer.getName());

        if (leader.isClanMember()) {
            performer.sendMessage(messages.error("you-already-in-clan"));
            return true;
        }

        // Create new clan
        Clan clan = new Clan(clanName, leader);
        if (!clan.create()) {
            performer.sendMessage(messages.error("clan-create-error", clanName));
            return true;
        }

        // Create ranks and abort if got some error
        Rank leaderRank = RankBuildDirector.getBuilder(LEADER).clan(clan).build();
        if (!leaderRank.create()) {
            clan.delete();
            performer.sendMessage(messages.error("rank-create-error", leaderRank.getName()));
            return true;
        }

        Rank playerRank = RankBuildDirector.getBuilder(PLAYER).clan(clan).build();
        if (!playerRank.create()) {
            clan.delete();
            leaderRank.delete();
            performer.sendMessage(messages.error("rank-create-error", leaderRank.getName()));
            return true;
        }

        Rank moderRank = RankBuildDirector.getBuilder(MODER).clan(clan).build();
        if (!moderRank.create()) {
            clan.delete();
            leaderRank.delete();
            playerRank.delete();
            performer.sendMessage(messages.error(
                    "rank-create-error", clan.getName(), moderRank.getName()
            ));
            return true;
        }

        // Ranks and clan are successfully created, now lets setup leader and members
        clan.addRank(leaderRank);
        clan.addRank(playerRank);
        clan.addRank(moderRank);
        clan.addMember(leader);
        clan.save();

        leader.setRank(leaderRank);
        leader.setClan(clan);
        leader.save();

        // All done successfully, notify player about it
        performer.sendMessage(messages.info("clan-created", clan.getName()));

        return true;
    }

    /**
     * Check is given clan name valid for given
     * regular expression.
     * <p>
     * If regular expression is «none» it will
     * return <tt>true</tt>.
     *
     * @param   regex       Regular expression to check name.
     * @param   clanName    Value to check with regex.
     * @return <tt>true</tt> if name is «none» or valid.
     */
    private boolean isClanNameValid(final String regex, final String clanName) {
        if (regex.equalsIgnoreCase("none"))
            return true;

        return Pattern.compile(regex).matcher(clanName).find();
    }
}

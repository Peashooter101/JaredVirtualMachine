package com.Peashooter101.jaredvm.listeners.other;

import com.Peashooter101.jaredvm.JaredVM;
import com.Peashooter101.jaredvm.listeners.command.CmdTestListener;
import com.Peashooter101.jaredvm.listeners.command.VCInviteListener;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.utils.cache.SnowflakeCacheView;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JaredVMReadyListener extends ListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(JaredVMReadyListener.class);

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        loadGuildCommands();
        addEventListeners();
        logger.info("onReady Event Completed, Application Ready!");
    }

    private static void loadGuildCommands() {
        SnowflakeCacheView<Guild> guilds = JaredVM.getApi().getGuildCache();
        for (Guild g : guilds) {
            logger.debug("Updating Commands for Guild: " + g.getName());
            g.updateCommands()
                    .addCommands(Commands.slash("vc-invite", "Invite a user to a private VC")
                            .addOption(OptionType.USER, "user", "The user to invite (Must already be in a VC).", true),
                    Commands.slash("cmd-test", "Used for messing with slash commands.")
                            .addOption(OptionType.USER, "user", "User"))
            .queue();
        }
    }

    private static void addEventListeners() {
        JaredVM.getApi().addEventListener(new VCInviteListener());
        JaredVM.getApi().addEventListener(new CmdTestListener());
    }

}

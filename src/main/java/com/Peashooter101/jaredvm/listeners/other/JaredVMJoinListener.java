package com.Peashooter101.jaredvm.listeners.other;

import com.Peashooter101.jaredvm.JaredVM;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JaredVMJoinListener extends ListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(JaredVMJoinListener.class);

    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        logger.info("Joined Guild: " + event.getGuild().getName());
        JaredVM.updateCommands(event.getGuild());
    }

}

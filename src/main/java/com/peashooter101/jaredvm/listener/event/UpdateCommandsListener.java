package com.peashooter101.jaredvm.listener.event;

import com.peashooter101.jaredvm.JaredVM;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class UpdateCommandsListener extends ListenerAdapter {

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        JaredVM.getApi().getGuilds().forEach(JaredVM::updateCommands);
    }

    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        JaredVM.updateCommands(event.getGuild());
    }

}

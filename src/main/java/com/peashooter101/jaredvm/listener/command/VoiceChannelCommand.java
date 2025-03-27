package com.peashooter101.jaredvm.listener.command;

import com.peashooter101.jaredvm.listener.command.subcommand.VcInviteCommand;
import com.peashooter101.jaredvm.listener.command.subcommand.VcRequestCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class VoiceChannelCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("vc")) return;
        event.deferReply().queue();
        String subcommand = event.getSubcommandName();
        if (subcommand == null) event.getHook().editOriginal("Not gonna lie, I have no idea how you did that.").queue();
        else if (subcommand.equalsIgnoreCase("invite")) VcInviteCommand.getInstance().handle(event);
        else if (subcommand.equalsIgnoreCase("request")) VcRequestCommand.getInstance().handle(event);
    }

}

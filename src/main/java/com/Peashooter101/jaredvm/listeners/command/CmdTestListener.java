package com.Peashooter101.jaredvm.listeners.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public class CmdTestListener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("cmd-test")) { return; }

        // Send thinking.
        event.deferReply().queue();

        // Test Reply
        OptionMapping user = event.getOption("user");
        if (user == null) {
            event.getHook().sendMessage("Nope " + event.getUser().getAsMention()).queue();
            return;
        }

        event.getHook().sendMessage(event.getUser().getAsMention() + " sent command.").queue();

        event.getHook().sendMessage("Sending a second message!").queue();

    }

}

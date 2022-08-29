package com.Peashooter101.jaredvm.listeners.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class AbbyListener extends ListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(AbbyListener.class);
    private static String path = "JaredVM_data/Abby";

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("abby")) { return; }
        event.deferReply().queue();

        // TODO: Add a way to properly add and remove pictures as requested.

        Random rand = new Random();
        String[] photos = new File(path).list();
        if (photos == null || photos.length == 0) {
            event.getHook().editOriginal("Hey, I couldn't find photos of Abby!").queue();
            return;
        }

        File file = new File(path + "/" + photos[rand.nextInt(photos.length)]);
        event.getHook().editOriginal("`" + file.getName() +"`").addFile(file).queue();

    }

}

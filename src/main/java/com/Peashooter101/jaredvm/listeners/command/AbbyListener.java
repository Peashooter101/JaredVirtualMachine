package com.Peashooter101.jaredvm.listeners.command;

import com.Peashooter101.jaredvm.utility.github.GitHubRepoItem;
import com.Peashooter101.jaredvm.utility.github.GitHubUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class AbbyListener extends ListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(AbbyListener.class);
    private static final String gitHubURI = "https://api.github.com/repos/Peashooter101/JaredVirtualMachine/contents/JaredVM_data/Abby";

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("abby")) { return; }

        // TODO: Add a way to properly add and remove pictures as requested.
        // TODO: Add a gallery.

        event.deferReply().queue();
        getPicture(event);

    }

    private void getPicture(SlashCommandInteractionEvent event) {

        ArrayList<GitHubRepoItem> items = GitHubUtil.getGitHubItems(gitHubURI);
        if (items == null) {
            logger.debug("An error has occurred with URI: " + gitHubURI);
            event.getHook().editOriginal("There was a problem accessing GitHub!").queue();
            return;
        }

        if (items.isEmpty()) {
            logger.debug("No items found at " + gitHubURI);
            event.getHook().editOriginal("Sorry! I couldn't find any Abby pictures!").queue();
            return;
        }

        GitHubRepoItem item = items.get(new Random().nextInt(items.size()));
        String name = item.name.substring(0, item.name.indexOf("."));
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle(name, null)
                .setColor(new Color(0x00FFFF))
                .setImage(item.download_url);
        event.getHook().editOriginalEmbeds(embed.build()).queue();

    }

}

package com.Peashooter101.jaredvm.listeners.command;

import com.Peashooter101.jaredvm.utility.github.GitHubRepoItem;
import com.Peashooter101.jaredvm.utility.github.GitHubUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.EmbedType;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.time.OffsetDateTime;
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
        // getVideo(event); TODO: Consider alternatives for sending video.

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

    // TODO: Remove, is used for testing.
    private void getVideo(SlashCommandInteractionEvent event) {

        String videoUrl = "https://video.twimg.com/ext_tw_video/1566354175470063619/pu/vid/640x360/SOeQspDBugrN_nPn.mp4?tag=12";
        MessageEmbed.VideoInfo video = new MessageEmbed.VideoInfo(videoUrl, 640, 360);
        MessageEmbed.Provider provider = new MessageEmbed.Provider("FixTweet - ✨ Click to join our Discord Server", "https://discord.gg/6CQTTTkGaH");
        MessageEmbed.AuthorInfo author = new MessageEmbed.AuthorInfo("good morning", "https://twitter.com/DPPt_Shitpost/status/1566354183208452099", null, null);
        String description = "boop";
        String url = "https://fxtwitter.com/DPPt_Shitpost/status/1566354183208452099";
        String title = "CEO of Sinnoh \uD83C\uDF1F (@DPPt_Shitpost)";

        MessageEmbed embed = new MessageEmbed(url, title, null, EmbedType.VIDEO, null, 11548729, null, provider, author, video, null, null, null);
        logger.warn("Embed Type: " + embed.getType());
        event.getHook().sendMessageEmbeds(embed).queue();
        MessageBuilder messageBuilder = new MessageBuilder().setEmbeds(embed);
        event.getHook().sendMessage(messageBuilder.build()).queue();
    }

}

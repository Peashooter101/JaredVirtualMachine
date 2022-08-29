package com.Peashooter101.jaredvm.listeners.command;

import com.Peashooter101.jaredvm.utility.github.GitHubRepoItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

public class AbbyListener extends ListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(AbbyListener.class);
    private static String path = "JaredVM_data/Abby";
    private static String gitHubURI = "https://api.github.com/repos/Peashooter101/JaredVirtualMachine/contents/JaredVM_data/Abby";
    private static ObjectMapper mapper = new ObjectMapper();
    private static HttpRequest request = HttpRequest.newBuilder().uri(URI.create(gitHubURI)).GET().build();
    private static HttpClient client = HttpClient.newHttpClient();

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("abby")) { return; }

        // TODO: Add a way to properly add and remove pictures as requested.
        // TODO: Add a gallery.

        event.deferReply().queue();
        getPicture(event);

    }

    private void getPicture(SlashCommandInteractionEvent event) {

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch (IOException | InterruptedException e) {
            logger.error("An error has occurred: " + e.getMessage());
            event.getHook().editOriginal("Sorry! Something went wrong accessing GitHub!").queue();
            return;
        }

        ArrayList<GitHubRepoItem> items;
        try {
            items = mapper.readValue(response.body(), new TypeReference<>(){});
        }
        catch (JsonProcessingException e) {
            logger.error("An error has occurred: " + e.getMessage());
            event.getHook().editOriginal("Sorry! Something went wrong when processing GitHub stuff!").queue();
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

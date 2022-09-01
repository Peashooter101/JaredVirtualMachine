package com.Peashooter101.jaredvm.listeners.command;

import com.Peashooter101.jaredvm.utility.valorant.ValorantProfile;
import com.Peashooter101.jaredvm.utility.valorant.ValorantRank;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Locale;
import java.util.Objects;

public class ValorantListener extends ListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ValorantListener.class);
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String valorantEndpoint = "https://api.henrikdev.xyz/valorant/";
    private static final String cannotFindProfile = "I could not find that user right now, did you spell it correctly? Maybe just try again later.";

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("valorant")) { return; }
        event.deferReply().queue();

        assert event.getSubcommandName() != null;

        if (event.getSubcommandName().equals("profile")) { profile(event); }
        if (event.getSubcommandName().equals("rank")) { rank(event); }


    }

    private ValorantProfile getProfile(@NotNull String name) {
        String[] nameData = name.split("#");
        if (nameData.length < 2) { return null; }
        String url = valorantEndpoint + "v1/account/" + nameData[0].replaceAll(" ", "%20") + "/" + nameData[1];
        String responseBody = getResponseBody(url);

        if (responseBody == null) { return null; }

        ValorantProfile profile;
        try {
            JsonNode node = mapper.readValue(responseBody, JsonNode.class);
            profile = mapper.readValue(node.get("data").toString(), ValorantProfile.class);
        }
        catch (JsonProcessingException e) {
            logger.error("An error has occurred, the profile cannot be mapped.");
            return null;
        }
        return profile;
    }

    private ValorantRank getRank(@NotNull String puuid, @NotNull String region) {
        String url = valorantEndpoint + "v2/by-puuid/mmr/" + region + "/" + puuid;
        String responseBody = getResponseBody(url);

        if (responseBody == null) { return null; }

        ValorantRank rank;
        try {
            JsonNode node = mapper.readValue(responseBody, JsonNode.class);
            rank = mapper.readValue(node.get("data").toString(), ValorantRank.class);
        }
        catch (JsonProcessingException e) {
            logger.error("An error has occurred, the rank cannot be mapped.");
            return null;
        }
        return rank;
    }

    private void profile(SlashCommandInteractionEvent event) {
        ValorantProfile profile = getProfile(Objects.requireNonNull(event.getOption("user")).getAsString());
        if (profile == null) {
            event.getHook().editOriginal(cannotFindProfile).queue();
            return;
        }

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle(profile.name, null)
                .setColor(new Color(0xBD3944))
                .setDescription(profile.name + "#" + profile.tag)
                .setFooter("Requested by: " + event.getUser().getAsTag(), event.getUser().getAvatarUrl())
                .setImage(profile.card.wide)
                .setThumbnail(profile.card.small)
                .addField("Region: ", profile.region.toUpperCase(Locale.ENGLISH), true)
                .addField("Account Level: ", String.valueOf(profile.account_level), true);

        ValorantRank rankData = getRank(profile.puuid, profile.region);

        if (rankData == null) {
            embed.addField("Rank: ", "Cannot be loaded.", false);
            event.getHook().editOriginalEmbeds(embed.build()).queue();
            return;
        }

        event.getHook().editOriginalEmbeds(embed.build()).queue();
    }

    private void rank(SlashCommandInteractionEvent event) {
        // ValorantProfile profile = getProfile(Objects.requireNonNull(event.getOption("user")).getAsString());
        event.getHook().editOriginal("This is not yet implemented!").queue();
    }

    private String getResponseBody(String url) {

        HttpResponse<String> response;
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch (IOException | InterruptedException e) {
            logger.error("An error has occurred when sending the request: " + e.getMessage());
            return null;
        }

        switch (response.statusCode()) {
            case 404 -> {
                logger.warn("An error has occurred, the user does not exist.");
                return null;
            }
            case 429 -> {
                logger.warn("An error has occurred, the rate limit has been hit.");
                return null;
            }
            case 500 -> {
                logger.warn("An error has occurred, internal server error.");
                return null;
            }
        }

        if (response.statusCode() != 200) {
            logger.warn("An unknown error has occurred.");
            return null;
        }

        return response.body();
    }

}

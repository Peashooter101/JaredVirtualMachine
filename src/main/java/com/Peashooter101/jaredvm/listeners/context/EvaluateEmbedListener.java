package com.Peashooter101.jaredvm.listeners.context;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

// Used for testing and will be disabled normally.
public class EvaluateEmbedListener extends ListenerAdapter {

    @Override
    public void onMessageContextInteraction(MessageContextInteractionEvent event) {
        if (!event.getName().equals("Evaluate Embed")) { return; }

        List<MessageEmbed> embeds = event.getTarget().getEmbeds();
        if (embeds.isEmpty()) {
            event.reply("There are no embeds on this message.").setEphemeral(true).queue();
            return;
        }

        event.deferReply(true).queue();

        StringBuilder content = new StringBuilder();

        for (MessageEmbed embed : embeds) {
            content.append("Title:\n  ").append(embed.getTitle()).append("\n");
            content.append("Author:\n  ").append(embed.getAuthor()).append("\n");
            content.append("Embed Type:\n  ").append(embed.getType()).append("\n");
            content.append("Color:\n  ").append(embed.getColor()).append("\n");
            if (embed.getVideoInfo() != null) {
                content.append("Video Info:\n  ").append(embed.getVideoInfo().getUrl()).append("\n");
                content.append("  ").append(embed.getVideoInfo().getWidth()).append("x").append(embed.getVideoInfo().getHeight()).append("\n");
            }
            content.append("Data:\n```json\n").append(embed.toData().toPrettyString()).append("\n```\n");
        }

        event.getUser().openPrivateChannel().queue((channel) -> {
            channel.sendMessage(content.toString()).queue();
        });

        event.getHook().editOriginal("I have sent you the Embed information in DMs.").queue();

    }

}

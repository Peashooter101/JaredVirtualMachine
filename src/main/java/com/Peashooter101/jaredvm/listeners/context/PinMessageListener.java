package com.Peashooter101.jaredvm.listeners.context;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class PinMessageListener extends ListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(PinMessageListener.class);

    @Override
    public void onMessageContextInteraction(MessageContextInteractionEvent event) {
        if (!event.getName().equals("Pin Message")) { return; }
        event.deferReply(true).queue();

        if (event.getUser().isBot() || event.getUser().isSystem()) {
            logger.warn("A system user or bot tried to use this context.");
            event.getHook().editOriginal("You are not a valid user.").queue();
            return;
        }

        Member member = event.getMember();
        if (member == null) {
            logger.debug("Member pulled from event is null.");
            event.getHook().editOriginal("Something went wrong here!").queue();
            return;
        }

        if (!member.hasPermission(Permission.ADMINISTRATOR)) {
            logger.debug(member.getUser().getAsTag() + " attempted to use [Pin Message] but does not have permission.");
            event.getHook().editOriginal("You do not have permission!").queue();
            return;
        }

        Guild guild = event.getGuild();
        if (guild == null) {
            logger.debug(member.getUser().getAsTag() + " attempted to use [Pin Message] but a guild was not found.");
            event.getHook().editOriginal("You are not in a guild!").queue();
            return;
        }

        TextChannel pinChannel = locatePinChannel(event.getGuild());
        if (pinChannel == null) {
            logger.debug(member.getUser().getAsTag() + " attempted to use [Pin Message] in " + guild.getName() + " (" + guild.getId() + ") but there is no channel beginning with \uD83D\uDCCC.");
            event.getHook().editOriginal("A channel is required to have the \uD83D\uDCCC emoji to be the Pin Channel.").queue();
            return;
        }

        String messageContent = event.getTarget().getContentRaw();
        String messageLink = event.getTarget().getJumpUrl();
        String channelName = event.getTarget().getChannel().getName();
        User author = event.getTarget().getAuthor();
        User pinUser = event.getUser();
        boolean isEdited = event.getTarget().getTimeEdited() != null;
        long epochTime = (isEdited) ? event.getTarget().getTimeEdited().toEpochSecond() : event.getTarget().getTimeCreated().toEpochSecond();

        String image = null;
        for (Message.Attachment a : event.getTarget().getAttachments()) {
            if ((a.isImage() || a.isVideo()) && !a.isSpoiler()) {
                image = a.getUrl();
                break;
            }
        }

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle(channelName)
                .setDescription(messageContent + (isEdited ? "\n*(Edited)*" : ""))
                .setAuthor("Posted by " + author.getName(), null, author.getAvatarUrl())
                .setColor(Color.getHSBColor((float) Math.random(), 0.9f, 1.0f))
                .setFooter("Pinned by " + pinUser.getName() + " on " + event.getTimeCreated().toLocalDate(), pinUser.getAvatarUrl())
                .addField("Date Posted:", "<t:" + epochTime + ":F>", false);

        if (image != null) {
            embed.setImage(image);
        }
        pinChannel.sendMessageEmbeds(embed.build()).setActionRow(Button.link(messageLink, "Original Message")).queue();
        event.getHook().editOriginal("Message pinned!").queue();
    }

    private static TextChannel locatePinChannel(Guild g) {
        for (TextChannel t : g.getTextChannels()) {
            if (t.getName().contains("\uD83D\uDCCC")) { return t; }
        }
        return null;
    }

}

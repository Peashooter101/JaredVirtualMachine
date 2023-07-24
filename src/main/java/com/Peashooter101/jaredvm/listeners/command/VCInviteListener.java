package com.Peashooter101.jaredvm.listeners.command;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;

public class VCInviteListener extends ListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(VCInviteListener.class);
    private static final HashMap<Member, Request> vcRequests = new HashMap<>();
    private static final long INVITE_TIMEOUT = 30000L;

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("vc-invite")) { return; }

        OptionMapping optionUser = event.getOption("user");
        assert optionUser != null;

        Member invitee = optionUser.getAsMember();
        Member inviter = event.getMember();
        Guild guild = event.getGuild();

        // NPE Checks
        assert invitee != null;
        assert inviter != null;
        assert guild != null;

        // Inviting Yourself
        if (invitee.equals(inviter)) {
            String response = "Hey " + inviter.getAsMention() + ", you cannot invite yourself!";
            event.reply(response).setEphemeral(true).queue();
            logger.debug(event.getUser().getName() + " issued command but failed: Invited themself.");
            return;
        }

        if (isNotUserAccount(invitee)) {
            String response = "Hey " + inviter.getAsMention() + ", " + invitee.getAsMention() + " isn't a normal user.";
            event.reply(response).setEphemeral(true).queue();
            logger.debug(event.getUser().getName() + " issued command but failed: Invited Admin / Bot Account (" + invitee.getUser().getName() + ").");
            return;
        }

        if (isNotUserAccount(inviter)) {
            String response = "Hey " + inviter.getAsMention() + ", you are not a normal user...";
            event.reply(response).setEphemeral(true).queue();
            logger.debug(event.getUser().getName() + " issued command but failed: User is Admin / Bot Account.");
            return;
        }

        VoiceChannel inviterVC = isInVC(inviter);

        if (inviterVC == null) {
            String response = "Hey " + inviter.getAsMention() + ", you are not in a Voice Channel...";
            event.reply(response).setEphemeral(true).queue();
            logger.debug(event.getUser().getName() + " issued command but failed: Not in a Voice Channel.");
            return;
        }

        if (!hasVCPermission(inviter, inviterVC)) {
            String response = "Hey " + inviter.getAsMention() + ", you cannot invite them! You do not have permission to join this Voice Channel...";
            event.reply(response).setEphemeral(true).queue();
            logger.debug(event.getUser().getName() + " issued command but failed: User does not have access to this Voice Channel (" + inviterVC.getName() + ").");
            return;
        }

        VoiceChannel inviteeVC = isInVC(invitee);

        if (inviteeVC == null) {
            String response = "Hey " + inviter.getAsMention() + ", " + invitee.getUser().getName() + " is not in a Voice Channel right now...";
            event.reply(response).setEphemeral(true).queue();
            logger.debug(event.getUser().getName() + " issued command but failed: Invitee is not in a Voice Channel (" + invitee.getUser().getName() + ").");
            return;
        }

        if (inviterVC.equals(inviteeVC)) {
            String response = "Hey " + inviter.getAsMention() + ", you are already in a Voice Channel with them.";
            event.reply(response).setEphemeral(true).queue();
            logger.debug(event.getUser().getName() + " issued command but failed: Both members are already in the same Voice Channel (" + invitee.getUser().getName() + ", " + inviterVC.getName() + ").");
            return;
        }

        // Handle old requests...
        if (vcRequests.containsKey(invitee)) {

            Request request = vcRequests.get(invitee);
            long timeElapsed = System.currentTimeMillis() - request.time;

            // If the request is still fresh...
            if (timeElapsed <= INVITE_TIMEOUT) {
                String response = "Hey " + inviter.getAsMention() + ", " + invitee.getUser().getName() + " has a request pending, please try again later!";
                event.reply(response).setEphemeral(true).queue();
                logger.debug(event.getUser().getName() + " issued command but failed: Invitee already has a request pending (" + invitee.getUser().getName() + ").");
                return;
            }

            // Remove old request and edit the old message.
            vcRequests.remove(invitee);
            String response = "This request from " + request.inviter.getUser().getName() + " sent to " + invitee.getUser().getName() + " has expired.";
            request.hook.editOriginal(response).queue();
            logger.debug(request.inviter.getUser().getName() + " issued command but failed: Invite expired (" + request.inviter.getUser().getName() + " -> " + invitee.getUser().getName() + " for " + request.channel.getName() + ").");
        }

        vcRequests.put(invitee, new Request(event.getHook(), System.currentTimeMillis(), inviterVC, inviter));

        String promptInvite = invitee.getAsMention() + ", " + inviter.getUser().getName() + " is inviting you to join " + inviterVC.getName() + "!";
        event.reply(promptInvite).addActionRow(
                Button.success("vc-invite-join", "Join VC"),
                Button.danger("vc-invite-deny", "Deny Request")
        ).queue();

        logger.debug(event.getUser().getName() + " issued command: Invite " + invitee.getUser().getName() + " to " + inviterVC.getName());
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (!event.getComponentId().equals("vc-invite-join") && !event.getComponentId().equals("vc-invite-deny")) { return; }

        Member member = event.getMember();

        // If the person isn't the invitee.
        if (member == null || !vcRequests.containsKey(member)) {
            event.deferReply(true).queue();
            String response = "Hey, this invitation is not for you!";
            event.reply(response).setEphemeral(true).queue();
            return;
        }

        Request request = vcRequests.get(member);
        long timeElapsed = System.currentTimeMillis() - request.time;

        // TODO: Make time configurable.
        // If the request timed out...
        if (timeElapsed > INVITE_TIMEOUT) {
            String response = "Oops sorry " + event.getUser().getAsMention() + ", your request timed out! Ask for another one from " + request.inviter.getUser().getName();
            event.editMessage(response).setActionRow(Collections.emptyList()).queue();
            logger.debug(request.inviter.getUser().getName() + " issued command but failed: Invite expired (" + request.inviter.getUser().getName() + " -> " + member.getUser().getName() + " for " + request.channel.getName() + ").");
            vcRequests.remove(member);
            return;
        }

        // If denied...
        if (event.getComponentId().equals("vc-invite-deny")) {
            String response = "Hey " + request.inviter.getAsMention() + ", " + member.getUser().getName() + " has denied your request to join " + request.channel.getName() + "!";
            event.editMessage(response).setActionRow(Collections.emptyList()).queue();
            logger.debug(request.inviter.getUser().getName() + " issued command but failed: Invite Denied (" + request.inviter.getUser().getName() + " -> " + member.getUser().getName() + " for " + request.channel.getName() + ").");
            vcRequests.remove(member);
            return;
        }

        // If accepted...
        Guild guild = request.channel.getGuild();
        // TODO: Check if the channel is full and if the user can bypass it.
        if (isInVC(member) == null) {
            String response = "Hey " + request.inviter.getAsMention() + ", " + member.getUser().getName() + " was in a voice chat but is no longer in any voice chat.";
            event.editMessage(response).setActionRow(Collections.emptyList()).queue();
            logger.debug(event.getUser().getName() + " accepted invite from " + request.inviter.getUser().getName() + " to " + request.channel.getName() + " but was no longer in a Voice Channel.");
            vcRequests.remove(member);
            return;
        }
        guild.moveVoiceMember(member, request.channel).queue();

        logger.debug(event.getUser().getName() + " accepted invite from " + request.inviter.getUser().getName() + " to " + request.channel.getName());
        String response = "Hey " + request.inviter.getUser().getName() + ", I moved " + member.getUser().getName() + " into " + request.channel.getName();
        event.editMessage(response).setActionRow(Collections.emptyList()).queue();

        vcRequests.remove(member);
    }


    private static boolean isNotUserAccount(@NotNull Member member) {
        return (member.getUser().isSystem() || member.getUser().isBot());
    }

    /**
     * Checks if user is in a Voice Channel, if they are, return the Voice Channel.
     * @param member User to search for.
     * @return Voice Channel if the user is in one, otherwise null.
     */
    private static VoiceChannel isInVC(@NotNull Member member) {
        GuildVoiceState gvs = member.getVoiceState();
        if (gvs == null) { return null; }
        return (VoiceChannel) gvs.getChannel();
    }

    private static boolean hasVCPermission(@NotNull Member member, @NotNull VoiceChannel vc) {
        return member.hasAccess(vc);
    }

    private record Request(InteractionHook hook, long time, VoiceChannel channel, Member inviter) {}

}

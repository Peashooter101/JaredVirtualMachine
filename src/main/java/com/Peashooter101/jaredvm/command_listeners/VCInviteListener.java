package com.Peashooter101.jaredvm.command_listeners;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;

public class VCInviteListener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("vc-invite")) { return; }

        // Deferred Reply: Thinking...
        event.deferReply().queue();

        // Ensure a valid user is provided.
        OptionMapping optionUser = event.getOption("user");
        if (optionUser == null) {
            String response = "Hey " + event.getUser().getAsMention() + ", you need to invite ***SOMEONE***!";
            event.getHook().sendMessage(response).queue();
            return;
        }
        Member invitee = optionUser.getAsMember();
        Member inviter = event.getMember();
        Guild guild = event.getGuild();

        // NPE Checks
        assert invitee != null;
        assert inviter != null;
        if (guild == null) {
            String response = "ERROR " + inviter.getAsMention() + ": Guild not Found (VCInviteListener)";
            event.getHook().sendMessage(response).queue();
            return;
        }
        //

        // Inviting Yourself
        if (invitee.equals(inviter)) {
            String response = "Hey " + inviter.getAsMention() + ", you cannot invite yourself!";
            event.getHook().sendMessage(response).queue();
            return;
        }

        if (isNotUserAccount(invitee)) {
            String response = "Hey " + inviter.getAsMention() + ", " + invitee.getAsMention() + " isn't a normal user.";
            event.getHook().sendMessage(response).queue();
            return;
        }

        if (isNotUserAccount(inviter)) {
            String response = "Hey " + inviter.getAsMention() + ", you are not a normal user...";
            event.getHook().sendMessage(response).queue();
            return;
        }

        VoiceChannel inviterVC = isInVC(inviter);

        if (inviterVC == null) {
            String response = "Hey " + inviter.getAsMention() + ", you are not in a Voice Channel...";
            event.getHook().sendMessage(response).queue();
            return;
        }

        if (!hasVCPermission(inviter, inviterVC)) {
            String response = "Hey " + inviter.getAsMention() + ", you cannot invite them! You do not have permission to join this Voice Channel...";
            event.getHook().sendMessage(response).queue();
            return;
        }

        VoiceChannel inviteeVC = isInVC(invitee);

        if (inviteeVC == null) {
            String response = "Hey " + inviter.getAsMention() + ", " + invitee.getUser().getName() + " is not in a Voice Channel right now...";
            event.getHook().sendMessage(response).queue();
            return;
        }

        if (inviteeVC.equals(inviterVC)) {
            String response = "Hey " + inviter.getAsMention() + ", you are already in a Voice Channel with them.";
            event.getHook().sendMessage(response).queue();
            return;
        }

        // TODO: Placeholder Functionality
        guild.moveVoiceMember(invitee, inviterVC).queue();
        String response = "Moved " + invitee.getAsMention() + " into " + inviterVC.getName();
        event.getHook().sendMessage(response).queue();
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

}

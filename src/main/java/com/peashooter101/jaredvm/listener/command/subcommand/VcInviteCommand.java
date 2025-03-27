package com.peashooter101.jaredvm.listener.command.subcommand;

import com.peashooter101.jaredvm.config.Config;
import com.peashooter101.jaredvm.data.VoiceChannelCommandInfo;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.HashMap;

public class VcInviteCommand {

    private static VcInviteCommand instance;

    public static VcInviteCommand getInstance() {
        if (instance == null) instance = new VcInviteCommand();
        return instance;
    }

    private HashMap<Member, VoiceChannelCommandInfo> cache = new HashMap<>();

    private VcInviteCommand() {}

    public void cleanCache(boolean purge) {
        if (purge) {
            cache.clear();
            return;
        }
        cache.entrySet().removeIf(entry ->
                System.currentTimeMillis() - entry.getValue().timestamp() > Config.getVcTimeout()
        );
    }

    public void handle(SlashCommandInteractionEvent event) {

    }

}

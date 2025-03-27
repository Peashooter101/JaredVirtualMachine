package com.peashooter101.jaredvm.listener.command.subcommand;

import com.peashooter101.jaredvm.config.Config;
import com.peashooter101.jaredvm.data.VoiceChannelCommandInfo;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.HashMap;

public class VcRequestCommand {

    private static VcRequestCommand instance;

    public static VcRequestCommand getInstance() {
        if (instance == null) instance = new VcRequestCommand();
        return instance;
    }

    private HashMap<Member, VoiceChannelCommandInfo> cache = new HashMap<>();

    private VcRequestCommand() {}

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

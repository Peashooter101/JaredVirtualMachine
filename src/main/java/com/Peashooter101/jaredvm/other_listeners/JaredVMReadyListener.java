package com.Peashooter101.jaredvm.other_listeners;

import com.Peashooter101.jaredvm.JaredVM;
import com.Peashooter101.jaredvm.command_listeners.VCInviteListener;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.utils.cache.SnowflakeCacheView;
import org.jetbrains.annotations.NotNull;

public class JaredVMReadyListener extends ListenerAdapter {

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        loadGuildCommands();
        JaredVM.getApi().addEventListener(new VCInviteListener());
    }

    private static void loadGuildCommands() {
        System.out.println("Loading Guild Commands");
        SnowflakeCacheView<Guild> guilds = JaredVM.getApi().getGuildCache();
        System.out.println(guilds.size());
        for (Guild g : guilds) {
            System.out.println("Found Guild: " + g.getName());
            g.updateCommands().addCommands(
                    Commands.slash("vc-invite", "Invite a user to a private VC")
                            .addOption(OptionType.USER, "user", "The user to invite (Must already be in a VC).")
            ).queue();
        }
    }

}

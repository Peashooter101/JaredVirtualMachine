package com.Peashooter101.jaredvm;

import com.Peashooter101.jaredvm.listeners.message.PingPongListener;
import com.Peashooter101.jaredvm.listeners.other.JaredVMReadyListener;
import com.Peashooter101.jaredvm.utility.AuthHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class JaredVM {
    private static JDA api = null;

    public static void main(String[] arguments) {

        try {
            api = JDABuilder.create(AuthHandler.getBotToken(),
                    GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS,
                    GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
                    GatewayIntent.GUILD_PRESENCES
            ).build();
        }
        catch (LoginException e) {
            e.printStackTrace();
            System.out.println("Failed to login, exiting...");
            return;
        }

        api.addEventListener(new PingPongListener());
        api.addEventListener(new JaredVMReadyListener());

    }

    public static JDA getApi() {
        return api;
    }

    public static void updateCommands(Guild g) {
        g.updateCommands()
                .addCommands(
                        Commands.slash("vc-invite", "Invite a user to a private VC")
                                .addOption(OptionType.USER, "user", "The user to invite (Must already be in a VC).", true),

                        Commands.slash("abby", "Used for amazing abby pictures."),

                        Commands.slash("valorant", "Valorant related stuff!").addSubcommands(
                                new SubcommandData("profile", "Get the profile of the user!")
                                        .addOption(OptionType.STRING, "user", "Example: Peashooter101#7016", true),
                                new SubcommandData("rank", "Get the ranked data of the user!")
                                        .addOption(OptionType.STRING, "user", "Example: Peashooter101#7016", true)),
                        Commands.message("Pin Message")
                        // Commands.message("Evaluate Embed") // Used for testing and will be disabled normally.
                        )
                .queue();
    }

}

package com.peashooter101.jaredvm;

import com.peashooter101.jaredvm.listener.command.VoiceChannelCommand;
import com.peashooter101.jaredvm.listener.event.UpdateCommandsListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class JaredVM {
    private static JDA api = null;

    public static void main(String[] arguments) {

        api = JDABuilder.create(getBotToken(null),
                GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_VOICE_STATES,
                GatewayIntent.GUILD_PRESENCES
        ).build();

        addListeners();
    }

    public static JDA getApi() {
        return api;
    }

    private static void addListeners() {
        api.addEventListener(new UpdateCommandsListener());

        api.addEventListener(new VoiceChannelCommand());
    }

    public static void updateCommands(Guild g) {
        g.updateCommands()
                .addCommands(
                        Commands.slash("vc", "Main Voice Channel Command")
                                .addSubcommands(
                                        new SubcommandData("invite", "Invite someone to the voice channel.")
                                                .addOption(OptionType.USER, "user", "Person to invite.", true),
                                        new SubcommandData("request", "Ask to get pulled into a voice channel.")
                                                .addOption(OptionType.USER, "user", "Person you are joining on.", true)
                                        // TODO: Create Temp Channel
                                )
                )
                .queue();
    }


    private static String getBotToken(String fileName) {
        String token = null;
        if (fileName == null) fileName = "JaredVM_data/BotToken";
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            token = scanner.nextLine();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }
}

package com.Peashooter101.jaredvm;

import com.Peashooter101.jaredvm.message_listeners.PingPongListener;
import com.Peashooter101.jaredvm.other_listeners.JaredVMReadyListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class JaredVM {
    private static String botToken = null;
    private static JDA api = null;

    public static void main(String[] arguments) throws Exception {
        api = JDABuilder.create(retrieveToken(),
                GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
                GatewayIntent.GUILD_PRESENCES
        ).build();

        api.addEventListener(new PingPongListener());
        api.addEventListener(new JaredVMReadyListener());

    }

    public static String retrieveToken() throws IOException {
        if (botToken != null) { return botToken; }
        Scanner scan = new Scanner(new File("JaredVM_data/BotToken"));
        if (scan.hasNextLine()) {
            botToken = scan.nextLine();
        }
        else {
            throw new IOException("File not Found: ./JaredVM_data/BotToken");
        }
        return botToken;
    }

    public static JDA getApi() {
        return api;
    }
}

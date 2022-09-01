package com.Peashooter101.jaredvm.utility;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class AuthHandler {

    public static String botToken;
    public static String imgurClientId;

    public static String getBotToken() throws IOException {
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

    public static String getImgurClientId() throws IOException {
        if (imgurClientId != null) { return imgurClientId; }
        Scanner scan = new Scanner(new File("JaredVM_data/imgur"));
        if (scan.hasNextLine()) {
            imgurClientId = scan.nextLine();
        }
        else {
            throw new IOException("File not Found: ./JaredVM_data/imgur");
        }
        return imgurClientId;
    }

}

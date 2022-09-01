package com.Peashooter101.jaredvm.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class AuthHandler {

    public static Logger logger = LoggerFactory.getLogger(AuthHandler.class);
    public static String botToken;
    public static String imgurClientId;

    public static String getBotToken() {
        if (botToken != null) { return botToken; }

        Scanner scan;
        try {
            scan = new Scanner(new File("JaredVM_data/BotToken"));
        }
        catch (FileNotFoundException e) {
            logger.error("Bot token not found: JaredVM_data/BotToken");
            return null;
        }

        if (scan.hasNextLine()) {
            botToken = scan.nextLine();
        }
        else {
            logger.error("No contents: JaredVM_data/BotToken");
            return null;
        }
        return botToken;
    }

    public static String getImgurClientId() {
        if (imgurClientId != null) { return imgurClientId; }

        Scanner scan;
        try {
            scan = new Scanner(new File("JaredVM_data/imgur"));
        }
        catch (FileNotFoundException e) {
            logger.error("Client ID not found: JaredVM_data/imgur");
            return null;
        }

        if (scan.hasNextLine()) {
            imgurClientId = scan.nextLine();
        }
        else {
            logger.error("No contents: JaredVM_data/imgur");
            return null;
        }
        return imgurClientId;
    }

}

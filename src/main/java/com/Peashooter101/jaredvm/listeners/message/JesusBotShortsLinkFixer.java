package com.Peashooter101.jaredvm.listeners.message;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JesusBotShortsLinkFixer extends ListenerAdapter {

    private final static Pattern youtubeShortsLinkCommand = Pattern.compile("!play (?:https://)?(?:www\\.)?youtube\\.com/shorts/([a-zA-Z0-9\\-_]*)(?:\\?si=[a-zA-Z0-9\\-_]*)?");
    private final static Pattern youtubeShortsLinkMention = Pattern.compile(".* play (?:https://)?(?:www\\.)?youtube\\.com/shorts/([a-zA-Z0-9\\-_]*)(?:\\?si=[a-zA-Z0-9\\-_]*)?");

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;
        Message message = event.getMessage();
        String content = message.getContentRaw();
        Matcher matcherCommand = youtubeShortsLinkCommand.matcher(content);
        Matcher matcherMention = youtubeShortsLinkMention.matcher(content);
        if (matcherCommand.matches() || matcherMention.matches())
        {
            String videoId = matcherCommand.matches() ? matcherCommand.group(1) : matcherMention.group(1);
            MessageChannel channel = event.getChannel();
            message.reply("Hey, the music bot cannot understand shorts links, use: `https://youtube.com/watch?v=" + videoId + "`.").queue();
        }
    }

    public static void main(String[] args) {
        String content = "!play https://youtube.com/shorts/wsg35Ty11Vs?si=pB7ur0K-xZ2KAWxo";
        Matcher matcherCommand = youtubeShortsLinkCommand.matcher(content);
        Matcher matcherMention = youtubeShortsLinkMention.matcher(content);
        if (matcherCommand.matches() || matcherMention.matches())
        {
            System.out.println("Works");
        }
    }
}

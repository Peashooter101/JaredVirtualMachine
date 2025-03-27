package com.peashooter101.jaredvm.data;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;

public record VoiceChannelCommandInfo(Member memberMove, Member memberTo, VoiceChannel destination, long timestamp) {
}

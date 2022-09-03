package com.Peashooter101.jaredvm.utility.valorant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties( value = { "all_players", "teams", "rounds", "kills" })
public class ValorantMatch {

    Metadata metadata;
    List<ValorantPlayer> red;
    List<ValorantPlayer> blue;

    @JsonIgnoreProperties(value = {"game_version", "game_start", "game_start_patched", "season_id", "platform", "region"})
    public static class Metadata {

        public String map;
        public long game_length;
        public int rounds_played;
        public String mode;
        public String queue;
        public String matchid;
        public String cluster;

    }

}

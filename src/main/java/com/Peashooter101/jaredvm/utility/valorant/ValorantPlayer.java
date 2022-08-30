package com.Peashooter101.jaredvm.utility.valorant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {

        // General Fields
        "currenttier", "player_card", "player_title", "party_id",

        // Field Categories
        "session_playtime", "behavior", "platform", "assets", "economy"

})
public class ValorantPlayer {

    public String puuid;
    public String name;
    public String tag;
    public String team;
    public int level;
    public String character;
    public String currenttier_patched;
    public AbilityUsage ability_casts;
    public PerformanceStats stats;
    public int damage_made;
    public int damage_received;

    public static class AbilityUsage {
        public int c_cast;
        public int q_cast;
        public int e_cast;
        public int x_cast;
    }

    public static class PerformanceStats {
        public int score;
        public int kills;
        public int deaths;
        public int assists;

        public int headshots;
        public int bodyshots;
        public int legshots;
    }



}

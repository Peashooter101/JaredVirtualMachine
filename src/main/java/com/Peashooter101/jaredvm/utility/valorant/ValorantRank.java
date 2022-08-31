package com.Peashooter101.jaredvm.utility.valorant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {
        // Image Fields
        "triangle_up", "triangle_down",

        // Categorical Fields
        "by_season"

})
public class ValorantRank {

    public String name;
    public String tag;
    public String puuid;

    public static class CurrentData {
        public int currenttier;
        public String currenttierpatched;
        public Images images;
        public int ranking_in_tier;
        public int mmr_change_to_last_game;
        public int elo;
        public int games_needed_for_rating;
        public boolean old;
    }

    public static class Images {
        public String small;
        public String large;
    }

    public static class SeasonStats {
        public int wins;
        public int number_of_games;
        public int final_rank;
        public String final_rank_patched;
    }

}

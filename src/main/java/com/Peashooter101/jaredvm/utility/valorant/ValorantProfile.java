package com.Peashooter101.jaredvm.utility.valorant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "last_update", "last_update_raw" })
public class ValorantProfile {

    public String puuid;
    public String region;
    public int account_level;
    public String name;
    public String tag;
    public Card card;

    public static class Card {
        public String small;
        public String large;
        public String wide;
        public String id;
    }

}

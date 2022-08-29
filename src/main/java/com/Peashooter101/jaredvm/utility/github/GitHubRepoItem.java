package com.Peashooter101.jaredvm.utility.github;

public class GitHubRepoItem {

    public String name;
    public String path;
    public String sha;
    public long size;
    public String url;
    public String html_url;
    public String git_url;
    public String download_url;
    public String type;
    public Links _links;

    public static class Links {

        public String self;
        public String git;
        public String html;

    }

}

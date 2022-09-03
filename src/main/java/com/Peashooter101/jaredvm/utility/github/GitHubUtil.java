package com.Peashooter101.jaredvm.utility.github;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class GitHubUtil {

    private static final Logger logger = LoggerFactory.getLogger(GitHubUtil.class);
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    public static ArrayList<GitHubRepoItem> getGitHubItems(String gitHubUri) {
        HttpRequest request = HttpRequest.newBuilder().uri(java.net.URI.create(gitHubUri)).GET().build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch (IOException | InterruptedException e) {
            logger.error("An error has occurred: " + e.getMessage());
            return null;
        }

        ArrayList<GitHubRepoItem> items;

        try {
            items = mapper.readValue(response.body(), new TypeReference<>(){});
        }
        catch (JsonProcessingException e) {
            logger.error("An error has occurred: " + e.getMessage());
            return null;
        }

        return items;
    }

}

package com.Peashooter101.jaredvm.utility.imgur;

import com.Peashooter101.jaredvm.utility.AuthHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class ImgurUtil {

    private static final Logger logger = LoggerFactory.getLogger(ImgurUtil.class);
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    private static final String imgurImageEndpoint = "https://api.imgur.com/3/image";

    public static String postImage(BufferedImage image) {

        String imageB64 = imageToB64(image);
        if (imageB64 == null) { return null; }

        HttpResponse<String> response = postToImgur(imageB64);
        if (response == null) { return null; }

        if (response.statusCode() != 200) {
            logger.error("Imgur POST Image failed with status code " + response.statusCode());
            return null;
        }

        JsonNode node;
        try {
            node = mapper.readValue(response.body(), JsonNode.class);
        }
        catch (JsonProcessingException e) {
            logger.error("Jackson could not parse response body.");
            return null;
        }

        return node.get("data").get("link").toString();

    }

    private static String imageToB64(@NotNull BufferedImage image) {
        ByteArrayOutputStream output = new ByteArrayOutputStream(8192);
        try {
            ImageIO.write(image, "png", output);
        }
        catch (IOException e) {
            logger.error("There was an issue converting image to Base 64.");
            return null;
        }
        return Base64.getEncoder().encodeToString(output.toByteArray());
    }

    private static HttpResponse<String> postToImgur(@NotNull String base64Image) {
        String requestBody = String.format("{\"image\":\"%s\"}", base64Image);

        URI uri;
        try {
            uri = new URI(imgurImageEndpoint);
        }
        catch (URISyntaxException e) {
            logger.error("The URI provided is invalid: " + imgurImageEndpoint);
            return null;
        }

        String clientId = AuthHandler.getImgurClientId();

        if (clientId == null) {
            logger.error("Imgur Client ID could not be found...");
            return null;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Authorization", "Client-ID " + clientId)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();

        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch (IOException | InterruptedException e) {
            logger.error(e.getMessage() + " was thrown while sending HTTP request.");
            return null;
        }
    }

}

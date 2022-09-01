package experimentation;

import com.Peashooter101.jaredvm.utility.AuthHandler;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class ImageToBase64Test {

    @Test
    public String convertImageTo64() throws IOException {
        BufferedImage image = ImageIO.read(new File("JaredVM_data/Experimentation/Images/thumbnail.png"));

        ByteArrayOutputStream output = new ByteArrayOutputStream(8192);
        ImageIO.write(image, "png", output);
        return Base64.getEncoder().encodeToString(output.toByteArray());
    }

    @Test
    public void uploadImageToImgur() throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String requestBody = String.format("{\"image\":\"%s\"}", convertImageTo64());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.imgur.com/3/image"))
                .header("Authorization", "Client-ID " + AuthHandler.getImgurClientId())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
    }

}

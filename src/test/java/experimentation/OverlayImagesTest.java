package experimentation;

import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class OverlayImagesTest {

    public static final String expImagesPath = "JaredVM_data/Valorant Account Borders/";
    public static final String outputPath = "JaredVM_data/Experimentation/Images/";

    public static final String valorantGoldIconURI = "https://media.valorant-api.com/competitivetiers/03621f52-342b-cf4e-4f86-9350a49c6d04/14/largeicon.png";
    public static final String valorantOmenWideBannerURI = "https://media.valorant-api.com/playercards/7b240a91-4925-8bb6-7812-60b49543e145/wideart.png";

    public static final String valorantBorder = "Borders/t0.png";
    public static final String valorantOmenSmallURI = "https://media.valorant-api.com/playercards/7b240a91-4925-8bb6-7812-60b49543e145/smallart.png";

    @Test
    public void generateThumbnail() {
        BufferedImage valorantAccLevel;
        BufferedImage omenSmall;

        // Get assets
        try {
            valorantAccLevel = ImageIO.read(new File(expImagesPath + valorantBorder));
            omenSmall = ImageIO.read(new URL(valorantOmenSmallURI));
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Merge Image
        BufferedImage thumbnail = new BufferedImage(valorantAccLevel.getWidth(), valorantAccLevel.getHeight(), BufferedImage.TYPE_INT_ARGB);
        double scaleFactor = 1;
        int scale = (int) (scaleFactor * omenSmall.getWidth());
        int offsetX = (thumbnail.getWidth() - scale) / 2;
        int offsetY = ((thumbnail.getHeight() - scale) / 2) - 11;

        Graphics g = thumbnail.getGraphics();
        g.drawImage(omenSmall, offsetX, offsetY, scale, scale, null);
        g.drawImage(valorantAccLevel, 0, 0, null);
        g.dispose();

        // Output Image
        try {
            ImageIO.write(thumbnail, "PNG", new File(outputPath + "thumbnail.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void mergeRankAndBanner() {
        BufferedImage goldIcon;
        BufferedImage omenWide;

        // Get assets from online source
        try {
            goldIcon = ImageIO.read(new URL(valorantGoldIconURI));
            omenWide = ImageIO.read(new URL(valorantOmenWideBannerURI));
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Merge Image
        BufferedImage merged = new BufferedImage(omenWide.getWidth(), omenWide.getHeight(), BufferedImage.TYPE_INT_ARGB);
        double scaleFactor = 0.85;
        int scale = (int) (scaleFactor * omenWide.getHeight());
        int offsetY = (int) ((1 - scaleFactor) * omenWide.getHeight() / 2);
        int offsetX = omenWide.getWidth() - scale - offsetY;

        Graphics g = merged.getGraphics();
        g.drawImage(omenWide, 0, 0, null);
        g.drawImage(goldIcon, offsetX, offsetY, scale, scale, null);
        g.dispose();

        // Output Image
        try {
            ImageIO.write(merged, "PNG", new File(outputPath + "merged.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // TODO: Send to Imgur?: https://apidocs.imgur.com/
    }

}

package com.Peashooter101.experimentation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class OverlayImagesExp {

    public static final String valorantGoldIconURI = "https://media.valorant-api.com/competitivetiers/03621f52-342b-cf4e-4f86-9350a49c6d04/14/largeicon.png";
    public static final String valorantOmenWideBannerURI = "https://media.valorant-api.com/playercards/7b240a91-4925-8bb6-7812-60b49543e145/wideart.png";

    public static void main(String[] args) {

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
            ImageIO.write(merged, "PNG", new File("JaredVM_data/Experimentation/Images/merged.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // TODO: Send to Imgur?: https://apidocs.imgur.com/

    }

}

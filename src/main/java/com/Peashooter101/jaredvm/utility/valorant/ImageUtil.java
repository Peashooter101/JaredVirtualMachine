package com.Peashooter101.jaredvm.utility.valorant;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImageUtil {

    public static String generateThumbnail(String smallIconURI, String levelBorderURI) {
        BufferedImage levelBorder;
        BufferedImage smallIcon;

        // Get assets
        try {
            levelBorder = ImageIO.read(new File(levelBorderURI));
            smallIcon = ImageIO.read(new URL(smallIconURI));
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        // Merge Image
        BufferedImage thumbnail = new BufferedImage(levelBorder.getWidth(), levelBorder.getHeight(), BufferedImage.TYPE_INT_ARGB);
        double scaleFactor = (levelBorder.getWidth() / (double) smallIcon.getWidth()) - .25;
        int scale = (int) (scaleFactor * smallIcon.getWidth());
        int offsetX = (thumbnail.getWidth() - scale) / 2;
        int offsetY = ((thumbnail.getHeight() - scale) / 2) - 7;

        Graphics g = thumbnail.getGraphics();
        g.drawImage(smallIcon, offsetX, offsetY, scale, scale, null);
        g.drawImage(levelBorder, 0, 0, null);
        g.dispose();

        // Output Image TODO: Figure out how to return this in a suitable way for an embed.
        return null;
    }

    public static String generateFooter(String wideBannerURI, String rankIconURI) {
        BufferedImage rankIcon;
        BufferedImage wideBanner;

        // Get assets from online source
        try {
            rankIcon = ImageIO.read(new URL(rankIconURI));
            wideBanner = ImageIO.read(new URL(wideBannerURI));
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Merge Image
        BufferedImage merged = new BufferedImage(wideBanner.getWidth(), wideBanner.getHeight(), BufferedImage.TYPE_INT_ARGB);
        double scaleFactor = 0.85;
        int scale = (int) (scaleFactor * wideBanner.getHeight());
        int offsetY = (int) ((1 - scaleFactor) * wideBanner.getHeight() / 2);
        int offsetX = wideBanner.getWidth() - scale - offsetY;

        Graphics g = merged.getGraphics();
        g.drawImage(wideBanner, 0, 0, null);
        g.drawImage(rankIcon, offsetX, offsetY, scale, scale, null);
        g.dispose();
    }

}

package com.Peashooter101.jaredvm.utility.valorant;

import com.Peashooter101.jaredvm.utility.imgur.ImgurUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImageUtil {

    public static String generateThumbnail(String smallIconURI, int level) {
        int tier = level / 20;

        // TODO: Determine image to use based on level.
        String levelBorderURI = "";

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
        double scaleFactor = 1;
        int scale = (int) (scaleFactor * smallIcon.getWidth());
        int offsetX = (thumbnail.getWidth() - scale) / 2;
        int offsetY = ((thumbnail.getHeight() - scale) / 2) - 11;

        Graphics g = thumbnail.getGraphics();
        g.drawImage(smallIcon, offsetX, offsetY, scale, scale, null);
        g.drawImage(levelBorder, 0, 0, null);
        g.dispose();

        return ImgurUtil.postImage(thumbnail);
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
            return null;
        }

        // Merge Image
        BufferedImage footer = new BufferedImage(wideBanner.getWidth(), wideBanner.getHeight(), BufferedImage.TYPE_INT_ARGB);
        double scaleFactor = 0.85;
        int scale = (int) (scaleFactor * wideBanner.getHeight());
        int offsetY = (int) ((1 - scaleFactor) * wideBanner.getHeight() / 2);
        int offsetX = wideBanner.getWidth() - scale - offsetY;

        Graphics g = footer.getGraphics();
        g.drawImage(wideBanner, 0, 0, null);
        g.drawImage(rankIcon, offsetX, offsetY, scale, scale, null);
        g.dispose();

        return ImgurUtil.postImage(footer);
    }

}

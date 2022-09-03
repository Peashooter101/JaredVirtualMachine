package com.Peashooter101.jaredvm.utility.valorant;

import com.Peashooter101.jaredvm.utility.github.GitHubRepoItem;
import com.Peashooter101.jaredvm.utility.github.GitHubUtil;
import com.Peashooter101.jaredvm.utility.imgur.ImgurUtil;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ImageUtil {

    private static final String borderIconsURI = "https://api.github.com/repositories/524571440/contents/JaredVM_data/Valorant%20Account%20Borders/Borders";

    @NotNull
    public static String generateThumbnail(String smallIconURI, int level) {
        int tier = level / 20;
        ArrayList<GitHubRepoItem> borderIcons = GitHubUtil.getGitHubItems(borderIconsURI);
        if (borderIcons == null) { return smallIconURI; }
        String levelBorderURI = (tier < borderIcons.size()) ? borderIcons.get(tier).download_url : borderIcons.get(borderIcons.size()-1).download_url;

        BufferedImage levelBorder;
        BufferedImage smallIcon;

        // Get assets
        try {
            levelBorder = ImageIO.read(new URL(levelBorderURI));
            smallIcon = ImageIO.read(new URL(smallIconURI));
        }
        catch (IOException e) {
            e.printStackTrace();
            return smallIconURI;
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

        String returnUri = ImgurUtil.postImage(thumbnail);

        return (returnUri == null) ? smallIconURI : returnUri;
    }

    @NotNull
    public static String generateFooter(String wideBannerURI, String rankIconURI) {
        BufferedImage rankIcon;
        BufferedImage wideBanner;

        // Get assets from online source
        try {
            rankIcon = ImageIO.read(new URL(rankIconURI));
            wideBanner = ImageIO.read(new URL(wideBannerURI));
        }
        catch (IOException e) {
            return wideBannerURI;
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


        String returnUri = ImgurUtil.postImage(footer);

        return (returnUri == null) ? wideBannerURI : returnUri;
    }

}

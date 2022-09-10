package experimentation;

import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TextOnGifTest {

    private static final String defenestrateGif = "JaredVM_data/Experimentation/Images/defenestrate-window.gif";
    private static final String defenestrateFrames = "JaredVM_data/Experimentation/Images/Defenestrate Frames/";

    @Test
    public void outputAllFrames() {

        ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
        ImageInputStream gifStream;
        try {
            gifStream = ImageIO.createImageInputStream(new File(defenestrateGif));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        reader.setInput(gifStream, false);

        ArrayList<BufferedImage> images = new ArrayList<>();
        for (int i = 0; true; i++) {
            try {
                images.add(reader.read(i));
            }
            catch (IndexOutOfBoundsException | IOException e) {
                break;
            }
        }

        for (int i = 0; i < images.size(); i++) {
            // Output Image
            try {
                ImageIO.write(images.get(i), "PNG", new File(defenestrateFrames + String.format("%02d", i) + ".png"));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}

package xsierra.digitguesser.drawer.pipeline;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BinarizeImageStep extends OrderablePipelineStep {


    public BinarizeImageStep(int stepOpder) {
        super(stepOpder);
    }

    @Override
    public BufferedImage processImage(BufferedImage img) {
        BufferedImage image = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_BYTE_BINARY);
        Graphics g = image.getGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();


        return image;
    }
}

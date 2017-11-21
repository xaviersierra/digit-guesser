package xsierra.digitguesser.drawer.pipeline;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ResizeImageStep extends OrderablePipelineStep implements PipelineStep {

    public ResizeImageStep(int stepOpder) {
        super(stepOpder);
    }

    @Override
    public BufferedImage processImage(BufferedImage img) {

        if (img.getHeight() == 28 && img.getWidth() == 28){
            return img;
        }

        Image tmp = img.getScaledInstance(28, 28, BufferedImage.SCALE_SMOOTH);
        BufferedImage resizedImg = new BufferedImage(28,28,BufferedImage.TYPE_INT_ARGB);
        resizedImg.getGraphics().drawImage(tmp, 0, 0, null);

        return resizedImg;
    }
}

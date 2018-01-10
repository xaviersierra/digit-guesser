package xsierra.digitguesser.drawer.pipeline;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ToRGBStep extends OrderablePipelineStep {

    public ToRGBStep(int stepOpder) {
        super(stepOpder);
    }

    @Override
    public BufferedImage processImage(BufferedImage pngImage) {
        BufferedImage rbgImage = new BufferedImage( pngImage.getWidth(), pngImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        rbgImage.createGraphics().drawImage( pngImage, 0, 0, Color.WHITE, null);
        return rbgImage;
    }
}

package xsierra.digitguesser.drawer.pipeline;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GrayScaleFilter extends OrderablePipelineStep {

    public GrayScaleFilter(int stepOpder) {
        super(stepOpder);
    }

    @Override
    public BufferedImage processImage(BufferedImage img) {

        Image grayImg = GrayFilter.createDisabledImage( img );

        BufferedImage grayScaleImage = new BufferedImage( img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB );
        Graphics g = grayScaleImage.getGraphics();
        g.drawImage( grayImg, 0, 0, null );
        g.dispose();

        return grayScaleImage;
    }
}

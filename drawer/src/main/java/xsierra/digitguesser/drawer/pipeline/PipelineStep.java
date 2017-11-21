package xsierra.digitguesser.drawer.pipeline;

import java.awt.image.BufferedImage;

public interface PipelineStep {

    int getStepOrder();

    BufferedImage processImage(BufferedImage img);
}

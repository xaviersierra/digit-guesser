package xsierra.digitguesser.drawer.pipeline;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;



public class ToRGBStepTest {

    private ToRGBStep filter = new ToRGBStep(1);

    @Test
    public void processImage() throws IOException {

        BufferedImage img1 = ImageIO.read(new ClassPathResource("7_drawn.png").getInputStream());

        BufferedImage rgbImage = filter.processImage(img1);

        assertThat(rgbImage.getTransparency()).isEqualTo(Transparency.OPAQUE);
    }
}
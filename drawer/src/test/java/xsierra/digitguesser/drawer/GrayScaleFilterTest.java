package xsierra.digitguesser.drawer;

import org.assertj.core.data.Percentage;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import xsierra.digitguesser.drawer.pipeline.GrayScaleFilter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;


public class GrayScaleFilterTest {

    private GrayScaleFilter filter = new GrayScaleFilter(1);

    @Test
    public void getStepOrder() throws Exception {
        assertThat(filter.getStepOrder()).isEqualTo(1);

    }

    @Test
    public void processImage() throws Exception {

        BufferedImage img1 = ImageIO.read(new ClassPathResource("7.png").getInputStream());

        BufferedImage img2 = filter.processImage(img1);

        assertThat(img2).isNotEqualTo(img1);

    }

}
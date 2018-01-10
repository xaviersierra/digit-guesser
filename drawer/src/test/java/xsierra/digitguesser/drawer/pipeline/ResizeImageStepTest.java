package xsierra.digitguesser.drawer.pipeline;

import org.assertj.core.data.Percentage;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import xsierra.digitguesser.drawer.pipeline.ResizeImageStep;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ResizeImageStepTest {

    private ResizeImageStep resizeImageStep = new ResizeImageStep(1);


    BufferedImage img1;

    @Before
    public void setUp() throws Exception{
        img1 = ImageIO.read(new ClassPathResource("7.png").getInputStream());
    }


    @Test
    public void testProcessImageSameSize() throws Exception {

        BufferedImage img2 = resizeImageStep.processImage(img1);

        assertThat(img2).isEqualTo(img1);

    }

    @Test
    public void testProcessImageDifferentSize() throws Exception {

        Image tmp = img1.getScaledInstance(35, 35, BufferedImage.SCALE_SMOOTH);
        BufferedImage upscaledImg1 = new BufferedImage(35,35,BufferedImage.TYPE_INT_ARGB);
        upscaledImg1.getGraphics().drawImage(tmp, 0, 0, null);

        BufferedImage img2 = resizeImageStep.processImage(upscaledImg1);

        int[] pixelsImg2 = img2.getRGB(0, 0, img2.getWidth(), img2.getHeight(), null, 0, img2.getWidth());

        int[] pixelsImg1 = img1.getRGB(0, 0, img1.getWidth(), img1.getHeight(), null, 0, img1.getWidth());

        double averagePixelImg1 = Arrays.stream(pixelsImg1).average().getAsDouble();

        double averagePixelImg2 = Arrays.stream(pixelsImg2).average().getAsDouble();

        assertThat(averagePixelImg1).isCloseTo(averagePixelImg2, Percentage.withPercentage(0.1));
        assertThat(img2).isNotEqualTo(img1);

        assertThat(img2.getWidth()).isEqualTo(28);
        assertThat(img2.getHeight()).isEqualTo(28);

    }

}
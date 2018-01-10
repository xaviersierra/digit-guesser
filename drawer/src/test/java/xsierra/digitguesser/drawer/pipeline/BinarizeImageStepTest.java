package xsierra.digitguesser.drawer.pipeline;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class BinarizeImageStepTest {

    private BinarizeImageStep filter = new BinarizeImageStep(2);

    @Test
    public void processImage() throws Exception {

        BufferedImage img1 = ImageIO.read(new ClassPathResource("7.png").getInputStream());

        BufferedImage img2 = filter.processImage(img1);

        assertThat(img2).isNotEqualTo(img1);

        int[] pixels = img2.getRGB(0, 0, img2.getWidth(), img2.getHeight(), null, 0, img2.getWidth());

        Stream<Integer> pixelStream = Arrays.stream(pixels).boxed().map(p -> p & 0xff);

        Map<Integer, Long> pixelDistribution = pixelStream.collect(Collectors.groupingBy(p -> p, Collectors.counting()));

        assertThat(pixelDistribution.size()).isEqualTo(2);

    }

}
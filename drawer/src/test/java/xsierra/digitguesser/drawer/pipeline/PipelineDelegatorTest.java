package xsierra.digitguesser.drawer.pipeline;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import xsierra.digitguesser.drawer.DigitGuesserService;
import xsierra.digitguesser.drawer.pipeline.PipelineDelegator;
import xsierra.digitguesser.drawer.pipeline.PipelineStep;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = {PipelineDelegator.class})
@RunWith(SpringRunner.class)
public class PipelineDelegatorTest {

    @MockBean
    private PipelineStep step;

    @MockBean
    private DigitGuesserService model;

    @Autowired
    private PipelineDelegator delegator;

    @Test
    public void imageGuessDigit() throws Exception {

        BufferedImage img1 = ImageIO.read(new ClassPathResource("7.png").getInputStream());

        BufferedImage img2 = ImageIO.read(new ClassPathResource("7.png").getInputStream());

        given(step.processImage(img1)).willReturn(img2);

        given(model.guessDigit(img2)).willReturn((byte)7);

        assertThat(delegator.imageGuessDigit(img1)).isEqualTo((byte) 7);

    }

}
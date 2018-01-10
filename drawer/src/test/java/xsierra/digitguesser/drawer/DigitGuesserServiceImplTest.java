package xsierra.digitguesser.drawer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceTestConfig.class)
public class DigitGuesserServiceImplTest {

    @Autowired
    private DigitGuesserServiceImpl service;

    @Autowired
    @Qualifier("sampleImage")
    private BufferedImage sample;

    @Test
    public void guessDigitIdealCase() throws IOException {

        byte guessedDigit = service.guessDigit(sample);

        assertThat(guessedDigit).isEqualTo((byte) 7);

    }

}
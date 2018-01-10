package xsierra.digitguesser.drawer;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.factory.Nd4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import xsierra.digitguesser.drawer.pipeline.BinarizeImageStep;
import xsierra.digitguesser.drawer.pipeline.ToRGBStep;
import xsierra.digitguesser.drawer.pipeline.ToRGBStepTest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Configuration
public class ServiceTestConfig {

    @Bean
    public MultiLayerNetwork model() throws IOException {
        ClassPathResource modelResource = new ClassPathResource("model.m");
        Nd4j.getRandom().setSeed(123);
        return ModelSerializer.restoreMultiLayerNetwork(modelResource.getFile());
    }

    @Bean("sampleImage")
    public BufferedImage sampleImage() throws IOException {
        BufferedImage image = ImageIO.read(new ClassPathResource("7_drawn.png").getInputStream());
        BufferedImage rgbImage = new ToRGBStep(0).processImage(image);
        return new BinarizeImageStep(1).processImage(rgbImage);
    }

    @Bean
    public DigitGuesserService digitGuesserService(MultiLayerNetwork model){
        return new DigitGuesserServiceImpl(model);
    }

}

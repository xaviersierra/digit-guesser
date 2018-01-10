package xsierra.digitguesser.drawer;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.factory.Nd4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import xsierra.digitguesser.drawer.pipeline.BinarizeImageStep;
import xsierra.digitguesser.drawer.pipeline.ResizeImageStep;
import xsierra.digitguesser.drawer.pipeline.ToRGBStep;

import java.io.IOException;

@SpringBootApplication
public class Drawer {

    public static void main(String[] args) {
        SpringApplication.run(Drawer.class, args);
    }

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory factory =
                new TomcatEmbeddedServletContainerFactory();
        return factory;
    }

    @Bean
    public ResizeImageStep resizeImageStep() {
        return new ResizeImageStep(0);
    }

    @Bean
    public ToRGBStep rgbStep() {
        return new ToRGBStep(1);
    }

    @Bean
    public BinarizeImageStep binarizeImageStep() {
        return new BinarizeImageStep(2);
    }

    @Bean
    public MultiLayerNetwork model(ModelLoader modelLoader) throws IOException {
        return modelLoader.loadModel();
    }

}

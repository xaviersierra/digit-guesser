package xsierra.digitguesser.drawer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import xsierra.digitguesser.drawer.pipeline.BinarizeImageStep;
import xsierra.digitguesser.drawer.pipeline.GrayScaleFilter;
import xsierra.digitguesser.drawer.pipeline.ResizeImageStep;

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

    public BinarizeImageStep binarizeImageStep() {
        return new BinarizeImageStep(1);
    }

//    @Bean
//    public GrayScaleFilter grayScaleFilter(){
//        return new GrayScaleFilter(1);
//    }
}

package xsierra.digitguesser;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import xsierra.digitguesser.initializer.InitializerService;

@SpringBootApplication
@EnableAutoConfiguration
@Profile("!test")
public class Trainer implements CommandLineRunner{

    public static void main(String[] args) {
        SpringApplication.run(Trainer.class, args);
    }

    @Autowired
    private InitializerService initializerService;

    @Autowired
    private TrainerService trainer;

    @Autowired
    private TrainingProperties properties;

    @Override
    public void run(String... args) throws Exception {

        initializerService.initializeData();;
        MultiLayerNetwork model = trainer.buildModel();
        trainer.exportModel(model, properties.getPath());

    }
}

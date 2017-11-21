package xsierra.digitguesser;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xsierra.digitguesser.initializer.InitializerService;
import xsierra.digitguesser.trainer.Context;
import xsierra.digitguesser.trainer.ConvolutionalTrainer;
import xsierra.digitguesser.trainer.HyperParameter;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Service
public class TrainerService {

    private ConvolutionalTrainer convolutionalTrainer;

    private TrainingProperties properties;

    private InitializerService initializer;

    @Autowired
    public TrainerService(ConvolutionalTrainer convolutionalTrainer, TrainingProperties properties, InitializerService initializer) {
        this.convolutionalTrainer = convolutionalTrainer;
        this.properties = properties;
        this.initializer = initializer;
    }

//    @PostConstruct
    public void buildModel() throws IOException{

        initializer.initializeData();

        Context trainingContext = new Context();
        trainingContext.putHyperParameter(HyperParameter.BATCH_SIZE, Long.valueOf(properties.getBatchSize()));
        trainingContext.putHyperParameter(HyperParameter.NUMBER_OF_EPOCHS, Long.valueOf(properties.getEpochs()));
        trainingContext.putHyperParameter(HyperParameter.NUMBER_OF_ITERATIONS, Long.valueOf(properties.getIterations()));
        trainingContext.putHyperParameter(HyperParameter.RANDOM_SEED, Long.valueOf(properties.getRandomSeed()));
        trainingContext.putHyperParameter(HyperParameter.NUMBER_OF_CLASSES, Long.valueOf(properties.getClasses()));
        trainingContext.putHyperParameter(HyperParameter.NUMBER_OF_CHANNEL, Long.valueOf(properties.getChannels()));

        String modelFilePath = properties.getPath();

        File file = new File(modelFilePath);

        if (file.exists()){
            file.delete();
        } else {
            file.mkdirs();
        }
        file.createNewFile();

        MultiLayerNetwork model = convolutionalTrainer.trainModel(trainingContext);

        ModelSerializer.writeModel(model, file, true);

    }
}

package xsierra.digitguesser;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xsierra.digitguesser.initializer.InitializerService;
import xsierra.digitguesser.trainer.Context;
import xsierra.digitguesser.trainer.ConvolutionalTrainer;

import java.io.File;
import java.io.IOException;

@Service
public class TrainerServiceImpl implements TrainerService {

    private ConvolutionalTrainer convolutionalTrainer;

    private TrainingProperties properties;

    @Autowired
    public TrainerServiceImpl(ConvolutionalTrainer convolutionalTrainer, TrainingProperties properties) {
        this.convolutionalTrainer = convolutionalTrainer;
        this.properties = properties;
    }

    @Override
    public MultiLayerNetwork buildModel() throws IOException{

        Context trainingContext = properties.buildTrainingContext();

        return convolutionalTrainer.trainModel(trainingContext);

    }

    @Override
    public void exportModel(MultiLayerNetwork model, String exportPath) throws IOException {

        File file = lookFilePath(exportPath);

        initializeModelFile(file);

        ModelSerializer.writeModel(model, file, false);

    }

    protected void initializeModelFile(File file) throws IOException {
        if (file.exists()){
            file.delete();
        } else {
            file.mkdirs();
        }
        file.createNewFile();
    }

    protected File lookFilePath(String modelFilePath){
        File file = new File(modelFilePath);

        if (!file.canWrite()){
            throw new SecurityException("Model path is not accesible");
        }

        return file;
    }

}

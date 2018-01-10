package xsierra.digitguesser;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

import java.io.IOException;

public interface TrainerService {
    //    @PostConstruct
    MultiLayerNetwork buildModel() throws IOException;

    void exportModel(MultiLayerNetwork model, String exportPath) throws IOException;
}

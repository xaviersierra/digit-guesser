package xsierra.digitguesser.drawer;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class ModelLoaderImpl implements ModelLoader {

    private ModelProperties modelProperties;

    @Autowired
    public ModelLoaderImpl(ModelProperties modelProperties) {
        this.modelProperties = modelProperties;
    }

    @Override
    public MultiLayerNetwork loadModel() {
        File modelFile = new File(modelProperties.getPath());

        if (!modelFile.exists()) {
            throw new RuntimeException("Model file does not exists");
        }

        try {

            return ModelSerializer.restoreMultiLayerNetwork(modelFile);

        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected issue loading the model", ioe);
        }
    }
}

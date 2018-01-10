package xsierra.digitguesser.drawer;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

public interface ModelLoader {
    MultiLayerNetwork loadModel();
}

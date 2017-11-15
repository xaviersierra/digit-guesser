package xsierra.digitguesser.initializer.domain;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class ImageDataSet {

    private INDArray features;

    private INDArray labels;

    public void stack(INDArray features, INDArray labels){
        if (this.features == null){
            this.features = features;
        } else {
            this.features = Nd4j.vstack(this.features, features);
        }

        if (this.labels == null){
            this.labels = labels;
        } else {
            this.labels = Nd4j.vstack(this.labels, labels);
        }
    }

    public INDArray getFeatures() {
        return features;
    }

    public void setFeatures(INDArray features) {
        this.features = features;
    }

    public INDArray getLabels() {
        return labels;
    }

    public void setLabels(INDArray labels) {
        this.labels = labels;
    }
}

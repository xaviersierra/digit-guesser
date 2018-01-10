package xsierra.digitguesser.initializer.domain;

import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import static org.assertj.core.api.Assertions.*;


public class ImageDataSetTest {
    @Test
    public void testStack() throws Exception {
        ImageDataSet dataSet = new ImageDataSet();
        double[] features = {1 , 2, 3};
        double[] labels = {1};

        dataSet.stack(Nd4j.create(features), Nd4j.create(labels));

        int[] featureShape = {1, 3};
        assertThat(dataSet.getFeatures().shape()).isEqualTo(featureShape);

        int[] labelShape = {1, 1};
        assertThat(dataSet.getLabels().shape()).isEqualTo(labelShape);

        dataSet.stack(Nd4j.create(features), Nd4j.create(labels));

        featureShape[0] = 2;
        assertThat(dataSet.getFeatures().shape()).isEqualTo(featureShape);

        labelShape[0] = 2;
        assertThat(dataSet.getLabels().shape()).isEqualTo(labelShape);

        INDArray firstRow = dataSet.getFeatures().getRow(0);
        double secondValue = firstRow.getDouble(1);
        assertThat(secondValue).isEqualTo(2);

    }

}
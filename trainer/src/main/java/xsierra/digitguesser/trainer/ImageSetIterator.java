package xsierra.digitguesser.trainer;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.DataSetPreProcessor;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.ops.transforms.Transforms;
import xsierra.digitguesser.initializer.domain.Sample;

import java.util.Arrays;
import java.util.List;

public class ImageSetIterator implements DataSetIterator{

    private List<Sample> samples;

    private int cursor;

    private int batch;

    public ImageSetIterator(List<Sample> samples, int batch) {
        this.samples = samples;
        this.cursor = 0;
        this.batch = batch;
    }


    @Override
    public DataSet next(int num) {
        double[][] featureData = new double[num][0];
        double[][] labelData = new double[num][0];

        int actualExamples = 0;
        for (int i = 0; i < num; i++, cursor++) {
            if(!hasMore())
                break;

            Sample s = this.samples.get(cursor);
            int[] img = s.getImage();
            double[] featureVec = new double[img.length];

            for (int j = 0; j < img.length; j++) {
//                int pixel = img[j];
//                if (pixel < 30){
//                    featureVec[j] = 0;
//                } else {
//                    featureVec[j] = 1;
//                }
                featureVec[j] = img[j];
            }

            featureData[i] = featureVec;

            labelData[i] = new double[totalOutcomes()];
            labelData[i][s.getLabel()] = 1.0f;
            actualExamples++;
        }

        if (actualExamples < num) {
            featureData = Arrays.copyOfRange(featureData, 0, actualExamples);
            labelData = Arrays.copyOfRange(labelData, 0, actualExamples);
        }


        INDArray features = Transforms.normalizeZeroMeanAndUnitVariance(Nd4j.create(featureData));
//        INDArray features = Nd4j.create(featureData);
        INDArray labels = Nd4j.create(labelData);

        return new DataSet(features, labels);
    }

    private boolean hasMore() {
        return cursor < samples.size();
    }

    @Override
    public int totalExamples() {
        return samples.size();
    }

    @Override
    public int inputColumns() {
        return samples.get(0).getImage().length;
    }

    @Override
    public int totalOutcomes() {
        return 10;
    }

    @Override
    public boolean resetSupported() {
        return false;
    }

    @Override
    public boolean asyncSupported() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public int batch() {
        return this.batch;
    }

    @Override
    public int cursor() {
        return cursor;
    }

    @Override
    public int numExamples() {
        return samples.size();
    }

    @Override
    public void setPreProcessor(DataSetPreProcessor preProcessor) {

    }

    @Override
    public DataSetPreProcessor getPreProcessor() {
        return null;
    }

    @Override
    public List<String> getLabels() {
        return null;
    }

    @Override
    public boolean hasNext() {
        return hasMore();
    }

    @Override
    public DataSet next() {
        return next(batch);
    }
}

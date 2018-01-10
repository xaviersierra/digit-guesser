package xsierra.digitguesser;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import xsierra.digitguesser.trainer.Context;
import xsierra.digitguesser.trainer.HyperParameter;

@Component
@ConfigurationProperties("trainer")
public class TrainingProperties {

    private int batchSize;

    private int epochs;

    private int channels;

    private int classes;

    private int iterations;

    private int randomSeed;

    private String path;


    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public int getEpochs() {
        return epochs;
    }

    public void setEpochs(int epochs) {
        this.epochs = epochs;
    }

    public int getChannels() {
        return channels;
    }

    public void setChannels(int channels) {
        this.channels = channels;
    }

    public int getClasses() {
        return classes;
    }

    public void setClasses(int classes) {
        this.classes = classes;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public int getRandomSeed() {
        return randomSeed;
    }

    public void setRandomSeed(int randomSeed) {
        this.randomSeed = randomSeed;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    Context buildTrainingContext() {
        Context trainingContext = new Context();
        trainingContext.putHyperParameter(HyperParameter.BATCH_SIZE, Long.valueOf(getBatchSize()));
        trainingContext.putHyperParameter(HyperParameter.NUMBER_OF_EPOCHS, Long.valueOf(getEpochs()));
        trainingContext.putHyperParameter(HyperParameter.NUMBER_OF_ITERATIONS, Long.valueOf(getIterations()));
        trainingContext.putHyperParameter(HyperParameter.RANDOM_SEED, Long.valueOf(getRandomSeed()));
        trainingContext.putHyperParameter(HyperParameter.NUMBER_OF_CLASSES, Long.valueOf(getClasses()));
        trainingContext.putHyperParameter(HyperParameter.NUMBER_OF_CHANNEL, Long.valueOf(getChannels()));
        return trainingContext;
    }
}

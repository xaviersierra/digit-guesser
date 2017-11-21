package xsierra.digitguesser.drawer;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.ops.transforms.Transforms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@Service
public class DigitGuesserServiceImpl implements DigitGuesserService {

    private ModelProperties modelProperties;

    private MultiLayerNetwork model;

    @Autowired
    public DigitGuesserServiceImpl(ModelProperties modelProperties) {
        this.modelProperties = modelProperties;
    }

    @PostConstruct
    public void init() throws Exception{

        File modelFile = new File(modelProperties.getPath());

        if (!modelFile.exists()){
            throw new RuntimeException("Model file does not exists");
        }

        model = ModelSerializer.restoreMultiLayerNetwork(modelFile);
    }

    @Override
    public byte guessDigit(BufferedImage image) {

//        int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
//        System.out.println("pixels lenght: " + pixels.length);

        int width = image.getWidth();
        int height = image.getHeight();

        int[] pixels = new int[784];
        for (int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                int rgb = image.getRGB(i, j);
                Color color = new Color(rgb, true);
                double grayLevel = color.getRed() * 0.299 + color.getGreen() * 0.587 + color.getBlue() * 0.114;
                if (color.getAlpha() > 30){
                    pixels[i + (28 * j)] = 0;
                } else {
                    pixels[i + (28 * j)] = 1;
                }
//                pixels[i + (28 * j)] = color.getAlpha();
            }
        }

        System.out.println(Arrays.toString(Arrays.stream(pixels).distinct().toArray()));

        double[] dPixels = Arrays.stream(pixels).asDoubleStream().toArray();

        System.out.println("Min value=" + Arrays.stream(dPixels).min().getAsDouble());
        System.out.println("Max value=" + Arrays.stream(dPixels).max().getAsDouble());

//        INDArray features = Transforms.normalizeZeroMeanAndUnitVariance(Nd4j.create(dPixels));

        INDArray features = Nd4j.create(dPixels);

        INDArray result = model.output(features, false);

        double lastProbability = 0;
        double digit = 0;
        for (int i = 0; i < 10; i++){
            double probability = result.getDouble(i);
            if (probability > lastProbability){
                lastProbability = probability;
                digit = i;
            }
            System.out.println("index: " + i + " probability: " + probability);
        }
        return (byte) digit;
    }

}

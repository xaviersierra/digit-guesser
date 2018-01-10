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
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DigitGuesserServiceImpl implements DigitGuesserService {

    private MultiLayerNetwork model;

    @Autowired
    public DigitGuesserServiceImpl(MultiLayerNetwork model) {
        this.model = model;
    }

    @Override
    public byte guessDigit(BufferedImage image) {

        int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());

        pixels = Arrays.stream(pixels).map(p -> p & 0xff).map(p -> p < 30 ? 1: 0).toArray();

        long zeroCount = Arrays.stream(pixels).boxed().filter(p -> p == 0).count();
        long oneCount = Arrays.stream(pixels).boxed().filter(p -> p != 0).count();

        System.out.println("Zeros: " + zeroCount);
        System.out.println("Ones: " + oneCount);

        double[] dPixels = Arrays.stream(pixels).asDoubleStream().toArray();

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
        }
        return (byte) digit;
    }



}

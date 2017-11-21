package xsierra.digitguesser.drawer.pipeline;

import java.awt.image.BufferedImage;

public interface DigitPipeline {

    /**
     * @param image An image that contains a drawed digit
     * @return the guessed digit, a number between 0 - 9
     */
    byte imageGuessDigit(BufferedImage image);
}

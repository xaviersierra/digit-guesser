package xsierra.digitguesser.drawer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import xsierra.digitguesser.drawer.pipeline.DigitPipeline;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@RestController
public class DrawerRestController {

    private DigitPipeline pipeline;

    @Autowired
    public DrawerRestController(DigitPipeline pipeline) {
        this.pipeline = pipeline;
    }

    @RequestMapping(value = "/digit", method = RequestMethod.POST, consumes = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte guessDigit(HttpServletRequest request) throws IOException {

        BufferedImage img = ImageIO.read(request.getInputStream());

        return pipeline.imageGuessDigit(img);
    }
}

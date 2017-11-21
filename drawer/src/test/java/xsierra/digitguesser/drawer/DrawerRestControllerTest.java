package xsierra.digitguesser.drawer;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import xsierra.digitguesser.drawer.pipeline.DigitPipeline;

import java.awt.image.BufferedImage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Drawer.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DrawerRestControllerTest {

    @MockBean
    private DigitPipeline pipeline;

    @Autowired
    private MockMvc mvc;

    @Before
    public void setUp() {

    }

    @Test
    public void guessDigit() throws Exception {

        given(pipeline.imageGuessDigit(any(BufferedImage.class))).willReturn((byte) 7);

        ClassPathResource resource = new ClassPathResource("7.png");

        byte[] imageBytes = IOUtils.toByteArray(resource.getInputStream());

        MockHttpServletResponse response = mvc.perform(post("/digit").content(imageBytes).contentType("image/png")).andExpect(status().isOk()).andReturn().getResponse();

        String guessedDigit = response.getContentAsString();

        assertThat(guessedDigit).isEqualTo("7");


    }

}
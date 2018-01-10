package xsierra.digitguesser;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import xsierra.digitguesser.initializer.InitializerService;
import xsierra.digitguesser.trainer.Context;
import xsierra.digitguesser.trainer.ConvolutionalTrainer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceTestConfiguration.class)
public class TrainerServiceImplTest {

    @MockBean
    private ConvolutionalTrainer cTrainer;

    @Autowired
    private TrainerServiceImpl service;

    private File testFile;

    private String testFolder;

    @Before
    public void setUp() throws Exception {
        ClassPathResource resource = new ClassPathResource("application.properties");
        String testFolder = resource.getFile().getParentFile().getPath();
        testFile = new File(testFolder + "test.m");
        testFile.createNewFile();
        FileWriter fw = new FileWriter(testFile);
        fw.write("test string");
        fw.close();
    }

    @After
    public void tearDown() throws Exception {
        if (testFile != null){
            testFile.delete();
        }
    }

    @Test
    public void buildModel() throws IOException {
        MultiLayerNetwork model = mock(MultiLayerNetwork.class);
        given(cTrainer.trainModel(any(Context.class))).willReturn(model);

        MultiLayerNetwork givenModel = service.buildModel();

        assertThat(givenModel).isEqualTo(model);

    }

    @Test
    public void testInitializeModelFileExistingFile() throws IOException {
        service.initializeModelFile(testFile);
        assertThat(testFile.length()).isEqualTo(0);
    }

    @Test
    public void testInitializeModelFileNonExistentFile() throws IOException {
        File file = new File(testFolder + "test2.m");
        service.initializeModelFile(file);
        assertThat(file.exists()).isTrue();
        file.delete();
    }

    @Test
    public void lookFilePathExistentFile() {
        File givenFile = service.lookFilePath(testFile.getPath());

        assertThat(givenFile).isNotNull();

    }

    @Test(expected = SecurityException.class)
    public void lookFilePathNonExistentFile(){
        service.lookFilePath("/fale_file.soFake");
    }
}
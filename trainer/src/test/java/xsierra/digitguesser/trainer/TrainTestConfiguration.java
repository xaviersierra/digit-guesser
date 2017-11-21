package xsierra.digitguesser.trainer;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import xsierra.digitguesser.TrainerService;

@Profile("test")
@Configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(classes = TrainerService.class))
public class TrainTestConfiguration {
}

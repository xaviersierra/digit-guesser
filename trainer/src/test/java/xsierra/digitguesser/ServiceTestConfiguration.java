package xsierra.digitguesser;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;

@Profile("test")
@Configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = CommandLineRunner.class))
@EnableAutoConfiguration
public class ServiceTestConfiguration {

}

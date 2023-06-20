package config;
import model.Cat;
import model.Dog;
import model.Parrot;
import model.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "model")
public class ProjectConfig {
    @Bean
    Parrot parrot1() {
        var parrot1 = new Parrot();
        parrot1.setName("Kesha");

        return parrot1;
    }
    @Bean
    Parrot parrot2() {
        var parrot2 = new Parrot();
        parrot2.setName("Borya");

        return parrot2;
    }

}

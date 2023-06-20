package config;
import model.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
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

    @Bean
    Cat cat() {
        var cat = new Cat();
        cat.setName("Musya");

        return cat;
    }

    @Bean
    Dog dog() {
        var dog = new Dog();
        dog.setName("Borya");

        return dog;
    }

    @Bean
    public Person person() {
        Person p = new Person();
        p.setName("Daria");
        p.setParrot1(parrot1());
        p.setParrot2(parrot2());
        p.setCat(cat());
        p.setDog(dog());
        return p;
    }
}

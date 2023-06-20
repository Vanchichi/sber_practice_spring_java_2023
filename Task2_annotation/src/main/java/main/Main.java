package main;
import model.Person;
import config.ProjectConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        Person p = context.getBean(Person.class);

        System.out.println("Person's name: " + p.getName());
        System.out.println("Person's cat: " + p.getCat());
        System.out.println("Person's dog: " + p.getDog());
        System.out.println("Person's parrots: " + p.getParrot1() + ", " + p.getParrot2());
    }
}
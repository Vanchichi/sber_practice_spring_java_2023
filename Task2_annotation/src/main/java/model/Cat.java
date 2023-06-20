package model;
import org.springframework.stereotype.Component;

@Component
public class Cat {

    private String name = "Musya";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

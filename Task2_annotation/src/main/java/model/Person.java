package model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Person {
    private String name = "Daria";
    private Parrot parrot1;
    private Parrot parrot2;
    private Cat cat;
    private Dog dog;

   @Autowired
    public Person(@Qualifier("parrot1") Parrot parrot1, @Qualifier("parrot2") Parrot parrot2, Cat cat, Dog dog) {
        this.parrot1 = parrot1;
        this.parrot2 = parrot2;
        this.cat = cat;
        this.dog = dog;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Parrot getParrot1() {
        return parrot1;
    }

    public void setParrot1(Parrot parrot1) {
        this.parrot1 = parrot1;
    }

    public Parrot getParrot2() {
        return parrot2;
    }

    public void setParrot2(Parrot parrot2) {
        this.parrot2 = parrot2;
    }

    public Cat getCat() {
        return cat;
    }

    public void setCat(Cat cat) {
        this.cat = cat;
    }

    public Dog getDog(){
        return dog;
    }

    public void setDog(Dog dog){
        this.dog=dog;
    }
}
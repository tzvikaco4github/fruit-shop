package net.tzvikaco.fruitshop.domain.internal;

import net.tzvikaco.fruitshop.domain.Contact;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Person extends Contact {

    @Field("birthdate")
    private LocalDate birthdate;

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public Person birthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Person person = (Person) o;
        return Objects.equals(birthdate, person.birthdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), birthdate);
    }

    @Override
    public String toString() {
        return "Person{" +
            super.toString() +
            "birthdate=" + birthdate +
            '}';
    }
}

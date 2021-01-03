package net.tzvikaco.fruitshop.service.dto.internal;

import net.tzvikaco.fruitshop.service.dto.ContactDTO;

import java.time.LocalDate;
import java.util.Objects;

public abstract class PersonDTO extends ContactDTO {

    private LocalDate birthdate;

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PersonDTO that = (PersonDTO) o;
        return Objects.equals(birthdate, that.birthdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), birthdate);
    }

}

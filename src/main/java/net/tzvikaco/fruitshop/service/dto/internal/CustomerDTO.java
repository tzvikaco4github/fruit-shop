package net.tzvikaco.fruitshop.service.dto.internal;

import net.tzvikaco.fruitshop.service.dto.ContactDTO;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class CustomerDTO extends ContactDTO implements Serializable {

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
        CustomerDTO that = (CustomerDTO) o;
        return Objects.equals(birthdate, that.birthdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), birthdate);
    }
}

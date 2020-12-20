package net.tzvikaco.fruitshop.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link net.tzvikaco.fruitshop.domain.Address} entity.
 */
public class AddressDTO implements Serializable {
    
    private String id;

    @NotNull
    @Size(min = 3)
    private String streetName;

    @Min(value = 0)
    private Integer streetNumber;

    @Min(value = 0)
    private Integer floor;

    @Min(value = 1)
    private Integer apartment;

    @Size(min = 7, max = 7)
    private String zipCode;

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public Integer getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getApartment() {
        return apartment;
    }

    public void setApartment(Integer apartment) {
        this.apartment = apartment;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AddressDTO)) {
            return false;
        }

        return id != null && id.equals(((AddressDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AddressDTO{" +
            "id=" + getId() +
            ", streetName='" + getStreetName() + "'" +
            ", streetNumber=" + getStreetNumber() +
            ", floor=" + getFloor() +
            ", apartment=" + getApartment() +
            ", zipCode='" + getZipCode() + "'" +
            "}";
    }
}

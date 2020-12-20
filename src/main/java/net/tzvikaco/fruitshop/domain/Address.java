package net.tzvikaco.fruitshop.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Address.
 */
@Document(collection = "address")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(min = 3)
    @Field("street_name")
    private String streetName;

    @Min(value = 0)
    @Field("street_number")
    private Integer streetNumber;

    @Min(value = 0)
    @Field("floor")
    private Integer floor;

    @Min(value = 1)
    @Field("apartment")
    private Integer apartment;

    @Size(min = 7, max = 7)
    @Field("zip_code")
    private String zipCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStreetName() {
        return streetName;
    }

    public Address streetName(String streetName) {
        this.streetName = streetName;
        return this;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public Integer getStreetNumber() {
        return streetNumber;
    }

    public Address streetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
        return this;
    }

    public void setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
    }

    public Integer getFloor() {
        return floor;
    }

    public Address floor(Integer floor) {
        this.floor = floor;
        return this;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getApartment() {
        return apartment;
    }

    public Address apartment(Integer apartment) {
        this.apartment = apartment;
        return this;
    }

    public void setApartment(Integer apartment) {
        this.apartment = apartment;
    }

    public String getZipCode() {
        return zipCode;
    }

    public Address zipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        return id != null && id.equals(((Address) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Address{" +
            "id=" + getId() +
            ", streetName='" + getStreetName() + "'" +
            ", streetNumber=" + getStreetNumber() +
            ", floor=" + getFloor() +
            ", apartment=" + getApartment() +
            ", zipCode='" + getZipCode() + "'" +
            "}";
    }
}

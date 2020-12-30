package net.tzvikaco.fruitshop.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link net.tzvikaco.fruitshop.domain.Contact} entity.
 */
public class ContactDTO implements Serializable {
    
    private String id;

    @NotNull
    @Size(min = 2)
    private String firstName;

    @Size(min = 2)
    private String lastName;

    @Pattern(regexp = "^0\\d([\\d]{0,1})([-]{0,1})\\d{7}$")
    private String phone;

    @Pattern(regexp = "^05\\d([-]{0,1})\\d{7}$")
    private String mobile;


    private String addressId;

    private String addressStreetName;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getAddressStreetName() {
        return addressStreetName;
    }

    public void setAddressStreetName(String addressStreetName) {
        this.addressStreetName = addressStreetName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContactDTO)) {
            return false;
        }

        return id != null && id.equals(((ContactDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContactDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", addressId='" + getAddressId() + "'" +
            ", addressStreetName='" + getAddressStreetName() + "'" +
            "}";
    }
}

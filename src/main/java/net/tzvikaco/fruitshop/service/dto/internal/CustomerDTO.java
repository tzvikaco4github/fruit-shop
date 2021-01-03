package net.tzvikaco.fruitshop.service.dto.internal;

import java.io.Serializable;

public class CustomerDTO extends PersonDTO implements Serializable {

    @Override
    public String toString() {
        return "CustomerDTO {" + super.toString() + "}";
    }
}

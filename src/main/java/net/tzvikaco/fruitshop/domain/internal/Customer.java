package net.tzvikaco.fruitshop.domain.internal;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customer")
public class Customer extends Person {

    @Override
    public String toString() {
        return "Customer{" +
            super.toString() +
            '}';
    }
}

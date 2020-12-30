package net.tzvikaco.fruitshop.domain.internal;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "contact")
@TypeAlias("customer")
public class Customer extends Person {
}

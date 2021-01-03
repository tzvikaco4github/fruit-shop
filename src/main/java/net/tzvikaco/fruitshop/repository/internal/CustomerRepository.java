package net.tzvikaco.fruitshop.repository.internal;

import net.tzvikaco.fruitshop.domain.internal.Customer;
import net.tzvikaco.fruitshop.domain.internal.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Customer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
}

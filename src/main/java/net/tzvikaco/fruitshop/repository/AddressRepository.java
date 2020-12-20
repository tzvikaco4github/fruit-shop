package net.tzvikaco.fruitshop.repository;

import net.tzvikaco.fruitshop.domain.Address;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Address entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AddressRepository extends MongoRepository<Address, String> {
}

package net.tzvikaco.fruitshop.repository.internal;

import net.tzvikaco.fruitshop.domain.internal.Supplier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends MongoRepository<Supplier, String> {
}

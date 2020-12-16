package net.tzvikaco.fruitshop.repository;

import net.tzvikaco.fruitshop.domain.Stock;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Stock entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockRepository extends MongoRepository<Stock, String> {
}

package net.tzvikaco.fruitshop.repository;

import net.tzvikaco.fruitshop.domain.JobTitle;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the JobTitle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobTitleRepository extends MongoRepository<JobTitle, String> {
}

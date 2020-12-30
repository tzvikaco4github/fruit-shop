package net.tzvikaco.fruitshop.service;

import net.tzvikaco.fruitshop.domain.JobTitle;
import net.tzvikaco.fruitshop.repository.JobTitleRepository;
import net.tzvikaco.fruitshop.service.dto.JobTitleDTO;
import net.tzvikaco.fruitshop.service.mapper.JobTitleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link JobTitle}.
 */
@Service
public class JobTitleService {

    private final Logger log = LoggerFactory.getLogger(JobTitleService.class);

    private final JobTitleRepository jobTitleRepository;

    private final JobTitleMapper jobTitleMapper;

    public JobTitleService(JobTitleRepository jobTitleRepository, JobTitleMapper jobTitleMapper) {
        this.jobTitleRepository = jobTitleRepository;
        this.jobTitleMapper = jobTitleMapper;
    }

    /**
     * Save a jobTitle.
     *
     * @param jobTitleDTO the entity to save.
     * @return the persisted entity.
     */
    public JobTitleDTO save(JobTitleDTO jobTitleDTO) {
        log.debug("Request to save JobTitle : {}", jobTitleDTO);
        JobTitle jobTitle = jobTitleMapper.toEntity(jobTitleDTO);
        jobTitle = jobTitleRepository.save(jobTitle);
        return jobTitleMapper.toDto(jobTitle);
    }

    /**
     * Get all the jobTitles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<JobTitleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all JobTitles");
        return jobTitleRepository.findAll(pageable)
            .map(jobTitleMapper::toDto);
    }


    /**
     * Get one jobTitle by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<JobTitleDTO> findOne(String id) {
        log.debug("Request to get JobTitle : {}", id);
        return jobTitleRepository.findById(id)
            .map(jobTitleMapper::toDto);
    }

    /**
     * Delete the jobTitle by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete JobTitle : {}", id);
        jobTitleRepository.deleteById(id);
    }
}

package net.tzvikaco.fruitshop.web.rest;

import net.tzvikaco.fruitshop.service.JobTitleService;
import net.tzvikaco.fruitshop.web.rest.errors.BadRequestAlertException;
import net.tzvikaco.fruitshop.service.dto.JobTitleDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link net.tzvikaco.fruitshop.domain.JobTitle}.
 */
@RestController
@RequestMapping("/api")
public class JobTitleResource {

    private final Logger log = LoggerFactory.getLogger(JobTitleResource.class);

    private static final String ENTITY_NAME = "fruitShopJobTitle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobTitleService jobTitleService;

    public JobTitleResource(JobTitleService jobTitleService) {
        this.jobTitleService = jobTitleService;
    }

    /**
     * {@code POST  /job-titles} : Create a new jobTitle.
     *
     * @param jobTitleDTO the jobTitleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobTitleDTO, or with status {@code 400 (Bad Request)} if the jobTitle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/job-titles")
    public ResponseEntity<JobTitleDTO> createJobTitle(@Valid @RequestBody JobTitleDTO jobTitleDTO) throws URISyntaxException {
        log.debug("REST request to save JobTitle : {}", jobTitleDTO);
        if (jobTitleDTO.getId() != null) {
            throw new BadRequestAlertException("A new jobTitle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobTitleDTO result = jobTitleService.save(jobTitleDTO);
        return ResponseEntity.created(new URI("/api/job-titles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /job-titles} : Updates an existing jobTitle.
     *
     * @param jobTitleDTO the jobTitleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobTitleDTO,
     * or with status {@code 400 (Bad Request)} if the jobTitleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobTitleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/job-titles")
    public ResponseEntity<JobTitleDTO> updateJobTitle(@Valid @RequestBody JobTitleDTO jobTitleDTO) throws URISyntaxException {
        log.debug("REST request to update JobTitle : {}", jobTitleDTO);
        if (jobTitleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JobTitleDTO result = jobTitleService.save(jobTitleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobTitleDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /job-titles} : get all the jobTitles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobTitles in body.
     */
    @GetMapping("/job-titles")
    public ResponseEntity<List<JobTitleDTO>> getAllJobTitles(Pageable pageable) {
        log.debug("REST request to get a page of JobTitles");
        Page<JobTitleDTO> page = jobTitleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /job-titles/:id} : get the "id" jobTitle.
     *
     * @param id the id of the jobTitleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobTitleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/job-titles/{id}")
    public ResponseEntity<JobTitleDTO> getJobTitle(@PathVariable String id) {
        log.debug("REST request to get JobTitle : {}", id);
        Optional<JobTitleDTO> jobTitleDTO = jobTitleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jobTitleDTO);
    }

    /**
     * {@code DELETE  /job-titles/:id} : delete the "id" jobTitle.
     *
     * @param id the id of the jobTitleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/job-titles/{id}")
    public ResponseEntity<Void> deleteJobTitle(@PathVariable String id) {
        log.debug("REST request to delete JobTitle : {}", id);
        jobTitleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}

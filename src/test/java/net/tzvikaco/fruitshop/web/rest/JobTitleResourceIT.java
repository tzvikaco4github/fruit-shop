package net.tzvikaco.fruitshop.web.rest;

import net.tzvikaco.fruitshop.RedisTestContainerExtension;
import net.tzvikaco.fruitshop.FruitShopApp;
import net.tzvikaco.fruitshop.domain.JobTitle;
import net.tzvikaco.fruitshop.repository.JobTitleRepository;
import net.tzvikaco.fruitshop.service.JobTitleService;
import net.tzvikaco.fruitshop.service.dto.JobTitleDTO;
import net.tzvikaco.fruitshop.service.mapper.JobTitleMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link JobTitleResource} REST controller.
 */
@SpringBootTest(classes = FruitShopApp.class)
@ExtendWith({ RedisTestContainerExtension.class, MockitoExtension.class })
@AutoConfigureMockMvc
@WithMockUser
public class JobTitleResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_JOB_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_JOB_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private JobTitleRepository jobTitleRepository;

    @Autowired
    private JobTitleMapper jobTitleMapper;

    @Autowired
    private JobTitleService jobTitleService;

    @Autowired
    private MockMvc restJobTitleMockMvc;

    private JobTitle jobTitle;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobTitle createEntity() {
        JobTitle jobTitle = new JobTitle()
            .name(DEFAULT_NAME)
            .jobDescription(DEFAULT_JOB_DESCRIPTION);
        return jobTitle;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobTitle createUpdatedEntity() {
        JobTitle jobTitle = new JobTitle()
            .name(UPDATED_NAME)
            .jobDescription(UPDATED_JOB_DESCRIPTION);
        return jobTitle;
    }

    @BeforeEach
    public void initTest() {
        jobTitleRepository.deleteAll();
        jobTitle = createEntity();
    }

    @Test
    public void createJobTitle() throws Exception {
        int databaseSizeBeforeCreate = jobTitleRepository.findAll().size();
        // Create the JobTitle
        JobTitleDTO jobTitleDTO = jobTitleMapper.toDto(jobTitle);
        restJobTitleMockMvc.perform(post("/api/job-titles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobTitleDTO)))
            .andExpect(status().isCreated());

        // Validate the JobTitle in the database
        List<JobTitle> jobTitleList = jobTitleRepository.findAll();
        assertThat(jobTitleList).hasSize(databaseSizeBeforeCreate + 1);
        JobTitle testJobTitle = jobTitleList.get(jobTitleList.size() - 1);
        assertThat(testJobTitle.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testJobTitle.getJobDescription()).isEqualTo(DEFAULT_JOB_DESCRIPTION);
    }

    @Test
    public void createJobTitleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobTitleRepository.findAll().size();

        // Create the JobTitle with an existing ID
        jobTitle.setId("existing_id");
        JobTitleDTO jobTitleDTO = jobTitleMapper.toDto(jobTitle);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobTitleMockMvc.perform(post("/api/job-titles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobTitleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JobTitle in the database
        List<JobTitle> jobTitleList = jobTitleRepository.findAll();
        assertThat(jobTitleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobTitleRepository.findAll().size();
        // set the field null
        jobTitle.setName(null);

        // Create the JobTitle, which fails.
        JobTitleDTO jobTitleDTO = jobTitleMapper.toDto(jobTitle);


        restJobTitleMockMvc.perform(post("/api/job-titles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobTitleDTO)))
            .andExpect(status().isBadRequest());

        List<JobTitle> jobTitleList = jobTitleRepository.findAll();
        assertThat(jobTitleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkJobDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobTitleRepository.findAll().size();
        // set the field null
        jobTitle.setJobDescription(null);

        // Create the JobTitle, which fails.
        JobTitleDTO jobTitleDTO = jobTitleMapper.toDto(jobTitle);


        restJobTitleMockMvc.perform(post("/api/job-titles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobTitleDTO)))
            .andExpect(status().isBadRequest());

        List<JobTitle> jobTitleList = jobTitleRepository.findAll();
        assertThat(jobTitleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllJobTitles() throws Exception {
        // Initialize the database
        jobTitleRepository.save(jobTitle);

        // Get all the jobTitleList
        restJobTitleMockMvc.perform(get("/api/job-titles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobTitle.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].jobDescription").value(hasItem(DEFAULT_JOB_DESCRIPTION)));
    }
    
    @Test
    public void getJobTitle() throws Exception {
        // Initialize the database
        jobTitleRepository.save(jobTitle);

        // Get the jobTitle
        restJobTitleMockMvc.perform(get("/api/job-titles/{id}", jobTitle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobTitle.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.jobDescription").value(DEFAULT_JOB_DESCRIPTION));
    }
    @Test
    public void getNonExistingJobTitle() throws Exception {
        // Get the jobTitle
        restJobTitleMockMvc.perform(get("/api/job-titles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateJobTitle() throws Exception {
        // Initialize the database
        jobTitleRepository.save(jobTitle);

        int databaseSizeBeforeUpdate = jobTitleRepository.findAll().size();

        // Update the jobTitle
        JobTitle updatedJobTitle = jobTitleRepository.findById(jobTitle.getId()).get();
        updatedJobTitle
            .name(UPDATED_NAME)
            .jobDescription(UPDATED_JOB_DESCRIPTION);
        JobTitleDTO jobTitleDTO = jobTitleMapper.toDto(updatedJobTitle);

        restJobTitleMockMvc.perform(put("/api/job-titles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobTitleDTO)))
            .andExpect(status().isOk());

        // Validate the JobTitle in the database
        List<JobTitle> jobTitleList = jobTitleRepository.findAll();
        assertThat(jobTitleList).hasSize(databaseSizeBeforeUpdate);
        JobTitle testJobTitle = jobTitleList.get(jobTitleList.size() - 1);
        assertThat(testJobTitle.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testJobTitle.getJobDescription()).isEqualTo(UPDATED_JOB_DESCRIPTION);
    }

    @Test
    public void updateNonExistingJobTitle() throws Exception {
        int databaseSizeBeforeUpdate = jobTitleRepository.findAll().size();

        // Create the JobTitle
        JobTitleDTO jobTitleDTO = jobTitleMapper.toDto(jobTitle);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobTitleMockMvc.perform(put("/api/job-titles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobTitleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JobTitle in the database
        List<JobTitle> jobTitleList = jobTitleRepository.findAll();
        assertThat(jobTitleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteJobTitle() throws Exception {
        // Initialize the database
        jobTitleRepository.save(jobTitle);

        int databaseSizeBeforeDelete = jobTitleRepository.findAll().size();

        // Delete the jobTitle
        restJobTitleMockMvc.perform(delete("/api/job-titles/{id}", jobTitle.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JobTitle> jobTitleList = jobTitleRepository.findAll();
        assertThat(jobTitleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

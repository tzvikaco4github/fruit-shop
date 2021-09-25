package net.tzvikaco.fruitshop.web.rest;

import net.tzvikaco.fruitshop.FruitShopApp;
import net.tzvikaco.fruitshop.RandomDateUtils;
import net.tzvikaco.fruitshop.RedisTestContainerExtension;
import net.tzvikaco.fruitshop.domain.internal.Supplier;
import net.tzvikaco.fruitshop.repository.internal.SupplierRepository;
import net.tzvikaco.fruitshop.service.dto.internal.SupplierDTO;
import net.tzvikaco.fruitshop.service.internal.SupplierService;
import net.tzvikaco.fruitshop.service.mapper.internal.SupplierMapper;
import net.tzvikaco.fruitshop.web.rest.internal.SupplierResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SupplierResource} REST controller.
 */
@SpringBootTest(classes = FruitShopApp.class)
@ExtendWith({RedisTestContainerExtension.class, MockitoExtension.class})
@AutoConfigureMockMvc
@WithMockUser
public class SupplierResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "030-1485645";
    private static final String UPDATED_PHONE = "0758796523";

    private static final String DEFAULT_MOBILE = "054-7954075";
    private static final String UPDATED_MOBILE = "050-3583429";

    private static final LocalDate DEFAULT_START_DATE = RandomDateUtils.randomLocalDate();
    private static final LocalDate UPDATED_START_DATE = RandomDateUtils.randomLocalDate();

    private static final LocalDate DEFAULT_END_DATE = DEFAULT_START_DATE.plusDays(1L);
    private static final LocalDate UPDATED_END_DATE = UPDATED_START_DATE.plusDays(1L);

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private MockMvc restSupplierMockMvc;

    private Supplier supplier;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Supplier createEntity() {
        Supplier supplier = (Supplier) new Supplier()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .phone(DEFAULT_PHONE)
            .mobile(DEFAULT_MOBILE)
            .address(AddressResourceIT.createEntity());
        return supplier;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Supplier createUpdatedEntity() {
        Supplier supplier = (Supplier) new Supplier()
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .phone(UPDATED_PHONE)
            .mobile(UPDATED_MOBILE)
            .address(AddressResourceIT.createEntity());
        return supplier;
    }

    @BeforeEach
    public void initTest() {
        supplierRepository.deleteAll();
        supplier = createEntity();
    }

    @Test
    public void getAllSuppliers() throws Exception {
        // Initialize the database
        supplierRepository.save(supplier);

        // Get all the SupplierList
        restSupplierMockMvc.perform(get("/api/Suppliers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplier.getId())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].start_date").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].end_date").value(hasItem(DEFAULT_END_DATE.toString())));
    }

    @Test
    public void getSupplier() throws Exception {
        // Initialize the database
        supplierRepository.save(supplier);

        // Get the Supplier
        restSupplierMockMvc.perform(get("/api/Suppliers/{id}", supplier.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(supplier.getId()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE))
            .andExpect(jsonPath("$.start_date").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.end_date").value(hasItem(DEFAULT_END_DATE.toString())));
    }

    @Test
    public void getNonExistingSupplier() throws Exception {
        // Get the Supplier
        restSupplierMockMvc.perform(get("/api/Suppliers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSupplier() throws Exception {
        // Initialize the database
        supplierRepository.save(supplier);

        int databaseSizeBeforeUpdate = supplierRepository.findAll().size();

        // Update the Customer
        Supplier updatedSupplier = supplierRepository.findById(supplier.getId()).get();
        updatedSupplier
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .phone(UPDATED_PHONE)
            .mobile(UPDATED_MOBILE);
        SupplierDTO customerDTO = supplierMapper.toDto(updatedSupplier);

        restSupplierMockMvc.perform(put("/api/suppliers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isOk());

        // Validate the Customer in the database
        List<Supplier> supplierList = supplierRepository.findAll();
        assertThat(supplierList).hasSize(databaseSizeBeforeUpdate);
        Supplier testSupplier = supplierList.get(supplierList.size() - 1);
        assertThat(testSupplier.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testSupplier.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testSupplier.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testSupplier.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testSupplier.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testSupplier.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    public void updateNonExistingCustomer() throws Exception {
        int databaseSizeBeforeUpdate = supplierRepository.findAll().size();

        // Create the Customer
        SupplierDTO customerDTO = supplierMapper.toDto(supplier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplierMockMvc.perform(put("/api/suppliers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Supplier> CustomerList = supplierRepository.findAll();
        assertThat(CustomerList).hasSize(databaseSizeBeforeUpdate);
    }


    @Test
    public void deleteSupplier() throws Exception {
        // Initialize the database
        supplierRepository.save(supplier);

        int databaseSizeBeforeDelete = supplierRepository.findAll().size();

        // Delete the Customer
        restSupplierMockMvc.perform(delete("/api/suppliers/{id}", supplier.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Supplier> CustomerList = supplierRepository.findAll();
        assertThat(CustomerList).hasSize(databaseSizeBeforeDelete - 1);
    }

}

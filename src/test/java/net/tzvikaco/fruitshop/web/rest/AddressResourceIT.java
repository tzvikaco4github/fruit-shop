package net.tzvikaco.fruitshop.web.rest;

import net.tzvikaco.fruitshop.RedisTestContainerExtension;
import net.tzvikaco.fruitshop.FruitShopApp;
import net.tzvikaco.fruitshop.domain.Address;
import net.tzvikaco.fruitshop.repository.AddressRepository;
import net.tzvikaco.fruitshop.service.AddressService;
import net.tzvikaco.fruitshop.service.dto.AddressDTO;
import net.tzvikaco.fruitshop.service.mapper.AddressMapper;

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
 * Integration tests for the {@link AddressResource} REST controller.
 */
@SpringBootTest(classes = FruitShopApp.class)
@ExtendWith({ RedisTestContainerExtension.class, MockitoExtension.class })
@AutoConfigureMockMvc
@WithMockUser
public class AddressResourceIT {

    private static final String DEFAULT_STREET_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STREET_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_STREET_NUMBER = 0;
    private static final Integer UPDATED_STREET_NUMBER = 1;

    private static final Integer DEFAULT_FLOOR = 0;
    private static final Integer UPDATED_FLOOR = 1;

    private static final Integer DEFAULT_APARTMENT = 1;
    private static final Integer UPDATED_APARTMENT = 2;

    private static final String DEFAULT_ZIP_CODE = "AAAAAAA";
    private static final String UPDATED_ZIP_CODE = "BBBBBBB";

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private AddressService addressService;

    @Autowired
    private MockMvc restAddressMockMvc;

    private Address address;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Address createEntity() {
        Address address = new Address()
            .streetName(DEFAULT_STREET_NAME)
            .streetNumber(DEFAULT_STREET_NUMBER)
            .floor(DEFAULT_FLOOR)
            .apartment(DEFAULT_APARTMENT)
            .zipCode(DEFAULT_ZIP_CODE);
        return address;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Address createUpdatedEntity() {
        Address address = new Address()
            .streetName(UPDATED_STREET_NAME)
            .streetNumber(UPDATED_STREET_NUMBER)
            .floor(UPDATED_FLOOR)
            .apartment(UPDATED_APARTMENT)
            .zipCode(UPDATED_ZIP_CODE);
        return address;
    }

    @BeforeEach
    public void initTest() {
        addressRepository.deleteAll();
        address = createEntity();
    }

    @Test
    public void createAddress() throws Exception {
        int databaseSizeBeforeCreate = addressRepository.findAll().size();
        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);
        restAddressMockMvc.perform(post("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isCreated());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeCreate + 1);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getStreetName()).isEqualTo(DEFAULT_STREET_NAME);
        assertThat(testAddress.getStreetNumber()).isEqualTo(DEFAULT_STREET_NUMBER);
        assertThat(testAddress.getFloor()).isEqualTo(DEFAULT_FLOOR);
        assertThat(testAddress.getApartment()).isEqualTo(DEFAULT_APARTMENT);
        assertThat(testAddress.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
    }

    @Test
    public void createAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = addressRepository.findAll().size();

        // Create the Address with an existing ID
        address.setId("existing_id");
        AddressDTO addressDTO = addressMapper.toDto(address);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddressMockMvc.perform(post("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkStreetNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = addressRepository.findAll().size();
        // set the field null
        address.setStreetName(null);

        // Create the Address, which fails.
        AddressDTO addressDTO = addressMapper.toDto(address);


        restAddressMockMvc.perform(post("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllAddresses() throws Exception {
        // Initialize the database
        addressRepository.save(address);

        // Get all the addressList
        restAddressMockMvc.perform(get("/api/addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(address.getId())))
            .andExpect(jsonPath("$.[*].streetName").value(hasItem(DEFAULT_STREET_NAME)))
            .andExpect(jsonPath("$.[*].streetNumber").value(hasItem(DEFAULT_STREET_NUMBER)))
            .andExpect(jsonPath("$.[*].floor").value(hasItem(DEFAULT_FLOOR)))
            .andExpect(jsonPath("$.[*].apartment").value(hasItem(DEFAULT_APARTMENT)))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE)));
    }
    
    @Test
    public void getAddress() throws Exception {
        // Initialize the database
        addressRepository.save(address);

        // Get the address
        restAddressMockMvc.perform(get("/api/addresses/{id}", address.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(address.getId()))
            .andExpect(jsonPath("$.streetName").value(DEFAULT_STREET_NAME))
            .andExpect(jsonPath("$.streetNumber").value(DEFAULT_STREET_NUMBER))
            .andExpect(jsonPath("$.floor").value(DEFAULT_FLOOR))
            .andExpect(jsonPath("$.apartment").value(DEFAULT_APARTMENT))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE));
    }
    @Test
    public void getNonExistingAddress() throws Exception {
        // Get the address
        restAddressMockMvc.perform(get("/api/addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAddress() throws Exception {
        // Initialize the database
        addressRepository.save(address);

        int databaseSizeBeforeUpdate = addressRepository.findAll().size();

        // Update the address
        Address updatedAddress = addressRepository.findById(address.getId()).get();
        updatedAddress
            .streetName(UPDATED_STREET_NAME)
            .streetNumber(UPDATED_STREET_NUMBER)
            .floor(UPDATED_FLOOR)
            .apartment(UPDATED_APARTMENT)
            .zipCode(UPDATED_ZIP_CODE);
        AddressDTO addressDTO = addressMapper.toDto(updatedAddress);

        restAddressMockMvc.perform(put("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isOk());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getStreetName()).isEqualTo(UPDATED_STREET_NAME);
        assertThat(testAddress.getStreetNumber()).isEqualTo(UPDATED_STREET_NUMBER);
        assertThat(testAddress.getFloor()).isEqualTo(UPDATED_FLOOR);
        assertThat(testAddress.getApartment()).isEqualTo(UPDATED_APARTMENT);
        assertThat(testAddress.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
    }

    @Test
    public void updateNonExistingAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressMockMvc.perform(put("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteAddress() throws Exception {
        // Initialize the database
        addressRepository.save(address);

        int databaseSizeBeforeDelete = addressRepository.findAll().size();

        // Delete the address
        restAddressMockMvc.perform(delete("/api/addresses/{id}", address.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

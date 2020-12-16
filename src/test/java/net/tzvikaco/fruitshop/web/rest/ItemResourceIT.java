package net.tzvikaco.fruitshop.web.rest;

import net.tzvikaco.fruitshop.RedisTestContainerExtension;
import net.tzvikaco.fruitshop.FruitShopApp;
import net.tzvikaco.fruitshop.domain.Item;
import net.tzvikaco.fruitshop.repository.ItemRepository;
import net.tzvikaco.fruitshop.service.ItemService;
import net.tzvikaco.fruitshop.service.dto.ItemDTO;
import net.tzvikaco.fruitshop.service.mapper.ItemMapper;

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

import net.tzvikaco.fruitshop.domain.enumeration.UnitType;
/**
 * Integration tests for the {@link ItemResource} REST controller.
 */
@SpringBootTest(classes = FruitShopApp.class)
@ExtendWith({ RedisTestContainerExtension.class, MockitoExtension.class })
@AutoConfigureMockMvc
@WithMockUser
public class ItemResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final UnitType DEFAULT_UNIT_TYPE = UnitType.WEIGHT;
    private static final UnitType UPDATED_UNIT_TYPE = UnitType.PACKAGE;

    private static final String DEFAULT_DESCRIPTION_LINK = "ftp://,EJBuq";
    private static final String UPDATED_DESCRIPTION_LINK = "https://p&I";

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemService itemService;

    @Autowired
    private MockMvc restItemMockMvc;

    private Item item;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Item createEntity() {
        Item item = new Item()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .unitType(DEFAULT_UNIT_TYPE)
            .descriptionLink(DEFAULT_DESCRIPTION_LINK)
            .image(DEFAULT_IMAGE);
        return item;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Item createUpdatedEntity() {
        Item item = new Item()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .unitType(UPDATED_UNIT_TYPE)
            .descriptionLink(UPDATED_DESCRIPTION_LINK)
            .image(UPDATED_IMAGE);
        return item;
    }

    @BeforeEach
    public void initTest() {
        itemRepository.deleteAll();
        item = createEntity();
    }

    @Test
    public void createItem() throws Exception {
        int databaseSizeBeforeCreate = itemRepository.findAll().size();
        // Create the Item
        ItemDTO itemDTO = itemMapper.toDto(item);
        restItemMockMvc.perform(post("/api/items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemDTO)))
            .andExpect(status().isCreated());

        // Validate the Item in the database
        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeCreate + 1);
        Item testItem = itemList.get(itemList.size() - 1);
        assertThat(testItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testItem.getUnitType()).isEqualTo(DEFAULT_UNIT_TYPE);
        assertThat(testItem.getDescriptionLink()).isEqualTo(DEFAULT_DESCRIPTION_LINK);
        assertThat(testItem.getImage()).isEqualTo(DEFAULT_IMAGE);
    }

    @Test
    public void createItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemRepository.findAll().size();

        // Create the Item with an existing ID
        item.setId("existing_id");
        ItemDTO itemDTO = itemMapper.toDto(item);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemMockMvc.perform(post("/api/items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Item in the database
        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemRepository.findAll().size();
        // set the field null
        item.setName(null);

        // Create the Item, which fails.
        ItemDTO itemDTO = itemMapper.toDto(item);


        restItemMockMvc.perform(post("/api/items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemDTO)))
            .andExpect(status().isBadRequest());

        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkUnitTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemRepository.findAll().size();
        // set the field null
        item.setUnitType(null);

        // Create the Item, which fails.
        ItemDTO itemDTO = itemMapper.toDto(item);


        restItemMockMvc.perform(post("/api/items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemDTO)))
            .andExpect(status().isBadRequest());

        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkImageIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemRepository.findAll().size();
        // set the field null
        item.setImage(null);

        // Create the Item, which fails.
        ItemDTO itemDTO = itemMapper.toDto(item);


        restItemMockMvc.perform(post("/api/items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemDTO)))
            .andExpect(status().isBadRequest());

        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllItems() throws Exception {
        // Initialize the database
        itemRepository.save(item);

        // Get all the itemList
        restItemMockMvc.perform(get("/api/items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(item.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].unitType").value(hasItem(DEFAULT_UNIT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].descriptionLink").value(hasItem(DEFAULT_DESCRIPTION_LINK)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE)));
    }
    
    @Test
    public void getItem() throws Exception {
        // Initialize the database
        itemRepository.save(item);

        // Get the item
        restItemMockMvc.perform(get("/api/items/{id}", item.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(item.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.unitType").value(DEFAULT_UNIT_TYPE.toString()))
            .andExpect(jsonPath("$.descriptionLink").value(DEFAULT_DESCRIPTION_LINK))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE));
    }
    @Test
    public void getNonExistingItem() throws Exception {
        // Get the item
        restItemMockMvc.perform(get("/api/items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateItem() throws Exception {
        // Initialize the database
        itemRepository.save(item);

        int databaseSizeBeforeUpdate = itemRepository.findAll().size();

        // Update the item
        Item updatedItem = itemRepository.findById(item.getId()).get();
        updatedItem
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .unitType(UPDATED_UNIT_TYPE)
            .descriptionLink(UPDATED_DESCRIPTION_LINK)
            .image(UPDATED_IMAGE);
        ItemDTO itemDTO = itemMapper.toDto(updatedItem);

        restItemMockMvc.perform(put("/api/items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemDTO)))
            .andExpect(status().isOk());

        // Validate the Item in the database
        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeUpdate);
        Item testItem = itemList.get(itemList.size() - 1);
        assertThat(testItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testItem.getUnitType()).isEqualTo(UPDATED_UNIT_TYPE);
        assertThat(testItem.getDescriptionLink()).isEqualTo(UPDATED_DESCRIPTION_LINK);
        assertThat(testItem.getImage()).isEqualTo(UPDATED_IMAGE);
    }

    @Test
    public void updateNonExistingItem() throws Exception {
        int databaseSizeBeforeUpdate = itemRepository.findAll().size();

        // Create the Item
        ItemDTO itemDTO = itemMapper.toDto(item);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemMockMvc.perform(put("/api/items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Item in the database
        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteItem() throws Exception {
        // Initialize the database
        itemRepository.save(item);

        int databaseSizeBeforeDelete = itemRepository.findAll().size();

        // Delete the item
        restItemMockMvc.perform(delete("/api/items/{id}", item.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

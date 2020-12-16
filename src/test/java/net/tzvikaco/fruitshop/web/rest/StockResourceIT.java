package net.tzvikaco.fruitshop.web.rest;

import net.tzvikaco.fruitshop.RedisTestContainerExtension;
import net.tzvikaco.fruitshop.FruitShopApp;
import net.tzvikaco.fruitshop.domain.Stock;
import net.tzvikaco.fruitshop.repository.StockRepository;
import net.tzvikaco.fruitshop.service.StockService;
import net.tzvikaco.fruitshop.service.dto.StockDTO;
import net.tzvikaco.fruitshop.service.mapper.StockMapper;

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
 * Integration tests for the {@link StockResource} REST controller.
 */
@SpringBootTest(classes = FruitShopApp.class)
@ExtendWith({ RedisTestContainerExtension.class, MockitoExtension.class })
@AutoConfigureMockMvc
@WithMockUser
public class StockResourceIT {

    private static final Float DEFAULT_SHOP_QUANTITY = 0F;
    private static final Float UPDATED_SHOP_QUANTITY = 1F;

    private static final Float DEFAULT_STOCK_QUANTITY = 0F;
    private static final Float UPDATED_STOCK_QUANTITY = 1F;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private StockService stockService;

    @Autowired
    private MockMvc restStockMockMvc;

    private Stock stock;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stock createEntity() {
        Stock stock = new Stock()
            .shopQuantity(DEFAULT_SHOP_QUANTITY)
            .stockQuantity(DEFAULT_STOCK_QUANTITY);
        return stock;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stock createUpdatedEntity() {
        Stock stock = new Stock()
            .shopQuantity(UPDATED_SHOP_QUANTITY)
            .stockQuantity(UPDATED_STOCK_QUANTITY);
        return stock;
    }

    @BeforeEach
    public void initTest() {
        stockRepository.deleteAll();
        stock = createEntity();
    }

    @Test
    public void createStock() throws Exception {
        int databaseSizeBeforeCreate = stockRepository.findAll().size();
        // Create the Stock
        StockDTO stockDTO = stockMapper.toDto(stock);
        restStockMockMvc.perform(post("/api/stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockDTO)))
            .andExpect(status().isCreated());

        // Validate the Stock in the database
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeCreate + 1);
        Stock testStock = stockList.get(stockList.size() - 1);
        assertThat(testStock.getShopQuantity()).isEqualTo(DEFAULT_SHOP_QUANTITY);
        assertThat(testStock.getStockQuantity()).isEqualTo(DEFAULT_STOCK_QUANTITY);
    }

    @Test
    public void createStockWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockRepository.findAll().size();

        // Create the Stock with an existing ID
        stock.setId("existing_id");
        StockDTO stockDTO = stockMapper.toDto(stock);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockMockMvc.perform(post("/api/stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Stock in the database
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkShopQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockRepository.findAll().size();
        // set the field null
        stock.setShopQuantity(null);

        // Create the Stock, which fails.
        StockDTO stockDTO = stockMapper.toDto(stock);


        restStockMockMvc.perform(post("/api/stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockDTO)))
            .andExpect(status().isBadRequest());

        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkStockQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockRepository.findAll().size();
        // set the field null
        stock.setStockQuantity(null);

        // Create the Stock, which fails.
        StockDTO stockDTO = stockMapper.toDto(stock);


        restStockMockMvc.perform(post("/api/stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockDTO)))
            .andExpect(status().isBadRequest());

        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllStocks() throws Exception {
        // Initialize the database
        stockRepository.save(stock);

        // Get all the stockList
        restStockMockMvc.perform(get("/api/stocks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stock.getId())))
            .andExpect(jsonPath("$.[*].shopQuantity").value(hasItem(DEFAULT_SHOP_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].stockQuantity").value(hasItem(DEFAULT_STOCK_QUANTITY.doubleValue())));
    }
    
    @Test
    public void getStock() throws Exception {
        // Initialize the database
        stockRepository.save(stock);

        // Get the stock
        restStockMockMvc.perform(get("/api/stocks/{id}", stock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stock.getId()))
            .andExpect(jsonPath("$.shopQuantity").value(DEFAULT_SHOP_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.stockQuantity").value(DEFAULT_STOCK_QUANTITY.doubleValue()));
    }
    @Test
    public void getNonExistingStock() throws Exception {
        // Get the stock
        restStockMockMvc.perform(get("/api/stocks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateStock() throws Exception {
        // Initialize the database
        stockRepository.save(stock);

        int databaseSizeBeforeUpdate = stockRepository.findAll().size();

        // Update the stock
        Stock updatedStock = stockRepository.findById(stock.getId()).get();
        updatedStock
            .shopQuantity(UPDATED_SHOP_QUANTITY)
            .stockQuantity(UPDATED_STOCK_QUANTITY);
        StockDTO stockDTO = stockMapper.toDto(updatedStock);

        restStockMockMvc.perform(put("/api/stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockDTO)))
            .andExpect(status().isOk());

        // Validate the Stock in the database
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeUpdate);
        Stock testStock = stockList.get(stockList.size() - 1);
        assertThat(testStock.getShopQuantity()).isEqualTo(UPDATED_SHOP_QUANTITY);
        assertThat(testStock.getStockQuantity()).isEqualTo(UPDATED_STOCK_QUANTITY);
    }

    @Test
    public void updateNonExistingStock() throws Exception {
        int databaseSizeBeforeUpdate = stockRepository.findAll().size();

        // Create the Stock
        StockDTO stockDTO = stockMapper.toDto(stock);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockMockMvc.perform(put("/api/stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Stock in the database
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteStock() throws Exception {
        // Initialize the database
        stockRepository.save(stock);

        int databaseSizeBeforeDelete = stockRepository.findAll().size();

        // Delete the stock
        restStockMockMvc.perform(delete("/api/stocks/{id}", stock.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

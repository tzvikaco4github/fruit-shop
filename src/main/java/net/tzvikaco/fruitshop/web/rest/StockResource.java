package net.tzvikaco.fruitshop.web.rest;

import net.tzvikaco.fruitshop.service.StockService;
import net.tzvikaco.fruitshop.web.rest.errors.BadRequestAlertException;
import net.tzvikaco.fruitshop.service.dto.StockDTO;

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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link net.tzvikaco.fruitshop.domain.Stock}.
 */
@RestController
@RequestMapping("/api")
public class StockResource {

    private final Logger log = LoggerFactory.getLogger(StockResource.class);

    private static final String ENTITY_NAME = "fruitShopStock";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StockService stockService;

    public StockResource(StockService stockService) {
        this.stockService = stockService;
    }

    /**
     * {@code POST  /stocks} : Create a new stock.
     *
     * @param stockDTO the stockDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stockDTO, or with status {@code 400 (Bad Request)} if the stock has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stocks")
    public ResponseEntity<StockDTO> createStock(@Valid @RequestBody StockDTO stockDTO) throws URISyntaxException {
        log.debug("REST request to save Stock : {}", stockDTO);
        if (stockDTO.getId() != null) {
            throw new BadRequestAlertException("A new stock cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StockDTO result = stockService.save(stockDTO);
        return ResponseEntity.created(new URI("/api/stocks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /stocks} : Updates an existing stock.
     *
     * @param stockDTO the stockDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stockDTO,
     * or with status {@code 400 (Bad Request)} if the stockDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stockDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stocks")
    public ResponseEntity<StockDTO> updateStock(@Valid @RequestBody StockDTO stockDTO) throws URISyntaxException {
        log.debug("REST request to update Stock : {}", stockDTO);
        if (stockDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StockDTO result = stockService.save(stockDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stockDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /stocks} : get all the stocks.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stocks in body.
     */
    @GetMapping("/stocks")
    public ResponseEntity<List<StockDTO>> getAllStocks(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("item-is-null".equals(filter)) {
            log.debug("REST request to get all Stocks where item is null");
            return new ResponseEntity<>(stockService.findAllWhereItemIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of Stocks");
        Page<StockDTO> page = stockService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /stocks/:id} : get the "id" stock.
     *
     * @param id the id of the stockDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stockDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stocks/{id}")
    public ResponseEntity<StockDTO> getStock(@PathVariable String id) {
        log.debug("REST request to get Stock : {}", id);
        Optional<StockDTO> stockDTO = stockService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stockDTO);
    }

    /**
     * {@code DELETE  /stocks/:id} : delete the "id" stock.
     *
     * @param id the id of the stockDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stocks/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable String id) {
        log.debug("REST request to delete Stock : {}", id);
        stockService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}

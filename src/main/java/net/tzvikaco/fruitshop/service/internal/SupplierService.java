package net.tzvikaco.fruitshop.service.internal;

import net.tzvikaco.fruitshop.domain.internal.Supplier;
import net.tzvikaco.fruitshop.repository.internal.SupplierRepository;
import net.tzvikaco.fruitshop.service.dto.internal.SupplierDTO;
import net.tzvikaco.fruitshop.service.mapper.internal.SupplierMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Supplier}.
 */
@Service
public class SupplierService {

    private final Logger log = LoggerFactory.getLogger(SupplierService.class);

    private final SupplierRepository supplierRepository;

    private final SupplierMapper supplierMapper;

    public SupplierService(SupplierRepository supplierRepository, SupplierMapper supplierMapper) {
        this.supplierRepository = supplierRepository;
        this.supplierMapper = supplierMapper;
    }

    /**
     * Save a supplier.
     *
     * @param supplierDTO the entity to save.
     * @return the persisted entity.
     */
    public SupplierDTO save(SupplierDTO supplierDTO) {
        log.debug("Request to save supplier : {}", supplierDTO);
        Supplier supplier = supplierMapper.toEntity(supplierDTO);
        supplier = supplierRepository.save(supplier);
        return supplierMapper.toDto(supplier);
    }

    /**
     * Get all the Suppliers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<SupplierDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Suppliers");
        return supplierRepository.findAll(pageable)
            .map(supplierMapper::toDto);
    }


    /**
     * Get one Supplier by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<SupplierDTO> findOne(String id) {
        log.debug("Request to get Supplier : {}", id);
        return supplierRepository.findById(id)
            .map(supplierMapper::toDto);
    }

    /**
     * Delete the Supplier by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Supplier : {}", id);
        supplierRepository.deleteById(id);
    }
}

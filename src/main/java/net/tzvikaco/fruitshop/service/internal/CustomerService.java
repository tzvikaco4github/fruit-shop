package net.tzvikaco.fruitshop.service.internal;

import net.tzvikaco.fruitshop.domain.Contact;
import net.tzvikaco.fruitshop.domain.internal.Customer;
import net.tzvikaco.fruitshop.repository.internal.CustomerRepository;
import net.tzvikaco.fruitshop.service.dto.internal.CustomerDTO;
import net.tzvikaco.fruitshop.service.mapper.internal.CustomerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Customer}.
 */
@Service
public class CustomerService {

    private final Logger log = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    /**
     * Save a customer.
     *
     * @param customerDTO the entity to save.
     * @return the persisted entity.
     */
    public CustomerDTO save(CustomerDTO customerDTO) {
        log.debug("Request to save Contact : {}", customerDTO);
        Customer customer = customerMapper.toEntity(customerDTO);
        customer = customerRepository.save(customer);
        return customerMapper.toDto(customer);
    }

    /**
     * Get all the customers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<CustomerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Customers");
        return customerRepository.findAll(pageable)
            .map(customerMapper::toDto);
    }


    /**
     * Get one customer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<CustomerDTO> findOne(String id) {
        log.debug("Request to get Customer : {}", id);
        return customerRepository.findById(id)
            .map(customerMapper::toDto);
    }

    /**
     * Delete the contact by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Contact : {}", id);
        customerRepository.deleteById(id);
    }
}

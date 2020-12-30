package net.tzvikaco.fruitshop.service.internal;

import net.tzvikaco.fruitshop.domain.Contact;
import net.tzvikaco.fruitshop.domain.internal.Customer;
import net.tzvikaco.fruitshop.repository.ContactRepository;
import net.tzvikaco.fruitshop.service.dto.ContactDTO;
import net.tzvikaco.fruitshop.service.dto.internal.CustomerDTO;
import net.tzvikaco.fruitshop.service.mapper.CustomerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Contact}.
 */
@Service
public class CustomerService {

    private final Logger log = LoggerFactory.getLogger(CustomerService.class);

    private final ContactRepository contactRepository;

    private final CustomerMapper customerMapper;

    public CustomerService(ContactRepository contactRepository, CustomerMapper customerMapper) {
        this.contactRepository = contactRepository;
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
        customer = contactRepository.save(customer);
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
        return contactRepository.findAll(pageable)
//            .stream().filter(contact -> contact instanceof Customer)
            .map(contact -> customerMapper.toDto((Customer) contact));
//            .map(customerMapper::toDto);
    }


    /**
     * Get one customer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<CustomerDTO> findOne(String id) {
        log.debug("Request to get Customer : {}", id);
        return contactRepository.findById(id)
            .filter(contact -> contact instanceof Customer)
            .map(contact -> customerMapper.toDto((Customer) contact));
    }

    /**
     * Delete the contact by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Contact : {}", id);
        contactRepository.deleteById(id);
    }
}

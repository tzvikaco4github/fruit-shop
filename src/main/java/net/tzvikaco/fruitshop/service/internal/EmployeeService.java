package net.tzvikaco.fruitshop.service.internal;

import net.tzvikaco.fruitshop.domain.Contact;
import net.tzvikaco.fruitshop.domain.internal.Employee;
import net.tzvikaco.fruitshop.repository.internal.EmployeeRepository;
import net.tzvikaco.fruitshop.service.dto.internal.EmployeeDTO;
import net.tzvikaco.fruitshop.service.mapper.internal.EmployeeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Employee}.
 */
@Service
public class EmployeeService {

    private final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    /**
     * Save a contact.
     *
     * @param employeeDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        log.debug("Request to save Employee : {}", employeeDTO);
        Employee employee = employeeMapper.toEntity(employeeDTO);
        employee = employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    /**
     * Get all the Employees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<EmployeeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Employees");
        return employeeRepository.findAll(pageable)
            .map(employeeMapper::toDto);
    }


    /**
     * Get one Employee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<EmployeeDTO> findOne(String id) {
        log.debug("Request to get Employee : {}", id);
        return employeeRepository.findById(id)
            .map(employeeMapper::toDto);
    }

    /**
     * Delete the Employee by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Employee : {}", id);
        employeeRepository.deleteById(id);
    }
}

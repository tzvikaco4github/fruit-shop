package net.tzvikaco.fruitshop.service.mapper.internal;


import net.tzvikaco.fruitshop.domain.internal.Customer;
import net.tzvikaco.fruitshop.domain.internal.Employee;
import net.tzvikaco.fruitshop.service.dto.ContactDTO;
import net.tzvikaco.fruitshop.service.dto.internal.CustomerDTO;
import net.tzvikaco.fruitshop.service.dto.internal.EmployeeDTO;
import net.tzvikaco.fruitshop.service.mapper.AddressMapper;
import net.tzvikaco.fruitshop.service.mapper.EntityMapper;
import net.tzvikaco.fruitshop.service.mapper.JobTitleMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Employee} and its DTO {@link EmployeeDTO}.
 */
@Mapper(componentModel = "spring", uses = {AddressMapper.class, JobTitleMapper.class})
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {

    @Mapping(source = "address.id", target = "addressId")
    @Mapping(source = "address.streetName", target = "addressStreetName")
    @Mapping(source = "jobTitle.id", target = "jobTitleId")
    @Mapping(source = "jobTitle.jobTitle", target = "jobTitleTitle")
    EmployeeDTO toDto(Employee employee);

    @Mapping(source = "addressId", target = "address")
    @Mapping(source = "jobTitleId", target = "jobTitle")
    Employee toEntity(EmployeeDTO employeeDTO);

    default Employee fromId(String id) {
        if (id == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.setId(id);
        return employee;
    }

}

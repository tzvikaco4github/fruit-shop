package net.tzvikaco.fruitshop.service.mapper;


import net.tzvikaco.fruitshop.domain.internal.Customer;
import net.tzvikaco.fruitshop.service.dto.ContactDTO;
import net.tzvikaco.fruitshop.service.dto.internal.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Customer} and its DTO {@link ContactDTO}.
 */
@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public interface CustomerMapper extends EntityMapper<CustomerDTO, Customer> {

    @Mapping(source = "address.id", target = "addressId")
    @Mapping(source = "address.streetName", target = "addressStreetName")
    CustomerDTO toDto(Customer customer);

    @Mapping(source = "addressId", target = "address")
    Customer toEntity(CustomerDTO customerDTO);

    default Customer fromId(String id) {
        if (id == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setId(id);
        return customer;
    }
}

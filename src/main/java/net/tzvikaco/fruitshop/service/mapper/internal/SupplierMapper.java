package net.tzvikaco.fruitshop.service.mapper.internal;


import net.tzvikaco.fruitshop.domain.internal.Customer;
import net.tzvikaco.fruitshop.domain.internal.Supplier;
import net.tzvikaco.fruitshop.service.dto.ContactDTO;
import net.tzvikaco.fruitshop.service.dto.internal.CustomerDTO;
import net.tzvikaco.fruitshop.service.dto.internal.SupplierDTO;
import net.tzvikaco.fruitshop.service.mapper.AddressMapper;
import net.tzvikaco.fruitshop.service.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


/**
 * Mapper for the entity {@link Customer} and its DTO {@link ContactDTO}.
 */
@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public interface SupplierMapper extends EntityMapper<SupplierDTO, Supplier> {

    @Mapping(source = "address.id", target = "addressId")
    @Mapping(source = "address.streetName", target = "addressStreetName")
    SupplierDTO toDto(Supplier customer);

    @Mapping(source = "addressId", target = "address")
    Supplier toEntity(SupplierDTO supplierDTO);

    default Supplier fromId(String id) {
        if (id == null) {
            return null;
        }
        Supplier supplier = new Supplier();
        supplier.setId(id);
        return supplier;
    }
}

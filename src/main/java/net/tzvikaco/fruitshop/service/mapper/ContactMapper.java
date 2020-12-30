package net.tzvikaco.fruitshop.service.mapper;


import net.tzvikaco.fruitshop.domain.*;
import net.tzvikaco.fruitshop.service.dto.ContactDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Contact} and its DTO {@link ContactDTO}.
 */
@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public interface ContactMapper extends EntityMapper<ContactDTO, Contact> {

    @Mapping(source = "address.id", target = "addressId")
    @Mapping(source = "address.streetName", target = "addressStreetName")
    ContactDTO toDto(Contact contact);

    @Mapping(source = "addressId", target = "address")
    Contact toEntity(ContactDTO contactDTO);

    default Contact fromId(String id) {
        if (id == null) {
            return null;
        }
        Contact contact = new Contact();
        contact.setId(id);
        return contact;
    }
}

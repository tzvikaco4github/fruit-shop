package net.tzvikaco.fruitshop.service.mapper;


import net.tzvikaco.fruitshop.domain.*;
import net.tzvikaco.fruitshop.service.dto.ItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Item} and its DTO {@link ItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {StockMapper.class})
public interface ItemMapper extends EntityMapper<ItemDTO, Item> {

    @Mapping(source = "stock.id", target = "stockId")
    ItemDTO toDto(Item item);

    @Mapping(source = "stockId", target = "stock")
    Item toEntity(ItemDTO itemDTO);

    default Item fromId(String id) {
        if (id == null) {
            return null;
        }
        Item item = new Item();
        item.setId(id);
        return item;
    }
}

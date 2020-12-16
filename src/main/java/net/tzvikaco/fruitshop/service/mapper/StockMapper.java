package net.tzvikaco.fruitshop.service.mapper;


import net.tzvikaco.fruitshop.domain.*;
import net.tzvikaco.fruitshop.service.dto.StockDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Stock} and its DTO {@link StockDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StockMapper extends EntityMapper<StockDTO, Stock> {


    @Mapping(target = "item", ignore = true)
    Stock toEntity(StockDTO stockDTO);

    default Stock fromId(String id) {
        if (id == null) {
            return null;
        }
        Stock stock = new Stock();
        stock.setId(id);
        return stock;
    }
}

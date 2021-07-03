package net.tzvikaco.fruitshop.service;

import net.tzvikaco.fruitshop.domain.enumeration.UnitType;
import net.tzvikaco.fruitshop.service.dto.ItemDTO;
import net.tzvikaco.fruitshop.service.extended.ItemExtendedService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ItemExtendedServiceIT {

    @Autowired
    private ItemExtendedService itemExtendedService;

    private static final UnitType[] VALUES = UnitType.values();

    public static UnitType randomUnitType()  {
        return VALUES[new Random().nextInt(VALUES.length)];
    }

    private ItemDTO createItemDTOWithNoStock() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setStockId(null);
        itemDTO.setName(RandomStringUtils.randomAlphabetic(6));
        itemDTO.setDescription(RandomStringUtils.randomAlphanumeric(25));
        itemDTO.setDescriptionLink("http://" + RandomStringUtils.randomAlphabetic(10).toLowerCase() + ".com");
        itemDTO.setImage(RandomStringUtils.randomAlphanumeric(2048));
        itemDTO.setUnitType(randomUnitType());
        return itemDTO;
    }

    @Test
    void whenSaveWithNoStock_thenCreateZeroQtyStock() {
        ItemDTO itemDTO = createItemDTOWithNoStock();
        ItemDTO resultDTO = itemExtendedService.save(itemDTO);
        assertThat(resultDTO).usingRecursiveComparison()
            .ignoringFields("stockId", "id")
            .isEqualTo(itemDTO);
        assertThat(resultDTO.getStockId()).isNotNull();
    }
}

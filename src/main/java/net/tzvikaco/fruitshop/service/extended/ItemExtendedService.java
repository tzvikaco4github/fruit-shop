package net.tzvikaco.fruitshop.service.extended;

import net.tzvikaco.fruitshop.repository.ItemRepository;
import net.tzvikaco.fruitshop.service.ItemService;
import net.tzvikaco.fruitshop.service.StockService;
import net.tzvikaco.fruitshop.service.dto.ItemDTO;
import net.tzvikaco.fruitshop.service.dto.StockDTO;
import net.tzvikaco.fruitshop.service.mapper.ItemMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Primary
@Service
public class ItemExtendedService extends ItemService {

    private StockService stockService;

    public ItemExtendedService(ItemRepository itemRepository, ItemMapper itemMapper, StockService stockService) {
        super(itemRepository, itemMapper);
        this.stockService = stockService;
    }

    @Transactional
    @Override
    public ItemDTO save(ItemDTO itemDTO) {
        if (itemDTO.getStockId() == null) {
            StockDTO newStockDTO = new StockDTO();
            newStockDTO.setStockQuantity(0F);
            newStockDTO.setShopQuantity(0F);
            itemDTO.setStockId(stockService.save(newStockDTO).getId());
        }
        return super.save(itemDTO);
    }
}

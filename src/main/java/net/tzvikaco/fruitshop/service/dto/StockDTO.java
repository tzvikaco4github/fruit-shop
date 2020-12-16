package net.tzvikaco.fruitshop.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link net.tzvikaco.fruitshop.domain.Stock} entity.
 */
public class StockDTO implements Serializable {
    
    private String id;

    @NotNull
    @DecimalMin(value = "0")
    private Float shopQuantity;

    @NotNull
    @DecimalMin(value = "0")
    private Float stockQuantity;

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Float getShopQuantity() {
        return shopQuantity;
    }

    public void setShopQuantity(Float shopQuantity) {
        this.shopQuantity = shopQuantity;
    }

    public Float getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Float stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StockDTO)) {
            return false;
        }

        return id != null && id.equals(((StockDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StockDTO{" +
            "id=" + getId() +
            ", shopQuantity=" + getShopQuantity() +
            ", stockQuantity=" + getStockQuantity() +
            "}";
    }
}

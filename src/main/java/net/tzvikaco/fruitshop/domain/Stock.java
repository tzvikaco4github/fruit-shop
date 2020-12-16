package net.tzvikaco.fruitshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Stock.
 */
@Document(collection = "stock")
public class Stock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @DecimalMin(value = "0")
    @Field("shop_quantity")
    private Float shopQuantity;

    @NotNull
    @DecimalMin(value = "0")
    @Field("stock_quantity")
    private Float stockQuantity;

    @DBRef
    @Field("item")
    @com.fasterxml.jackson.annotation.JsonBackReference
    private Item item;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Float getShopQuantity() {
        return shopQuantity;
    }

    public Stock shopQuantity(Float shopQuantity) {
        this.shopQuantity = shopQuantity;
        return this;
    }

    public void setShopQuantity(Float shopQuantity) {
        this.shopQuantity = shopQuantity;
    }

    public Float getStockQuantity() {
        return stockQuantity;
    }

    public Stock stockQuantity(Float stockQuantity) {
        this.stockQuantity = stockQuantity;
        return this;
    }

    public void setStockQuantity(Float stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Item getItem() {
        return item;
    }

    public Stock item(Item item) {
        this.item = item;
        return this;
    }

    public void setItem(Item item) {
        this.item = item;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Stock)) {
            return false;
        }
        return id != null && id.equals(((Stock) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Stock{" +
            "id=" + getId() +
            ", shopQuantity=" + getShopQuantity() +
            ", stockQuantity=" + getStockQuantity() +
            "}";
    }
}

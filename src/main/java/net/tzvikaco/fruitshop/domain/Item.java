package net.tzvikaco.fruitshop.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;

import net.tzvikaco.fruitshop.domain.enumeration.UnitType;

/**
 * A Item.
 */
@Document(collection = "item")
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(min = 3, max = 20)
    @Field("name")
    private String name;

    @Size(min = 10, max = 200)
    @Field("description")
    private String description;

    @NotNull
    @Field("unit_type")
    private UnitType unitType;

    @Pattern(regexp = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")
    @Field("description_link")
    private String descriptionLink;

    @NotNull
    @Field("image")
    private String image;

    @DBRef
    @Field("stock")
    private Stock stock;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Item name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Item description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public Item unitType(UnitType unitType) {
        this.unitType = unitType;
        return this;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }

    public String getDescriptionLink() {
        return descriptionLink;
    }

    public Item descriptionLink(String descriptionLink) {
        this.descriptionLink = descriptionLink;
        return this;
    }

    public void setDescriptionLink(String descriptionLink) {
        this.descriptionLink = descriptionLink;
    }

    public String getImage() {
        return image;
    }

    public Item image(String image) {
        this.image = image;
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Stock getStock() {
        return stock;
    }

    public Item stock(Stock stock) {
        this.stock = stock;
        return this;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Item)) {
            return false;
        }
        return id != null && id.equals(((Item) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Item{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", unitType='" + getUnitType() + "'" +
            ", descriptionLink='" + getDescriptionLink() + "'" +
            ", image='" + getImage() + "'" +
            "}";
    }
}

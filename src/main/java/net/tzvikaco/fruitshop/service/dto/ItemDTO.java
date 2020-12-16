package net.tzvikaco.fruitshop.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import net.tzvikaco.fruitshop.domain.enumeration.UnitType;

/**
 * A DTO for the {@link net.tzvikaco.fruitshop.domain.Item} entity.
 */
public class ItemDTO implements Serializable {
    
    private String id;

    @NotNull
    @Size(min = 3, max = 20)
    private String name;

    @Size(min = 10, max = 200)
    private String description;

    @NotNull
    private UnitType unitType;

    @Pattern(regexp = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")
    private String descriptionLink;

    @NotNull
    private String image;

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }

    public String getDescriptionLink() {
        return descriptionLink;
    }

    public void setDescriptionLink(String descriptionLink) {
        this.descriptionLink = descriptionLink;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemDTO)) {
            return false;
        }

        return id != null && id.equals(((ItemDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ItemDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", unitType='" + getUnitType() + "'" +
            ", descriptionLink='" + getDescriptionLink() + "'" +
            ", image='" + getImage() + "'" +
            "}";
    }
}

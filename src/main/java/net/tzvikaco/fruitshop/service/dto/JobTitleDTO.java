package net.tzvikaco.fruitshop.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link net.tzvikaco.fruitshop.domain.JobTitle} entity.
 */
public class JobTitleDTO implements Serializable {
    
    private String id;

    @NotNull
    @Size(min = 4)
    private String name;

    @NotNull
    @Size(min = 10)
    private String jobDescription;

    
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

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobTitleDTO)) {
            return false;
        }

        return id != null && id.equals(((JobTitleDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobTitleDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", jobDescription='" + getJobDescription() + "'" +
            "}";
    }
}

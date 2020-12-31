package net.tzvikaco.fruitshop.domain.internal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import net.tzvikaco.fruitshop.domain.JobTitle;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "employee")
public class Employee extends Person {

    @DBRef
    @Field("jobTitle")
    @JsonIgnoreProperties(value = "employees", allowSetters = true)
    private JobTitle jobTitle;

    public JobTitle getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(JobTitle jobTitle) {
        this.jobTitle = jobTitle;
    }
}
